package com.project;

import com.project.attempt.LeetCodeAttempt;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeAttemptTest {

    @Test
    public void mostProfitablePathInATreeTest() {

        int[][] edges1 = new int[][]{{0,1},{1,2},{1,3},{3,4}};
        int[] amount1 = new int[]{-2,4,2,-4,6};

        assertEquals(6, LeetCodeAttempt.mostProfitablePathInATree(edges1, amount1, 3));

        int[][] edges2 = new int[][]{{0,1}};
        int[] amount2 = new int[]{-7280,2350};

        assertEquals(-7280, LeetCodeAttempt.mostProfitablePathInATree(edges2, amount2, 1));

    }

}
