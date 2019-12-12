package com.lingkj.project.api.commodity.dto;

import com.lingkj.common.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ApiCommodityExpectDto
 *
 * @author chen yongsong
 * @className ApiCommodityExpectDto
 * @date 2019/9/27 17:45
 */
@Data
public class ApiCommodityExpectDto {
    private Long id;
    private Integer day;
    private String dayDate;
    private BigDecimal amount;
    private Integer maxNum;

    public String getDayDate() {
        if(this.day!=null&&this.day>0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM", new Locale("es", "ES"));
            return dateFormat.format(DateUtils.addDateDays(new Date(), this.day));
        }
        return null;
    }
}
