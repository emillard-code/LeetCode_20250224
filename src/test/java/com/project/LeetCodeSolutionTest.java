package com.project;

import com.project.attempt.LeetCodeAttempt;
import com.project.solution.LeetCodeSolution;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeSolutionTest {

    @Test
    public void mostProfitablePathTest() {

        int[][] edges1 = new int[][]{{0,1},{1,2},{1,3},{3,4}};
        int[] amount1 = new int[]{-2,4,2,-4,6};

        assertEquals(6, LeetCodeSolution.mostProfitablePath(edges1, 3, amount1));

        int[][] edges2 = new int[][]{{0,1}};
        int[] amount2 = new int[]{-7280,2350};

        assertEquals(-7280, LeetCodeSolution.mostProfitablePath(edges2, 1, amount2));

    }

}
