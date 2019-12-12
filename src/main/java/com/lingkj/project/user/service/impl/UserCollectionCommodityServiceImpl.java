package com.lingkj.project.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.R;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.user.dto.UserCollectionAndCommodityDto;
import com.lingkj.project.user.dto.UserCollectionDto;
import com.lingkj.project.user.entity.UserCollectionCommodity;
import com.lingkj.project.user.mapper.UserCollectionCommodityMapper;
import com.lingkj.project.user.service.UserCollectionCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author chenyongsong
 * @date 2019-09-20 15:40:52
 */
@Service
public class UserCollectionCommodityServiceImpl extends ServiceImpl<UserCollectionCommodityMapper, UserCollectionCommodity> implements UserCollectionCommodityService {

    @Autowired
    private MessageUtils messageUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserCollectionCommodity> wrapper = new QueryWrapper<>();
        Long userId = (Long) params.get("userId");
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        IPage<UserCollectionCommodity> page = this.page(
                new Query<UserCollectionCommodity>(params).getPage(),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserCollectionAndCommodityDto> getUserCollectionList(Long userId, Integer start, Integer end) {
        List<UserCollectionAndCommodityDto> userCollection = this.baseMapper.getUserCollection(userId, start, end);
        return userCollection;
    }

    @Override
    public Integer getUserCollectionCount(Long userId) {
        return this.baseMapper.queryUserCollectionCount(userId);
    }

    @Override
    public void deleteUserCollectionIds(Long userId, List<Long> ids) {
        this.baseMapper.deleteUserCollection(userId, ids);
    }

    @Override
    public Integer getUserCollectionIdsCount(Long userId) {
        QueryWrapper<UserCollectionCommodity> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", userId);
        return this.count(wrapper1);
    }

    @Override
    public R addOrDelFavorites(Long userId, Long commodityId, HttpServletRequest request) {
        Integer integer = this.baseMapper.selectRepeat(userId, commodityId);
        if (integer > 0) {
            this.baseMapper.removeFavorites(userId, commodityId);
            return  R.ok(messageUtils.getMessage("api.collection.cancel",request));
        } else {
            UserCollectionCommodity collectionCommodity = new UserCollectionCommodity();
            collectionCommodity.setCommodityId(commodityId);
            collectionCommodity.setUserId(userId);
            collectionCommodity.setCreateTime(new Date());
            this.save(collectionCommodity);
            return  R.ok(messageUtils.getMessage("api.collection.success",request));
        }

    }

    @Override
    public Integer selectRepeat(Long userId, Long id) {
        return this.baseMapper.selectRepeat(userId, id);
    }

    @Override
    public PageUtils selectPageByUserId(Page page, Long userId) {
        Integer totalCount = this.baseMapper.selectCountByUserId(userId);
        List<UserCollectionDto> list = this.baseMapper.selectPageByUserId(page.getPageSize(), page.getLimit(), userId);
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

}
