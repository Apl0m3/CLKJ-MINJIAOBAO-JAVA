package com.lingkj.project.operation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.operation.entity.OperateAreas;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政区域县区信息表
 *
 * @author chenyongsong
 *
 * @date 2019-07-05 12:33:44
 */
@Mapper
public interface OperateAreasMapper extends BaseMapper<OperateAreas> {
    /**
     * 根据州市 查找
     *
     * @param parentId
     * @param level
     * @return
     */
    List<OperateAreas> selectListById(@Param("parentId") String parentId, @Param("level") Integer level);
}
