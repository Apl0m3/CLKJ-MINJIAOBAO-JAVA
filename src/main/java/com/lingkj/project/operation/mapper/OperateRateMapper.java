package com.lingkj.project.operation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.operation.entity.OperateRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 费率表
 *
 * @author chenyongsong
 * @date 2019-10-10 09:15:52
 */
@Mapper
public interface OperateRateMapper extends BaseMapper<OperateRate> {
    /**
     * 根据费率类型查询
     * @param type
     * @return
     */
    OperateRate selectByType(@Param("type") Integer type);
}
