package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.CommonProblemDto;
import com.lingkj.project.operation.entity.CommonProblem;

import java.util.List;
import java.util.Map;

/**
 * 常见问题
 *
 * @author chenyongsong
 * @date 2019-09-19 11:02:41
 */
public interface CommonProblemService extends IService<CommonProblem> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);


    /**
     * 新增or更新
     */

    void saveOrUpdateCommonProblem(CommonProblem commonProblem);


    void updateBatchIds(Long[] ids);


    List<CommonProblemDto> getCommonProblemDtoList();

}

