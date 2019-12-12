package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.UserAccountDto;
import com.lingkj.project.user.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户金额
 *
 * @author chenyongsong
 * @date 2019-09-24 11:37:31
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    /**
     * 查询用户钱包 加锁
     * @param userId
     * @return
     */
    UserAccount selectByUserIdForUpdate(Long userId);

    /**
     * 修改用户钱包金额
     *
     * @param id
     * @param amount
     * @return
     */
    int updateAccount(@Param("id") Long id, @Param("amount") BigDecimal amount);
    /**
     * 修改用户钱包总金额
     *
     * @param id
     * @param amount
     * @return
     */
    int updateAccountTotalAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);

    /**
     * 修改用户结算总金额
     * @param id
     * @param amount
     * @return
     */
    int updateAccountSettlementAmount(@Param("id") Long id, @Param("amount") BigDecimal amount);


    /**
     * 统计 条数
     *
     * @return
     * @param type
     * @param key
     */
    Integer selectCountByCondition(@Param("type") Integer type, @Param("key") String key);

    /**
     * 列表总数
     *
     * @param limit
     * @param pageSize
     * @param type
     * @param key
     * @return
     */
    List<UserAccountDto> selectListByCondition(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("type") Integer type, @Param("key") String key);

    /**
     * 加锁查询
     * @param id
     * @return
     */
    UserAccount selectByIdForUpdate(Long id);
}
