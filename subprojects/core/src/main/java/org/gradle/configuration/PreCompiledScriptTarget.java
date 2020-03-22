/*
 * Copyright 2020 the original author or authors.
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

package org.gradle.configuration;

import groovy.lang.Script;
import org.gradle.api.internal.plugins.PluginManagerInternal;
import org.gradle.groovy.scripts.BasicScript;
import org.gradle.plugin.use.internal.PluginsAwareScript;

public class PreCompiledScriptTarget implements ScriptTarget {
    @Override
    public String getId() {
        return "dsl";
    }

    @Override
    public Class<? extends BasicScript> getScriptClass() {
        return PluginsAwareScript.class;
    }

    @Override
    public String getClasspathBlockName() {
        return "buildscript";
    }

    @Override
    public boolean getSupportsPluginsBlock() {
        return true;
    }

    @Override
    public boolean getSupportsPluginManagementBlock() {
        return false;
    }

    @Override
    public boolean getSupportsMethodInheritance() {
        return false;
    }

    @Override
    public void attachScript(Script script) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PluginManagerInternal getPluginManager() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addConfiguration(Runnable runnable, boolean deferrable) {
        throw new UnsupportedOperationException();
    }
}
