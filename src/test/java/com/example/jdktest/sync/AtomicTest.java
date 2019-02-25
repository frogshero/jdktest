package com.example.jdktest.sync;

import java.util.Vector;

class VoTest {
  private volatile int cnt = 0;  //加volatile也不行，cnt++不是原子操作
  //private AtomicInteger cnt = new AtomicInteger(0);  //AtomicInteger内部值自带volatile
  public void incCnt(int l) {
    for (int i=0; i<l; i++) {
      //synchronized(this) {
      //cnt.incrementAndGet();
      cnt++;
      //}
    }
  }
  public int getCnt() {
    return cnt;
    //return cnt.get();
  }
}

public class AtomicTest {

  public static void main(String[] args) throws Exception {

    for (int j=0; j<5; j++) {
      VoTest voTest = new VoTest();

      Vector<Thread> threads = new Vector<>();
      for (int i = 0; i < 4; i++) {
        threads.add(new Thread(() -> voTest.incCnt(1000)));
      }

      for (Thread t : threads) {
        t.start();
      }

      for (Thread t : threads) {
        t.join();
      }

      System.out.println(voTest.getCnt());
    }
  }
}
