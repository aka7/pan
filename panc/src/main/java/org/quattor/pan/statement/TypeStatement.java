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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/statement/TypeStatement.java $
 $Id: TypeStatement.java 3602 2008-08-18 14:34:24Z loomis $
 */

package org.quattor.pan.statement;

import org.quattor.pan.dml.data.Element;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.SourceRange;
import org.quattor.pan.type.FullType;

/**
 * Associates a name with a FullType definition. A type can only be defined once
 * per machine profile.
 *
 * @author loomis
 *
 */
public class TypeStatement extends Statement {

	private final String name;

	private final FullType fullType;

	/**
	 * Creates a TypeStatement which associates a name to a FullType.
	 *
	 * @param sourceRange
	 *            source location of this statement
	 * @param name
	 *            String giving the name of this type
	 * @param fullType
	 *            type definition
	 */
	public TypeStatement(SourceRange sourceRange, String name, FullType fullType) {

		super(sourceRange);

		assert (name != null);
		assert (fullType != null);
		this.name = name;
		this.fullType = fullType;
	}

	/**
	 * Retrieve the name of the defined type. This information is used by
	 * Template for compile-time error checking.
	 *
	 * @return name of the defined type
	 */
	public String getName() {
		return name;
	}

	@Override
	public Element execute(Context context) {
		try {
			context.setFullType(name, fullType, context.getCurrentTemplate(),
					getSourceRange());
		} catch (EvaluationException ee) {
			throw ee.addExceptionInfo(getSourceRange(), context);
		}
        return null;
	}

	/**
	 * Return a reasonable string representation of this statement.
	 *
	 * @return String representation of this TypeStatement
	 */
	@Override
	public String toString() {
		return "TYPE: " + name + ", " + fullType;
	}

}
