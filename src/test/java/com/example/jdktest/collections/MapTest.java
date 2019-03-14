package com.example.jdktest.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@RunWith(JUnit4.class)
public class MapTest {

  private void log(Object msg) {
    System.out.println(msg);
  }

  /*map内部容器table的元素类可能是TreeNode或Node：
  Node<K,V> implements Map.Entry<K,V> {
      final int hash;   //key.hashCode() ^ (key.hashCode() >>> 16)
      final K key;
      V value;
      Node<K,V> next;  //链表
      ----------------------满足TREEIFY_THRESHOLD，则把链表转换为Tree
  TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
      TreeNode<K,V> parent;  // red-black tree links
      TreeNode<K,V> left;
      TreeNode<K,V> right;
      TreeNode<K,V> prev;    // needed to unlink next upon deletion
  */
  /**
   * has算法：key.hashCode() >>> 16 保留16位以上的bit
   * key.hashCode() ^ (key.hashCode() >>> 16) ？？
   * 2145440000 & 64
   * 2145430000 & 64
   * 2145420000 & 64
   * 2145410000 & 64
   * 这些的数据高位不同，但是地位一样导致hash重复，保留高位再和地位异或可以减少hash值的重复
   */
  /**
   * 元素的数组下标=(数组size - 1) & hash
   * threshold = loadFactor * size  //threshold：要重新分配数组大小的阈值，loadFactor：要resize的百分比
   * 到达threshold后翻倍
   * 怎么resize？循环把旧数组赋值到新数组 //不能用System.arrayCopy
   */

  @Test
  public void test() throws NoSuchFieldException, IllegalAccessException {
    HashMap<String, String> map = new HashMap<>();
    Field fld = HashMap.class.getDeclaredField("table");
    fld.setAccessible(true);
    Field fldThreshold = HashMap.class.getDeclaredField("threshold");
    fldThreshold.setAccessible(true);
    Field fldLoadFactor = HashMap.class.getDeclaredField("loadFactor");
    fldLoadFactor.setAccessible(true);

    for (int i = 0; i < 2000; i++) {
      map.put(i + "as#df$xx", "GFJFG" + i);
      Object[] tab = (Object[]) fld.get(map);
      log("threshHold:" + fldThreshold.getInt(map));
      log("loadFactor:" + fldLoadFactor.getFloat(map));
      System.out.println(tab.length);
    }
    //assertThat(map.size()).isEqualTo(1);
  }

  @Test
  public void testRandom() {
    String key = "";
    HashMap<String, String> map = new HashMap<>();
    for (int i = 0; i < 100; i++) {
      String k = UUID.randomUUID().toString();
      map.put(k, "");
      if (i==50) {
        key = k;
      }
    }

    String ret = map.get(key); //3个比较: hash && == && equal

    map.remove(key); //处理列表或treeNode
    log("----------------------");
  }

}
