package com.chenpeng.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class MyThread05 implements Runnable{
	@Override
	public void run() {
//		String url = "http://localhost:80/wechatservice/tempMsg/send.do";
		String url = "http://10.51.29.216:80/wechatservice/tempMsg/send";
		String result = "";
		JSONObject msgJson = new JSONObject();
		msgJson.accumulate("loginName", "864000253114633");
		msgJson.accumulate("account", "333");
		
		JSONArray dataJsonArray = new JSONArray();
		dataJsonArray.add(0, "和融通支付");
		dataJsonArray.add(1, "555");
		dataJsonArray.add(2, "2016年11月30日 11:50");
		
		msgJson.accumulate("data", dataJsonArray);
		
		try {
			result = CommonUtil.sendPost(url, msgJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
		System.out.println("返回数据："+result);
	}
}

public class TestWeChatMsg {
	public static void main(String[] args) {
		MyThread05 task = new MyThread05();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		/*int x = 0;
		while(x<5){
			for(int i = 0; i < 5; i++){
				pool.submit(task);//分配任务
			}
			try {
				x++;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		pool.submit(task);//分配任务
		pool.shutdown();//释放任务
	}
}

