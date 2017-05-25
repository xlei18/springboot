package leo.demo.java.sync;

/**
 * Created by leo on 2017/5/25.
 */
public class ThreadTest1 extends Thread{

    private Foo foo;

    public ThreadTest1(Foo foo){
       this.foo = foo;
    }

    @Override
    public void run() {
        try {
            foo.a();
            System.out.println(Thread.currentThread().getName());
            foo.c();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
