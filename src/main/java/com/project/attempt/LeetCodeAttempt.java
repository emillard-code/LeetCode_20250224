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

    // This method returns the most profitable path in a tree for Alice.
    public static int mostProfitablePathInATree(int[][] edges, int[] amount, int bob) {

        // We create the initial root node that will always have a value of 0.
        TreeNode root = new TreeNode(0);

        // We call a helper method that will create and link nodes,
        // using the information from int[] edges to form a complete tree.
        linkNodes(root, edges);

        // We then a call another helper method to find the starting node for Bob.
        TreeNode bobStartingNode = bobNode(root, bob);

        // We then call a recursive method to simulate the travel that Alice and Bob performs,
        // which will also calculate and return the value of the most profitable path for Alice.
        return traverseTree(root, bobStartingNode, 0, amount);

    }

    // This recursive method calculates what the most profitable path for Alice would be.
    public static int traverseTree(TreeNode aliceNode, TreeNode bobNode, int aliceValue, int[] amount) {

        // In order to avoid conflicts during recursive calls of this method,
        // we create a copy of the amount array at the beginning of each call.
        int[] newAmount = new int[amount.length];

        for (int i = 0; i < amount.length; i++) {
            newAmount[i] = amount[i];
        }

        // For similar reasons, we create a new copy of int aliceValue.
        int newAliceValue = aliceValue;

        // We then increment the value of int newAliceValue by the "amount" assigned to the
        // current node that she is at. If it is detected that Bob landed on the same node at
        // the same time, it is only incremented by half the value.
        if (aliceNode == bobNode) {
            newAliceValue = newAliceValue + newAmount[aliceNode.val]/2;
        } else {
            newAliceValue = newAliceValue + newAmount[aliceNode.val];
        }

        // Once we finish adding the "amount" to int newAliceValue, we then zero out the value of
        // the "amount" at the nodes that Alice and Bob are at, to indicate they have crossed them.
        newAmount[aliceNode.val] = 0;
        newAmount[bobNode.val] = 0;

        // If the current node that Bob is at still has a parent node, we set the parent node
        // as the next node that Bob will travel to. Otherwise, he stays at the current node.
        TreeNode nextBobNode;

        if (bobNode.root != null) {
            nextBobNode = bobNode.root;
        } else {
            nextBobNode = bobNode;
        }

        // If there is a child node at the current node that Alice is at, we set that node as the
        // next node that Alice will travel to. If there are two child nodes, then we will have
        // Alice travel down both of them and calculate which path brings more profit to Alice.
        if (aliceNode.left != null && aliceNode.right != null) {

            int leftValue = traverseTree(aliceNode.left, nextBobNode, newAliceValue, newAmount);
            int rightValue = traverseTree(aliceNode.right, nextBobNode, newAliceValue, newAmount);
            return Math.max(leftValue, rightValue);

        } else if (aliceNode.left != null) {

            return traverseTree(aliceNode.left, nextBobNode, newAliceValue, newAmount);

        }

        // If there are no more nodes for Alice to travel, we simply return the current value
        // of int newAliceValue in order to reflect her current "profit".
        return newAliceValue;

    }

    // A helper method that finds Bob's starting position. We use recursion to
    // travel down the tree until a node with the same value as int node is found.
    public static TreeNode bobNode(TreeNode root, int node) {

        TreeNode left = null;
        TreeNode right = null;

        // If the current node contains the value of int node, return that node.
        if (root.val == node) {

            return root;

        }

        // Otherwise, we check both the left and right of the current node and perform a
        // recursive call to see if we can find a node that contains the value we want.
        if (root.left != null) {
            left = bobNode(root.left, node);
        }

        if (root.right != null) {
            right = bobNode(root.right, node);
        }

        // If at any point either recursive call returns a non-null value, that means
        // a node with the desired value has been found, and we will return it.
        if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }

        // If not, then we simply return null.
        return null;

    }

    // A helper method that constructs a full linked tree from the information
    // provided in int[][] edges, creating and linking the nodes necessary for it.
    public static void linkNodes(TreeNode head, int[][] edges) {

        // We take note of the value of the current node.
        int currentValue = head.val;

        int leftValue = -1;
        int rightValue = -1;

        // We then perform a loop through int[][] edges. If we find any pairs of values that
        // contain int currentValue, then we know that the other value in the pair is the value
        // of a child element of the current node. We prioritize placing child elements in the
        // left node first, and then place it in the right node if one already exists in the left
        // node. As such, we will set the values of int leftValue and int rightValue to reflect
        // the values of any potential child elements we may have to add. They are set to a default
        // of -1 if there is no child element for their respective directions, and are a positive
        // value otherwise. We also set the pairs of values to -1 in int[][] edges whenever we
        // have finished taking a look at them and have accounted for their information.
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

        // If a value for the left node has been found, we construct a new node for it
        // and then perform a recursive call on that node to continue building the tree.
        // We also make sure that node has a pointer back to its parent node (i.e. the current
        // node that we are at). We will need this to perform the necessary logic for Bob later.
        if (leftValue != -1) {
            head.left = new TreeNode(leftValue);
            head.left.root = head;
            linkNodes(head.left, edges);
        }

        // If a value for the right node has been found, we construct a new node for it
        // and then perform a recursive call on that node to continue building the tree.
        // We also make sure that node has a pointer back to its parent node (i.e. the current
        // node that we are at). We will need this to perform the necessary logic for Bob later.
        if (rightValue != -1) {
            head.right = new TreeNode(rightValue);
            head.right.root = head;
            linkNodes(head.right, edges);
        }

    }

}
