package com.bobocode.intro;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {
    private int[] arr;
    private int n;

    public MergeSortTask(int[] arr){
        this.arr = arr;
        this.n = arr.length;
    }
    @Override
    protected void compute() {
        if(arr.length ==1){
            return;
        }
        var left = Arrays.copyOfRange(arr,0,n/2);
        var right = Arrays.copyOfRange(arr,n/2,n);
        var leftTask = new MergeSortTask(left);
        var rightTask = new MergeSortTask(right);
        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        int[] supportArray = Arrays.copyOf(arr, arr.length);
        int n = arr.length;
        for (int size = 1; size < n; size *= 2) {
            for (int j = 0; j < n - size; j += 2 * size) {
                merge(arr, supportArray, j, j + size - 1, j + size, Math.min(j + 2 * size - 1, n - 1));
            }
        }
    }
    public static void merge(int[] array, int[] supportArray, int leftStart, int leftEnd, int rightStart, int rightEnd) {
        for (int i = leftStart; i <= rightEnd; i++) {
            supportArray[i] = array[i];
        }
        int l = leftStart;
        int r = rightStart;
        for (int i = leftStart; i <= rightEnd; i++) {
            if(l > leftEnd) {
                array[i] = supportArray[r];
                r += 1;
            } else if (r > rightEnd) {
                array[i] = supportArray[l];
                l += 1;
            } else if (supportArray[l] < supportArray[r]) {
                array[i] = supportArray[l];
                l += 1;
            } else {
                array[i] = supportArray[r];
                r += 1;
            }
        }
    }
}
