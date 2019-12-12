package com.lingkj.project.operation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.operation.entity.ReturnReasons;

import java.util.List;
import java.util.Map;

/**
 * 退款原因
 *
 * @author chenyongsong
 * @date 2019-09-19 16:11:50
 */
public interface ReturnReasonsService extends IService<ReturnReasons> {

    PageUtils queryPage(Map<String, Object> params);

}

