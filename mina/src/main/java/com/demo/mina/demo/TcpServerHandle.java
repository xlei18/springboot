package com.demo.mina.demo;

import org.apache.mina.core.IoUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Collection;

/**
 * Created by leo on 2016/11/28.
 */
public class TcpServerHandle extends IoHandlerAdapter {

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    // 接收到新的数据
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        // 接收客户端的数据
        String msg = (String) message;
        System.out.println("===============server接收到请求：" + msg);
        Thread.sleep(10000);
        // 发送到客户端
        session.write("from service");
        System.out.println("++++++++++++++++++ 处理完毕");

//        // 获取所有正在连接的IoSession
//        Collection<IoSession> sessions = session.getService().getManagedSessions().values();
//        // 将消息写到所有IoSession
//        IoUtil.broadcast(message, sessions);


    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("sessionCreated");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed");
    }
}
