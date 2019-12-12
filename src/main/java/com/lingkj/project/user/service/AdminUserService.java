package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.authentication.paramsvalidation.RegisterForm;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.user.dto.ApiUserDto;
import com.lingkj.project.user.dto.UpdatePwdReqDto;
import com.lingkj.project.user.dto.UpdateUserIntegralReqDto;
import com.lingkj.project.user.dto.UserLogonDto;
import com.lingkj.project.user.dto.UserRespDto;
import com.lingkj.project.user.entity.User;
import com.lingkj.common.authentication.paramsvalidation.LoginForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
public interface AdminUserService extends IService<User> {
    /**********************manage api******************************/
    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 启用 禁用  用户
     *
     * @param id
     */
    void enableDisable(Long id);

    /**
     * 重置密码
     *
     * @param id
     */
    String restPwd(Long id);

    /**
     * 注册账号时保存当前用户，并创建积分表
     *
     * @param userLogonDto
     */
    Map<String, Object> saveOrUpdateUser(UserLogonDto userLogonDto);

    /**********************API************************************/
    /**
     * 登录
     *
     * @param loginForm
     * @return
     */
    Map<String, Object> login(LoginForm loginForm);

    /**
     * 通过手机号查询
     *
     * @param mobile
     * @return
     */

    User queryByMobile(String mobile);
    List<User> queryByMobileLike(String mobile);


    /**
     * 通过邮箱查询
     *
     * @param mail
     * @return
     */
    User queryByMail(String mail);

    void UpdateUserIntegral(UpdateUserIntegralReqDto updateUserIntegralReqDto,HttpServletRequest request);

    /**
     * 通过QR code 查询用户
     *
     * @param qrcode
     * @return
     */
    User queryByQrCode(String qrcode);

    /**
     * 注册
     *
     * @param form
     * @return
     */
    Map<String, Object> register(RegisterForm form);

    /**
     * 退出登录
     *
     * @param
     * @param userId
     */
    void saveMuseOpenId(Long userId);

    /**
     * @param user
     * @return
     */
    Map<String, Object> getLoginMap(User user);

    /**
     * 修改登录密码
     *
     * @param updatePwdReqDto
     * @param userId
     * @return
     */
    Map<String, Object> updatePwd(UpdatePwdReqDto updatePwdReqDto, Long userId);

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    UserRespDto queryPersonInfo(Long userId);

    /**
     * 用户绑定微信openId 并设置自动登录
     *
     * @param openId
     * @return
     */
    Map<String, Object> loginByOpenId(String openId);

    /**
     * 获取用户下属用户 获取积分
     * @param params
     * @return
     */
    PageUtils getInvitationUserLog(Map<String, Object> params);

    User selectInfoById(Long id);

    //申请设计师等类型，修改用户信息
    void updateUser(Long userId, UserRespDto userRespDto, HttpServletRequest request);

    ApiUserDto selectApiUserDto(Long userId);

    List<User> getUserList();

    List<User> queryList(Integer page, Integer pageSize);
}

