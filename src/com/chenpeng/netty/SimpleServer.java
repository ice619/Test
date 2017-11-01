package com.chenpeng.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/** 
 *  
 * Netty中，通讯的双方建立连接后，会把数据按照ByteBuf的方式进行传输， 
 * 例如http协议中，就是通过HttpRequestDecoder对ByteBuf数据流进行处理，转换成http的对象。 
 *  
 */  
public class SimpleServer {  
    private int port;  
  
    public SimpleServer(int port) {  
        this.port = port;  
    }  
  
    public void run() throws Exception {  
        //EventLoopGroup是用来处理IO操作的多线程事件循环器  
        //bossGroup 用来接收进来的连接  
        EventLoopGroup bossGroup = new NioEventLoopGroup();   
        //workerGroup 用来处理已经被接收的连接  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            //启动 NIO 服务的辅助启动类  
            ServerBootstrap b = new ServerBootstrap();   
            b.group(bossGroup, workerGroup)  
                //配置 Channel  
                .channel(NioServerSocketChannel.class)  
                .childHandler(new ChannelInitializer<SocketChannel>() {   
                        @Override  
                        public void initChannel(SocketChannel ch) throws Exception {  
                            // 注册handler    
                            ch.pipeline().addLast(new SimpleServerHandler()); 
                            //ChannelPipeline可以理解成一个消息（ 或消息事件，ChanelEvent）流转的通道，
                            //在这个通道中可以被附上许多用来处理消息的handler，当消息在这个通道中流转的时候，
                            //如果有与这个消息类型相对应的handler，就会触发这个handler去执行相应的动作。
                        }  
                    })  
                .option(ChannelOption.SO_BACKLOG, 128)   
                .childOption(ChannelOption.SO_KEEPALIVE, true);   
  
            // 绑定端口，开始接收进来的连接  
            ChannelFuture f = b.bind(port).sync(); //同步阻塞方法sync等待绑定操作完成 
            // 等待服务器 socket 关闭 。  
            f.channel().closeFuture().sync();  //等待服务器链路关闭之后main函数才退出
        } finally {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        new SimpleServer(9999).run();  
    }  
} 