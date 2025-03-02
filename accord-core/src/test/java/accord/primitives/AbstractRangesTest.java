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

package accord.primitives;

import accord.impl.IntKey;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractRangesTest
{
    @Test
    public void testToString()
    {
        Ranges ranges = new Ranges(
                range("first", 0, 10),
                range("first", 10, 20),
                range("second", 20, 30),
                range("third", 30, 40)
        );
        assertThat(ranges.toString()).isEqualTo("[first:[[0,10), [10,20)], second:[[20,30)], third:[[30,40)]]");
    }

    private static Range range(Object prefix, int start, int end)
    {
        return Range.range(new PrefixKey(start, prefix), new PrefixKey(end, prefix), true , false);
    }

    private static class Ranges extends AbstractRanges
    {
        Ranges(@Nonnull Range... ranges)
        {
            super(ranges);
        }

        @Override
        public Routables<?> slice(accord.primitives.Ranges ranges)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public Routables<Range> slice(accord.primitives.Ranges ranges, Slice slice)
        {
            throw new UnsupportedOperationException();
        }
    }

    private static class PrefixKey extends IntKey.Routing
    {
        private final Object prefix;

        public PrefixKey(int key, Object prefix)
        {
            super(key);
            this.prefix = prefix;
        }

        @Override
        public Object prefix()
        {
            return prefix;
        }
    }
}