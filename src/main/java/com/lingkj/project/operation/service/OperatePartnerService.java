package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.WorkUsDto;
import com.lingkj.project.operation.entity.OperatePartner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 合作伙伴
 *
 * @author chenyongsong
 * @date 2019-09-20 14:44:16
 */
public interface OperatePartnerService extends IService<OperatePartner> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增or修改
     * @param operatePartner 实体
     */
    void saveOrUpdatePartner(OperatePartner operatePartner);

    /**
     * UI端接口 返回所有合作伙伴dto
     */
    WorkUsDto queryList(String  name);




}

