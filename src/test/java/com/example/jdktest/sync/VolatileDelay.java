package com.example.jdktest.sync;

class VoTest2 {
  //private int cnt = 0;
  private volatile int cnt = 0;
  public void incCnt(int l) throws InterruptedException {
    Thread.sleep(200);
    for (int i=0; i<l; i++) {
      cnt++;
    }
    System.out.println("INC Complete " + cnt);
  }

  public void waitSingal(int l) throws InterruptedException {
    while(cnt != l) {
      //不用volatile，这个线程永不结束
    }
    System.out.println("Wait End " + cnt);
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

    tInc.join();
    tWait.join();
  }
}
