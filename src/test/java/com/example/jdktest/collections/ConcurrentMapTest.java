package com.example.jdktest.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RunWith(JUnit4.class)
public class ConcurrentMapTest {

  private void log(Object o) {System.out.println(o);}

  @Test
  public void test() throws InterruptedException, NoSuchFieldException {
    Field fld = HashMap.class.getDeclaredField("table");
    fld.setAccessible(true);

    Vector<Thread> threads = new Vector<>();
    ArrayList<String> list = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      list.add(UUID.randomUUID().toString());
    }
    log("Total:" + list.size());

    for (int ii=0; ii<10; ii++) {
      //HashMap<String, String> map = new HashMap<>();  //会丢失key
      ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();  //最终长度没有差异
      for (int i = 0; i < 10000; i++) {
        //map.put(UUID.randomUUID().toString(), "");
        final int j = i;
        Thread t = new Thread(() -> map.put(list.get(j), ""));
        threads.add(t);
        t.start();
      }

      for (Thread t : threads) {
        t.join();
      }
      Thread.sleep(3000); //等待线程结束
      log(map.size());  //9998 ???

      int lack = 0;
      for (int i=0; i<list.size(); i++) {
        if (!map.containsKey(list.get(i))) {
          log(ii + "-----" + i + "-------" + list.get(i));
          lack++;
        }
      }
      if (lack > 2) {
        log("----------------------------------------------------------");
        log(list);
        log("----------------------------------------------------------");
        log(map.keySet());
        log("----------------------------------------------------------");
      }
      for (String k: map.keySet()) {
        if (list.indexOf(k) < 0) {
          log(ii + "--------------------" + k);
        }
      }
    }
  }
}
