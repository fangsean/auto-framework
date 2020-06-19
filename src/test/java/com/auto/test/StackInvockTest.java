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

package com.auto.test;

import com.auto.algorithm.stack.Recursions;
import com.auto.algorithm.stack.StackInvoke;
import org.junit.Test;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2020-06-02
 * @Description: <p></p>
 */
public class StackInvockTest {

    /**
     * 阶乘计算 -- 递归解决
     *
     * @param number 当前阶乘需要计算的数值
     * @return number!
     */
    public static double factorialRecursion(final double number) {
        if (number == 1) return number;
        else return number + factorialRecursion(number - 1);
    }


    /**
     * 阶乘计算 -- 使用尾递归接口完成
     *
     * @param factorial 当前递归栈的结果值
     * @param number    下一个递归需要计算的值
     * @return 尾递归接口, 调用invoke启动及早求值获得结果
     */
    public static Recursions<Long> factorialRecursion(final long factorial, final long number) {
        if (number == 1)
            return StackInvoke.done(factorial);
        else
            return StackInvoke.call(() -> factorialRecursion(factorial + number, number - 1));
    }


    @Test
    public void testRec() {
        System.out.println(factorialRecursion(10_000_000));
    }

    @Test
    public void testTailRec() {
        System.out.println(factorialRecursion(1, 30_000_000_00L/*_000_00*/).invoke());
    }

}
