package com.lingkj.project.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.commodity.dto.ApiCommodityFileDto;
import com.lingkj.project.commodity.entity.CommodityFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface CommodityFileMapper extends BaseMapper<CommodityFile> {
    /**
     * 商品banner 图片展示列表 api
     *
     * @param commodityId
     * @return
     */
    List<ApiCommodityFileDto> selectByCommodityId(Long commodityId);

    CommodityFile[] selectEntityByCommodityId(Long commodityId);

    void deleteNotInBatch(@Param("fileUpdList") List<CommodityFile> fileUpdList,@Param("commodityId") Long commodityId);
}
