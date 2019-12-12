package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.OperateTermsAgreementDto;
import com.lingkj.project.operation.entity.OperateDeliveryMethod;
import com.lingkj.project.operation.entity.OperateTermsAgreement;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:25
 */
public interface OperateTermsAgreementService extends IService<OperateTermsAgreement> {

    PageUtils queryPage(Map<String, Object> params);

    void updateStatusByIds(List<Long> asList);
    void saveOrUpdateTermsAgreement(OperateTermsAgreement operateTermsAgreement, Long sysUserId, HttpServletRequest request);

    OperateTermsAgreement queryByType(Integer type);

    List<OperateTermsAgreementDto> getTermsAgreementDtoDtoLists(Integer type);
}

