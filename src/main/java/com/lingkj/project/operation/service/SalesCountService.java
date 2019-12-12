package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.SalesAcount;

import java.util.Map;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description
 * @date 2019/11/2 15:04
 */
public interface SalesCountService extends IService<SalesAcount> {

    /**
     * 后端接口  分页信息
     * @param  params
     * @return
     */
    Map<String,Object> queryPage(Map<String, Object> params);
}
