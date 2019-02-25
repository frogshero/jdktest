package com.example.jdktest.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

@RunWith(JUnit4.class)
public class ListTest {

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
      list.add(UUID.randomUUID().toString());
    }
    //用System.arraycopy进行数组复制，并减少size
    list.remove(23);

    //modCount变量：The number of times this list has been structurally modified.
    //通过检查modCount判断是否被其他线程改变了
    list.sort(null);

  }
}
