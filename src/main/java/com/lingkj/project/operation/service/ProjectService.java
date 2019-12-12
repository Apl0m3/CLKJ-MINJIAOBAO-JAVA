package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.ConcatUsDto;
import com.lingkj.project.operation.entity.OperateProject;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface ProjectService extends IService<OperateProject> {

    PageUtils queryPage(Map<String, Object> params);

    void saveOrUpdate(OperateProject project, Long sysUserId);


    List<ConcatUsDto> queryList();




}

