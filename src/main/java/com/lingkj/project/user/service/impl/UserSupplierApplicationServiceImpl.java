package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.*;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.commodity.entity.CommodityType;
import com.lingkj.project.commodity.service.CommodityTypeService;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import com.lingkj.project.operation.entity.OperateType;
import com.lingkj.project.operation.service.OperateTypeService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.UserAndSupplierDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.*;
import com.lingkj.project.user.mapper.UserSupplierApplicationMapper;
import com.lingkj.project.user.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;


/**
 * 用户供应商 申请  表
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Service
public class UserSupplierApplicationServiceImpl extends ServiceImpl<UserSupplierApplicationMapper, UserSupplierApplication> implements UserSupplierApplicationService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AdminUserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserDesignerApplicationService userDesignerApplicationService;

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private OperateTypeService operateTypeService;
    @Autowired
    private UserApplicationFileService applicationFileService;
    @Autowired
    private CommodityTypeService commodityTypeService;
    @Autowired
    private UserApplicationFileService userApplicationFileService;
    @Autowired
    private UserBankService userBankService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserSupplierApplication> wrapper = new QueryWrapper<>();
        String name = (String) params.get("name");
        String status = (String) params.get("status");

        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", Integer.parseInt(status));
        }
        wrapper.orderByDesc("create_time");
        IPage<UserSupplierApplication> page = this.page(
                new Query<UserSupplierApplication>(params).getPage(),
                wrapper
        );

        //获取用户联系方式

        // 获取审批人员名称
        for (UserSupplierApplication userSupplierApplication : page.getRecords()) {
            if (userSupplierApplication.getStatus() != Constant.UserSupplierApplicationStatus.WAIT.getStatus()) {
                SysUserEntity sysUserEntity = sysUserService.getById(userSupplierApplication.getApplicationBy());
                userSupplierApplication.setApplicationByName(sysUserEntity.getUsername());
            }
            Long userId = userSupplierApplication.getUserId();
            if (userId != null) {
                User user = adminUserService.selectInfoById(userId);
                if (user.getUserName() != null) {
                    userSupplierApplication.setUserName(user.getUserName());
                }
            }
        }

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(UserSupplierApplication userSupplierApplication,HttpServletRequest request) {
        // 修改状态以及审批人员信息
        userSupplierApplication.setApplicationBy(ShiroUtils.getUserEntity().getUserId());
        userSupplierApplication.setApplicationByName(ShiroUtils.getUserEntity().getUsername());
        userSupplierApplication.setApplicationTime(DateUtils.current());
        this.updateById(userSupplierApplication);

        //添加站内信信息
        Message message = new Message();
        message.setCreateTime(DateUtils.current());
        message.setTitle(messageUtils.getMessage("manage.user.apply.supplier.feedback", request));
        message.setUserId(userSupplierApplication.getUserId());
        message.setCreateSysUserId(getSysUserId());
        if (userSupplierApplication.getStatus() == Constant.UserDesignerApplicationStatus.ERROR.getStatus()) {
            // 审核未通过
            message.setContent(messageUtils.getMessage("manage.user.apply.supplier.fail", request) + userSupplierApplication.getReason());
        } else {
            // 审核通过
            message.setContent(messageUtils.getMessage("manage.user.apply.supplier.success", request));
            //更改用户角色
            User user = userService.getById(userSupplierApplication.getUserId());
            user.setUserRoleId(User.member_role_supplier);
            userService.updateById(user);
            //审核通过 保存佣金
//            List<CommissionDto> commissionDtoList = userSupplierApplication.getCommissionDto();
//            if (commissionDtoList.size()>0 && commissionDtoList !=null){
//                for (int i = 0; i < commissionDtoList.size() ; i++) {
//                    CommissionDto commissionDto = commissionDtoList.get(i);
//                    commissionDto.setUserId(userSupplierApplication.getUserId());
//                    commissionDto.setCreateBy(ShiroUtils.getUserEntity().getUserId());
//                    commissionDto.setCreateTime(new Date());
//                }
//            }
//            this.insertCommission(commissionDtoList);
        }

        //给用户发送站内信
        messageService.save(message);
    }

    @Override
    public List<CommissionDto> getCommissionDtoList(long id) {
        List<Integer> proIds = this.getProIds(id);
        //封装CommissionDto实体集合
        return baseMapper.getTypeList(proIds);
    }

    @Override
    public List<CommissionDto> getCommissionList(long id) {
        List<Integer> proIds = this.getProIds(id);
        //封装CommissionDto实体集合
        return baseMapper.getTypeListCommission(proIds);
    }

    @Override
    public UserSupplierApplication selectByUserId(Long userId) {
        QueryWrapper<UserSupplierApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.getOne(wrapper);
    }

    @Override
    public Integer getUserSupplierApplicationCount(Long userId) {
        QueryWrapper<UserSupplierApplication> supplier = new QueryWrapper<>();
        supplier.eq("user_id", userId);
        return this.count(supplier);
    }

    @Override
    public Map<String, Object> queryInfo(Long id) {
        // 查询供应商申请信息
        UserSupplierApplication userSupplierApplication = this.getById(id);
        // 根据id查询申请称为供应商的用户信息
        UserRespDto userRespDto = adminUserService.queryPersonInfo(userSupplierApplication.getUserId());
        //根据id 和userId 查申请用户的银行开户信息
        UserBank userBank = baseMapper.querySupplyBankByApplicationIdAndUserId(userSupplierApplication.getUserId(), id);
        // 根据typeIds查询出申请成为供应商的供应产品
        String typeIds = userSupplierApplication.getTypeIds();
        String[] ids = typeIds.split(",");
//        List<OperateType> operateTypes = operateTypeService.selectBatchIds(Arrays.asList(ids));
        List<CommodityType> commodityTypes = commodityTypeService.selectBatchIds(Arrays.asList(ids));
        StringBuffer supplyProducts = new StringBuffer();
        commodityTypes.forEach(x -> {
            supplyProducts.append(x.getName() + ";");
        });
//        operateTypes.forEach(x -> {
//            supplyProducts.append(x.getName() + ";");
//        });
        userSupplierApplication.setSupplyProducts(supplyProducts.toString());
        // 根据设计师申请表id查询出相关报价表文件url
        QueryWrapper<UserApplicationFile> wrapper = new QueryWrapper<>();
        wrapper.eq("application_id", userSupplierApplication.getId());
        wrapper.eq("type", Constant.ApplicationType.SUPPLIER.getType());
        List<UserApplicationFile> userApplicationFiles = applicationFileService.list(wrapper);
        ArrayList<String> applicationFiles = new ArrayList<>();
        userApplicationFiles.forEach(x -> {
            applicationFiles.add(x.getUrl());
        });
        userSupplierApplication.setApplicationFiles(applicationFiles);

        Map<String, Object> map = new HashMap<>(2);
        map.put("userSupplierApplication", userSupplierApplication);
        map.put("userRespDto", userRespDto);
        map.put("userBank", userBank);
        return map;
    }

    @Transactional
    @Override
    public void saveSupplier(UserAndSupplierDto userAndSupplierDto, Long userId, HttpServletRequest request) {
        UserSupplierApplication userSupplierApplication = userAndSupplierDto.getUserSupplierApplication();
        if (userSupplierApplication.getId() == null) {
            userSupplierApplication.setCreateTime(new Date());
            userSupplierApplication.setStatus(0);
            userSupplierApplication.setUserId(userId);
            this.save(userSupplierApplication);

        } else
            //说明申请过，且审核失败或提交审核状态下才能修改信息
            if (userSupplierApplication.getStatus() != 1) {
            userSupplierApplication.setStatus(0);
//          userSupplierApplication.setTypeIds(userSupplierApplication.getTypeIds());
            this.updateById(userSupplierApplication);
        } else {
            throw new RRException(messageUtils.getMessage("api.user.apply", request), 400);
        }
        UserApplicationFile userApplicationFile = userAndSupplierDto.getUserApplicationFile();
        //用户是否拥有相应数据，如果为null则新增前端传回来的对象
        if (userApplicationFile.getId() == null) {
            userApplicationFile.setApplicationId(userSupplierApplication.getId());
            userApplicationFile.setType(2);
            userApplicationFile.setUserId(userId);
            userApplicationFile.setCreateTime(new Date());
            userApplicationFileService.save(userApplicationFile);
        } else {
            userApplicationFileService.updateById(userApplicationFile);
        }

        boolean b = userBankService.saveAndUpdate(userAndSupplierDto.getUserBank(), userId, userSupplierApplication.getId(),request);
        if (!b) {
            throw new RRException(messageUtils.getMessage("api.user.bank.null", request), 400);
        }
//        if(StringUtils.isNotBlank(userAndSupplierDto.getUserBank().getBankAccount())){
//            if (!userAndSupplierDto.getUserBank().getBankAccount().matches(bankVerification)) {
//                throw new RRException(messageUtils.getMessage("api.user.bank.bankAccount.verification", request), 400);
//            }
//        }
        adminUserService.updateUser(userId, userAndSupplierDto.getUserRespDto(),request);
    }

    private void insertCommission(List<CommissionDto> commissionDto) {
        userDesignerApplicationService.insertCom(commissionDto);
    }

    /***
     * 通过申请商id 获取 //产品id 类型:字符串 在保存到list
     */
    private List<Integer> getProIds(long id) {
        //产品id 类型:字符串
        String proIds = baseMapper.getProIds(id);
        //产品数组
        String[] ids = proIds.split(",");
        //创建产品集合
        List<Integer> idList = new ArrayList<>();
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                int parseInt = Integer.parseInt(ids[i]);
                idList.add(parseInt);
            }
        }
        return idList;
    }

}
