package com.demo.mina.demo;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2016/11/28.
 */
public class TcpServer {

    public static void main(String[] args) throws IOException {
        // 用于监听进来的连接
        // 配置I/O processor thread线程数量
        IoAcceptor acceptor = new NioSocketAcceptor(4);
        // 4字节的Header指定Body的字节数，对这种消息的处理
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
        // 将TcpServerHandle中的业务逻辑拿到ExecutorFilter的线程池中执行
        acceptor.getFilterChain().addLast("executor", new ExecutorFilter(Executors.newCachedThreadPool()));
        // 用于服务客户端连接和当前时间的请求
        acceptor.setHandler(new TcpServerHandle());
        // 缓冲区大小的指定用于告诉底层操作系统给进来的数据分配多少空间
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        // 将指定什么时候检测空闲session
        // 第一个参数定义决定session是否空闲时，什么动作检测，第二个参数定义时间长度，必须在session认为空闲之前发生
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        // 绑定端口
        acceptor.bind(new InetSocketAddress(8080));
    }

}
