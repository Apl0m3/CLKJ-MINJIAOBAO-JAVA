package com.lingkj.project.loaddown.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpHandler {

	private static final String BOUNDARY = "---------------------------7db1c523809b2";// 数据分割线

	public static boolean uploadHttpURLConnection(File file, String type,
                                                  String id) {
		int ret = 0;
		OutputStream out = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		try {
			// 仿Http协议发送数据方式进行拼接
			StringBuilder sb = new StringBuilder();
			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"username\""
					+ "\r\n");
			sb.append("\r\n");
			sb.append("username" + "\r\n");

			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"password\""
					+ "\r\n");
			sb.append("\r\n");
			sb.append("password" + "\r\n");

			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ file.getName() + "\"" + "\r\n");
			sb.append("Content-Type: image/pjpeg" + "\r\n");
			sb.append("\r\n");

			byte[] before = sb.toString().getBytes("UTF-8");
			byte[] after = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

			URL url = new URL("http://192.168.0.127:8080/filemanage/uploadImg.do");
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("Content-Length", String
					.valueOf(before.length + file.length() + after.length));
			// conn.setRequestProperty("HOST", "192.168.0.44:8080");
			conn.setDoOutput(true);
			out = conn.getOutputStream();
			in = new FileInputStream(file);
			out.write(before);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.write(after);
			ret = conn.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (conn != null)
					conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret == 200;
	}
	
	public static String uploadHttpURLConnection(File file)throws Exception {
		int res = 0;
		OutputStream out = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		StringBuilder sb2 = new StringBuilder();
		try {
			// 仿Http协议发送数据方式进行拼接
			StringBuilder sb = new StringBuilder();
			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"username\""
					+ "\r\n");
			sb.append("\r\n");
			sb.append("unacme1" + "\r\n");

			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"password\""
					+ "\r\n");
			sb.append("\r\n");
			sb.append("123456" + "\r\n");

			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ file.getName() + "\"" + "\r\n");
			sb.append("Content-Type: image/pjpeg" + "\r\n");
			sb.append("\r\n");

			byte[] before = sb.toString().getBytes("UTF-8");
			byte[] after = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

			URL url = new URL("http://192.168.0.116:8080/filemanage/uploadLoginImg.do");
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("Content-Length", String
					.valueOf(before.length + file.length() + after.length));
			// conn.setRequestProperty("HOST", "192.168.0.44:8080");
			conn.setDoOutput(true);
			out = conn.getOutputStream();
			in = new FileInputStream(file);
			out.write(before);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.write(after);
			res = conn.getResponseCode();
			
			InputStream inPutS = null;
			byte[] end_data = ("--" + BOUNDARY + "--" + "\r\n").getBytes();
			out.write(end_data);
			out.flush();
			
			if (res == 200) {
				inPutS = conn.getInputStream();
				int ch;
				while ((ch = inPutS.read()) != -1) {
					sb2.append((char) ch);
				}
			}
			out.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (conn != null)
					conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return sb2.toString();
	}

	/**
	 * 判断文件大小
	 * 
	 * @param f
	 *            文件对象
	 * @return
	 */
	public static long getSize(File f) {
		FileInputStream fis = null;
		long s = 0;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (fis != null) {
				s = fis.available();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s;

	}
}
