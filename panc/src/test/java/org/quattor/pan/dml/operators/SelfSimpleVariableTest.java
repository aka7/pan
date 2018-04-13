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
 $Id: VariableTest.java 3550 2008-08-02 14:54:26Z loomis $
 */

package org.quattor.pan.dml.operators;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.quattor.pan.dml.AbstractOperationTestUtils;
import org.quattor.pan.dml.Operation;
import org.quattor.pan.dml.data.Undef;
import org.quattor.pan.exceptions.CompilerError;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.BuildContext;
import org.quattor.pan.ttemplate.CompileTimeContext;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.ttemplate.ReadOnlySelfHolder;

public class SelfSimpleVariableTest extends AbstractOperationTestUtils {

	@Test
	public void verifyCorrectType() throws SyntaxException {

		Operation op = Variable.getInstance(null, "SELF");

		assertTrue(op instanceof SelfSimpleVariable);
	}

	@Test(expected = SyntaxException.class)
	public void testInvalidSelfContext() throws SyntaxException {

		Operation op = Variable.getInstance(null, "SELF");

		op.checkInvalidSelfContext();
	}

	@Test(expected = EvaluationException.class)
	public void testNotInCompileTimeContext() {

		Context context = new CompileTimeContext();

		Operation op = Variable.getInstance(null, "SELF");

		op.execute(context);
	}

	@Test
	public void getOfFixedSelfValueOk() {

		Context context = new BuildContext();

		Operation op = Variable.getInstance(null, "SELF");

		context.initializeSelfHolder(new ReadOnlySelfHolder(Undef.VALUE));
		op.execute(context);
	}

	@Test(expected = CompilerError.class)
	public void testMissingSelfDefinition() {

		Context context = new BuildContext();

		Operation op = Variable.getInstance(null, "SELF");

		op.execute(context);
	}

}
