package com.lingkj.project.operation.mapper;

import com.lingkj.project.operation.entity.OperateRules;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 积分规则
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface OperateRulesMapper extends BaseMapper<OperateRules> {

    void updateStatusByIds(@Param("asList") List<Long> asList);
    OperateRules  getOneByType(@Param("type") Integer type,@Param("ruleType") Integer ruleType);

}
