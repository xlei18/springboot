package com.demo.mina.demo;

import org.apache.mina.core.service.IoAcceptor;
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
        // 配置I/O processor thread线程数量
        IoAcceptor acceptor = new NioSocketAcceptor(4);
        // 4字节的Header指定Body的字节数，对这种消息的处理
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
        // 将TcpServerHandle中的业务逻辑拿到ExecutorFilter的线程池中执行
        acceptor.getFilterChain().addLast("executor", new ExecutorFilter(Executors.newCachedThreadPool()));
        acceptor.setHandler(new TcpServerHandle());
        acceptor.bind(new InetSocketAddress(8080));
    }

}
