package com.lingkj.project.operation.mapper;

import com.lingkj.project.api.operation.dto.OperateTermsAgreementDto;
import com.lingkj.project.operation.entity.OperateTermsAgreement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface TermsAgreementMapper extends BaseMapper<OperateTermsAgreement> {
    /**
     * 逻辑删除
     *
     * @param asList
     */
    void updateStatusByIds(@Param("asList") List<Long> asList);


    List<OperateTermsAgreementDto> getTermsAgreementDtoDtoListsByType(@Param("type") Integer type);





}
