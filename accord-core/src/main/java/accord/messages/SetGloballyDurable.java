/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package accord.messages;

import accord.local.PreLoadContext;
import accord.local.DurableBefore;
import accord.local.SafeCommandStore;

import static accord.messages.SimpleReply.Ok;

public class SetGloballyDurable extends AbstractEpochRequest<SimpleReply>
        implements Request, PreLoadContext
{
    public final DurableBefore durableBefore;

    public SetGloballyDurable(DurableBefore durableBefore)
    {
        super(null);
        this.durableBefore = durableBefore;
    }

    @Override
    public void process()
    {
        node.mapReduceConsumeAllLocal(this, this);
    }

    @Override
    public SimpleReply apply(SafeCommandStore safeStore)
    {
        DurableBefore cur = safeStore.commandStore().durableBefore();
        DurableBefore upd = DurableBefore.merge(durableBefore, cur);
        safeStore.commandStore().setDurableBefore(upd);
        return Ok;
    }

    @Override
    public SimpleReply reduce(SimpleReply r1, SimpleReply r2)
    {
        return r1.merge(r2);
    }

    @Override
    public void accept(SimpleReply ok, Throwable failure)
    {
    }

    @Override
    public String toString()
    {
        return "SetGloballyDurable{" + durableBefore + '}';
    }

    @Override
    public MessageType type()
    {
        return MessageType.SET_GLOBALLY_DURABLE_REQ;
    }
}
