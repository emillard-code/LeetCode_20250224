package com.project.attempt;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        int[][] edges1 = new int[][]{{0,1},{1,2},{1,3},{3,4}};
        int[] amount1 = new int[]{-2,4,2,-4,6};

        System.out.println(mostProfitablePathInATree(edges1, amount1, 3));

        int[][] edges2 = new int[][]{{0,1}};
        int[] amount2 = new int[]{-7280,2350};

        System.out.println(mostProfitablePathInATree(edges2, amount2, 1));

    }

    public static int mostProfitablePathInATree(int[][] edges, int[] amount, int bob) {

        TreeNode root = new TreeNode(0);

        linkNodes(root, edges, amount);
        TreeNode bobStartingNode = bobNode(root, bob);

        return traverseTree(root, bobStartingNode, 0, amount);

    }

    public static int traverseTree(TreeNode aliceNode, TreeNode bobNode, int aliceValue, int[] amount) {

        int[] newAmount = new int[amount.length];

        for (int i = 0; i < amount.length; i++) {
            newAmount[i] = amount[i];
        }

        int newAliceValue = aliceValue;

        if (aliceNode == bobNode) {
            newAliceValue = newAliceValue + newAmount[aliceNode.val]/2;
        } else {
            newAliceValue = newAliceValue + newAmount[aliceNode.val];
        }

        newAmount[aliceNode.val] = 0;
        newAmount[bobNode.val] = 0;

        TreeNode nextBobNode;

        if (bobNode.root != null) {
            nextBobNode = bobNode.root;
        } else {
            nextBobNode = bobNode;
        }

        if (aliceNode.left != null && aliceNode.right != null) {

            int leftValue = traverseTree(aliceNode.left, nextBobNode, newAliceValue, newAmount);
            int rightValue = traverseTree(aliceNode.right, nextBobNode, newAliceValue, newAmount);
            return Math.max(leftValue, rightValue);

        } else if (aliceNode.left != null) {

            return traverseTree(aliceNode.left, nextBobNode, newAliceValue, newAmount);

        }

        return newAliceValue;

    }

    // This method finds Bob's starting position.
    public static TreeNode bobNode(TreeNode root, int node) {

        TreeNode left = null;
        TreeNode right = null;

        if (root.val == node) {

            return root;

        }

        if (root.left != null) {
            left = bobNode(root.left, node);
        }

        if (root.right != null) {
            right = bobNode(root.right, node);
        }

        if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }

        return null;

    }

    public static void linkNodes(TreeNode head, int[][] edges, int[] amount) {

        int currentValue = head.val;

        int leftValue = -1;
        int rightValue = -1;

        for (int i = 0; i < edges.length; i++) {

            if (edges[i][0] == currentValue && leftValue == -1) {
                leftValue = edges[i][1];
                edges[i][0] = -1;
                edges[i][1] = -1;
            } else if (edges[i][0] == currentValue && rightValue == -1) {
                rightValue = edges[i][1];
                edges[i][0] = -1;
                edges[i][1] = -1;
            } else if (edges[i][1] == currentValue && leftValue == -1) {
                leftValue = edges[i][0];
                edges[i][0] = -1;
                edges[i][1] = -1;
            } else if (edges[i][1] == currentValue && rightValue == -1) {
                rightValue = edges[i][0];
                edges[i][0] = -1;
                edges[i][1] = -1;
            }

        }

        if (leftValue != -1) {
            head.left = new TreeNode(leftValue);
            head.left.root = head;
            head.left.amount = amount[leftValue];
            linkNodes(head.left, edges, amount);
        }

        if (rightValue != -1) {
            head.right = new TreeNode(rightValue);
            head.right.root = head;
            head.right.amount = amount[rightValue];
            linkNodes(head.right, edges, amount);
        }

    }

}
