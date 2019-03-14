package com.example.jdktest.collections;

import com.example.jdktest.base.TestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ListTest extends TestBase {

  private void log(Object msg) {
    System.out.println(msg);
  }

  @Test
  public void test() throws NoSuchFieldException, IllegalAccessException {
    ArrayList<String> list = new ArrayList<>();
    Field fld = ArrayList.class.getDeclaredField("elementData");
    fld.setAccessible(true);
    for (int i=0; i<100; i++) {
      log(((Object[])fld.get(list)).length);
      //需要扩容时声明一个新的1.5倍长数组，用System.arraycopy把数据从旧数组复制过来
      list.add(UUID.randomUUID().toString());
    }
    //用System.arraycopy进行数组复制，并减少size
    list.remove(23);

    list.sort(null);

    //modCount变量：The number of times this list has been structurally modified.
    //通过检查modCount判断是否被其他线程改变了


    Iterator<String> it = list.iterator();
    //it.forEachRemaining(s -> prt("----@@@:", s));
    while(it.hasNext()) {
      //检查modCount和expectedModCount
      String r = it.next();
      prt("Iterator:", r);
      //维护modCount和expectedModCount的一致, 还有cursor， lastRet
      it.remove();
      //list.remove(50);  //导致ConcurrentModificationException

      prt("----------remain:", it);
      it.forEachRemaining(s -> prt("----OK:", s));
    }
  }
}
