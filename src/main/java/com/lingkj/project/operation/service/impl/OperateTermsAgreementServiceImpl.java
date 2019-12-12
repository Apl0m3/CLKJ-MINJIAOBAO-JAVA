package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.operation.dto.OperateTermsAgreementDto;
import com.lingkj.project.operation.entity.OperateTermsAgreement;
import com.lingkj.project.operation.mapper.TermsAgreementMapper;
import com.lingkj.project.operation.service.OperateTermsAgreementService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class OperateTermsAgreementServiceImpl extends ServiceImpl<TermsAgreementMapper, OperateTermsAgreement> implements OperateTermsAgreementService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        IPage<OperateTermsAgreement> page = this.page(
                new Query<OperateTermsAgreement>(params).getPage(),
                new QueryWrapper<>()
        );
        for (OperateTermsAgreement operateTermsAgreement : page.getRecords()) {
            if (operateTermsAgreement.getUpdateSysUserId() != null) {
                SysUserEntity sysUserEntity = sysUserService.getById(operateTermsAgreement.getUpdateSysUserId());
                operateTermsAgreement.setCreateSysUserName(sysUserEntity.getUsername());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        baseMapper.updateStatusByIds(asList);
    }

    @Override
    public void saveOrUpdateTermsAgreement(OperateTermsAgreement operateTermsAgreement, Long sysUserId, HttpServletRequest request) {
        Date currentTime = DateUtils.current();
        if (operateTermsAgreement.getId() == null) {
            OperateTermsAgreement operateTermsAgreement1 = this.queryByType(operateTermsAgreement.getType());
            if (operateTermsAgreement1 != null) throw new RRException(messageUtils.getMessage("manage.terms.agreement.error", request));
            operateTermsAgreement.setCreateSysUserId(sysUserId);
            operateTermsAgreement.setCreateTime(currentTime);
            operateTermsAgreement.setStatus(0);
            save(operateTermsAgreement);
        } else {
            operateTermsAgreement.setUpdateSysUserId(sysUserId);
            operateTermsAgreement.setUpdateTime(currentTime);
            updateById(operateTermsAgreement);
        }
    }

    @Override
    public OperateTermsAgreement queryByType(Integer type) {
        return baseMapper.selectOne(new QueryWrapper<OperateTermsAgreement>().eq("type",type));
    }

    @Override
    public List<OperateTermsAgreementDto> getTermsAgreementDtoDtoLists(Integer type) {
        return baseMapper.getTermsAgreementDtoDtoListsByType(type);
    }

}
