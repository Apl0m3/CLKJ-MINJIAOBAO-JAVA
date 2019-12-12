package com.lingkj.project.loaddown.utils;

import com.lingkj.config.SystemParams;

import java.util.HashMap;
import java.util.Map;

public class DownloadParams {
	private static String[] fileStoreDirs;
	public static String fileServerRemoteIp = "localhost";

	public static String fileServerRemotePort = "8080";
	// 1[同一服务器]2【不同服务器】
	public static String fileServerIp = "localhost";
	public static String fileServerState = "1";
	public static String fileServerPort = "8080";
	public static String fileServerProject = "filemanage";
	public static String fileServerUploadAction = "toUpload.do";
	public static String fileServerDeleteAction = "toUpload.do";
	public static String imgFolder = "thumbnail";
	public static String imgWidth = "320";
	public static String imgHeight = "256";
	public static String imgWidthHeigth = "";

	/**
	 * @return the imgWidth
	 */
	public static String getImgWidth(String imgType) {
		return getWidthHeigthByType(imgType)==null?imgWidth:String.valueOf(getWidthHeigthByType(imgType).get("width"));
	}

	/**
	 * @return the imgHeight
	 */
	public static String getImgHeight(String imgType) {
		return getWidthHeigthByType(imgType)==null?imgHeight:String.valueOf(getWidthHeigthByType(imgType).get("heigth"));
	}

	/**
	 * @return the imgWidthHeigth
	 */
	public static String getImgWidthHeigth() {
		return imgWidthHeigth;
	}

	/**
	 * @param imgWidthHeigth the imgWidthHeigth to set
	 */
	public void setImgWidthHeigth(String imgWidthHeigth) {
		DownloadParams.imgWidthHeigth = imgWidthHeigth;
	}

	/**
	 * @return the imgFolder
	 */
	public static String getImgFolder() {
		return imgFolder;
	}

	/**
	 * @param imgFolder the imgFolder to set
	 */
	public void setImgFolder(String imgFolder) {
		DownloadParams.imgFolder = imgFolder;
	}


	/**
	 * 取得图片缩放比例集合
	 *
	 *
	 * */
	private static Map getImgWidthHeigthMap(){
		Map map = null;
		String[] img = imgWidthHeigth.split(";");
		if(img.length > 0){
			map = new HashMap();
			for(String type : img){
				if(type!=null&&!"".equals(type)){
					String[] strType = type.split(":");
					if(strType.length > 0){
						Map temp = new HashMap();
						if(!strType[1].equals("")){
							String[] str = strType[1].split("[*]");
							temp.put("width", Integer.parseInt(str[0]));
							temp.put("heigth", Integer.parseInt(str[1]));
						}
						map.put(strType[0], temp);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 根据类型取得宽与高的集合
	 *
	 * @RETURN map
	 * **/
	public static Map getWidthHeigthByType(String imgType){
		return getImgWidthHeigthMap()==null?null:(HashMap)getImgWidthHeigthMap().get(imgType);
	}

	/**
	 * 加载上传文件存储仓库工作目录
	 * @param fileStorePaths
	 */
	public void setFileStorePaths(String fileStorePaths) {
		String[] tmpArr1 = fileStorePaths.split(";");
		this.fileStoreDirs = new String[tmpArr1.length];
		for(int ti =0;ti< tmpArr1.length;ti++){
			this.fileStoreDirs[ti]=tmpArr1[ti];
		}
	}
	/**
	 * 取得指定序号的存储仓库工作路径
	 * @param seqno
	 * @return
	 */
	public static String getStorePathByNo(int seqno){
		String fileStorePaths = SystemParams.fileStoreDir;
		String[] tmpArr1 = fileStorePaths.split(";");
		fileStoreDirs = new String[tmpArr1.length];
		for(int ti =0;ti< tmpArr1.length;ti++){
		    fileStoreDirs[ti]=tmpArr1[ti];
		}
		return fileStoreDirs[seqno];
	}

	/**
	 * 取得当前存储仓库号
	 */
	public static int getCurrentStoreNo(){
		/*
		 * 先不判断磁盘是否有足够的空间
		 */
		/*
		File[] roots = File.listRoots();//获取磁盘分区列表
        for(int i=1;i<roots.length;i++){
        	if(size<roots[i].getFreeSpace()){
            	return i;
            }
        }*/
		//默认返回第一个存储仓库
		return 0;
	}
	/**
	 * 通过文件名得到存储仓库目录
	 * @return
	 */
	public static String getCurrentStroreDir(){
		return getStorePathByNo(getCurrentStoreNo());
	}

	/**
	 * 取得URl
	 *
	 * @return
	 */
	private static String getFileManageUrl() {
		return new StringBuilder().append("http://")
		.append(fileServerIp)
				.append(
						fileServerPort.equals("80")
								|| fileServerPort.equals("") ? "" : ":"
								+ fileServerPort).append(
						fileServerProject.equals("") ? "" : "/"
								+ fileServerProject).toString();
	}

	/**
	 * 取得远端URl
	 *
	 * @return
	 */
	private static String getFileManageRmoteUrl() {
		return new StringBuilder().append("http://")
		.append(fileServerRemoteIp)
				.append(
						fileServerRemotePort.equals("80")
								|| fileServerRemotePort.equals("") ? "" : ":"
								+ fileServerRemotePort).append(
						fileServerProject.equals("") ? "" : "/"
								+ fileServerProject).toString();
	}

	public static String getFileManageProjectUrl(){
		return (fileServerState.equals("")||fileServerState.equals("1"))?getFileManageUrl().toString():getFileManageRmoteUrl().toString();
	}

	/**
	 * 取得上传ACTION
	 *
	 * @return
	 * */
	public static String getUploadActionUrl(){
		return getFileManageProjectUrl() + "/" + fileServerUploadAction;
	}

	/**
	 * 取得删除ACTION
	 *
	 * @return
	 * */
	public static String getDeleteActionUrl(){
		return getFileManageProjectUrl() + "/" + fileServerDeleteAction;
	}

	/**
	 * @return the fileServerRemoteIp
	 */
	public String getFileServerRemoteIp() {
		return fileServerRemoteIp;
	}

	/**
	 * @param fileServerRemoteIp the fileServerRemoteIp to set
	 */
	public void setFileServerRemoteIp(String fileServerRemoteIp) {
		DownloadParams.fileServerRemoteIp = fileServerRemoteIp;
	}

	/**
	 * @return the fileServerRemotePort
	 */
	public String getFileServerRemotePort() {
		return fileServerRemotePort;
	}

	/**
	 * @param fileServerRemotePort the fileServerRemotePort to set
	 */
	public void setFileServerRemotePort(String fileServerRemotePort) {
		DownloadParams.fileServerRemotePort = fileServerRemotePort;
	}

	/**
	 * @return the fileServerState
	 */
	public String getFileServerState() {
		return fileServerState;
	}

	/**
	 * @param fileServerState the fileServerState to set
	 */
	public void setFileServerState(String fileServerState) {
		DownloadParams.fileServerState = fileServerState;
	}

	/**
	 * @return the fileServerIp
	 */
	public String getFileServerIp() {
		return fileServerIp;
	}

	/**
	 * @param fileServerIp the fileServerIp to set
	 */
	public void setFileServerIp(String fileServerIp) {
		DownloadParams.fileServerIp = fileServerIp;
	}

	/**
	 * @return the fileServerPort
	 */
	public String getFileServerPort() {
		return fileServerPort;
	}

	/**
	 * @param fileServerPort the fileServerPort to set
	 */
	public void setFileServerPort(String fileServerPort) {
		DownloadParams.fileServerPort = fileServerPort;
	}

	/**
	 * @return the fileServerProject
	 */
	public String getFileServerProject() {
		return fileServerProject;
	}

	/**
	 * @param fileServerProject the fileServerProject to set
	 */
	public void setFileServerProject(String fileServerProject) {
		DownloadParams.fileServerProject = fileServerProject;
	}
	/**
	 * @return the fileServerUploadAction
	 */
	public String getFileServerUploadAction() {
		return fileServerUploadAction;
	}

	/**
	 * @param fileServerUploadAction the fileServerUploadAction to set
	 */
	public void setFileServerUploadAction(String fileServerUploadAction) {
		DownloadParams.fileServerUploadAction = fileServerUploadAction;
	}

	/**
	 * @return the fileServerDeleteAction
	 */
	public String getFileServerDeleteAction() {
		return fileServerDeleteAction;
	}

	/**
	 * @param fileServerDeleteAction the fileServerDeleteAction to set
	 */
	public void setFileServerDeleteAction(String fileServerDeleteAction) {
		DownloadParams.fileServerDeleteAction = fileServerDeleteAction;
	}
}
