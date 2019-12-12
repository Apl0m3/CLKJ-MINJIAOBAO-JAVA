package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.*;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.UserDesignerApplicationDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.*;
import com.lingkj.project.user.mapper.UserDesignerApplicationMapper;
import com.lingkj.project.user.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;


/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Service
public class UserDesignerApplicationServiceImpl extends ServiceImpl<UserDesignerApplicationMapper, UserDesignerApplication> implements UserDesignerApplicationService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AdminUserService userService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private UserApplicationFileService applicationFileService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserDesignerApplication> wrapper = new QueryWrapper<>();
        String name = (String) params.get("name");
        Long userId = (Long) params.get("userId");
        String status = (String) params.get("status");
        if (userId != null) wrapper.eq("user_id", userId);
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", Integer.parseInt(status));
        }
        wrapper.orderByDesc("create_time");
        IPage<UserDesignerApplication> page = this.page(
                new Query<UserDesignerApplication>(params).getPage(),
                wrapper
        );
        // 获取审批人员名称
        for (UserDesignerApplication userDesignerApplication : page.getRecords()) {
            if (userDesignerApplication.getStatus() != Constant.UserDesignerApplicationStatus.WAIT.getStatus()) {
                SysUserEntity sysUserEntity = sysUserService.getById(userDesignerApplication.getApplicationBy());
                userDesignerApplication.setApplicationByName(sysUserEntity.getUsername());
            }
            Long userID = userDesignerApplication.getUserId();
            if(userID != null){
                User user = adminUserService.selectInfoById(userID);
                if(user.getUserName() != null){
                    userDesignerApplication.setUserName(user.getUserName());
                }
            }
        }

        return new PageUtils(page);
    }

    @Override
    public void approval(UserDesignerApplication userDesignerApplication,HttpServletRequest request) {
        // 修改状态以及审批人员信息
        userDesignerApplication.setApplicationBy(ShiroUtils.getUserEntity().getUserId());
        userDesignerApplication.setApplicationByName(ShiroUtils.getUserEntity().getUsername());
        userDesignerApplication.setApplicationTime(DateUtils.current());
        this.updateById(userDesignerApplication);
        // 添加站内信信息
        Message message = new Message();
        message.setCreateTime(DateUtils.current());
        message.setTitle(messageUtils.getMessage("manage.user.apply.designer.feedback", request));
        message.setCreateSysUserId(getSysUserId());
        message.setUserId(userDesignerApplication.getUserId());
        if (userDesignerApplication.getStatus() == Constant.UserDesignerApplicationStatus.ERROR.getStatus()) {
            // 审核未通过
            message.setContent(messageUtils.getMessage("manage.user.apply.designer.fail", request) + userDesignerApplication.getReason());
        } else {
            // 审核通过
            message.setContent(messageUtils.getMessage("manage.user.apply.designer.success", request));
            //更改用户角色
            changeUserRole(userDesignerApplication);
            //审核通过 保存佣金
            List<CommissionDto> commissionDtoList = getCommissionDtos(userDesignerApplication);

            this.insertCom(commissionDtoList);

        }
        // 给用户发送站内信
        messageService.save(message);
    }

    @Override
    public UserDesignerApplication getUserDesignerApplicationOne(Long userId) {
        QueryWrapper<UserDesignerApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return this.getOne(wrapper);
    }

    private List<CommissionDto> getCommissionDtos(UserDesignerApplication userDesignerApplication) {
        List<CommissionDto> commissionDtoList = userDesignerApplication.getCommissionDto();
        if (commissionDtoList != null && commissionDtoList.size() > 0) {
            for (int i = 0; i < commissionDtoList.size(); i++) {
                CommissionDto commissionDto = commissionDtoList.get(i);
                commissionDto.setUserId(userDesignerApplication.getUserId());
                commissionDto.setCreateBy(ShiroUtils.getUserEntity().getUserId());
                commissionDto.setCreateTime(new Date());
            }
        }
        return commissionDtoList;
    }

    private void changeUserRole(UserDesignerApplication userDesignerApplication) {
        User user = userService.getById(userDesignerApplication.getUserId());
        user.setUserRoleId(User.member_role_designer);
        userService.updateById(user);
    }

    @Override
    public List<CommissionDto> getTypeList() {
        return baseMapper.getTypeList();
    }

    @Override
    public List<CommissionDto> getCommissionDto() {
        return baseMapper.getCommissionDto();
    }

    @Override
    public void insertCom(List<CommissionDto> commissionDto) {
        baseMapper.insertCommission(commissionDto);
    }

    @Override
    public Integer getUserDesignerApplicationCount(Long userId) {
        QueryWrapper<UserDesignerApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return this.count(wrapper);
    }

    @Override
    public Map<String, Object> queryInfo(Long id) {
        // 查询设计师申请信息
        UserDesignerApplication userDesignerApplication = this.getById(id);
        // 根据id查询申请成为设计师的用户信息
            User user = adminUserService.selectInfoById(userDesignerApplication.getUserId());
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userDesignerApplication.getUserId());
        //根据id 和userId 查申请用户的银行开户信息
        UserBank userBank = baseMapper.queryUserBankByApplicationIdAndUserId(userDesignerApplication.getUserId(), id);

        // 根据typeIds查询出申请成为设计师选择的擅长设计软件
        String typeIds = userDesignerApplication.getTypeIds();
        String[] ids = typeIds.split(",");
        List<OperateType> operateTypes = operateTypeService.selectBatchIds(Arrays.asList(ids));
        StringBuffer operateTypeNames = new StringBuffer();
        operateTypes.forEach(x -> {
            operateTypeNames.append(x.getName() + ";");
        });
        userDesignerApplication.setOperateTypeNames(operateTypeNames.toString());
        // 根据设计师申请表id查询出相关技术案例文件url
        QueryWrapper<UserApplicationFile> wrapper = new QueryWrapper<>();
        wrapper.eq("application_id", userDesignerApplication.getId());
        wrapper.eq("type",Constant.ApplicationType.DESIGNER.getType());
        List<UserApplicationFile> userApplicationFiles = applicationFileService.list(wrapper);
        ArrayList<String> applicationFiles = new ArrayList<>();
        userApplicationFiles.forEach( x -> {
            applicationFiles.add(x.getUrl());
        });
        userDesignerApplication.setApplicationFiles(applicationFiles);

        Map<String, Object> map = new HashMap<>(2);
        map.put("userDesignerApplication", userDesignerApplication);
        map.put("userBank",userBank);
        map.put("userRespDto",userRespDto);
        return map;
    }

    @Transactional
    @Override
    public void saveDesigner(UserDesignerApplicationDto userDesignerApplicationDto, Long userId, HttpServletRequest request) {
        UserDesignerApplication userDesignerApplication = userDesignerApplicationDto.getUserDesignerApplication();
        List<UserApplicationFile> userApplicationFiles = userDesignerApplicationDto.getUserApplicationFiles();
        if(userDesignerApplication.getId()==null){
            userDesignerApplication.setCreateTime(new Date());
            userDesignerApplication.setStatus(0);
            userDesignerApplication.setUserId(userId);
            this.save(userDesignerApplication);
        }else if(userDesignerApplication.getStatus()!=1 ){
            userDesignerApplication.setStatus(0);
            this.updateById(userDesignerApplication);
        } else {
            throw new RRException(messageUtils.getMessage("api.user.apply", request),400);
        }

        userApplicationFileService.deleteFile(userDesignerApplication.getId(),1);
        for (UserApplicationFile userApplicationFile: userApplicationFiles){
            userApplicationFile.setId(null);
            userApplicationFile.setCreateTime(new Date());
            userApplicationFile.setUserId(userId);
            userApplicationFile.setApplicationId(userDesignerApplication.getId());
            userApplicationFileService.save(userApplicationFile);
        }
        boolean b = userBankService.saveAndUpdate(userDesignerApplicationDto.getUserBank(), userId, userDesignerApplication.getId(),request);
        if(!b){
            throw new RRException(messageUtils.getMessage("api.user.bank.null", request),400);
        }
//        if(StringUtils.isNotBlank(userDesignerApplicationDto.getUserBank().getBankAccount())){
//            if(!userDesignerApplicationDto.getUserBank().getBankAccount().matches(ApiApplyDesignerController.bankVerification)){
//                throw new RRException(messageUtils.getMessage("api.user.bank.bankAccount.verification", request),400);
//            }
//        }
        adminUserService.updateUser(userId,userDesignerApplicationDto.getUserRespDto(),request);
    }


}
