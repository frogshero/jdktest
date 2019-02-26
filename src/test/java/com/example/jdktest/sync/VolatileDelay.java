package com.example.jdktest.sync;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

class VoTest2 {

  private static final int THIS_SHIFT = Unsafe.ARRAY_INT_BASE_OFFSET + Unsafe.ARRAY_INT_INDEX_SCALE * 50;
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

  //private int cnt = 0;   //1  100%卡住
  private volatile int cnt = 0;     //2   100% 不卡
  //private int[] cnt = new int[100];    //3  100%卡住
  //private volatile int[] cnt = new int[100];  //4   30% 卡住
  public void incCnt(int l) throws InterruptedException {
    Thread.sleep(1000);
    System.out.println("Inc started ");
    for (int i=0; i<l; i++) {
      //cnt[50]++;
      cnt++;
    }
    System.out.println("INC Complete " + l);
  }

  public void waitSingal(int l) throws InterruptedException {
    System.out.println("Wait started ");
    int tmp=0;
    //while(U.getIntVolatile(cnt, THIS_SHIFT) != l) {  //也不行
    while((tmp=cnt) != l) {
      //不用volatile，这个线程永不结束
      //System.out.println(tmp);
    }
    System.out.println("Wait for " + l + " End ");
  }
}

public class VolatileDelay {

  public static void main(String[] args) throws InterruptedException {
    VoTest2 voTest = new VoTest2();
    Thread tWait = new Thread(() -> {
      try {
        voTest.waitSingal(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    Thread tInc = new Thread(()-> {
      try {
        voTest.incCnt(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    tWait.start();
    tInc.start();

    tWait.join();
    tInc.join();
  }
}
