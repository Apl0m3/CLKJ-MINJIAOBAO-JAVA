package com.lingkj.project.api.commodity.dto;

import com.lingkj.project.commodity.entity.CommodityCommentFile;
import lombok.Data;

import java.util.List;

@Data
public class ApiCommodityCommentAndFileDto {
    private List<CommodityCommentFile> list;
    /**
     * 交易订单号
     */
    private String transactionId;
    /**
     * 商品id
     */
    private Long commodityId;
    private  Long transactionCommodityId;
    /**
     * 评论内容
     */
    private String comment;

    //类型：0-用户  1-平台  2-设计师  3-供应商
    private  Integer userType=0;

}
