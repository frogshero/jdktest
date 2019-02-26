package com.example.jdktest.sync;

class VoTest2 {
  //private int cnt = 0;
  //private volatile int cnt = 0;
  private volatile int[] cnt = new int[100];  //数组用volatle只是其引用有volatile语义
  public void incCnt(int l) throws InterruptedException {
    Thread.sleep(1000);
    System.out.println("Inc started ");
    for (int i=0; i<l; i++) {
      cnt[50]++;
    }
    System.out.println("INC Complete " + cnt[50]);
  }

  public void waitSingal(int l) throws InterruptedException {
    System.out.println("Wait started ");
    while(cnt[50] != l) {
      //不用volatile，这个线程永不结束
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
        voTest.incCnt(5000);
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
