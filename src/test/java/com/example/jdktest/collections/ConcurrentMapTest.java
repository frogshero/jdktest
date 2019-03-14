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

@RunWith(JUnit4.class)
public class ConcurrentMapTest {

  private void log(Object o) {System.out.println(o);}

/**
 * 更新值
 static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                      Node<K,V> c, Node<K,V> v) {
      return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
  }
 tab：值数组
 c：原值
 v: 新值

 Class<?> ak = Node[].class;
 ABASE = U.arrayBaseOffset(ak);    //arrayBaseOffset：传入数组类,得到数组第一个元素的偏移地址
 int scale = U.arrayIndexScale(ak);    //arrayIndexScale：可以获取数组中元素的增量地址
 if ((scale & (scale - 1)) != 0)
 throw new Error("data type scale not a power of two");
 ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
*/

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
    }
  }
}
