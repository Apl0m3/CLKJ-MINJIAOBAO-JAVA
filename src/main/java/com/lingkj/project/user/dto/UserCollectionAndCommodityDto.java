package com.lingkj.project.user.dto;

import lombok.Data;

@Data
public class UserCollectionAndCommodityDto {
    private String name;
    /**
     * 商品图片
     */
    private String image;

    private Long id;
    private  Long commodityId;
}
