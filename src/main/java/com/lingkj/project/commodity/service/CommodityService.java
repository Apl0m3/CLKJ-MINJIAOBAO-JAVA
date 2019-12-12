package com.lingkj.project.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.commodity.dto.ApiCommodityAttributeDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityListDto;
import com.lingkj.project.api.commodity.dto.ApiDetailIntegralCommodityDto;
import com.lingkj.project.commodity.dto.CommodityDto;
import com.lingkj.project.commodity.dto.IntegralCommodityAddDto;
import com.lingkj.project.commodity.entity.Commodity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
public interface CommodityService extends IService<Commodity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateBatchIds(Long[] ids);

    List<Commodity> getCommodity(String name);

    CommodityDto selectCommodity(Long id);
    IntegralCommodityAddDto  selectIntegralCommodity(Long id);

    void insertCommodity(CommodityDto commodityAddDto, Long sysUserId);

    void insertIntegralCommodity(IntegralCommodityAddDto integralCommodityAddDto);

    void updateIntegralCommodity(IntegralCommodityAddDto integralCommodityAddDto);

    void updateCommodity(CommodityDto commodityAddDto, Long sysUserId);

    void updateBatchStatus(Long[] ids);

    /**
     * 分页数据查询
     *
     * @param page
     * @param type
     * @param userId
     * @param typeId
     * @return
     */
    PageUtils queryPageApi(Page page, Integer type, Long userId, Long typeId);

    /**
     *  查询商品详情
     * @param id
     * @param userId
     * @param request
     * @return
     */
    ApiCommodityDto info(Long id, Long userId, HttpServletRequest request);

    /**
     * 查询商品属性
     * @param commodityId
     * @return
     */
    List<ApiCommodityAttributeDto> queryAttributeApi(Long commodityId);

    /**
     * 积分商品分页数据查询
     *
     *
     * @param params 分页信息
     * @return
     */
    PageUtils queryIntegralPageApi(Page params);

    /**
     *
     * @author xxx
     * @date 2019/10/8 17:35
     * @param params
     * @return com.lingkj.project.api.commodity.dto.ApiDetailIntegralCommodityDto
     */
    ApiDetailIntegralCommodityDto queryIntegralDetailApi(Map<String,Object> params);



    /**
     * 查询商品阶梯价和商品预计到货时间
     * @param commodityId
     * @return
     */
    Map<String, Object> queryExpectedDeliveryApi(Long commodityId);

    /**
     * 查询最新的20个商品
     * @author XXX <XXX@163.com>
     * @date 2019/10/10 16:58
     * @param []
     * @param userId
     * @return java.util.List<com.lingkj.project.api.commodity.dto.ApiCommodityDto>
     */
    List<ApiCommodityListDto> queryLastCommodityList(Long id, Long userId);

    //查询商品详情 加锁
    Commodity getCommodity1(Long id);

    //修改商品数量
     void  updateCommodityNum(Integer num, Date current,Long id );
}

