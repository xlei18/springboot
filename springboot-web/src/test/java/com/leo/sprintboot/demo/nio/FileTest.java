package com.leo.sprintboot.demo.nio;
/**
 * Created by leo on 2016/11/8.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileTest
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/8 10:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class FileTest {

    String filePath = "D:\\file.txt";

    @Test
    public void fileNIOTest(){
        long start = System.currentTimeMillis();
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile(filePath,"rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);

            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);

            while(bytesRead != -1)
            {
                buf.flip();
                while(buf.hasRemaining())
                {
                    System.out.print((char)buf.get());
                }

                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("============================fileNIOTest()耗时：" + (System.currentTimeMillis() - start) + "ms" );
    }

    @Test
    public void fileIOTest(){
        long start = System.currentTimeMillis();
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(filePath));

            byte [] buf = new byte[1024];
            int bytesRead = in.read(buf);
            while(bytesRead != -1)
            {
                for(int i=0;i<bytesRead;i++)
                    System.out.print((char)buf[i]);
                bytesRead = in.read(buf);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("============================fileIOTest()耗时：" + (System.currentTimeMillis() - start) + "ms" );
    }
}
