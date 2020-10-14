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

import java.util.stream.Stream;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2020-06-02
 * @Description: <p>入栈递归算法调用通用包装类 避免max递归造成的StackOverflowError.</p>
 */
public interface RecursiveWrapper<T> {

    RecursiveWrapper<T> apply();

    default boolean isFinished() {
        return false;
    }

    default T getResult() {
        throw new Error("The recursion is not over yet, the result of the call is abnormal. ");
    }

    default T invoke() {
        return Stream
                .iterate(this, RecursiveWrapper::apply)
                .filter(RecursiveWrapper::isFinished)
                .findFirst()
                .orElse(this)
                .getResult();
    }

    static <T> RecursiveWrapper<T> call(final RecursiveWrapper<T> nextFrame) {
        return nextFrame;
    }

    static <T> RecursiveWrapper<T> done(T value) {
        return new RecursiveWrapper() {
            @Override
            public RecursiveWrapper<T> apply() {
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
