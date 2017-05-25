package leo.demo.java.sync;

/**
 * Created by leo on 2017/5/25.
 */
public class Foo {

    public void a()  throws Exception{
        System.out.println("a");
        Thread.sleep(10000l);
    }

    public synchronized void b() throws Exception{
        System.out.println("b");
        Thread.sleep(10000l);
    }

    public synchronized void c() throws Exception{
        System.out.println("c");
        Thread.sleep(10000l);
    }
}
