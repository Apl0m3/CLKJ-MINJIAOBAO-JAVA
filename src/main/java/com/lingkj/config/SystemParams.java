/**
 * Copyright (c) 2015 CHENGLING, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * CHENGLING, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with CHENGLING.
 */
package com.lingkj.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统参数
 *
 * @author zhangyl
 * @version $Revision$ $Date$
 */

@Component
public class SystemParams {
    /**
     * 文件服务器
     */
    public static String fileServerIp;
    /**
     * 文件应用端口号
     */
    public static String fileServerPort;
    /**
     * 上传文件存储位置
     */
    public static String fileStoreDir;
    /**
     *
     */
    public static String webUrl;
    public static String certPath;
    public static String shellPath;

    /**
     * 文件服务器下载文件Action
     */
    public final static String fileServerDownloadAction = "common/file/download";


    /**
     * 存储文件名的变量名
     */
    public final static String fileServerStoreFNameVName = "storeFileName";

    /**
     * 本地打开上传对话URL
     */
    private static String staticHtmlStoreDir = "D:\\uploads";


    public SystemParams() {
    }

    /**
     * 取得文件管理项目的URl
     *
     * @return
     */
    private static StringBuilder getFileManageProjUrl() {

        return new StringBuilder().append("http://")
                .append(fileServerIp)
                .append(fileServerPort.equals("80") || fileServerPort.equals("") ? "" : ":" + fileServerPort);
    }

    public static StringBuilder getFileShowUrl() {

        return new StringBuilder().append(getFileManageProjUrl()).append("/")
                .append(fileServerDownloadAction)
                .append("?").append(fileServerStoreFNameVName).append("=");
    }

    @Value("${common.file.fileServerIp}")
    private void setFileServerIp(String fileServerIp) {
        SystemParams.fileServerIp = fileServerIp;
    }

    @Value("${common.file.fileServerPort}")
    private void setFileServerPort(String fileServerPort) {
        SystemParams.fileServerPort = fileServerPort;
    }

    @Value("${common.file.fileStoreDir}")
    private void setFileStoreDir(String fileStoreDir) {
        SystemParams.fileStoreDir = fileStoreDir;
    }

    @Value("${common.file.webUrl}")
    private void setWebUrl(String webUrl) {
        SystemParams.webUrl = webUrl;
    }

    @Value("${common.file.certPath}")
    private void setCertPath(String certPath) {
        SystemParams.certPath = certPath;
    }

    @Value("${common.file.shellPath}")
    private void setShellPath(String shellPath) {
        SystemParams.shellPath = shellPath;
    }

}
