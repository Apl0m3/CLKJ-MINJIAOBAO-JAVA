package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.OperateAreasPostalCode;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-20 14:43:23
 */
public interface OperateAreasPostalCodeService extends IService<OperateAreasPostalCode> {

    PageUtils queryPage(Map<String, Object> params);

    List<OperateAreasPostalCode> selectByPostalCode(String postalCode);
}

