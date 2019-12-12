package com.lingkj.project.commodity.dto;

import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.entity.CommodityFile;
import lombok.Data;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description 新增积分商品Dto
 * @date 2019/10/9 9:42
 */
@Data
public class IntegralCommodityAddDto {
    /**
     * 商品
     */
    private Commodity commodity;

    /**
     * 轮播图片
     */
    private CommodityFile[] commodityFiles;


}
