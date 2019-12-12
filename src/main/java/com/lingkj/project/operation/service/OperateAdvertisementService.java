package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.AdvertisementDto;
import com.lingkj.project.operation.dto.AdvertisementRespDto;
import com.lingkj.project.operation.entity.OperateAdvertisement;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
public interface OperateAdvertisementService extends IService<OperateAdvertisement> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatusByIds(List<Long> asList);

    /**
     * 查询广告 列表
     *
     * @return
     */
    List<AdvertisementRespDto> selectListByStatus();


    List<AdvertisementDto> queryAdvertisementRespDtoList(Integer position,Integer type);


    List<Map<String,Object>> getTypeList();
}

