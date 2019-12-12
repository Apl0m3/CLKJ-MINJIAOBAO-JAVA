package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.operation.dto.CommonProblemDto;
import com.lingkj.project.operation.entity.CommonProblem;
import com.lingkj.project.operation.mapper.CommonProblemMapper;
import com.lingkj.project.operation.service.CommonProblemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;

/**
 * 常见问题
 *
 * @author chenyongsong
 * @date 2019-09-19 11:02:41
 */
@Service
public class CommonProblemServiceImpl extends ServiceImpl<CommonProblemMapper, CommonProblem> implements CommonProblemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        QueryWrapper<CommonProblem> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(name),"title",name);
        IPage<CommonProblem> page = this.page(
                new Query<CommonProblem>(params).getPage(),
                wrapper.orderByDesc("create_time")
        );

        return new PageUtils(page);
    }

    @Override
    public void saveOrUpdateCommonProblem(CommonProblem commonProblem) {
        Long id = commonProblem.getId();
        //新增
        if (id == null) {
            commonProblem.setCreateTime(new Date());
            commonProblem.setCreateBy(getSysUserId());
            baseMapper.insert(commonProblem);
        }else {
            //更新
            commonProblem.setUpdateTime(new Date());
            commonProblem.setUpdateBy(getSysUserId());
            baseMapper.updateById(commonProblem);
        }

    }

    @Override
    public void updateBatchIds(Long[] ids) {
        baseMapper.updateBatchIds(ids);
    }

    @Override
    public List<CommonProblemDto> getCommonProblemDtoList() {

        return baseMapper.getCommonProblemDtoList();
    }

}
