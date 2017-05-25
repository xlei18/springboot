package leo.demo.java.sync;

/**
 * Created by leo on 2017/5/25.
 */
public class ThreadTest extends Thread{

    private Foo foo;

    public ThreadTest(Foo foo){
       this.foo = foo;
    }

    @Override
    public void run() {
        try {
            foo.a();
            System.out.println(Thread.currentThread().getName());
            foo.b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
