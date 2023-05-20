package org.example.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPoolMain {
  private final static Logger logger = LoggerFactory.getLogger(FixedThreadPoolMain.class);

  private static ExecutorService es = Executors.newFixedThreadPool(4);
  private static List<Callable<String>> callables = new ArrayList<>();
  public static void main(String[] args) {
    callables.add(() -> "A");
    callables.add(() -> "B");
    callables.add(() -> "C");
    callables.add(() -> "D");
    
    invokeAll();

  }

  private static void invokeAll() {
    try {
      List<Future<String>> list = es.invokeAll(callables);
      for(Future<String> future: list) {
        logger.info(future.get());
      }
    }catch (Exception e){
      logger.error("exception:",e);
    }finally {
      es.shutdown();
    }

    logger.info("End!!");
  }

}
