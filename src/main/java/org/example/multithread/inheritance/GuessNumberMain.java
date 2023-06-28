package org.example.multithread.inheritance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuessNumberMain {
  public static final int MAX_PASSWORD = 9999;
  private static Logger logger = LoggerFactory.getLogger(GuessNumberMain.class);

  public static void main(String[] args) {
    Random random = new Random();
    int password = random.nextInt(MAX_PASSWORD);
    logger.info("random-number:{}",password);
    Vault vault = new Vault(password);

    List<Thread> threads = new ArrayList<>();
    threads.add(new AscendingHackerThread(vault));
    threads.add(new DescendingHackerThread(vault));
    threads.add(new PoliceThread());

    for(Thread thread: threads) {
      thread.start();
    }

  }

  private static class Vault {
    private int password;

    public Vault(int password){
      this.password = password;
    }

    public boolean isCorrectPassword(int guess) {
      try {
        Thread.sleep(5);
      }catch (InterruptedException e) {

      }
      return this.password == guess;
    }
  }

  private static abstract class HackerThread extends Thread {
    protected Vault vault;

    public HackerThread(Vault vault) {
      this.vault = vault;
      this.setName(this.getClass().getSimpleName());
      this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void start() {
      logger.info("Starting thread {}", this.getName());
      super.start();
    }
  }

  private static class AscendingHackerThread extends HackerThread{

    public AscendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run() {
      for(int guess = 0; guess < MAX_PASSWORD; guess++) {
        if(vault.isCorrectPassword(guess)) {
          logger.info("{} guessed the password {}", this.getName(), guess);
          System.exit(0);
        }
      }
    }
  }

  private static class DescendingHackerThread extends HackerThread{

    public DescendingHackerThread(Vault vault) {
      super(vault);
    }

    @Override
    public void run() {
      for(int guess = MAX_PASSWORD; guess >=0 ; guess--) {
        if(vault.isCorrectPassword(guess)) {
          logger.info("{} guessed the password {}", this.getName(), guess);
          System.exit(0);
        }
      }
    }
  }

  private static class PoliceThread extends Thread {
    PoliceThread() {
      this.setName("police-thread");
    }
    @Override
    public void run() {
      for(int i = 10; i > 0; i--) {
        try {
          Thread.sleep(1000);
        }catch (InterruptedException e) {
        }
        logger.info("{}", i);
      }
      logger.info("Game over for you hackers");
      System.exit(0);
    }
  }
}
