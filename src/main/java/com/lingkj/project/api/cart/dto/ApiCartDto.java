package com.lingkj.project.api.cart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Data
public class ApiCartDto {

    /**
     *
     */
    private Long id;

    /**
     * 用户上传设计稿件
     */
    private String manuscriptUrl;
    /**
     * 是否需要设计师
     */
    private Long needDesignerStatus;
    /**
     * 设计要求
     */
    private Long designRequirements;
    /**
     * 商品id
     */
    private Long commodityId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String image;

    /**
     * 0 正常  1 删除
     */
    private Integer status;
    /**
     * 数量填写方式 1：输入，2：选择，3：服装
     */
    private Integer numberSelectType;
    /**
     * 总数
     */
    private Integer totalQuantity;
    private Integer num;
    /**
     * 商品价格
     */
    private BigDecimal amount = BigDecimal.ZERO;
    /**
     * 设计费用
     */
    private BigDecimal designerAmount = BigDecimal.ZERO;
    /**
     * 商品属性 信息
     */
    private List<ApiCartAttributesDto> attributes;
    /**
     * 商品数量信息
     */
    private ApiCartLadderDto ladderNum;
    /**
     * 商品预计到达时间
     */
    private ApiCartExpectDto expect;
    /**
     *
     */
    private List<ApiCartNumberAttributeDto> numberAttributes;


}
