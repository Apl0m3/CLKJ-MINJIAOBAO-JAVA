package com.lingkj.project.transaction.dto;

import com.lingkj.common.utils.PageUtils;
import lombok.Data;
import org.hibernate.validator.constraints.EAN;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TransactionCommodityDeliveryAndCountRespDto {
    private PageUtils pageUtils;
    private Integer allSheetCount;//全部
    private Integer waitingSheetCount; //未处理
    private Integer  pendingSheetCount; //未确认
    private Integer  completeSheetCount;//确认
}
