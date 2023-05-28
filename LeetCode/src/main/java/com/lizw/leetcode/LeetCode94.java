package com.lizw.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的中序遍历
 */
public class LeetCode94 {
    public static void main(String[] args) {
        // 输入：root = [1,null,2,3]
        // 输出：[1,3,2]
        TreeNode root = new TreeNode(1, null, new TreeNode(2, new TreeNode(3, null, null), null));
        List<Integer> results = inorderTraversal(root);
        System.out.println(results);
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        // 定义结果集合
        List<Integer> results = new ArrayList<>();

        if (root == null) {
            return results;
        }

        nextNode(root, results);

        return results;
    }

    private static void nextNode(TreeNode node, List<Integer> results) {
        // 1、是否有左节点，有则遍历左节点
        // 2、如果没有左节点，则输出自己
        // 3、判断是否有右节点，有则遍历右节点

        if (node.left != null) {
            nextNode(node.left, results);
        }

        results.add(node.val);

        if (node.right != null) {
            nextNode(node.right, results);
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}