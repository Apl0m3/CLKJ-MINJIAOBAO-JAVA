package com.lingkj.project.operation.mapper;

import com.lingkj.project.api.operation.dto.ConcatUsDto;
import com.lingkj.project.operation.entity.OperateProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface ProjectMapper extends BaseMapper<OperateProject> {



    List<ConcatUsDto> queryProjectList();

}
