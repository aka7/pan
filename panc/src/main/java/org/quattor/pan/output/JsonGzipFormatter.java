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

 $HeadURL: https://svn.lal.in2p3.fr/LCG/QWG/panc/trunk/src/org/quattor/pan/output/PanFormatter.java $
 $Id: PanFormatter.java 3597 2008-08-17 09:08:57Z loomis $
 */

package org.quattor.pan.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

public class JsonGzipFormatter extends JsonFormatter {

	private static final JsonGzipFormatter instance = new JsonGzipFormatter();

	private JsonGzipFormatter() {
		super("json.gz", "json.gz");
	}

	public static JsonGzipFormatter getInstance() {
		return instance;
	}

	@Override
	protected PrintWriter getPrintWriter(File file) throws Exception {
		OutputStream os = new FileOutputStream(file);
		OutputStream gzip = new GZIPOutputStream(os);
		return new PrintWriter(gzip);
	}

}
