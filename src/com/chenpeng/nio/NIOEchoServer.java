package com.chenpeng.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOEchoServer {
	private Selector selector;
	private ExecutorService tp = Executors.newCachedThreadPool();
	public static Map<Socket,Long> time_stat = new HashMap<Socket,Long>(10240);
	
	public static void main(String[] args) throws Exception {
		new NIOEchoServer().startServer();
	}
	
	private void startServer() throws Exception{
		selector = SelectorProvider.provider().openSelector();//工厂方法获得一个Selector对象实例
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//ServerSocketChannel实例
		serverSocketChannel.configureBlocking(false);//将这个socketchannel设置为非阻塞模式
		
		InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),9898);
		serverSocketChannel.socket().bind(inetSocketAddress);//绑定端口地址
		
		//将serverSocketChannel注册到selector上
		//SelectionKey表示Selector和Channel之间的对应契约关系，当Selector或Channel被关闭时，对应的SelectionKey会失效
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		for(;;){//无限循环轮询准备就绪的key
			
			int readyChanelNum = selector.select();//返回的整数值通知有多少个通道准备好进行通信
			if(readyChanelNum == 0){
				continue;
			}
			//访问就绪通道
			Set<SelectionKey> readyKeys = selector.selectedKeys();//获取准备好的SelectionKey
			
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			long e = 0;
			while(iterator.hasNext()){
				SelectionKey selectionKey = iterator.next();
				iterator.remove();//移除，否则会重复处理相同的SelectionKey
				
				if(selectionKey.isAcceptable()){//判断是否在acceptable状态
					doAccept(selectionKey);
				}else if(selectionKey.isValid() && selectionKey.isReadable()){//是否可读
					if(!time_stat.containsKey(((SocketChannel)selectionKey.channel()).socket())){
						time_stat.put(((SocketChannel)selectionKey.channel()).socket(), System.currentTimeMillis());
						doRead(selectionKey);
					}
				}else if(selectionKey.isValid() && selectionKey.isWritable()){//是否可写
					doWrite(selectionKey);
					e = System.currentTimeMillis();
					long b = time_stat.remove(((SocketChannel)selectionKey.channel()).socket());
					System.out.println("spend:" +(e-b) + "ms");
				}
			}
		}
	}
	
	/**
	 * 与客户端建立连接
	 * @param selectionKey
	 */
	private void doAccept(SelectionKey selectionKey){
		ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = serverChannel.accept();
			clientChannel.configureBlocking(false);
			
			//Register this channel for reading
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			//allocate an EchoClient instance and attach it to this selection key
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);
			
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
			
		} catch (IOException e) {
			System.out.println("Failed to accept new client.");
			e.printStackTrace();
		}
	}
	//读
	private void doRead(SelectionKey selectionKey){
		SocketChannel channel = (SocketChannel) selectionKey.channel();
		ByteBuffer bb = ByteBuffer.allocate(8192);
		int len;
		
		try {
			len = channel.read(bb);
			if(len < 0){
//				disconnect(selectionKey);
				selectionKey.cancel();
//				channel.close();
				return;
			}
		} catch (IOException e) {
			System.out.println("Failed to read from client.");
			e.printStackTrace();
//			disconnect(selectionKey);
			selectionKey.cancel();
			try {
				channel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		bb.flip();
		tp.execute(new HandleMsg(selectionKey,bb));
	}
	//写
	private void doWrite(SelectionKey selectionKey){
		SocketChannel channel = (SocketChannel) selectionKey.channel();
		EchoClient echoClient = (EchoClient) selectionKey.attachment();
		LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
		
		ByteBuffer bb = outq.getLast();
		
		try {
			int len = channel.write(bb);
			if(len == -1){
//				disconnect(selectionKey);
				selectionKey.cancel();
//				channel.close();
				return;
			}
			if(bb.remaining() == 0){
				outq.removeLast();//The buffer was completely written,remove it.
			}
		} catch (IOException e) {
			System.out.println("Failed to write to client.");
			e.printStackTrace();
//			disconnect(selectionKey);
			selectionKey.cancel();
			try {
				channel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(outq.size() == 0){
			selectionKey.interestOps(SelectionKey.OP_READ);
			
		}
		
	}
	
	class EchoClient{
		private LinkedList<ByteBuffer> outq;
		EchoClient(){
			outq = new LinkedList<ByteBuffer>();
		}
		public LinkedList<ByteBuffer> getOutputQueue(){
			return outq;
		}
		public void enqueue(ByteBuffer bb){
			outq.addFirst(bb);
		}
	}
	
	class HandleMsg implements Runnable{
		SelectionKey selectionKey;
		ByteBuffer bb;
		public HandleMsg(SelectionKey selectionKey,ByteBuffer bb){
			this.selectionKey = selectionKey;
			this.bb = bb;
		}
		
		@Override
		public void run() {
			EchoClient echoClient = (EchoClient) selectionKey.attachment();		
			echoClient.enqueue(bb);
			selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			selector.wakeup();//强迫selector立即返回
		}
		
	}
	
}
