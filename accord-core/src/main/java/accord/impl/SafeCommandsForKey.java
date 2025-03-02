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

package accord.impl;

import accord.api.Key;
import accord.impl.CommandTimeseries.CommandLoader;

public abstract class SafeCommandsForKey implements SafeState<CommandsForKey>
{
    private final Key key;

    public SafeCommandsForKey(Key key)
    {
        this.key = key;
    }

    protected abstract void set(CommandsForKey update);

    public Key key()
    {
        return key;
    }

    CommandsForKey update(CommandsForKey update)
    {
        set(update);
        return update;
    }

    public CommandsForKey initialize(CommandLoader<?> loader)
    {
        return update(new CommandsForKey(key, loader));
    }

    public static abstract class Update<U extends CommandsForKey.Update, D> extends CommandsForKeyGroupUpdater.Mutable<D> implements SafeState<U>
    {
        private final Key key;

        public Update(Key key, CommandLoader<D> loader)
        {
            super(loader);
            this.key = key;
        }

        public Key key()
        {
            return key;
        }
        public abstract void initialize();
    }
}
