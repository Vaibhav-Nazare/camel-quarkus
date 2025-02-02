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
package org.apache.camel.quarkus.k.runtime;

import java.util.List;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import org.apache.camel.main.MainListener;
import org.apache.camel.main.RoutesCollector;
import org.apache.camel.quarkus.k.core.Runtime;
import org.apache.camel.quarkus.main.CamelMain;
import org.apache.camel.spi.ModelReifierFactory;
import org.slf4j.LoggerFactory;

@Recorder
public class ApplicationRecorder {

    public void version(String version) {
        LoggerFactory.getLogger(Runtime.class).info("Apache Camel K Runtime {}", version);
    }

    public RuntimeValue<MainListener> createMainListener(List<Runtime.Listener> listeners) {
        return new RuntimeValue<>(new Application.ListenerAdapter(listeners));
    }

    public void publishRuntime(RuntimeValue<CamelMain> main, BeanContainer container) {
        container.beanInstance(ApplicationProducers.class).setRuntime(new Application.Runtime(main.getValue()));
    }

    public RuntimeValue<RoutesCollector> createRoutesCollector() {
        return new RuntimeValue<>(new Application.NoRoutesCollector());
    }

    public RuntimeValue<ModelReifierFactory> modelReifierFactory() {
        return new RuntimeValue<>(new ApplicationModelReifierFactory());
    }
}
