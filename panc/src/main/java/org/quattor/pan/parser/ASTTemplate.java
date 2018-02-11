/*
 Copyright (c) 2006 Charles A. Loomis, Jr, Cedric Duprilot, and
 Centre National de la Recherche Scientifique (CNRS).

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/parser/ASTTemplate.java $
 $Id: ASTTemplate.java 998 2006-11-15 19:44:28Z loomis $
 */

package org.quattor.pan.parser;

import org.quattor.pan.ttemplate.Template.TemplateType;

public class ASTTemplate extends SimpleNode {

	private TemplateType type = TemplateType.ORDINARY;

	private String identifier = null;

	public ASTTemplate(int id) {
		super(id);
	}

	public ASTTemplate(PanParser p, int id) {
		super(p, id);
	}

	public void setTemplateType(int type) {

		switch (type) {
		case PanParserConstants.STRUCTURE:
			this.type = TemplateType.STRUCTURE;
			break;
		case PanParserConstants.OBJECT:
			this.type = TemplateType.OBJECT;
			break;
		case PanParserConstants.DECLARATION:
			this.type = TemplateType.DECLARATION;
			break;
		case PanParserConstants.UNIQUE:
			this.type = TemplateType.UNIQUE;
			break;
		default:
			this.type = TemplateType.ORDINARY;
			break;
		}
	}

	@Override
	public Object getSubtype() {
		return type;
	}

	public TemplateType getTemplateType() {
		return type;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		String s = type.toString() + " template";
		if (identifier != null) {
			s = s + "(" + identifier + ")";
		}
		return s;
	}

}
