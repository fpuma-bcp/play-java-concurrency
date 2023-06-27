package org.example.multithread.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CurrentThreadExample {
  private static Logger logger = LoggerFactory.getLogger(CurrentThreadExample.class);

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(
      () -> logger.info("new thread")
    );
    thread.setName("my-worker");
    logger.info("init");
    thread.start();
    logger.info("end");

    Thread.sleep(1000);
  }
}
