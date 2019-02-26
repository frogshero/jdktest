package com.example.jdktest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

  private static final sun.misc.Unsafe U;
  static {
    try {
      System.out.println("UUU");
      Field unsafeFld = Unsafe.class.getDeclaredField("theUnsafe");
      unsafeFld.setAccessible(true);
      U = (Unsafe)unsafeFld.get(null);

    } catch (Exception e) {
      throw new Error(e);
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("------");

    int[] arr = new int[50];
    for (int i=0; i<arr.length; i++) {
      arr[i] = i+100;
    }

    int r = U.getIntVolatile(arr, Unsafe.ARRAY_INT_BASE_OFFSET + Unsafe.ARRAY_INT_INDEX_SCALE * 20);
    System.out.println(r);

  }
}
