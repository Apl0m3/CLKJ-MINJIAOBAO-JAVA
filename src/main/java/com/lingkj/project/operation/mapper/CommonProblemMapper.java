package com.lingkj.project.operation.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.api.operation.dto.CommonProblemDto;
import com.lingkj.project.operation.entity.CommonProblem;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 常见问题
 *
 * @author chenyongsong
 * @date 2019-09-19 11:02:41
 */
@Mapper
public interface CommonProblemMapper extends BaseMapper<CommonProblem> {

   void updateBatchIds(Long[] ids);

   List<CommonProblemDto> getCommonProblemDtoList();

}
