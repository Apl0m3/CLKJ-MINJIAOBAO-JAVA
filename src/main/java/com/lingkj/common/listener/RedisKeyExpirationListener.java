package com.lingkj.common.listener;

import com.lingkj.common.utils.RedisUtils;
import com.lingkj.project.transaction.service.TransactionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


/**
 * RedisKeyExpirationListener
 *
 * @author chen yongsong
 * @className RedisKeyExpirationListener
 * @date 2019/7/10 16:22
 */
@Slf4j
//@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    public RedisUtils redisUtils;
    @Autowired
    public TransactionRecordService transactionRecordService;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
       /* String expiredKey = message.toString();
        if (expiredKey.startsWith("order_")) {
            String transactionId = expiredKey.replace("order_", "");
            log.info("订单:" + transactionId + ";24小时取消订单 开始");
            System.out.println("expiredKey:::" + expiredKey);
            transactionRecordService.cancelTransaction(transactionId);
            log.info("订单:" + transactionId + ";24小时取消订单 结算");
        } else if (expiredKey.equals("jsApi_ticket")) {
            String jsApiTicket = WeChatUtils.getJSApiTicket();
            redisUtils.set("jsApi_ticket", jsApiTicket, 115 * 60);
        } else if (expiredKey.startsWith("ship_order_")) {
            String str = expiredKey.replace("ship_order_", "");
            String[] strs = str.split("_");
            String transactionId = strs[0];
            Long userId = Long.valueOf(strs[1]);
            log.info("订单:" + transactionId + ";发货72小时自动收货 开始");
            System.out.println("expiredKey:::" + expiredKey);
            UpdateTransactionStatusReqDto updateTransactionStatusReqDto = new UpdateTransactionStatusReqDto();
            updateTransactionStatusReqDto.setStatus(TransactionStatusEnum.received.getStatus());
            updateTransactionStatusReqDto.setTransactionId(transactionId);
            transactionRecordService.updateTransactionStatus(updateTransactionStatusReqDto, userId);
            log.info("订单:" + transactionId + ";发货72小时自动收货 结束");
        } else if (expiredKey.startsWith("refund_order_")) {
            String transactionId = expiredKey.replace("refund_order_", "");
            log.info("订单:" + transactionId + ";24小时自动退款 开始");
            System.out.println("expiredKey:::" + expiredKey);
            transactionRecordService.updateTransactionRefund(transactionId,null);
            log.info("订单:" + transactionId + ";24小时自动退款 结算");
        }*/
    }
}
