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
package org.apache.freemarker.generator.cli;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

/**
 * Run unit tests with local templates directory and configuration file.
 */
abstract class AbstractMainTest {

    protected static final boolean WRITE_TO_STDOUT = false;
    protected static final String TEST_TEMPLATES_DIRECTORY = "./src/app/templates";
    protected static final String TEST_CONFIG_FILE = "./src/app/config/freemarker-generator.properties";

    private static final String SPACE = " ";

    String execute(String commandLine) throws IOException {
        try (Writer writer = new StringWriter()) {
            final String[] args = buildFinalCommandLine(commandLine).split(SPACE);
            if (Main.execute(args, writer) == 0) {
                final String output = writer.toString();
                if (WRITE_TO_STDOUT) {
                    System.out.println(output);
                }
                return output;
            } else {
                throw new RuntimeException("Executing freemarker-cli failed: " + Arrays.toString(args));
            }
        }
    }

    private String buildFinalCommandLine(String commandLine) {
        return String.format("--config %s --template-dir %s %s", TEST_CONFIG_FILE, TEST_TEMPLATES_DIRECTORY, commandLine);
    }
}
