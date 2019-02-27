package com.example.jdktest.thread;

import com.example.jdktest.base.TestBase;

class VoTest {
  private int conflict = 0;
  public void getConflict() throws InterruptedException {
    synchronized(this) {
      Thread.sleep(1000);
    }
  }

  public void goWait() throws InterruptedException {
    synchronized(this) {
      //System.out.println("wait");
      this.wait();
    }
  }

  public void goNotify() {
    synchronized(this) {
      //System.out.println("notify");
      this.notify();
    }
  }
}

public class ThreadStatusTest extends TestBase {
  public static void main(String[] args) throws InterruptedException {
    VoTest voTest = new VoTest();

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);  //TIME_WAITING
          voTest.getConflict();
          //System.out.println("waiting");
          voTest.goWait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread blocking = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          voTest.getConflict();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread notify = new Thread(new Runnable() {
      @Override
      public void run() {
        voTest.goNotify();
      }
    });

    prt("new", t.getState());  //NEW

    t.start();
    prt("start", t.getState());  //RUNNABLE = ready, running

    Thread.sleep(500);
    prt("sleep", t.getState());  //TIMED_WAITING

    blocking.start();
    Thread.sleep(600);
    prt("blocking", t.getState());  //BLOCKED

    Thread.sleep(1500);
    prt("wait", t.getState());

    notify.start();    //

    t.join();
    prt("join", t.getState());  //TERMINATED

  }
}
