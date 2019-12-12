/* =============================================
 * Created [Apr 2, 2011 5:52:25 PM] by zhangyl
 * =============================================
 *
 * 钢运在线
 *
 * =============================================
 *  Copyright (c) 2006 Dczg, Inc.
 *  All rights reserved
 * =============================================
 */
package com.lingkj.project.loaddown.action;

import com.lingkj.common.utils.R;
import com.lingkj.project.loaddown.utils.LoaddownUtils;
import com.lingkj.config.SystemParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author zhangyl 版本历史
 * version   Date        Author     Content
 * -------- ---------   ---------- ------------------------
 * 1.0      Apr 2, 2011 zhangyl    最初版本
 * 1.1      2011-6-22   yzhimportFileUrl
 */
@RequestMapping("/common/file/*")
@Controller
public class LoadDownAction {
    private static final Pattern RANGE_PATTERN = Pattern.compile("/bytes=(\\d*)-(\\d*)/");
    private Log log = LogFactory.getLog(this.getClass());
    private static Integer REP_STATUS_OK = Integer.valueOf(1);//成功
    private static Integer curStoreNo = 0;
    private static Integer REP_STATUS_ERR = Integer.valueOf(0);//失败
    private static String FILE_INFO_STOREFNAME_SPLIT_MARK = ",";//存储文件之间的分隔符
    private static String FILE_INFO_SPLIT_MARK = "###";//上传后响应信息项之间的分隔符,上传状态###存储文件名(可能是多个)###信息

    @RequestMapping("/uploads")
    @ResponseBody
    public String uploads(HttpServletRequest request) throws IOException {
        /*
         * 文件名格式：storeNo(存储仓库编号1位)+yyyyMMddHHmmss（时间：年月日时分秒）+0-9999随机数
         * 1.根据时间产生存储目录
         * 2.根据上传的每个文件产生存储文件名
         * 3.保存文件到存储目录
         */
        Date curDate = new Date();
        int curStoreNo = RandomUtils.nextInt(9);
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        String errInfo = "";
        int reqStatus = REP_STATUS_OK;
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //提示信息
        StringBuilder rtnsb = new StringBuilder();
        StringBuilder storeFNamesb = new StringBuilder();//返回给上传客户的存储文件名
        String path = fullFilePath;
        File folder = new File(fullFilePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile orifile = multipartRequest.getFile(key);
                String orgiFName = orifile.getOriginalFilename();
                if (orifile.getOriginalFilename().length() > 0) {
                    //产生存储文件名
                    String strFileExt = LoaddownUtils.getFileExt(orgiFName);
                    String storeFileName = LoaddownUtils.getStoreFileName(curStoreNo, curDate, strFileExt);
                    File storeFile = new File(fullFilePath + File.separator + storeFileName);
                    if (storeFile.exists()) {
                        storeFile.delete();
                    }
                    if (storeFNamesb.length() == 0) {
                        storeFNamesb.append(storeFileName);
                    } else {
                        storeFNamesb.append(FILE_INFO_STOREFNAME_SPLIT_MARK)
                                .append(storeFileName);
                    }
                    LoaddownUtils.copyFile(orifile, storeFile);
                    String imgDist = path + File.separator + storeFileName;
                    LoaddownUtils.copyFile(orifile, new File(imgDist));
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            reqStatus = REP_STATUS_ERR;
            errInfo = e.getMessage();
        }
        rtnsb.append(reqStatus)
                .append(FILE_INFO_SPLIT_MARK)
                .append(storeFNamesb.toString())
                .append(FILE_INFO_SPLIT_MARK)
                .append(errInfo);
        return rtnsb.toString();
    }

    /**
     * 上传图片
     *
     * @param request
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public R uploadImg(HttpServletRequest request) {
        /*
         *	上传图片时，根据应用端的设置判断是否添加水印，
         *  如果水印图片不存在，不加水印，如果没有文字也不加文字水印
         *  不进行图片的缩放
         */

        Map result = getMap((MultipartHttpServletRequest) request);

        return R.ok().put("result", result);
    }

    /**
     * 上传图片（ueditor）  图片压缩
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadUEImg")
    @ResponseBody
    public R uploadUEImg(HttpServletRequest request, HttpServletResponse response) {
        /*
         *	上传图片时，根据应用端的设置判断是否添加水印，
         *  如果水印图片不存在，不加水印，如果没有文字也不加文字水印
         *  不进行图片的缩放
         */
        Map result = getMap((MultipartHttpServletRequest) request);

        return R.ok().put("result", result);
    }

    /**
     * 上传图片（kindeditor）
     *
     * @param request
     * @param response 上传时的参数
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadKEImg")
    @ResponseBody
    public Map<String, Object> uploadKEImg(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> result = new HashMap<String, Object>();
        Date curDate = new Date();
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //提示信息
        StringBuilder storeFNamesb = new StringBuilder();//返回给上传客户的存储文件名
        try {
            uploadFile(curDate, fullFilePath, multipartRequest, storeFNamesb);

        } catch (Exception e) {
            result.put("error", 1);
            result.put("url", "");
            result.put("message", "上传文件失败");
        }
        result.put("error", 0);
        result.put("url", SystemParams.getFileShowUrl().toString() + storeFNamesb);
        result.put("message", storeFNamesb + "");
        return result;
    }

    private Map getMap(MultipartHttpServletRequest request) {
        Date curDate = new Date();
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        MultipartHttpServletRequest multipartRequest = request;

        //返回给上传客户的存储文件名
        StringBuilder storeFNamesb = new StringBuilder();
        String orgiFName = "";
        try {
            uploadFile(curDate, fullFilePath, multipartRequest, storeFNamesb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map result = new HashMap();
        // UEDITOR的规则:不为SUCCESS则显示state的内容
        result.put("state", "SUCCESS");
        result.put("title", storeFNamesb);
        result.put("original", orgiFName);
        return result;
    }


    /**
     * 上传图片多张(ajaxFileUpload上传)
     *
     * @param request
     * @param
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadMultiImg")
    @ResponseBody
    public R uploadMultiImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        /*
         *	上传图片时，根据应用端的设置判断是否添加水印，
         *  如果水印图片不存在，不加水印，如果没有文字也不加文字水印
         *  不进行图片的缩放
         */
        Date curDate = new Date();
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String orgiFName = "";
        try {
            List<MultipartFile> files = multipartRequest.getFiles("files");
            for (MultipartFile orifile : files) {
                orgiFName = orifile.getOriginalFilename();
                if (orifile.getOriginalFilename().length() > 0) {
                    Map result = new HashMap();
                    //产生存储文件名
                    String strFileExt = LoaddownUtils.getFileExt(orgiFName);
                    String storeFileName = LoaddownUtils.getStoreFileName(curStoreNo, curDate, strFileExt);
                    String storeFullPath = fullFilePath + File.separator + storeFileName;
                    File storeFile = new File(storeFullPath);
                    if (storeFile.exists()) {
                        storeFile.delete();
                    }
                    LoaddownUtils.copyFile(orifile, storeFile);

                    result.put("title", storeFileName);
                    list.add(result);
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


        return R.ok().put("result", list);
    }


    /**
     * 上传APK
     *
     * @param request 上传时的参数
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadAPKFile")
    @ResponseBody
    public Map<String, Object> uploadAPKFile(HttpServletRequest request, HttpServletResponse response) {
        /*
         *	上传图片时，根据应用端的设置判断是否添加水印，
         *  如果水印图片不存在，不加水印，如果没有文字也不加文字水印
         *  不进行图片的缩放
         */
        Date curDate = new Date();
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        //返回给上传客户的存储文件名
        StringBuilder storeFNamesb = new StringBuilder();
        String orgiFName = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        try {
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile orifile = multipartRequest.getFile(key);
                orgiFName = orifile.getOriginalFilename();
                if (orifile.getOriginalFilename().length() > 0) {
                    //产生存储文件名
                    String strFileExt = LoaddownUtils.getFileExt(orgiFName);
                    String storeFileName = LoaddownUtils.getStoreAPKFileName(curStoreNo, curDate, strFileExt);
                    String storeFullPath = fullFilePath + File.separator + storeFileName;
                    File storeFile = new File(storeFullPath);
                    if (storeFile.exists()) {
                        storeFile.delete();
                    }
                    if (storeFNamesb.length() == 0) {
                        storeFNamesb.append(storeFileName);
                    } else {
                        storeFNamesb.append(FILE_INFO_STOREFNAME_SPLIT_MARK)
                                .append(storeFileName);
                    }
                    LoaddownUtils.copyFile(orifile, storeFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map result = new HashMap();
        // UEDITOR的规则:不为SUCCESS则显示state的内容
        result.put("state", "SUCCESS");
        result.put("title", storeFNamesb + "");
        result.put("original", orgiFName + "");

        return result;
    }


    private void uploadFile(Date curDate, String fullFilePath, MultipartHttpServletRequest multipartRequest, StringBuilder storeFNamesb) throws IOException {
        String orgiFName;
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
            String key = (String) it.next();
            MultipartFile orifile = multipartRequest.getFile(key);
            orgiFName = orifile.getOriginalFilename();
            if (orifile.getOriginalFilename().length() > 0) {
                //产生存储文件名
                File storeFile = getFile(curDate, fullFilePath, storeFNamesb, orgiFName, File.separator);
                LoaddownUtils.copyFile(orifile, storeFile);
            }
        }
    }

    private File getFile(Date curDate, String fullFilePath, StringBuilder storeFNamesb, String orgiFName, String separator) {
        String strFileExt = LoaddownUtils.getFileExt(orgiFName);
        String storeFileName = LoaddownUtils.getStoreFileName(curStoreNo, curDate, strFileExt);
        String storeFullPath = fullFilePath + separator + storeFileName;
        File storeFile = new File(storeFullPath);
        if (storeFile.exists()) {
            storeFile.delete();
        }
        if (storeFNamesb.length() == 0) {
            storeFNamesb.append(storeFileName);
        } else {
            storeFNamesb.append(FILE_INFO_STOREFNAME_SPLIT_MARK)
                    .append(storeFileName);
        }
        return storeFile;
    }


    /**
     * 根据存储文件名删除文件
     *
     * @param storeFileName
     * @param response
     */
    @RequestMapping("/deleteFile")
    public void delele(String storeFileName, HttpServletResponse response) {
        String s = "删除成功！";
        File file = new File(LoaddownUtils.parseFullFileNameFromStoreFName(storeFileName));
        boolean bl = false;
        if (file.exists()) {
            file.delete();
            bl = true;
        }
        File thumbnailFile = new File(LoaddownUtils.parseFullThumbnailStoreFName(storeFileName));
        if (thumbnailFile.exists()) {
            thumbnailFile.delete();
            bl = true;
        }
        if (bl) {
            s = "文件已不存在！删除失败！";
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载文件
     *
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(String storeFileName, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotBlank(storeFileName) && !storeFileName.equals("null") && !storeFileName.equals("undefined")) {

            String fullFName = LoaddownUtils.parseFullFileNameFromStoreFName(storeFileName);

            response.reset();
            response.setContentType("applicatoin/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            File file = new File(fullFName);
            //如果文件不存在就返回
            if (!file.exists()) {
                return;
            }

            response.setHeader("Content-Disposition", "attachment; filename=" + new String(storeFileName.getBytes("ISO-8859-1"), "UTF-8"));
            response.setContentLength((int) file.length());

            outStream(response, file);
        }

    }

    /**
     * 下载视频文件
     *
     * @throws Exception
     */
    @RequestMapping("/downloadVideo")
    public void downloadVideo(String storeFileName, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (StringUtils.isNotBlank(storeFileName)) {
            String fullFName = LoaddownUtils.parseFullFileNameFromStoreFName(storeFileName);
            File file = new File(fullFName);
            //只读模式
            RandomAccessFile randomFile = null;
            try {
                randomFile = new RandomAccessFile(file, "r");
                long contentLength = randomFile.length();
                String range = request.getHeader("Range");
                int start = 0, end = 0;
                if (range != null && range.startsWith("bytes=")) {
                    String[] values = range.split("=")[1].split("-");
                    start = Integer.parseInt(values[0]);
                    if (values.length > 1) {
                        end = Integer.parseInt(values[1]);
                    }
                }
                int requestSize = 0;
                if (end != 0 && end > start) {
                    requestSize = end - start + 1;
                } else {
                    requestSize = Integer.MAX_VALUE;
                }

                response.setContentType("video/mp4");
                response.setHeader("Accept-Ranges", "bytes");
                response.setHeader("ETag", storeFileName);
                response.setHeader("Last-Modified", new Date().toString());
                //第一次请求只返回content length来让客户端请求多次实际数据
                if (range == null) {
                    response.setHeader("Content-length", contentLength + "");
                } else {
                    //以后的多次以断点续传的方式来返回视频数据 //206
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    long requestStart = 0, requestEnd = 0;
                    String[] ranges = range.split("=");
                    if (ranges.length > 1) {
                        String[] rangeDatas = ranges[1].split("-");
                        requestStart = Integer.parseInt(rangeDatas[0]);
                        if (rangeDatas.length > 1) {
                            requestEnd = Integer.parseInt(rangeDatas[1]);
                        }
                    }
                    long length = 0;
                    if (requestEnd > 0) {
                        length = requestEnd - requestStart + 1;
                        response.setHeader("Content-length", "" + length);
                        response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
                    } else {
                        length = contentLength - requestStart;
                        response.setHeader("Content-length", "" + length);
                        response.setHeader("Content-Range", "bytes " + requestStart + "-" + (contentLength - 1) + "/" + contentLength);
                    }
                }
                ServletOutputStream out = response.getOutputStream();
                int needSize = requestSize;
                randomFile.seek(start);
                while (needSize > 0) {
                    byte[] buffer = new byte[4096];
                    int len = randomFile.read(buffer);
                    if (needSize < buffer.length) {
                        out.write(buffer, 0, needSize);
                    } else {
                        out.write(buffer, 0, len);
                        if (len < buffer.length) {
                            break;
                        }
                    }
                    needSize -= buffer.length;
                }
                randomFile.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void outStream(HttpServletResponse response, File file) throws IOException {
        FileInputStream fi = null;
        ServletOutputStream os = null;
        byte[] b = new byte[2000];
        try {
            fi = new FileInputStream(file);
            os = response.getOutputStream();
            while (fi.read(b) > 0) {
                os.write(b);
            }
            os.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        } finally {
            if (os != null) {
                os.close();
            }
            if (fi != null) {
                fi.close();
            }
        }
    }


    /**
     * 下载PDF文件
     *
     * @param
     * @throws Exception
     */
    @RequestMapping("/downloadPDF")
    public void downloadPDF(String storeFileName, String trueFileName, String flag, HttpServletResponse response) throws Exception {
        if (null != storeFileName && !"".equals(storeFileName)) {
            String fullFName = "";
            if (null == flag || "1".equals(flag))
                fullFName = LoaddownUtils.parseFullFileNameFromStoreFName(storeFileName);
            else
                fullFName = LoaddownUtils.parseFullThumbnailStoreFName(storeFileName);

            response.reset();
            response.setContentType("application/pdf;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String docName = (trueFileName == null || trueFileName.equals("")) ? storeFileName : java.net.URLEncoder.encode(trueFileName, "UTF-8");

            File file = new File(fullFName);
            //如果文件不存在就返回
            if (!file.exists()) {
                return;
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(docName.getBytes("ISO-8859-1"), "UTF-8"));
            response.setHeader("Connection", "close");
            outStream(response, file);
        }

    }

    /**
     * 上传视频
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadVideo")
    @ResponseBody
    public R uploadVideo(HttpServletRequest request, HttpServletResponse response) {
        /*
         *	上传图片时，根据应用端的设置判断是否添加水印，
         *  如果水印图片不存在，不加水印，如果没有文字也不加文字水印
         *  不进行图片的缩放
         */
        Date curDate = new Date();
        String strPathDate = LoaddownUtils.getDateDirName(curDate);
        String fullFilePath = LoaddownUtils.composeStoreCatalogFullPath(curStoreNo, strPathDate);
        //检查存储目录，没有要创建
        LoaddownUtils.createFileDirs(fullFilePath);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //返回给上传客户的存储文件名
        StringBuilder storeFNamesb = new StringBuilder();

        String orgiFName = "";
        try {
            for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = (String) it.next();
                MultipartFile orifile = multipartRequest.getFile(key);
                orgiFName = orifile.getOriginalFilename();
                if (orifile.getOriginalFilename().length() > 0) {
                    //产生存储文件名
                    File storeFile = getFile(curDate, fullFilePath, storeFNamesb, orgiFName, "/");
                    LoaddownUtils.copyFile(orifile, storeFile);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map result = new HashMap();
        // UEDITOR的规则:不为SUCCESS则显示state的内容
        result.put("state", "SUCCESS");
        result.put("url", fullFilePath + storeFNamesb);
        result.put("title", "/upload" + "/" + strPathDate + "/" + storeFNamesb);
        result.put("original", orgiFName);
        return R.ok().put("result", result);
    }

}
