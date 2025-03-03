/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.compileflow.engine.process.preruntime.compiler.impl;

import com.alibaba.compileflow.engine.common.CompileFlowException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yusu
 */
public class FlowClassLoader extends URLClassLoader {

    private static volatile FlowClassLoader instance = null;

    public FlowClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static FlowClassLoader getInstance() {
        if (instance == null) {
            synchronized (FlowClassLoader.class) {
                if (instance == null) {
                    try {
                        URL url = new URL("file:///" + CompileConstants.FLOW_COMPILE_CLASS_DIR);
                        instance = new FlowClassLoader(new URL[] {url},
                            FlowClassLoader.class.getClassLoader());
                    } catch (MalformedURLException e) {
                        throw new CompileFlowException(e);
                    }
                }
            }
        }
        return instance;
    }

    public void clearCache() {
        instance = null;
    }

    public Class<?> defineClass(String name, byte[] classBytes) {
        return defineClass(name, classBytes, 0, classBytes.length);
    }

}
