/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.auto.algorithm.stack;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2020-06-02
 * @Description: <p></p>
 */
public class StackInvoke {

    public static <T> Recursions<T> call(final Recursions<T> nextFrame) {
        return nextFrame;
    }

    public static <T> Recursions<T> done(T value) {
        return new Recursions() {
            @Override
            public Recursions<T> apply() {
                throw new Error("Recursion has ended, illegally apply method.");
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public T getResult() {
                return value;
            }
        };
    }

}
