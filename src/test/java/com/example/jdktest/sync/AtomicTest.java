package com.example.jdktest.sync;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Vector;

class VoTest {
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

  //private volatile int cnt = 0;  //加volatile也不行，cnt++不是原子操作
  //private AtomicInteger cnt = new AtomicInteger(0);  //AtomicInteger内部值自带volatile
  private volatile int[] cnt = new int[100]; //必须配合getAndAddInt
  public void incCnt(int l) {
    //cnt[50] = 0;
    for (int i=0; i<l; i++) {
      //synchronized(this) {
      //cnt.incrementAndGet();
      //cnt++;
      U.getAndAddInt(cnt, THIS_SHIFT, 1);  //
      //cnt[50]++;
      //}
    }
  }
  public int getCnt() {
    return cnt[50];
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
