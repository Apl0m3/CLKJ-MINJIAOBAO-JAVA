package com.lingkj.project.api.transaction.controller;

import com.alibaba.fastjson.JSONObject;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.payment.alipay.util.AlipayNotify;
import com.lingkj.common.utils.payment.visa.VisaUtils;
import com.lingkj.config.AliPayConfig;
import com.lingkj.project.operation.entity.OperatePaymentMethod;
import com.lingkj.project.operation.service.PaymentMethodService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sis.redsys.api.ApiMacSha256;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * CallbackController
 *
 * @author chen yongsong
 * @className CallbackController
 * @date 2019/9/16 14:53
 */
@RestController
@Slf4j
@RequestMapping("/api/callback")
public class ApiCallbackController {

    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private TransactionRecordService transactionRecordService;

    /**
     * 支付宝 回调
     *
     * @return void
     * @throws
     * @Title: alipaynotify
     * @author zhangxy
     */
    @RequestMapping(value = "/aliCallBack")
    @ResponseBody
    public void aliCallBack(HttpServletRequest request, HttpServletResponse response) {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            String[] values = (String[]) entry.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put((String) entry.getKey(), valueStr);
        }
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        try {
            String outTradeNo = new String(request.getParameter("out_trade_no")
                    .getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(
                    "ISO-8859-1"), "UTF-8");
            // 支付回调金额
            String totalFee = new String(request.getParameter("total_fee").getBytes(
                    "ISO-8859-1"), "UTF-8");
            // 交易状态
            String tradeStatus = new String(request.getParameter("trade_status")
                    .getBytes("ISO-8859-1"), "UTF-8");
            // 加密方式
            String signType = new String(request.getParameter("sign_type")
                    .getBytes("ISO-8859-1"), "UTF-8");
            boolean verifyResult = false;
            //获取支付宝配置信息
            AliPayConfig ac = new AliPayConfig();

            /*log.info("支付宝返回信息:" + params +", getPartner: "+ ac.getPartner()+", getKey: "+ ac.getKey()+", getAli_public_key: "+
                        ac.getAli_public_key());*/
//            /网页支付   /移动端支付
            OperatePaymentMethod operatePaymentMethod = paymentMethodService.selectByType(OperatePaymentMethod.paymentType_alipay);
            if ("MD5".equals(signType)) {
                verifyResult = AlipayNotify.verifyReturn(params, operatePaymentMethod.getKey(), "MD5");
            } else if ("RSA".equals(signType)) {
                verifyResult = AlipayNotify.verify(params, operatePaymentMethod.getMchId(), operatePaymentMethod.getKey(),
                        operatePaymentMethod.getPublicKey(), "0001");
            }
            PrintWriter out = response.getWriter();
            // 验证成功
            if (verifyResult) {
                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    // 可空，怕报空指针，所以在这里获取
                    String payment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8");
                    SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp paytime = new Timestamp(sdf0.parse(payment).getTime());
                    try {
                        log.info(outTradeNo + ":out_trade_no======trade_no:" + tradeNo);
//                        this.payOrder(request, response, (int) OrderPaymentType.ORDER_PAY_TYPE_ALIPAY, out_trade_no, new BigDecimal(total_fee), paytime.toString(), trade_no, "");
                        //支付成功
                        out.print("success");
                    } catch (Exception e) {
                        out.print("fail");
                    }
                    // 注意：
                    // 付款完成后，支付宝系统发送该交易状态通知
                } else {// 验证失败
                    out.print("fail");
                }
            }
        } catch (Exception e) {
            log.error("支付宝支付回调解析失败------》" + e.getMessage());
            throw new RRException(e.getMessage());
        }
    }

    /**
     * visa支付回调
     */
    @RequestMapping(value = "/getCallback")
    @ResponseBody
    public String getCallback(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        String version=httpServletRequest.getParameter("Ds_SignatureVersion");
        String ds_merchantParameters=httpServletRequest.getParameter("Ds_MerchantParameters");
        String signature=httpServletRequest.getParameter("Ds_Signature");
        ApiMacSha256 apiMacSha256=new ApiMacSha256();
        String params=apiMacSha256.decodeMerchantParameters(ds_merchantParameters);
        String codigoRespuesta=apiMacSha256.getParameter("Ds_Response");
        JSONObject json=JSONObject.parseObject(params);
        String sign=apiMacSha256.createMerchantSignatureNotif(VisaUtils.key,ds_merchantParameters);
        System.out.println(json.toJSONString());
        System.out.println(sign);
        System.out.println(signature);
        String Ds_AuthorisationCode =json.getString("Ds_AuthorisationCode"); //三方交易号?
        //签名验证
        if(json.get("Ds_ErrorCode")==null && sign.equals(signature)){
            //订单操作
            int status=transactionRecordService.getCallback(json.getString("Ds_Order"),json.getString("Ds_Amount"));
            if(status==1){
                return "OK";
            }else{
                return "KO";
            }

        }else{
            return "KO";
        }
    }

}
