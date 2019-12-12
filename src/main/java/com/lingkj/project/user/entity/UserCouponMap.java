package com.lingkj.project.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user transaction log
 *
 * @author chenyongsong
 * @date 2019-09-20 16:05:21
 */
@Data
@TableName("user_coupon_map")
public class UserCouponMap implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 0正常，1已删除,2已过期,3使用
     */
    public static final Integer STATUS_USE = 3;
    public static final Integer STATUS_DEL = 1;
    public static final Integer STATUS_EXPIRED = 2;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * merchantId
     */
    private Long userId;
    /**
     * merchantId
     */
    private Long couponId;
    /**
     * 获得时间
     */
    private Date createTime;
    /**
     * 生效时间
     */
    private Date startTime;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 持有数量
     */
    private Integer num;
    /**
     * 总数量
     */
    private Integer totalNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 0正常，1已删除,2已过期,3使用
     */
    private Integer status;
    /**
     * 0  未读  1 已读
     */
    private Integer readStatus;
    /**
     * 优惠券码
     */
    private String couponCode;

}
