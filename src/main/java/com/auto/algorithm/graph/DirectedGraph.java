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

package com.auto.algorithm.graph;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2020-04-27
 * @Description: <p>拓扑排序</p>
 */
public class DirectedGraph<T> {


    @Data
    static class Vertex<T> {
        /**
         * 节点值
         */
        private T value;

        /**
         * 节点入度
         */
        private int inDegree;

        /**
         * 储存从该定点出发的边
         */
        private List<Edge<T>> edges;
    }

    @Data
    static class Edge<T> {

        /**
         * 终点边
         */
        private Vertex<T> endVertex;

        /**
         * 权重
         */
        private int cost;
    }


    private List<Vertex<T>> vertexList;

    private List<Edge<T>> edgeList;

    /**
     * 拓扑排序
     */
    public List<T> topologySort() throws Exception {

        int cnt = 0;

        List<T> sortedList = new ArrayList<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        // 将所有入度为0的节点入队
        for (Vertex<T> vertex : vertexList) {
            if (vertex.getInDegree() == 0) {
                queue.offer(vertex);
            }
        }

        // 如果没有入度为0的节点，说明出现循环依赖
        if (queue.isEmpty()) {
            throw new Exception("detected circle, no zero indegree node.");
        }

        while (!queue.isEmpty()) {
            Vertex<T> v = queue.poll();
            // 排序
            sortedList.add(v.getValue());
            cnt++;
            for (Edge<T> edge : v.getEdges()) {
                // 更新所有关联顶点的入度
                Vertex<T> endVertex = edge.getEndVertex();
                if (endVertex != null) {
                    endVertex.setInDegree(endVertex.getInDegree() - 1);
                    if (endVertex.getInDegree() == 0) {
                        queue.offer(endVertex);
                    }
                }
            }
        }


        if (cnt != vertexList.size()) {
            // 如果拓扑排序结束后数量不匹配，说明有环
            throw new Exception("detected circle!");
        }

        return sortedList;

    }

    public static void main(String[] args) throws Exception {
        DirectedGraph directedGraph = new DirectedGraph();
        List list = directedGraph.topologySort();
        System.out.printf(JSON.toJSONString(list));
    }

}
