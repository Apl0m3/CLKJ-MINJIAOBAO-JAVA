package com.lingkj.project.api.cart.dto;

import com.lingkj.common.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ApiCartAttributesDto
 *
 * @author chen yongsong
 * @className ApiCartAttributesDto
 * @date 2019/10/22 18:11
 */
@Data
public class ApiCartExpectDto {
    private Long id;
    /**
     * 购物车
     */
    private Long cartId;
    /**
     * commodity expect id
     */
    private Long expectId;
    /**
     * 预计 n 天后，送达
     */
    private Integer day;
    /**
     * 商品数量上限
     */
    private Integer maxNum;
    /**
     * 额外金额
     */
    private BigDecimal amount;
    private BigDecimal factoryPrice;
    private String dayDate;

    public String getDayDate() {
        if(this.day!=null&&this.day>0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM", new Locale("es", "ES"));
            return dateFormat.format(DateUtils.addDateDays(new Date(), this.day));
        }
        return null;
    }
}
