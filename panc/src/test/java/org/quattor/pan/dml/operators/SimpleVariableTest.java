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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/dml/operators/VariableTest.java $
 $Id: VariableTest.java 2659 2008-01-07 14:48:07Z loomis $
 */

package org.quattor.pan.dml.operators;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quattor.pan.dml.AbstractOperationTestUtils;
import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Element;
import org.quattor.pan.dml.data.HashResource;
import org.quattor.pan.dml.data.ListResource;
import org.quattor.pan.dml.data.LongProperty;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.InvalidTermException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.CompileTimeContext;
import org.quattor.pan.ttemplate.Context;

public class SimpleVariableTest extends AbstractOperationTestUtils {

	@Test
	public void testSimpleValue() throws SyntaxException {

		Context context = new CompileTimeContext();

		long va = 1L;
		context.setLocalVariable("x", LongProperty.getInstance(va));

		// Execute the operations.
		Operation op = new SimpleVariable(null, "x", false);
		Element r1 = op.execute(context);

		// Pull the value off the stack.
		assertTrue(r1 instanceof LongProperty);
		LongProperty s1 = (LongProperty) r1;
		long sresult = s1.getValue().longValue();
		assertTrue(va == sresult);
	}

	@Test
	public void testHashValue() throws SyntaxException, InvalidTermException {

		Context context = new CompileTimeContext();

		HashResource dict = new HashResource();
		context.setLocalVariable("x", dict);

		// Execute the operations.
		Operation op = new SimpleVariable(null, "x", false);
		Element r1 = op.execute(context);

		// Pull the value off the stack.
		assertTrue(r1 instanceof HashResource);
	}

	@Test
	public void testListValue() throws SyntaxException, InvalidTermException {

		Context context = new CompileTimeContext();

		ListResource list = new ListResource();
		context.setLocalVariable("x", list);

		// Execute the operations.
		Operation op = new SimpleVariable(null, "x", false);
		Element r1 = op.execute(context);

		// Pull the value off the stack.
		assertTrue(r1 instanceof ListResource);

	}

	@Test
	public void testUndefinedLookup() throws SyntaxException {
		runDml(new SimpleVariable(null, "x", true));
	}

	@Test(expected = EvaluationException.class)
	public void testUndefinedVariable() throws SyntaxException {
		runDml(new SimpleVariable(null, "x", false));
	}

}
