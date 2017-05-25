package leo.demo.java.sync;

/**
 * Created by leo on 2017/5/25.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        Foo foo = new Foo();
        ThreadTest t1 = new ThreadTest(foo);
        ThreadTest1 t2 = new ThreadTest1(foo);
        t1.start();
        t2.start();
    }
}
