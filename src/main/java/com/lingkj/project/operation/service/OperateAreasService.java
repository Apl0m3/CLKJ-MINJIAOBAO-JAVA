package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.AreasDto;
import com.lingkj.project.operation.dto.AreasReqDto;
import com.lingkj.project.operation.entity.OperateAreas;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 行政区域县区信息表
 *
 * @author chenyongsong
 * @date 2019-07-05 12:33:44
 */
public interface OperateAreasService extends IService<OperateAreas> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询地址
     *
     * @param areasReqDto@return
     */
    List<OperateAreas> selectListById(AreasReqDto areasReqDto);

    /**
     * 通过code  获取name
     *
     * @param areasCode
     * @return
     */
    String getAreaName(String areasCode);

    /**
     *
     * @param postalCode
     * @param request
     * @return
     */
    List<AreasDto> queryByPostalCode(String postalCode, HttpServletRequest request);

    /**
     *
     * 根据上级字段，查找省级或城市
     */
    List<OperateAreas>  selectParentId(Long ParentId);
}

