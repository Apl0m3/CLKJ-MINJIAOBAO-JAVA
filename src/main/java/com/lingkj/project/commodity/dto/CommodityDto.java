package com.lingkj.project.commodity.dto;

import com.lingkj.project.commodity.entity.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Created by jojo on 2019/7/5
 */
@Data
public class CommodityDto{
    /**
     * 商品
     */
    private Commodity commodity;
    /**
     * 商品属性
     */
    private CommodityAttributesDto[] commodityAttributesArray;
    /**
     * 商品数量属性
     */
    private CommodityNumberAttributesDto[] commodityNumberAttributesArray;
    /**
     * 轮播图片
     */
    private CommodityFile[] commodityFiles;
    /**
     * 阶梯价
     */
    private CommodityLadder[] commodityLadderArray;
    /**
     * 预计到货时间
     */
    private CommodityExpect[] commodityExpectedArray;

}
