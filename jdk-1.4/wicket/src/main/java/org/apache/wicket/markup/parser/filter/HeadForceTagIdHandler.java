/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.markup.parser.filter;

import java.text.ParseException;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupElement;
import org.apache.wicket.markup.WicketTag;
import org.apache.wicket.markup.parser.AbstractMarkupFilter;
import org.apache.wicket.util.string.AppendingStringBuffer;


/**
 * Handler that sets unique tag id for every inline script and style element in &lt;wicket:head&gt;,
 * unless the element already has one. <br/> This is needed to be able to detect multiple ajax
 * header contribution. Tags that are not inline (stript with src attribute set and link with href
 * attribute set) do not require id, because the detection is done by comparing URLs.
 * <p>
 * Tags with wicket:id are <strong>not processed</strong>. To setOutputWicketId(true) on attached
 * component is developer's responsibility. FIXME: Really? And if so, document properly
 * 
 * @author Matej Knopp
 */
public class HeadForceTagIdHandler extends AbstractMarkupFilter
{
	/** Common prefix for all id's generated by this filter */
	private final String headElementIdPrefix;

	/** Unique value per markup file */
	private int counter = 0;

	/** we are in wicket:head */
	private boolean inHead = false;

	/**
	 * Construct.
	 * 
	 * @param markupFileClass
	 *            Used to generated the a common prefix for the id
	 */
	public HeadForceTagIdHandler(final Class markupFileClass)
	{
		// generate the prefix from class name
		final AppendingStringBuffer buffer = new AppendingStringBuffer(markupFileClass.getName());
		for (int i = 0; i < buffer.getValue().length; ++i)
		{
			if (Character.isLetterOrDigit(buffer.getValue()[i]) == false)
			{
				buffer.getValue()[i] = '-';
			}
		}

		buffer.append("-");
		headElementIdPrefix = buffer.toString();
	}

	/**
	 * @see org.apache.wicket.markup.parser.IMarkupFilter#nextTag()
	 */
	public MarkupElement nextTag() throws ParseException
	{
		final ComponentTag tag = (ComponentTag)getParent().nextTag();

		if (tag != null)
		{
			// is it a <wicket:head> tag?
			if (tag instanceof WicketTag && ((WicketTag)tag).isHeadTag())
			{
				inHead = tag.isOpen();
			}
			// no, it's not. Are we in <wicket:head> ?
			else if (inHead == true)
			{
				// is the tag open and has empty wicket:id?
				if ((tag instanceof WicketTag == false) && (tag.getId() == null) &&
						(tag.isOpen() || tag.isOpenClose()) && needId(tag))
				{
					if (tag.getAttributes().get("id") == null)
					{
						tag.getAttributes().put("id", headElementIdPrefix + nextValue());
						tag.setModified(true);
					}
				}
			}
		}

		return tag;
	}

	/**
	 * 
	 * @param tag
	 * @return true, if id is needed
	 */
	private final boolean needId(final ComponentTag tag)
	{
		final String name = tag.getName().toLowerCase();
		if (name.equals("script") && tag.getAttributes().containsKey("src") == false)
		{
			return true;
		}
		else if (name.equals("style") && tag.getAttributes().containsKey("href") == false)
		{
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return The next value
	 */
	private final int nextValue()
	{
		return counter++;
	}
}
