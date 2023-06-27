package org.example.multithread.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CatchExceptionThreadExample {
  private static Logger logger = LoggerFactory.getLogger(CatchExceptionThreadExample.class);

  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(
      () -> {
        logger.info("new thread");
        throw new RuntimeException("an exception");
      }
    );
    thread.setName("my-worker");
    thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
        logger.error("error happened in thread", e);
      }
    });
    logger.info("init");
    thread.start();
    logger.info("end");

    Thread.sleep(1000);
  }
}
