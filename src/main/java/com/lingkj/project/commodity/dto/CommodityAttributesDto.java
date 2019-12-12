package com.lingkj.project.commodity.dto;

import com.lingkj.project.commodity.entity.CommodityAttributes;
import com.lingkj.project.commodity.entity.CommodityAttributesValue;

/**
 * @Author Created by jojo on 2019/9/23
 */
public class CommodityAttributesDto extends CommodityAttributes {
    CommodityAttributesValue[] attributeValueList;

    public CommodityAttributesValue[] getAttributeValueList() {
        return attributeValueList;
    }

    public void setAttributeValueList(CommodityAttributesValue[] attributeValueList) {
        this.attributeValueList = attributeValueList;
    }
}
