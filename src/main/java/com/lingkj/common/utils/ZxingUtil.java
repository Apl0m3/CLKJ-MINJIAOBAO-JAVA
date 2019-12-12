package com.lingkj.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jojo on 2018/7/19 13:08.
 */
public class ZxingUtil {

    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    public static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }

    /**
     * 生成二维码bufferedImage图片
     *
     * @param content       编码内容
     * @param barcodeFormat 编码类型
     * @param width         图片宽度
     * @param height        图片高度
     * @param hints         设置参数
     * @return
     */
    public static BufferedImage getQRCODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {
        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 设置 logo
     *
     * @param matrixImage 源二维码图片
     * @param logoStream
     * @return 返回带有logo的二维码图片
     * @throws IOException
     * @author Administrator sangwenhao
     */
    public static BufferedImage LogoMatrix(BufferedImage matrixImage, InputStream logoStream) throws IOException {
        /**
         * 读取二维码图片，并构建绘图对象
         */
        Graphics2D g2 = matrixImage.createGraphics();

        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();

        int logoWidth = matrixWidth / 5;
        int logoHeight = matrixHeigh / 5;

        /**
         * logo放在中心
         */
        int x = (matrixWidth - logoWidth) / 2;
        int y = (matrixHeigh - logoHeight) / 2;

        /**
         * 读取Logo图片
         */
        BufferedImage logo = ImageIO.read(logoStream);
        BufferedImage zoom = zoom(logoWidth, logoHeight, logo);


//        BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
//        g2.setStroke(stroke);// 设置笔画对象
//        //指定弧度的圆角矩形
//        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
//        g2.setColor(Color.white);
//        g2.draw(round);// 绘制圆弧矩形

        //填充实心圆弧矩形
        g2.fillRoundRect(x - 5, y - 5, logoWidth + 10, logoHeight + 10, 20, 20);

        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 20, 20);
        g2.setColor(new Color(179, 183, 194));
        g2.draw(round2);// 绘制圆弧矩形

        //开始绘制图片
        g2.drawImage(zoom, x, y, logoWidth, logoHeight, null);//绘制
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    /**
     * 图片缩放.
     *
     * @param width  须要的宽度
     * @param height 须要的高度
     * @throws Exception
     */
    public static BufferedImage zoom(int width, int height, BufferedImage bi) {
        double sx = 0.0;
        double sy = 0.0;

        // 计算x轴y轴缩放比例--如需等比例缩放，在调用之前确保參数width和height是等比例变化的
        sx = (double) width / bi.getWidth();
        sy = (double) height / bi.getHeight();

        AffineTransformOp op = new AffineTransformOp(
                AffineTransform.getScaleInstance(sx, sy), null);
        BufferedImage zoomImage = op.filter(bi, null);
        return zoomImage;
    }

    public static void Encode_QR_CODE(String content, InputStream logStream, OutputStream os) throws IOException, WriterException {
        int width = 400; // 二维码图片宽度 300
        int height = 400; // 二维码图片高度300
        // 生成二维码
        BufferedImage bi = getQRCODEBufferedImage(content, BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
        //添加logo
        BufferedImage logImage = LogoMatrix(bi, logStream);
        ImageIO.write(logImage, "png", os);

    }

    public static void Invite_QR_CODE(String content, OutputStream os) throws IOException, WriterException {
        int width = 400; // 二维码图片宽度 300
        int height = 400; // 二维码图片高度300
        // 生成二维码
        BufferedImage bi = getQRCODEBufferedImage(content, BarcodeFormat.QR_CODE, width, height, getDecodeHintType());
        ImageIO.write(bi, "png", os);
    }

}
