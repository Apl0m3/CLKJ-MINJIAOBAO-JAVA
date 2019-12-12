package com.lingkj.project.api.transaction.dto;

import com.lingkj.project.transaction.entity.TransactionCommodityAttributes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransactionCommodityDto
 *
 * @author chen yongsong
 * @className TransactionCommodityDto
 * @date 2019/9/20 15:25
 */
@ApiModel("订单商品返回实体类")
@Data
public class ApiTransactionCommodityRespDto {
    //商品订单Id
    private Long id;
    private Long commodityId;
    private String image;
    private String name;

    private ApiTransactionCommodityLadderDto ladderNum;
    private List<TransactionCommodityAttributes> attributes;
    private List<ApiTransactionCommodityNumberAttributeDto> numberAttributeList;

    private Integer needDesignerStatus;
    @ApiModelProperty("订单积分商品数量")
    private Integer commodityNum;
    @ApiModelProperty("设计师名称")
    private String designer;
    @ApiModelProperty("供应商名称")
    private String supplier;
    @ApiModelProperty("订单类型")
    private Integer commodityType;
    @ApiModelProperty("数量类型 ")
    private Integer numberSelectType;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("子状态")
    private Integer substate;
    @ApiModelProperty("金额")
    private BigDecimal trAmount;
    @ApiModelProperty("订单号")
    private String transactionId;
    //留言备注
    private  String remark;
    //清单状态
    private  Integer deliveryStatus;
    //清单id
    private  Long deliveryId;
    //订单商品下单时间
    private Date createTime;

    private  Long recordId;

    /**
     * 审核失败原因
     */
    private String reason;

    private Integer transactionServiceType;
}
