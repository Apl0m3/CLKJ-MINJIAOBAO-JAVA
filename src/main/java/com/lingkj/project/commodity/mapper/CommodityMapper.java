package com.lingkj.project.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.commodity.dto.ApiCommodityDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityListDto;
import com.lingkj.project.api.commodity.dto.ApiDetailIntegralCommodityDto;
import com.lingkj.project.api.commodity.dto.ApiIntegralCommodityDto;
import com.lingkj.project.commodity.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
    /**
     * 逻辑删除
     *
     * @param ids
     */
    void updateBatchIds(Long[] ids);


    /**
     * 统计商城商品
     *
     * @param type
     * @param key
     * @param typeId
     * @return
     */
    Integer queryCommodityCount(@Param("key") String key, @Param("type") Integer type, @Param("typeId") Long typeId);


    void updateBatchStatus(Long[] ids);

    void updateSoldNum(@Param("commodityId") Long commodityId, @Param("size") Integer size, @Param("sysUserId") Long sysUserId, @Param("updateTime") Date updateTime);

    /**
     * 分页查询商品
     *
     * @param limit
     * @param pageSize
     * @param userId
     * @param key
     * @param type
     * @param typeId
     * @return
     */
    List<ApiCommodityListDto> queryCommodityList(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("userId") Long userId, @Param("key") String key, @Param("type") Integer type, @Param("typeId") Long typeId);

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    ApiCommodityDto info(Long id);


    /**
     * 查询积分商品总记录数
     */
    Integer queryAllIntegralCommodityCount();

    /**
     * 查询积分商品每一页list
     */
    List<ApiIntegralCommodityDto> queryIntegralCommodityList(@Param("limit")Integer limit,@Param("pageSize")Integer pageSize);

    /**
     * 查询积分商品详情
     * @author xxx
     * @date 2019/10/8 17:07
     * @param id 商品id
     * @return com.lingkj.project.api.commodity.dto.ApiDetailIntegralCommodityDto
     */
    ApiDetailIntegralCommodityDto queryIntegralDetail(@Param("id") Integer id);
    /**
     * 查询积分商品轮播图
     * @author xxx
     * @date 2019/10/8 17:29
     * @param id 商品id
     * @return java.util.List<java.lang.String>
     */
    List<String> queryIntegralCommodityFileList(@Param("id") Integer id);

    /**
     * 查询最新20个商品
     * @author XXX <XXX@163.com>
     * @date 2019/10/10 17:09
     * @param id
     * @param userId
     * @return java.util.List<com.lingkj.project.commodity.entity.Commodity>
     */
    List<ApiCommodityListDto> queryLastCommodityList(@Param("id") Long id, Long userId);

    //商品详情  加锁
    Commodity  queryCommodity(@Param("id") Long id);

    void updateNum(@Param("num") Integer num,@Param("current")Date current,@Param("id") Long id);


}
