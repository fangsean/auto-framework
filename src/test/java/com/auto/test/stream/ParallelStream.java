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

package com.auto.test.stream;

import com.auto.algorithm.stack.Recursions;
import com.auto.algorithm.stack.StackInvoke;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.*;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2020-06-04
 * @Description: <p></p>
 */
public class ParallelStream<T> {

    /**
     * A tree node data model used for DFS traversal stream generators
     */
    public static class TreeNode<T> {
        private final T value;
        private final int level;
        private final TreeNode<T> parentNode;

        TreeNode(T value, int level, TreeNode<T> parentNode) {
            this.value = value;
            this.level = level;
            this.parentNode = parentNode;
        }

        /**
         * Value of the tree node
         */
        public T getValue() {
            return value;
        }

        /**
         * Level (depth) of the current node in the tree
         */
        public int getLevel() {
            return level;
        }

        /**
         * Return the direct parent of the current node.
         */
        public TreeNode<T> getParentNode() {
            return parentNode;
        }

        /**
         * Return a stream that contains all parent (ancestor) nodes back to the root, not include the current node.
         */
        public Stream<TreeNode<T>> getParentStream() {
//            if (this.getParentNode() == null) {
            return Stream.empty();
//            } else {
//                return Stream.iterate(this.getParentNode(), Objects::nonNull, TreeNode::getParentNode);
//            }
        }

        /**
         * Return a stream that contains nodes in the path from the root, including the current node.
         * This method is less performance, if you don't care about top-down order, use {@link #getParentStream()} instead.
         */
        public Stream<TreeNode<T>> getPath() {
            return Stream.concat(
                    // parent nodes in reverse order
                    StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(
                                    getParentStream()
                                            .collect(Collectors.toCollection(LinkedList::new))
                                            .descendingIterator(),
                                    Spliterator.ORDERED
                            ),
                            false
                    ),
                    // followed by the current node
                    Stream.of(this)
            );
        }
    }

    /**
     * Stream friendly immutable linked stack for DFS tree traversal
     * Instances should be treat immutable. DO NOT reassign its fields, use constructor only.
     *
     * @param <T>
     */
    private static class TreeDfsStackHead<T> {
        TreeNode<T> node;
        Iterator<T> siblingIterator; // An iterator from the children stream to find next sibling node
        Stream<T> stream; // Keep track of the original children stream for clean up
        TreeDfsStackHead<T> previousHead;

        TreeDfsStackHead(TreeNode<T> node, Iterator<T> siblingIterator, Stream<T> steram, TreeDfsStackHead<T> previousHead) {
            this.node = node;
            this.stream = steram;
            this.siblingIterator = siblingIterator;
            this.previousHead = previousHead;
        }
    }


    /**
     * Return a {@link Stream} that retrieve {@link TreeNode} in a tree data structure in Depth-First-Search order. A {@link TreeNode} contains
     * the value of the node, depth (level) of the node in the tree and the parent node. It also provide convenient methods to find out all the
     * ancestor nodes and the path from the root node to the current node.
     *
     * @param root              root node of the tree
     * @param childrenGenerator lambda function to return children stream for a given node
     */
//    public static <T> Stream<TreeNode<T>> dfsTreeNodeStream(T root, Function<TreeNode<T>, Stream<T>> childrenGenerator) {
//        // TreeDfsStackHead object should be treated immutable in this method, DO NOT reassign its fields, use constructor only
//        return Stream.iterate(
//                new TreeDfsStackHead<>(new TreeNode<>(root, 1, null), Collections.emptyIterator(), Stream.empty(), null),
//                Objects::nonNull,
//                stack -> {
//
//
//                    final Stream<T> children = childrenGenerator.apply(stack.node);
//                    if (children != null) {
//                        final Iterator<T> cit = children.iterator();
//                        if (cit.hasNext()) {
//                            // has children, push first child to stack
//                            return new TreeDfsStackHead<>(new TreeNode<>(cit.next(), stack.node.level + 1, stack.node), cit, children, stack);
//                        }
//                    }
//                    // leaf, backtrack to find closest node in stack that has sibling or until stack is empty
//                    final Optional<TreeDfsStackHead<T>> nextHeadHasSibling = Stream.iterate(
//                            stack,
//                            Objects::nonNull,  // stop at root
//                            head -> {
//                                if (head.siblingIterator.hasNext()) {
//                                    return head;
//                                } else {
//                                    // pop stack
//                                    head.stream.close();
//                                    return head.previousHead;
//                                }
//                            }
//                    )
//                            .dropWhile(s -> !s.siblingIterator.hasNext())
//                            .findFirst();
//                    // if found, get the next sibling
//                    return nextHeadHasSibling
//                            .map(s -> new TreeDfsStackHead<>(new TreeNode<>(s.siblingIterator.next(), s.node.level, s.node.parentNode), s.siblingIterator, s.stream, s.previousHead))
//                            .orElse(null);  // if not found, finish travelling, null value stops the iteration
//                })
//                .map(it -> it.node);
//    }
    @Test
    public void test() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        long start = System.currentTimeMillis();
        List<Integer> collect = numbers.stream().map(num -> {
            try {
                Thread.sleep(12L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num);
            return num;
        }).collect(Collectors.toList());

        long end = System.currentTimeMillis();

        System.out.println(String.format("======================%s !", end - start));
    }


    public static <T> Stream<T> dfsTreeStream(T root, Function<T, Stream<T>> childrenGenerator) {
        return Stream.concat(Stream.of(root),
                childrenGenerator.apply(root).flatMap(child -> dfsTreeStream(child, childrenGenerator)));
    }

//    public static <T> Stream<T> dfsTreeStreamByCollection(T root, Function<T, Collection<T>> childrenGenerator) {
//        return dfsTreeStream(root, it -> Optional.ofNullable(childrenGenerator.apply(it)).orElse(List.of()).stream());
//    }

    @Test
    public void test2() {

        TreeNode<String> root = new TreeNode("value", 1, null);

//        final List<TreeNode> nodes = dfsTreeStream(root, it -> Optional.ofNullable(it.getChildren()).orElse(List.of()).stream())
//                .filter(node -> node.getValue() > 20)
//                .collect(Collectors.toList());


    }


    @Test
    public void main() throws IOException {

        //Creating Streams using user/programmatically specified elements
        Stream<String> Userstream = Stream.of("Creating", "Streams", "from", "Specific", "elements");
        Userstream.forEach(p -> System.out.println(p));


        //Creating Streams using array of objects
        Stream<String> ArrayStream = Stream.of(new String[]{"Stream", "from", "an", "array", "of", "objects"});
        ArrayStream.forEach(p -> System.out.println(p));


        //Creating Streams from an array
        String[] StringArray = new String[]{"We", "can", "convert", "an", "array", "to", "a", "Stream", "using", "Arrays", "as", "well"};
        Stream<String> StringStream = Arrays.stream(StringArray);
        StringStream.forEach(p -> System.out.println(p));

        //Creating Streams from Collection
        List<Double> myCollection = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            myCollection.add(Math.random());
        }
        //sequential stream
        Stream<Double> sequentialStream = myCollection.stream();
        sequentialStream.forEach(p -> System.out.println(p));

        //parallel stream
        Stream<Double> parallelStream = myCollection.parallelStream();
        parallelStream.forEach(p -> System.out.println(p));


        //Stream from Hashmap
        Map<String, Integer> mapData = new HashMap<>();
        mapData.put("This", 1900);
        mapData.put("is", 2000);
        mapData.put("HashMap", 2100);

        mapData.entrySet()
                .stream()
                .forEach(p -> System.out.println(p));

        mapData.keySet()
                .stream()
                .forEach(p -> System.out.println(p));

        //primitive streams
        IntStream.range(1, 4)
                .forEach(p -> System.out.println(p));

        LongStream.rangeClosed(1, 4)
                .forEach(p -> System.out.println(p));

        DoubleStream.of(1.0, 2.0, 3.0, 4.0)
                .forEach(p -> System.out.println(p));

        //Infinite Streams using generate()
        Stream<Double> sequentialDoubleStream = Stream.generate(Math::random);

        Stream<Integer> sequentialIntegerStream = Stream.generate(new AtomicInteger()::getAndIncrement);

        //Infinite Streams using iterate()
        Stream<Integer> sequentialIntegerStream1 = Stream.iterate(Integer.MIN_VALUE, i -> i++);

        Stream<BigInteger> sequentialBigIntegerStream = Stream.iterate(BigInteger.ZERO, i -> i.add(BigInteger.TEN));

        //Streams from File
        Stream<String> streamOfStrings = Files.lines(Paths.get("Apology_by_Plato.txt"));
        Stream<String> streamWithCharset = Files.lines(Paths.get("Apology_by_Plato.txt"), Charset.forName("UTF-8"));


    }


    public static Recursions<Long> factorialTailRecursion(final long factorial, final long number) {
        if (number == 1)
            return StackInvoke.call(factorial);
        else
            return factorialTailRecursion(factorial + number, number - 1);
    }

    @Test
    public void test3() {
        List<String> strings = Arrays.asList("Creating", "Streams", "from", "Specific", "elements");
        Stream.iterate(strings, t -> t)
                .limit(1)
                .forEach(System.out::println);

        System.out.println("=======");

        List<String> strings1 = Stream.iterate(strings, t -> t)
                .findFirst()
                .get();

        System.out.println(strings);

    }


}
