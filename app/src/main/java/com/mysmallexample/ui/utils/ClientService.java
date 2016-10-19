package com.mysmallexample.ui.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ClientService {

	public static final String TAG_GET = "Get方式";
	public static final String TAG_POST = "Post方式";
	public static final String TAG_HTTPGET = "HttpGet方式";
	public static final String TAG_HTTPPOST = "HttpPost方式";
	public static final int HTTP_200 = 200;

	// Get方式请求
	public static void requestByGet() throws Exception {
		String path = "https://reg.163.com/logins.jsp?id=helloworld&pwd=android";
		// 新建一个URL对象
		URL url = new URL(path);
		// 打开一个HttpURLConnection连接
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		// 设置连接超时时间
		urlConn.setConnectTimeout(5 * 1000);
		// 开始连接
		urlConn.connect();
		// 判断请求是否成功
		if (urlConn.getResponseCode() == HTTP_200) {
			// 获取返回的数据
			byte[] data = readStream(urlConn.getInputStream());
			Log.i(TAG_GET, "Get方式请求成功，返回数据如下：");
			Log.i(TAG_GET, new String(data, "UTF-8"));
		} else {
			Log.i(TAG_GET, "Get方式请求失败");
		}
		// 关闭连接
		urlConn.disconnect();
	}

	// Post方式请求
	public static void requestByPost() throws Throwable {
		String path = "https://reg.163.com/logins.jsp";
		// 请求的参数转换为byte数组
		String params = "id=" + URLEncoder.encode("helloworld", "UTF-8")
				+ "&pwd=" + URLEncoder.encode("android", "UTF-8");
		byte[] postData = params.getBytes();
		// 新建一个URL对象
		URL url = new URL(path);
		// 打开一个HttpURLConnection连接
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		// 设置连接超时时间
		urlConn.setConnectTimeout(5 * 1000);
		// Post请求必须设置允许输出
		urlConn.setDoOutput(true);
		// Post请求不能使用缓存
		urlConn.setUseCaches(false);
		// 设置为Post请求
		urlConn.setRequestMethod("POST");
		urlConn.setInstanceFollowRedirects(true);
		// 配置请求Content-Type
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencode");
		// 开始连接
		urlConn.connect();
		// 发送请求参数
		DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
		dos.write(postData);
		dos.flush();
		dos.close();
		// 判断请求是否成功
		if (urlConn.getResponseCode() == HTTP_200) {
			// 获取返回的数据
			byte[] data = readStream(urlConn.getInputStream());
			Log.i(TAG_POST, "Post请求方式成功，返回数据如下：");
			Log.i(TAG_POST, new String(data, "UTF-8"));
		} else {
			Log.i(TAG_POST, "Post方式请求失败");
		}
	}

	// 获取连接返回的数据
	private static byte[] readStream(InputStream inputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		byte[] data = baos.toByteArray();
		inputStream.close();
		baos.close();
		return data;
	}

}