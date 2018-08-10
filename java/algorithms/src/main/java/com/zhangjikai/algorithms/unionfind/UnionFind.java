package com.zhangjikai.algorithms.unionfind;

/**
 * 路径压缩的并查集算法
 *
 * @author Jikai Zhang
 * @date 2017/11/19.
 */
public class UnionFind {

    private int[] points;
    private int[] weights;
    private int count;

    public UnionFind(int n) {
        points = new int[n];
        weights = new int[n];
        count = 0;
        for (int i = 0; i < n; i++) {
            points[i] = i;
            weights[i] = 1;
        }
        count = n;
    }

    public int find(int p) {
        while (p != points[p]) {
            points[p] = points[points[p]];
            p = points[p];
        }
        return p;
    }


    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (weights[i] > weights[j]) {
            points[j] = i;
            weights[i] += weights[j];
        } else {
            points[i] = points[j];
            weights[j] += weights[i];
        }
        count--;
    }
}
