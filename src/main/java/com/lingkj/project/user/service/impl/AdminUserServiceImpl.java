package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.authentication.paramsvalidation.LoginForm;
import com.lingkj.common.authentication.paramsvalidation.RegisterForm;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.enums.ErrorMessage;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.*;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.utils.jwt.JwtUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.api.user.dto.ApiUserDto;
import com.lingkj.project.integral.entity.IntegralUser;
import com.lingkj.project.integral.entity.IntegralUserLog;
import com.lingkj.project.integral.service.UserIntegralLogService;
import com.lingkj.project.integral.service.UserIntegralService;
import com.lingkj.project.operation.entity.OperateAreas;
import com.lingkj.project.operation.service.OperateAreasService;
import com.lingkj.project.operation.service.OperateRulesService;
import com.lingkj.project.user.dto.*;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.entity.UserToken;
import com.lingkj.project.user.mapper.UserMapper;
import com.lingkj.project.user.service.AdminUserService;
import com.lingkj.project.user.service.UserTokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {

    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private OperateRulesService operateRulesService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;
    @Autowired
    private OperateAreasService operateAreasService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        String username = (String) params.get("userName");
        String userRoleId = (String) params.get("userRoleId");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        if (StringUtils.isNotBlank(username)) {
            wrapper.like("user_name", username);
        }
        if (StringUtils.isNotBlank(userRoleId)) {
            wrapper.eq("user_role_id", Integer.valueOf(userRoleId));
        }

        if (StringUtils.isNotBlank(startTime)) {
            wrapper.ge("create_time", startTime);
        }
        if (StringUtils.isNotBlank(endTime)) {
            wrapper.le("create_time", endTime);
        }
        wrapper.orderByDesc("create_time");
        IPage<User> page = this.page(
                new Query<User>(params).getPage(),
                wrapper
        );
        for (User record : page.getRecords()) {
            IntegralUser userIntegral = userIntegralService.selectByUserId(record.getId());
            if (userIntegral != null) {
                record.setIntegral(userIntegral.getIntegral());
            }
           /* Map<String, Object> map = transactionRecordService.getTransaction(record.getId());
            //交易订单数
            record.setCountPay(Integer.valueOf(map.get("count").toString()));
            //交易订单金额
            record.setSumPay(Integer.valueOf(map.get("sum").toString()));*/
        }
        return new PageUtils(page);
    }

    /**
     * 启用 禁用 用户
     *
     * @param id
     */
    @Override
    public void enableDisable(Long id) {
        User user = baseMapper.selectById(id);
        if (user.getStatus() == 0) {
            user.setStatus(1);
        } else {
            user.setStatus(0);
        }
        this.updateById(user);
    }

    /**
     * 重置密码
     *
     * @param id
     */
    @Override
    public String restPwd(Long id) {
        User user = baseMapper.selectById(id);
        String password = PasswordUtil.getStringRandom(8);
        user.setPassword(DigestUtils.sha256Hex(password));
        this.updateById(user);
        return password;
    }

    @Transactional
    @Override
    public Map<String, Object> saveOrUpdateUser(UserLogonDto userLogonDto) {
        User use = new User();
        Date current = DateUtils.current();
        if (use.getId() == null) {
            use.setUserName(userLogonDto.getMail());
            use.setName(userLogonDto.getName());
            use.setCreateTime(new Date());
            use.setPassword(DigestUtils.sha256Hex(userLogonDto.getPassword()));
            use.setAutoLoginStatus(0);
            this.save(use);
            //添加 用户积分表
            saveUserIntegral(current, use);
        }
        UserRespDto userRespDto = this.queryPersonInfo(use.getId());
        //生成token，并保存到数据库
        Map<String, Object> map = this.getLoginMap(use);
        map.put("user", userRespDto);
        map.put("code", 200);
        return map;
    }

    @Override
    public User queryByMobile(String mobile) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", mobile);
        return this.getOne(wrapper);
    }

    @Override
    public List<User> queryByMobileLike(String mobile) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(mobile)) {
            wrapper.like("user_name", mobile);
        }
        List<User> users = this.list(wrapper);
        return users;
    }

    //注册邮箱查询
    @Override
    public User queryByMail(String mail) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", mail);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional
    public void UpdateUserIntegral(UpdateUserIntegralReqDto updateUserIntegralReqDto,HttpServletRequest request) {
        //查询用户积分
        IntegralUser integralUser = userIntegralService.selectByUserIdForUpdate(updateUserIntegralReqDto.getId());

        IntegralUserLog integralUserLog = new IntegralUserLog();
        integralUserLog.setPreviousValue(integralUser.getIntegral());
        integralUserLog.setChangeValue(updateUserIntegralReqDto.getIntegral());
        integralUserLog.setType(updateUserIntegralReqDto.getStatus());
        Integer currentValue;
        if (updateUserIntegralReqDto.getStatus() == 2) {
            if (integralUser.getIntegral() < updateUserIntegralReqDto.getIntegral()) {
                throw new RRException(messageUtils.getMessage("api.user.integral.insufficient", request));
            }
            currentValue = integralUserLog.getPreviousValue() - integralUserLog.getChangeValue();
            integralUserLog.setRemark(messageUtils.getMessage("manage.system.deduction", request));
        } else {
            currentValue = integralUserLog.getPreviousValue() + integralUserLog.getChangeValue();
            integralUserLog.setRemark(messageUtils.getMessage("manage.system.add", request));
        }
        integralUserLog.setCurrentValue(currentValue);
        integralUserLog.setCreateTime(DateUtils.current());
        integralUserLog.setUserId(updateUserIntegralReqDto.getId());
        //添加用户积分记录
        userIntegralLogService.save(integralUserLog);
        //修改用户积分
        integralUser.setIntegral(integralUserLog.getCurrentValue());
        userIntegralService.updateById(integralUser);


    }

    @Override
    public User queryByQrCode(String qrcode) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional
    public Map<String, Object> register(RegisterForm form) {
        String redisKey = "send_code_" + form.getMobile() + "_register";
        if (!redisUtils.exists(redisKey)) {
            throw new RRException("验证码已过期,请重新发送");
        }
        String code = redisUtils.get(redisKey);
        if (!form.getCode().equals(code)) {
            throw new RRException("验证码输入错误,请重新输入");
        }
        Date current = new Date();
        User user = new User();
        user.setUserName(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        user.setGender(1);
        user.setNickName("庆全健康_" + form.getMobile().substring(7, 11));
        user.setStatus(0);
        this.save(user);
        //添加 用户积分表
        saveUserIntegral(current, user);

        redisUtils.delete(redisKey);
        return getLoginMap(user);
    }


    /**
     * 添加用户积分
     *
     * @param current
     * @param user
     */
    private void saveUserIntegral(Date current, User user) {
        IntegralUser userIntegral = new IntegralUser();
        userIntegral.setCreateTime(current);
        userIntegral.setUserId(user.getId());
        userIntegral.setIntegral(0);
        userIntegral.setStatus(0);
        userIntegralService.save(userIntegral);
    }

    @Override
    public void saveMuseOpenId(Long userId) {
        User user = baseMapper.selectById(userId);
        user.setAutoLoginStatus(0);
        this.updateById(user);
        UserToken userToken = userTokenService.getById(user.getId());
        String token = jwtUtils.generateToken(user.getId());
        //更新token
        userToken.setToken(token);
        userTokenService.updateById(userToken);
    }

    @Override
    public Map<String, Object> getLoginMap(User user) {
        //当前时间
        Date now = new Date();
        //生成token
        String token = jwtUtils.generateToken(user.getId());
        //判断是否生成过token
        UserToken userToken = userTokenService.getById(user.getId());
        //过期时间
        Date expireDate = new Date(now.getTime() + jwtUtils.getExpire() * 1000);
        if (userToken == null) {
            userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireDate);

            //保存token
            userTokenService.save(userToken);
        } else {
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireDate);

            //更新token
            userTokenService.updateById(userToken);
        }
        Map<String, Object> map = new HashMap<>(5);
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        return map;
    }

    @Override
    public Map<String, Object> updatePwd(UpdatePwdReqDto updatePwdReqDto, Long userId) {
        String redisKey = "send_code_" + updatePwdReqDto.getMobile();
        if (updatePwdReqDto.getType() == 1) {
            redisKey = redisKey + "_forget";
        } else {
            redisKey = redisKey + "_update";
        }

        if (!redisUtils.exists(redisKey)) {
            throw new RRException("验证码已过期,请重新发送");
        }
        String code = redisUtils.get(redisKey);
        if (!updatePwdReqDto.getCode().equals(code)) {
            throw new RRException("验证码输入错误,请重新输入");
        }

        User user = queryByMobile(updatePwdReqDto.getMobile());
        user.setPassword(DigestUtils.sha256Hex(updatePwdReqDto.getPassword()));
        this.updateById(user);
        if (updatePwdReqDto.getType() == 1) {
            return getLoginMap(user);
        }
        return null;
    }

    @Override
    public UserRespDto queryPersonInfo(Long userId) {
        User user = this.getById(userId);
        UserRespDto userRespDto = new UserRespDto();
        if (user != null) {
            BeanUtils.copyProperties(user, userRespDto);
            if (userRespDto.getProvince() != null && userRespDto.getProvince() != 1) {
                OperateAreas operateAreas = operateAreasService.getById(userRespDto.getProvince());
                if (operateAreas == null) {
                    userRespDto.setAddressName((StringUtils.isBlank(userRespDto.getAddressName()) ? "" : userRespDto.getAddressName()) + "");
                } else {
                    userRespDto.setAddressName((StringUtils.isBlank(userRespDto.getAddressName()) ? "" : userRespDto.getAddressName()) + operateAreas.getName());
                    userRespDto.setProvinceStr(operateAreas.getName());
                }
            }
            if (userRespDto.getCity() != null) {
                OperateAreas operateAreas = operateAreasService.getById(userRespDto.getCity());
                if (operateAreas == null) {
                    userRespDto.setAddressName((StringUtils.isBlank(userRespDto.getAddressName()) ? "" : userRespDto.getAddressName()) + "");
                } else {
                    userRespDto.setAddressName((StringUtils.isBlank(userRespDto.getAddressName()) ? "" : userRespDto.getAddressName()) + "-" + operateAreas.getName());
                    userRespDto.setCityStr(operateAreas.getName());
                }

            }
        }

        return userRespDto;
    }

    @Override
    public Map<String, Object> loginByOpenId(String openId) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("auto_login_status", 1);
        User user = this.getOne(wrapper);

        Assert.isNull(user, "用户不存在", 10106);
        return getLoginMap(user);
    }

    @Override
    public Map<String, Object> login(LoginForm loginForm) {
        User user = queryByMobile(loginForm.getMobile());
        Assert.isNull(user, ErrorMessage.user_exist.getMessage(), ErrorMessage.user_exist.getCode());
        if (user.getStatus() == null || user.getStatus() != 0) {
            throw new RRException("用户已禁用");
        }
        if (!user.getPassword().equals(DigestUtils.sha256Hex(loginForm.getPassword()))) {
            throw new RRException(ErrorMessage.user_exist.getMessage(), ErrorMessage.user_exist.getCode());
        }
        this.updateById(user);

        //当前时间
        return getLoginMap(user);
    }

    @Override
    public PageUtils getInvitationUserLog(Map<String, Object> params) {
        Integer page = (Integer) params.get("page");
        Integer pageSize = (Integer) params.get("limit");
        Long userId = (Long) params.get("userId");

        Integer pageStart = (page - 1) * pageSize;
        Integer pageEnd = page * pageSize;

        List<GetInvitationUserLogRespDto> logRespDtos = this.baseMapper.getInvitationUserLog(userId, pageStart, pageEnd);
        Integer count = this.baseMapper.getInvitationUserLogCount(userId);
        PageUtils pageUtils = new PageUtils(logRespDtos, count, pageSize, page);
        return pageUtils;
    }

    @Override
    public User selectInfoById(Long id) {
        return this.getById(id);
    }

    @Override
    public void updateUser(Long userId, UserRespDto userRespDto, HttpServletRequest request) {
        Assert.isBlank(userRespDto.getAddress(), messageUtils.getMessage("api.userAddress.address", request), 400);
        Assert.isNull(userRespDto.getPostalCode(), messageUtils.getMessage("api.userAddress.postalCode", request), 400);
        Assert.isNull(userRespDto.getProvince(), messageUtils.getMessage("api.userAddress.province", request), 400);
        Assert.isNull(userRespDto.getCity(), messageUtils.getMessage("api.userAddress.city", request), 400);

        User user = this.getById(userId);
        if (StringUtils.isNotBlank(userRespDto.getPostalCode())) {
            user.setPostalCode(userRespDto.getPostalCode());
        }
        user.setAddress(userRespDto.getAddress());
        user.setProvince(userRespDto.getProvince());
        user.setCity(userRespDto.getCity());
        user.setCountry(userRespDto.getCountry());
        this.updateById(user);
    }

    @Override
    public ApiUserDto selectApiUserDto(Long userId) {
        ApiUserDto apiUserDto = new ApiUserDto();
        User user = this.getById(userId);
        BeanUtils.copyProperties(user, apiUserDto);
        return apiUserDto;
    }

    @Override
    public List<User> getUserList() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public List<User> queryList(Integer page, Integer pageSize) {
        Page page1 = new Page(page, pageSize);
        return this.baseMapper.queryList(page1.getLimit(), page1.getPageSize());
    }

    private String getAddressName(User user) {
        StringBuilder addressName = new StringBuilder();
        if (StringUtils.isNotBlank(user.getAddress())) {
            String[] strings = user.getAddress().split("_");
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    OperateAreas operateAreas = operateAreasService.getById(string);
                    if (operateAreas == null) {
                        continue;
                    }
                    addressName.append(operateAreas.getName());
                    if (operateAreas.getLevel() != 3) {
                        addressName.append("-");
                    }

                }
            }
        }
        return addressName.toString();
    }

}
