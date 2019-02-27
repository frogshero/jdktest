package com.example.jdktest.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class SimpleSortTest {

  int[] arr = {98, 78, 75, 57, 83, 58, 32, 9};
  //    int[] arr = new int[8];
//    for (int i = 0; i < arr.length; i++) {
//      arr[i] = (int) Math.round(Math.random() * 100);
//    }

  private void prtArr(int[] arr) {
    System.out.println(Arrays.toString(arr));
  }
  @Test
  public void insertSort() {
    // 处理方向由左向右，比较方向由当前元素开始从右向左，把较大的值右移，当前值左移
    System.out.println("INSERT SORT");
    prtArr(arr);
    for (int i = 1; i <= 7; i++) {
      int temp = arr[i];
      int j = i - 1;
      while (j >= 0 && arr[j] > temp) {
        arr[j + 1] = arr[j];
        j--;
      }
      arr[j + 1] = temp;
      prtArr(arr);
    }
    prtArr(arr);
  }

  @Test
  public void bubbleSort() {
    System.out.println("BUBBLE SORT");
    //结果的尾部先有序，最大的先移到右边
    prtArr(arr);
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
      prtArr(arr);
    }
    prtArr(arr);
  }

  @Test
  public void mergeSort() {
    //合并有序的的子集，比较左右2个指针，小的放入结果集
    int[] arr = {57, 75, 78, 98, 9, 32, 58, 83};
    System.out.println(Arrays.toString(arr));
    int l = 0;
    int m = 3;
    int r = 7;
    int len1 = m - l + 1, len2 = r - m;
    int[] left = new int[len1];
    int[] right = new int[len2];
    for (int i = 0; i < len1; i++)
      left[i] = arr[l + i];
    for (int i = 0; i < len2; i++)
      right[i] = arr[m + 1 + i];

    int i = 0;
    int j = 0;
    int k = l;
    // after comparing, we merge those two array
    // in larger sub array
    while (i < len1 && j < len2) {
      if (left[i] <= right[j]) {
        arr[k] = left[i];
        i++;
      } else {
        arr[k] = right[j];
        j++;
      }
      k++;
    }

    prtArr(arr);
    // copy remaining elements of left, if any
    while (i < len1) {
      arr[k] = left[i];
      k++;
      i++;
    }

    System.out.println(Arrays.toString(arr));
    // copy remaining element of right, if any
    while (j < len2) {
      arr[k] = right[j];
      k++;
      j++;
    }

    prtArr(arr);
  }

  @Test
  public void quickSort() {
    //快排: 最左边的元素为key，把左边比key大的和右边比key小的交换。
    //一趟快排之后将key的位置找到。左边的都比key小，右边都比key大，称为partition
    // 然后分别对左边，右边再递归Recursively排序
    int[] arr = {58, 78, 75, 98, 83, 57, 9, 32};
    prtArr(arr);
    quickSort(arr, 0, 7);
    prtArr(arr);
  }

  private int partition(int[] arr, int left, int right) {
    int i = left;
    int j = right;
    int key = arr[left];
    while (i < j) {
      while (i < j && arr[j] > key) {
        j--;
      }
      arr[i] = arr[j];
      //从后往前找到第一个比key小的数与arr[i]交换；
      while (i < j && arr[i] < key) {
        i++;
      }
      arr[j] = arr[i];
      //从前往后找到第一个比key大的数与array[j]交换；
    }
    arr[i] = key;
    return i;
  }

  private void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  //Lomuto partition scheme
  private int partition2(int[] arr, int left, int right) {
    int i = left;
    int key = arr[right]; //用最右的为key
    for (int j = left + 1; j < right; j++) {
      if (arr[j] < key) {
        swap(arr, i, j);
        i++;
      }
    }
    swap(arr, i, right);
    return i;
  }

  private void quickSort(int[] arr, int left, int right) {
    if (left >= right) {
      return;
    }
    int i = partition2(arr, left, right);

    quickSort(arr, left, i - 1);
    quickSort(arr, i + 1, right);
  }
}
