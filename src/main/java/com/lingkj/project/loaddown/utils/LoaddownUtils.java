package com.lingkj.project.loaddown.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * <p>LoaddownUtils.java</p> 
 * 
 * @author zhangyl
 * 版本历史 
 * 	   version  Date      Author     Content   
 *     -------- --------- ---------- ------------------------   
 *     1.0      Apr 24, 2011   fwang    最初版本   
 */
public class LoaddownUtils {
	/**
	 * 产生0-9999的随机数
	 * @return
	 */
	public static int getRandomNumOfFName(){
		Random random = new Random();
		return Math.abs(random.nextInt())%10000;
	}
	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 获得存储文件名
	 */
	public static String getStoreFileName(int storeNo, Date pdate, String extendName){
		StringBuilder fnamesb = new StringBuilder();
		fnamesb.append(storeNo)//存储仓库号
		.append(format(pdate, "yyyyMMddHHmmss"))//时间格式：年月日时分秒
		.append(getRandomNumOfFName())//随机数 
		.append(".")
		.append(extendName);//扩展名
		
		//DecimalFormat df = new DecimalFormat("0000");
		
		return fnamesb.toString();
	}
	/**
	 * 获得存储APK文件名
	 */
	public static String getStoreAPKFileName(int storeNo, Date pdate, String extendName){
		StringBuilder fnamesb = new StringBuilder();
		fnamesb.append(storeNo)//存储仓库号
		.append(format(pdate, "yyyyMMdd"))//时间格式：年月日时分秒
		.append(getRandomNumOfFName())//随机数 
		.append("_dada")//项目名
		.append(".")
		.append(extendName);//扩展名
		
		//DecimalFormat df = new DecimalFormat("0000");
		
		return fnamesb.toString();
	}
	/**
	 * 得到文件的扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		
		return org.apache.commons.lang.StringUtils.substringAfterLast(fileName, ".");
	}
	/**
	 * 获取文件名，去掉扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFNameWithExt(String fileName){
		
		return org.apache.commons.lang.StringUtils.substringBeforeLast(fileName, ".");
	}

	/**
	 * 取得时间的目录路径
	 * @param pdate 被格式分目录的日期
	 * @return 返回传入日期yyyy/MM/dd格式化的三级目录
	 */
	public static String getDateDirName(Date pdate){
		String str1 = format(pdate, "yyyy/MM/dd");
		return str1;
	}
	/**
	 * 创建存储文件的目录
	 * 如果存在就不创建
	 * @param fullFilePath 存储文件路径
	 */
	public static void createFileDirs(String fullFilePath){
		File tt = new File(fullFilePath);
		if(!tt.exists()){
			tt.mkdirs();
		}
		
	}
	/**
	 * 创建时间的目录
	 * 如果存在就不创建
	 * @param dateDirPath 日期相对根目录的路径
	 */
	public static void createFileDirsByDate(int storeNo,String dateDirPath){
		File tt = new File(composeStoreCatalogFullPath(storeNo,dateDirPath));
		if(!tt.exists()){
			tt.mkdirs();
		}
		
	}
	/**
	 * 获取存储目录的完整路径
	 * @param pathDate 日期相对根目录的路径
	 */
	public static String composeStoreCatalogFullPath(int storeNo, String pathDate){
		
		return    DownloadParams.getStorePathByNo(storeNo)+File.separator+pathDate;
	}
	/**
	 * 从存储文件解析出文件实际存储的全路径
	 * @param storeFName
	 * @return
	 */
	public static String parseFullFileNameFromStoreFName(String storeFName){
		String fullFPath = composeStoreCatalogFullPath(pickRootCataArrno(storeFName),pickDatePathFromStoreFilename(storeFName));
		String fullFName = fullFPath+File.separator+storeFName;
		return fullFName;
	}
	
	/**
	 * 取得缩略备份图片的路径
	 * @param storeFName
	 * @return
	 * 
	 * */
	public static String parseFullThumbnailStoreFName(String storeFName){
		String fullFPath = composeStoreCatalogFullPath(pickRootCataArrno(storeFName),pickDatePathFromStoreFilename(storeFName));
		String fullFName = fullFPath+File.separator+DownloadParams.getImgFolder()+File.separator+storeFName;
		return fullFName;
	}
	
	/**
	 * 从存储文件名获取存储文件的相对根的相对目录
	 * @param storeRealName 存储文件名
	 * @return
	 */
	public static String pickDatePathFromStoreFilename(String storeRealName){
		if(null != storeRealName && !"".equals(storeRealName)){
		String yearStr = storeRealName.substring(1, 5);
		String monthStr = storeRealName.substring(5, 7);
		String dayStr = storeRealName.substring(7,9);
		return yearStr+File.separator+monthStr +File.separator+dayStr;
		}
		else
			return "";
	}
	/**
	 * 从从存储文件名中获取根目录索引号
	 * @param storeRealName 存储文件名
	 * @return
	 */
	public static int pickRootCataArrno(String storeRealName){
		return (null == storeRealName || "".equals(storeRealName))?0:Integer.valueOf(storeRealName.substring(0,1)).intValue();
	}
	/**
     * 给URL参数解码（UTF－8）
     * @param str
     * @return
     */
	public static String decodeURLStringToUTF8(String str){
		String srtn = "";
		
		try {
			srtn = URLDecoder.decode(str.replaceAll("@", "%"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return srtn;
	}
	/**
	 * 把上传的文件copy到相应路径中
	 * @param srcMFile
	 * @param destFile
	 * @throws IOException
	 */
	public static  void copyFile(MultipartFile srcMFile, File destFile) throws IOException {
		if(!srcMFile.isEmpty()){
			if (isImage(destFile)){
				Thumbnails.of(srcMFile.getInputStream()).scale(1f).outputQuality(0.5f).toFile(destFile);
			}else{
				FileOutputStream os=new FileOutputStream(destFile);
				FileCopyUtils.copy(srcMFile.getInputStream(), os);
			}
		}

	}

	/**
	 * 判断文件是否是图片
	 * @param file
	 * @return
	 */
	public static boolean isImage(File file) {
		if (!file.exists()) {
			return false;
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断是否是图片类型
	 * 
	 * @return boolean
	 * */
	public static boolean checkImgType(String fileExt){
		boolean bl = false;
		String[] collectExt = ("jpg,bmp,tga,vst,pcd,pct,gif,ai,fpx,img,cal,wi,png").split(",");
		for(String s : collectExt){
			if(fileExt.toLowerCase().equals(s)){
				bl = true;
				break;
			}
		}
		return bl;
	}
	/**
	 * 是否是可以写的图片类型
	 * @param fname
	 * @return
	 */
	public static boolean isCanWriteImage(String fname){
		String pattern="(.*)(\\.jpg|\\.bmp|\\.gif|\\.png|\\.jpeg)$";
		Pattern p = Pattern.compile(pattern);
		 Matcher m = p.matcher(fname.toLowerCase());
		 return m.matches();
	}
	/**
	 * 保存图片,并返回存储名
	 *
	 * @param oldPath 原图片路劲
	 * @return
	 */
	public static String saveNewImg(String oldPath) throws IOException {
		if (oldPath == null)
			return null;
		Date curDate = new Date();
		int curStoreNo = DownloadParams.getCurrentStoreNo();
		String strPathDate = LoaddownUtils.getDateDirName(curDate);
		String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
		File storePath = new File(fullFilePath);
		if (!storePath.exists()) {
			storePath.mkdirs();
		}
		//产生存储文件名
		String storeFileName = LoaddownUtils.getStoreFileName(curStoreNo, curDate, "png");
		String storeFullPath = fullFilePath + File.separator + storeFileName;
		IOUtils.copy(new FileInputStream(oldPath), new FileOutputStream(storeFullPath));
		return storeFileName;
	}
}
