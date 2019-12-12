package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.UserCollectionAndCommodityDto;
import com.lingkj.project.user.dto.UserCollectionDto;
import com.lingkj.project.user.entity.UserCollectionCommodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenyongsong
 * @date 2019-09-20 15:40:52
 */
@Mapper
public interface UserCollectionCommodityMapper extends BaseMapper<UserCollectionCommodity> {
    List<UserCollectionAndCommodityDto> getUserCollection(@Param("userId") Long userId, @Param("start") Integer start, @Param("end") Integer end);

    Integer queryUserCollectionCount(@Param("userId") Long userId);

    void deleteUserCollection(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    /**
     * 重复收藏判断
     *
     * @param userId
     * @param commodityId
     * @return
     */
    Integer selectRepeat(@Param("userId") Long userId, @Param("commodityId") Long commodityId);

    /**
     * 上传收藏判断
     * @param userId
     * @param commodityId
     */
    void removeFavorites(@Param("userId") Long userId, @Param("commodityId") Long commodityId);

    /**
     * 后端分页
     * @param userId
     * @return
     */
    Integer selectCountByUserId(Long userId);

    /**
     * 后端分页
     * @param pageSize
     * @param limit
     * @param userId
     * @return
     */
    List<UserCollectionDto> selectPageByUserId(Integer pageSize, Integer limit, Long userId);
}
