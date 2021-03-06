/**
 * Reactive rule engine
 * <p>
 * Copyright (c) 2015, UniCredit Business Integrated Solutions S.c.p.A
 * <p>
 * This project includes software developed by UniCredit Business Integrated Solutions S.c.p.A
 * <p>
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package eu.unicredit.dna.rre;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

public class RulesProcessorTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void targetShouldReceiveExpectedReaction() {
        final JavaTestKit probe = new JavaTestKit(system);

        final Props props = RulesProcessor.props(probe.getRef(), "targetShouldReceiveExpectedReaction");
        final TestActorRef<RulesProcessor> ref = TestActorRef.create(system, props, UUID.randomUUID().toString());

        ref.tell(new DemoMessage(), probe.getRef());
        probe.expectMsgClass(DemoReaction.class);

    }

    @Test
    public void actorShouldNotHandleUnknownMessages() {
        final JavaTestKit probe = new JavaTestKit(system);

        final Props props = RulesProcessor.props(probe.getRef(), "targetShouldReceiveExpectedReaction");
        final TestActorRef<RulesProcessor> ref = TestActorRef.create(system, props, UUID.randomUUID().toString());

        ref.tell(new Object(), probe.getRef());
        probe.expectNoMsg();

    }
}
