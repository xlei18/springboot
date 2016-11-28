package com.demo.mina.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by leo on 2016/11/28.
 */
public class TcpClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = null;
        DataOutputStream out = null;
        DataInputStream in =null;

        try {

            socket = new Socket("localhost", 8080);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // 请求服务器
            String data1 = "牛顿";
            byte[] outputBytes1 = data1.getBytes("UTF-8");
            out.writeInt(outputBytes1.length); // write header
            out.write(outputBytes1); // write body
            out.flush();

            byte[] byteArray = new byte[1024];
            int length = in.read();
            System.out.println(new String(byteArray, 0, length, "UTF-8"));

            String data2 = "爱因斯坦";
            byte[] outputBytes2 = data2.getBytes("UTF-8");
            out.writeInt(outputBytes2.length); // write header
            out.write(outputBytes2); // write body
            out.flush();

            byteArray = new byte[1024];
            length = in.read();
            System.out.println(new String(byteArray, 0, length, "UTF-8"));

        } finally {
            // 关闭连接
            out.close();
            in.close();
            socket.close();
        }
    }
}
