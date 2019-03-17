package com.auto.algorithm.tree;

import java.util.*;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-16
 * @Description: <p></p>
 */
public class TrieTree {
    private HashSet<String> res = new HashSet<>();

    class Trie<T> {

        private TrieNode<T> root; //定义一个根节点

        public Trie() {
            root = new TrieNode();
        }

        //方法：增加节点
        public boolean insert(String data) {
            if (data == null) {     // 如果添加的是一个空数据，那增加失败
                return false;
            }

            // 将数据封装为节点，目的：节点有next可以处理关系
            TrieNode node = root;
            // 链表的关键就在于根节点
            for (char c : data.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            node.item = data;

            /*if (root == null) {  //如果根节点是空的，那么新添加的节点就是根节点。(第一次调用add方法时，根节点当然是空的了)
                root = newNode;
            } else {
                root.addNode(newNode);
            }*/
            return true;
        }

        public boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return node.item.equals(word);
        }

        // Returns if there is any word in the trie
        // that starts with the given prefix.
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (node.children[c - 'a'] == null) return false;
                node = node.children[c - 'a'];
            }
            return true;
        }

        class TrieNode<T> {
            static final int ALPHABET_SIZE = 64;
            TrieNode[] children = new TrieNode[ALPHABET_SIZE];
            public String item = "";

            TrieNode() {
                for (int i = 0; i < ALPHABET_SIZE; i++) {
                    children[i] = null;
                }
            }

        }

       /* class Node<T> {
            T value;
            *//**
         * next
         *//*
            Node<T> next;

            Node() {
            }

            Node(T value) {
                this.value = value;
            }

            public void addNode(Node newNode) {

                //下面这段用到了递归，需要反复理解
                if (this.next == null) {   // 递归的出口：如果当前节点之后没有节点，说明我可以在这个节点后面添加新节点
                    this.next = newNode;   //添加新节点
                } else {
                    this.next.addNode(newNode);  //向下继续判断，直到当前节点之后没有节点为止

                }
            }

        }*/
    }


    /**
     * @param board
     * @param words
     * @return
     */
    public List<String> searchWords(char[][] board, String[] words) {
        Trie<String> trie = new Trie<>();
        for (String word : words) {
            trie.insert(word);
        }

        int m = board.length;
        int n = board[0].length;

        boolean[][] visited = new boolean[m][m];

        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, visited, "", i, j, trie);
            }
        }

        return new ArrayList<String>(res);
    }

    public void dfs(char[][] board, boolean[][] visited, String str, int x, int y, Trie<String> trie) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return;
        if (visited[x][y]) return;
        str += (board[x][y]);
        if (!trie.startsWith(str.toString())) return;

        if (trie.search(str.toString())) {
            res.add(str.toString());
        }

        visited[x][y] = true;
        dfs(board, visited, str, x, y - 1, trie);
        dfs(board, visited, str, x, y + 1, trie);
        dfs(board, visited, str, x - 1, y, trie);
        dfs(board, visited, str, x + 1, y, trie);
        visited[x][y] = false;
    }

    public static void main(String[] args) {

        String[] words = new String[]{"oath", "pea", "eat", "rain"};
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
        };

        TrieTree trieTree = new TrieTree();
        List<String> res = trieTree.searchWords(board, words);
        System.out.println(Arrays.toString(res.toArray()));

    }


}
