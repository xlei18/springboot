package leo.demo.java.executor.demo;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SleepTwoSecondsTask implements Callable<String> {
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "running..............");
        TimeUnit.SECONDS.sleep(2);
        return new Date().toString();
    }
}