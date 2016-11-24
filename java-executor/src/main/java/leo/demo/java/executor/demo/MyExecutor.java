package leo.demo.java.executor.demo;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MyExecutor extends ThreadPoolExecutor {

    private ConcurrentHashMap<String, Date> startTimes;

    public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        startTimes = new ConcurrentHashMap<>();
    }

    @Override
    public void shutdown() {
        String str = "--------shutdown()--------";
        System.out.printf(str + "MyExecutor: Going to shutdown.\n");
        System.out.printf(str + "MyExecutor: Executed tasks:%d\n", getCompletedTaskCount());
        System.out.printf(str + "MyExecutor: Running tasks:%d\n", getActiveCount());
        System.out.printf(str + "MyExecutor: Pending tasks:%d\n", getQueue().size());
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        String str = "--------shutdownNow()--------";
        System.out.printf(str + "MyExecutor: Going to immediately shutdown.\n");
        System.out.printf(str + "MyExecutor: Executed tasks: %d\n", getCompletedTaskCount());
        System.out.printf(str + "MyExecutor: Running tasks: %d\n", getActiveCount());
        System.out.printf(str + "MyExecutor: Pending tasks: %d\n", getQueue().size());
        return super.shutdownNow();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String str = "--------beforeExecute()--------";
        System.out.printf(str + "MyExecutor: A task is beginning: %s : %s\n", t.getName(), r.hashCode());
        startTimes.put(String.valueOf(r.hashCode()), new Date());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String str = "--------afterExecute()--------";
        Future<?> result = (Future<?>) r;
        try {
            System.out.printf(str + "MyExecutor: A task is finishing.\n");
            System.out.printf(str + "MyExecutor: Result: %s\n", result.get());
            Date startDate = startTimes.remove(String.valueOf(r.
                    hashCode()));
            Date finishDate = new Date();
            long diff = finishDate.getTime() - startDate.getTime();
            System.out.printf(str + "MyExecutor: Duration: %d\n", diff);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

