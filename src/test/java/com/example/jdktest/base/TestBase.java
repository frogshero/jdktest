package com.example.jdktest.base;

public class TestBase {
  public static void prt(String title, Object msg) {
    System.out.println(String.format("%1$10s", title) + "    " + msg.toString());
  }
}
