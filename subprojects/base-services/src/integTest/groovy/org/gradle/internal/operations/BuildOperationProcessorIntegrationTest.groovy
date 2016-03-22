/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.internal.operations

import groovy.transform.NotYetImplemented
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.util.Matchers


class BuildOperationProcessorIntegrationTest extends AbstractIntegrationSpec {

    @NotYetImplemented
    def "produces sensible error when there are failures both enqueueing and running operations" () {
        buildFile << """
            import org.gradle.internal.operations.BuildOperationProcessor
            import org.gradle.internal.operations.RunnableBuildOperation

            task causeErrors {
                doLast {
                    def buildOperationProcessor = services.get(BuildOperationProcessor)
                    buildOperationProcessor.run { queue ->
                        queue.add(new TestOperation())
                        queue.add(new TestOperation())
                        throw new Exception("queue failure")
                    }
                }
            }

            class TestOperation implements RunnableBuildOperation {
                @Override
                public String getDescription() {
                    return "test operation"
                }

                @Override
                public void run() {
                    throw new Exception("operation failure")
                }
            }
        """

        when:
        fails("causeErrors")

        then:
        failure.assertHasCause("There was a failure while populating the build operation queue:")
        failure.assertThatCause(Matchers.containsText("Multiple build operations failed"));
    }
}
