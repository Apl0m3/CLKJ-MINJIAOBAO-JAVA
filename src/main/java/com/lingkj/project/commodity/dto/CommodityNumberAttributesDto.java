package com.lingkj.project.commodity.dto;

import com.lingkj.project.commodity.entity.CommodityNumberAttributes;
import com.lingkj.project.commodity.entity.CommodityNumberAttributesValue;

/**
 * @Author Created by jojo on 2019/10/14
 */
public class CommodityNumberAttributesDto extends CommodityNumberAttributes {
    CommodityNumberAttributesValue[] numberAttributeValueList;

    public CommodityNumberAttributesValue[] getNumberAttributeValueList() {
        return numberAttributeValueList;
    }

    public void setNumberAttributeValueList(CommodityNumberAttributesValue[] numberAttributeValueList) {
        this.numberAttributeValueList = numberAttributeValueList;
    }
}
