package com.lingkj.project.loaddown.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class HttpFileUpTool {
	//protected static final String url = "http://192.168.1.2:8585/web/UploadImage";

	public static String post(String url, Map<String, String> params, Map<String, File> files) throws IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA+ ";boundary=" + BOUNDARY);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		if (files != null) {
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\""+ file.getKey() + "\"; filename=\""+ file.getValue().getName() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is=null;
				System.out.println("原图大小为:"+getSize(file.getValue()));
				if(getSize(file.getValue())>=51200){ //50KB
//					 BitmapFactory.Options options = new BitmapFactory.Options();
//					 options.inJustDecodeBounds = true;
//				     BitmapFactory.decodeFile(file.getValue().getAbsolutePath(), options);
//				     options.inSampleSize=FileUtil.computeSampleSize(options,-1, 500*500);
//					 options.inJustDecodeBounds = false;
//					 Bitmap bmpa = BitmapFactory.decodeFile(file.getValue().getAbsolutePath(), options);  
//					 is=Bitmap2IS(bmpa);
//					 System.out.println("压缩后图片像素为:"+bmpa.getWidth()+":"+bmpa.getHeight());
					 System.out.println("图片过大，已将其压缩!");
				}else{ 
					is = new FileInputStream(file.getValue());
					
				}
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		}
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		StringBuilder sb2 = new StringBuilder();
		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2.toString();
	}
	/**
	 * 判断文件大小
	 * @param f 文件对象
	 * @return
	 */
	public static long getSize(File f){
   	 FileInputStream fis = null;
   	 long s = 0;
			try {
				fis = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fis!=null){
					s= fis.available();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return s;
   	
   }
//	private static InputStream  Bitmap2IS(BeanMap bm){  
//	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//	    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);  
//	    InputStream sbs = new ByteArrayInputStream(baos.toByteArray());  
//	    System.out.println("压缩后图片大小为:"+baos.size());
//	    return sbs;  
//	} 
}
