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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/statement/BindStatementTest.java $
 $Id: BindStatementTest.java 3550 2008-08-02 14:54:26Z loomis $
 */

package org.quattor.pan.statement;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.quattor.pan.exceptions.SyntaxException;
import org.quattor.pan.ttemplate.Context;
import org.quattor.pan.utils.Path;

public class BindStatementTest extends StatementTestUtils {

	@Test
	public void simpleBindDefinition() throws Exception {

		Path path = new Path("/result");

		Context context = setupTemplateToRun2("bs1", "bind '" + path
				+ "' = boolean; '" + path + "' = true;", true);

		assertTrue(context.getBindings().containsKey(path));
	}

	@Test
	public void simpleDefaultBindDefinition() throws Exception {

		Path path = new Path("/result");

		Context context = setupTemplateToRun2("bs2", "bind '" + path
				+ "' = boolean = false; ", false);

		assertTrue(context.getBindings().containsKey(path));
	}

	@Test
	public void nestedDefaultBindDefinition() throws Exception {

		Path path = new Path("/result");
		Path cpath = new Path("/result/alpha");

		// Needs to be run through the default setting and validation
		// for the complete test.
		Context context = setupTemplateToRun2("bs3", "bind '" + path
				+ "' = extensible {\n'alpha' : string = 'a'\n} = nlist();",
				true);

		assertTrue(context.getBindings().containsKey(path));
		assertTrue(context.getElement(cpath) != null);
	}

	@Test(expected = SyntaxException.class)
	public void illegalExternalBind() throws Exception {
		Path path = new Path("external:/result");
		runExpectingException("bs4", "bind '" + path + "' = string;");
	}

	@Test(expected = SyntaxException.class)
	public void illegalRelativeBind() throws Exception {
		Path path = new Path("result");
		runExpectingException("bs5", "bind '" + path + "' = string;");
	}

}
