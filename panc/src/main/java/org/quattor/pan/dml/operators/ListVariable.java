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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/operators/Variable.java $
 $Id: Variable.java 3506 2008-07-30 18:09:38Z loomis $
 */

package org.quattor.pan.dml.operators;

import org.quattor.pan.dml.AbstractOperation;
import org.quattor.pan.dml.Operation;
import org.quattor.pan.ttemplate.SourceRange;

/**
 * Looks up and potentially dereferences a variable in the execution context.
 * 
 * @author loomis
 * 
 */
abstract public class ListVariable extends AbstractOperation {

	protected final String identifier;

	protected ListVariable(SourceRange sourceRange, String identifier,
			Operation... operations) {
		super(sourceRange, operations);
		assert (identifier != null);
		this.identifier = identifier;
	}

	public static ListVariable getInstance(SourceRange sourceRange,
			String identifier, Operation... operations) {
		return createSubclass(sourceRange, identifier, operations);
	}

	public static ListVariable getInstance(Variable v) {
		return createSubclass(v.getSourceRange(), v.identifier, v
				.getOperations());
	}

	private static ListVariable createSubclass(SourceRange sourceRange,
			String identifier, Operation... operations) {

		ListVariable result = null;

		if (operations == null || operations.length == 0) {
			if ("SELF".equals(identifier)) {
				result = new SelfSimpleListVariable(sourceRange);
			} else {
				result = new SimpleListVariable(sourceRange, identifier);
			}
		} else {
			if ("SELF".equals(identifier)) {
				result = new SelfNestedListVariable(sourceRange, operations);
			} else {
				result = new NestedListVariable(sourceRange, identifier,
						operations);
			}
		}

		return result;
	}

}
