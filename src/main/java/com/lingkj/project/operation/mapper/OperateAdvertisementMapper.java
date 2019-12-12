package com.lingkj.project.operation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.operation.dto.AdvertisementDto;
import com.lingkj.project.operation.dto.AdvertisementRespDto;
import com.lingkj.project.operation.entity.OperateAdvertisement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface OperateAdvertisementMapper extends BaseMapper<OperateAdvertisement> {
    /**
     * 删除 逻辑
     *
     * @param asList
     */
    void updateStatusByIds(@Param("asList") List<Long> asList);

    /***
     * api 广告查询
     * @return
     */
    List<AdvertisementRespDto> selectListByStatus();

    List<Map<String, Object>> getTypeList();

   /**
    * 查询广告列表
    * @author XXX <XXX@163.com>
    * @date 2019/10/10 11:47
    * @param position 广告展示位置 1 首页  2 商品列表页 3 积分商品页
    * @param type   （1-滚动，2-固定）（）
    * @return java.util.List<com.lingkj.project.api.operation.dto.AdvertisementDto>
    */
    List<AdvertisementDto> selectListByTypeAndPosition(@Param("position") Integer position,@Param("type") Integer type);

}
