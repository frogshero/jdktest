package com.example.jdktest.inner;

public class InnerClassTest {
  private int cnt = 0;
  //如果VoTest2定义为内部类：VolatileDelay.this cannot be referenced from a static context
  //main是静态方法
  class VoTest2 {
    public int getCnt() {
      //返回外部类的私有成员，如果内部类能独立创建，这就完蛋了
      return cnt;
    }
  }

  public static void main(String[] args) {
    //VoTest2 voTest = new VoTest2();
    InnerClassTest t = new InnerClassTest();
    VoTest2 voTest = t.new VoTest2();
    voTest.getCnt();

  }
}
