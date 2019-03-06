package com.example.jdktest.thread;

import com.example.jdktest.base.TestBase;

public class ThreadStatusTest extends TestBase {

  static class VoTest {
    private int conflict = 0;
    public void getConflict() {
      synchronized(this) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          TestBase.prt("Exception", "interrupted");
        }
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

  public static void main(String[] args) throws InterruptedException {
    VoTest voTest = new VoTest();

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          try {
            Thread.sleep(1000);  //TIME_WAITING
          } catch (InterruptedException e) {
            TestBase.prt("EXCEPTION", "interrupted");
            return;
          }
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
        voTest.getConflict();
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
    //t.interrupt();
    prt("sleep", t.getState());  //TIMED_WAITING

    Thread.sleep(50);
    if (t.isAlive()) {   //check if interrupted
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
}
