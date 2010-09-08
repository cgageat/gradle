/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.tasks.diagnostics.internal;

import org.gradle.api.Project;
import org.gradle.logging.StyledTextOutput;
import org.gradle.logging.internal.WriterBackedStyledTextOutput;

import java.io.*;

import static org.gradle.logging.StyledTextOutput.Style.*;

/**
 * <p>A basic {@link ProjectReportRenderer} which writes out a text report.
 */
public class TextProjectReportRenderer implements ProjectReportRenderer {
    public static final String SEPARATOR = "------------------------------------------------------------";
    private StyledTextOutput textOutput;
    private boolean close;

    public void setOutput(StyledTextOutput textOutput) {
        setWriter(textOutput, false);
    }

    public void setOutputFile(File file) throws IOException {
        cleanupWriter();
        setWriter(new WriterBackedStyledTextOutput(new BufferedWriter(new FileWriter(file))), true);
    }

    public void startProject(Project project) {
        textOutput.println().style(Header).println(SEPARATOR);
        if (project.getRootProject() == project) {
            textOutput.println("Root Project");
        } else {
            textOutput.formatln("Project %s", project.getPath());
        }
        textOutput.text(SEPARATOR).style(Normal).println();
    }

    public void completeProject(Project project) {
    }

    public void complete() throws IOException {
        cleanupWriter();
    }

    private void setWriter(StyledTextOutput styledTextOutput, boolean close) {
        this.textOutput = styledTextOutput;
        this.close = close;
    }

    private void cleanupWriter() throws IOException {
        try {
            if (textOutput != null && close) {
                ((Closeable) textOutput).close();
            }
        } finally {
            textOutput = null;
        }
    }

    protected StyledTextOutput getTextOutput() {
        return textOutput;
    }
}
