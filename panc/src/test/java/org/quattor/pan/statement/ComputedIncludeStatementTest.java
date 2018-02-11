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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/statement/ComputedIncludeStatementTest.java $
 $Id: ComputedIncludeStatementTest.java 3550 2008-08-02 14:54:26Z loomis $
 */

package org.quattor.pan.statement;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quattor.pan.exceptions.EvaluationException;
import org.quattor.pan.ttemplate.Context;

public class ComputedIncludeStatementTest extends StatementTestUtils {

	@Test
	public void legalComputedInclude() throws Exception {

		String fullName = "statement_test_cis1";

		Context context = setupTemplateToRun3("cis1", "include {'" + fullName
				+ "_include'};", "variable x = 1;", false);

		assertTrue(context.getGlobalVariable("x") != null);
	}

	@Test
	public void nullComputedInclude() throws Exception {

		Context context = setupTemplateToRun3("cis2", "include {null};",
				"variable x = 1;", false);

		assertTrue(context.getGlobalVariable("x") == null);
	}

	@Test
	public void undefComputedInclude() throws Exception {

		Context context = setupTemplateToRun3("cis3", "include {undef};",
				"variable x = 1;", false);

		assertTrue(context.getGlobalVariable("x") == null);
	}

	@Test(expected = EvaluationException.class)
	public void illegalNonstringComputedInclude() throws Exception {
		setupTemplateToRun2("cis4", "variable X=1; include {X};", false);
	}

	@Test(expected = EvaluationException.class)
	public void illegalAbsoluteComputedInclude() throws Exception {
		setupTemplateToRun2("cis5",
				"variable X='/invalid/absolute/name'; include {X};", false);
	}

}
