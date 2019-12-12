package com.lingkj.project.transaction.mapper;

import com.lingkj.project.api.transaction.dto.ApiTransactionRecordDto;
import com.lingkj.project.transaction.dto.TransactionDetailRespDto;
import com.lingkj.project.transaction.dto.TransactionRecordRespDto;
import com.lingkj.project.transaction.dto.TransactionReqDto;
import com.lingkj.project.transaction.entity.TransactionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:25
 */
@Mapper
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {
    /**
     * 根据订单和订单状态 查询
     *
     * @param transactionId
     * @param status
     * @return
     */
    TransactionRecord selectByTransactionIdStatus(@Param("transactionId") String transactionId, @Param("status") Integer status);

    /**
     * 统计订单
     *
     * @param userId
     * @param type
     * @return
     */
    Integer queryUserTransactionRecordCount(@Param("userId") Long userId, @Param("type") Integer type);

    Integer queryUserTransactionRecordSumPaymentAmount(@Param("userId") Long userId);

    Integer queryUserTransactionRecordCountPay(@Param("userId") Long userId);

    /**
     * 分页查询订单
     *
     * @param limit
     * @param pageSize
     * @param userId
     * @param type
     * @return
     */
    List<TransactionRecordRespDto> queryUserTransactionRecordPage(@Param("limit") Integer limit, @Param("pageSize") Integer pageSize, @Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 根据订单和用户id 查询
     *
     * @param transactionId
     * @param userId
     * @return
     */
    TransactionRecord selectByTransactionIdUserIdForUpdate(@Param("transactionId") String transactionId, @Param("userId") Long userId);

    TransactionRecord selectByTransactionIdForUpdate(@Param("transactionId") String transactionId);

    TransactionRecord selectByIdForUpdate(Long id);

    /**
     * 查询用户订单
     *
     * @param userId
     * @param pageSize
     * @param limit
     * @param type
     * @return
     */
    List<ApiTransactionRecordDto> queryPageApi(@Param("userId") Long userId, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit, @Param("type") Integer type);

    /**
     * 根据类型统计订单
     *
     * @param userId
     * @param type
     * @return
     */
    Integer countApi(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 订单详情
     *
     * @param userId
     * @param id
     * @return
     */
    ApiTransactionRecordDto transactionInfoApi(@Param("userId") Long userId, @Param("id") Long id);

    /**
     * 管理后台查询 统计
     *
     * @param transactionReqDto
     * @return
     */
    Integer selectCountByCondition(TransactionReqDto transactionReqDto);

    /**
     * 管理后台查询
     *
     * @param transactionReqDto
     * @param pageSize
     * @param limit
     * @return
     */
    List<TransactionRecordRespDto> selectPageByCondition(@Param("transactionReqDto") TransactionReqDto transactionReqDto, @Param("pageSize") Integer pageSize, @Param("limit") Integer limit);

    /**
     * 后台查询订单详情
     *
     * @param id
     * @return
     */
    TransactionDetailRespDto selectRecordDtoById(Long id);

    BigDecimal monthlySales();



}
