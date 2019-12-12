package com.lingkj.project.transaction.dto;

import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import com.lingkj.project.transaction.entity.TransactionCommodityExpect;
import com.lingkj.project.transaction.entity.TransactionCommodityLadder;
import com.lingkj.project.transaction.entity.TransactionCommodityNumberAttributes;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransactionCommodityRespDto
 *
 * @author chen yongsong
 * @className TransactionCommodityRespDto
 * @date 2019/7/11 14:06
 */
@ApiModel
@Data
public class TransactionCommodityRespDto {
    private String name;
    private String image;
    private Integer commodityNum;
    private Integer status;
    private Integer numberSelectType;
    private Integer substate;
    private BigDecimal amount;
    private Integer needDesignerStatus;
    private String manuscriptUrl;
    private TransactionCommodityLadder ladderNum;
    private TransactionCommodityExpect expect;
    private List<TransactionCommodityAttributes> attribute;
    private List<TransactionCommodityNumberAttributes> numberAttributes;
    private Date expectTime;
    private Long commodityId;
    private Long id;
    private  Integer transactionServiceType;
}
