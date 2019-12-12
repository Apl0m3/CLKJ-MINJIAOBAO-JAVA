package com.lingkj.project.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.bean.entity.Page;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.commodity.dto.*;
import com.lingkj.project.commodity.dto.CommodityAttributesDto;
import com.lingkj.project.commodity.dto.CommodityDto;
import com.lingkj.project.commodity.dto.CommodityNumberAttributesDto;
import com.lingkj.project.commodity.dto.IntegralCommodityAddDto;
import com.lingkj.project.commodity.entity.*;
import com.lingkj.project.commodity.mapper.CommodityMapper;
import com.lingkj.project.commodity.service.*;
import com.lingkj.project.hot.entity.HotWords;
import com.lingkj.project.hot.service.HotWordsService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.project.user.service.UserCollectionCommodityService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.lingkj.common.utils.ShiroUtils.getSysUserId;


/**
 * @author Administrator
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CommodityFileService commodityFileService;
    @Autowired
    private CommodityAttributesService commodityAttributesService;
    @Autowired
    private CommodityAttributesValueService commodityAttributesValueService;
    @Autowired
    private CommodityNumberAttributesService commodityNumberAttributesService;
    @Autowired
    private CommodityNumberAttributesValueService commodityNumberAttributesValueService;
    @Autowired
    private MessageUtils messageUtils;
    @Autowired
    private CommodityTypeService commodityTypeService;
    @Autowired
    private CommodityLadderService commodityLadderService;
    @Autowired
    private CommodityExpectedService commodityExpectedService;
    @Autowired
    private UserCollectionCommodityService userCollectionCommodityService;
    @Autowired
    private HotWordsService hotWordsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.ne("status", 2);
        String name = (String) params.get("name");
        String status = (String) params.get("status");

        //分类id
        String commodityTypeId = (String) params.get("commodityTypeId");
        String type = (String) params.get("type");
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.isNotBlank(status)) {
            wrapper.eq("status", Long.valueOf(status));
        }

        if (StringUtils.isNotBlank(commodityTypeId)) {
            wrapper.eq("commodity_type_id", Long.valueOf(commodityTypeId));
        }
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq("type", Long.valueOf(type));
        }
        wrapper.orderByDesc("create_time");
        IPage<Commodity> page = this.page(
                new Query<Commodity>(params).getPage(),
                wrapper.orderByDesc("create_time"));

        for (Commodity methodEntity : page.getRecords()) {
//            methodEntity.setTypeName(methodEntity.getType() == 1 ? "普通商品" : "积分商品");
            SysUserEntity sysUserEntity = sysUserService.getById(methodEntity.getCreateSysUserId());
            if (sysUserEntity != null) {
                methodEntity.setCreateSysUserName(sysUserEntity.getUsername());
                if (methodEntity.getUpdateSysUserId() != null) {
                    sysUserEntity = sysUserService.getById(methodEntity.getUpdateSysUserId());
                    methodEntity.setUpdateSysUserName(sysUserEntity.getUsername());
                }
            }

            Long commodityTypeId1 = methodEntity.getCommodityTypeId();
            CommodityType commodityType = commodityTypeService.getById(commodityTypeId1);
            if(commodityType != null){
                methodEntity.setCommodityTypeName(commodityType.getName());
            }
//            EntityWrapper entityWrapper = new EntityWrapper<>();
//            entityWrapper.eq("commodity_id", methodEntity.getId());

        }
        return new PageUtils(page);
    }

    @Override
    public void updateBatchIds(Long[] ids) {

        baseMapper.updateBatchIds(ids);
    }


    @Override
    public List<Commodity> getCommodity(String name) {
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        if (StringUtils.isNotBlank(name)) {
            wrapper.like("name", name);
        }
        return this.list(wrapper);
    }


    @Override
    public CommodityDto selectCommodity(Long id) {
        CommodityDto dto = new CommodityDto();
        Commodity commodity = this.getById(id);

        dto.setCommodity(commodity);
        //查询轮播图片
        CommodityFile[] files = commodityFileService.selectEntityByCommodityId(id);
        dto.setCommodityFiles(files);

        //查询商品属性
        CommodityAttributesDto[] commodityAttributesArray = commodityAttributesService.getList(id);
        if (commodityAttributesArray != null && commodityAttributesArray.length > 0) {
            for (CommodityAttributesDto commodityAttributesDto : commodityAttributesArray) {
                CommodityAttributesValue[] commodityAttributesValues = commodityAttributesValueService.queryByCommodityAttributesId(commodityAttributesDto.getId());
                commodityAttributesDto.setAttributeValueList(commodityAttributesValues);
            }
        }

        //查询数量属性 只有数量选择类型是3服装，才有下边的属性
          if (commodity.getNumberSelectType().equals(3)) {
            CommodityNumberAttributesDto[] numberAttributeList = commodityNumberAttributesService.getList(id);
            dto.setCommodityNumberAttributesArray(numberAttributeList);
        }else{
            dto.setCommodityNumberAttributesArray(new CommodityNumberAttributesDto[]{});
        }

        dto.setCommodityAttributesArray(commodityAttributesArray);
        //查询商品 预计到达时间
        CommodityExpect[] commodityExpects = commodityExpectedService.getList(id);
        //查询商品阶梯价
        CommodityLadder[] commodityLadders = commodityLadderService.getList(id);
        dto.setCommodityExpectedArray(commodityExpects);
        dto.setCommodityLadderArray(commodityLadders);
        return dto;
    }

    @Override
    public IntegralCommodityAddDto selectIntegralCommodity(Long id) {
        IntegralCommodityAddDto dto = new IntegralCommodityAddDto();
        Commodity commodity = this.getById(id);
        //查询轮播图片
        CommodityFile[] files = commodityFileService.selectEntityByCommodityId(id);
        dto.setCommodity(commodity);
        dto.setCommodityFiles(files);
        return dto;
    }

    @Override
    @Transactional
    public void insertCommodity(CommodityDto commodityAddDto, Long sysUserId) {
        Date now = new Date();
        Commodity commodity = commodityAddDto.getCommodity();
        commodity.setType(1);
        commodity.setCreateTime(now);
        commodity.setCreateSysUserId(sysUserId);
        commodity.setStatus(1);
        this.save(commodity);
        //添加多图
        insertCommodityFile(commodityAddDto.getCommodityFiles(), sysUserId, now, commodity.getId());
        //添加商品属性
        this.insertCommodityAttributes(commodityAddDto.getCommodityAttributesArray(), commodity.getId(), sysUserId, now);
        //添加商品数量属性
        this.insertCommodityNumberAttributes(commodityAddDto.getCommodityNumberAttributesArray(), commodity, sysUserId, now);
        //添加商品阶梯价
        this.insertCommodityLadder(commodityAddDto.getCommodityLadderArray(), commodity.getId(), sysUserId, now);
        //添加商品预计到货时间
        this.insertCommodityExpect(commodityAddDto.getCommodityExpectedArray(), commodity.getId(), sysUserId, now);
    }

    /**
     * 属性商品数量属性
     *
     * @param commodityNumberAttributesArray 数量属性集合
     * @param commodity                      商品
     * @param sysUserId                      操作管理员id
     * @param now                            现在时间
     */
    private void insertCommodityNumberAttributes(CommodityNumberAttributesDto[] commodityNumberAttributesArray, Commodity commodity, Long sysUserId, Date now) {
        //只有数量选择类型为3，才有数量属性
        if (!commodity.getNumberSelectType().equals(3)) {
            return;
        }
        //非空判断
        if (commodityNumberAttributesArray == null || commodityNumberAttributesArray.length == 0) {
            return;
        }
        CommodityNumberAttributes commodityNumberAttributes;
        List<CommodityNumberAttributesValue> insertList = new ArrayList<>();
        for (int i = 0; i < commodityNumberAttributesArray.length; i++) {
            //新增属性
            CommodityNumberAttributesDto dto = commodityNumberAttributesArray[i];
            commodityNumberAttributes = new CommodityNumberAttributes();
            commodityNumberAttributes.setCommodityId(commodity.getId());
            commodityNumberAttributes.setName(dto.getName());
            commodityNumberAttributes.setSort(i);
            commodityNumberAttributes.setCreateTime(now);
            commodityNumberAttributes.setCreateBy(sysUserId);
            commodityNumberAttributes.setStatus(0);
            //保存属性
            commodityNumberAttributesService.save(commodityNumberAttributes);
            //添加属性值 到新增集合
            CommodityNumberAttributesValue[] numberAttributeValueList = dto.getNumberAttributeValueList();
            if (numberAttributeValueList == null || numberAttributeValueList.length == 0) {
                continue;
            }
            for (int j = 0; j < numberAttributeValueList.length; j++) {
                CommodityNumberAttributesValue commodityNumberAttributesValue = numberAttributeValueList[j];
                commodityNumberAttributesValue.setSort(j);
                commodityNumberAttributesValue.setCommodityNumberAttributesId(commodityNumberAttributes.getId());
                commodityNumberAttributesValue.setStatus(0);
                commodityNumberAttributesValue.setCreateBy(sysUserId);
                commodityNumberAttributesValue.setCreateTime(now);
                //add新增集合
                insertList.add(commodityNumberAttributesValue);
            }
        }

        //保存属性值集合
        if (insertList.size() > 0) {
            commodityNumberAttributesValueService.saveBatch(insertList);
        }
    }

    @Override
    @Transactional
    public void insertIntegralCommodity(IntegralCommodityAddDto integralCommodityAddDto) {
        Date now = new Date();
        Commodity commodity = integralCommodityAddDto.getCommodity();
        commodity.setCreateTime(now);
        commodity.setCreateSysUserId(getSysUserId());
        commodity.setStatus(1);
        this.save(commodity);
        //添加多图
        insertCommodityFile(integralCommodityAddDto.getCommodityFiles(), getSysUserId(), now, commodity.getId());
    }

    @Override
    public void updateIntegralCommodity(IntegralCommodityAddDto integralCommodityAddDto) {
        //修改商品
        Date now = new Date();
        Commodity commodity = integralCommodityAddDto.getCommodity();
        commodity.setUpdateTime(now);
        commodity.setUpdateSysUserId(getSysUserId());
        this.updateById(commodity);
        //修改轮播图
        updateCommodityFiles(integralCommodityAddDto.getCommodityFiles(), getSysUserId(), now, commodity.getId());
    }

    /**
     * 新增商品属性
     *
     * @param commodityAttributesArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void insertCommodityAttributes(CommodityAttributesDto[] commodityAttributesArray, Long commodityId, Long sysUserId, Date now) {
        if (commodityAttributesArray == null || commodityAttributesArray.length == 0) {
            return;
        }

        CommodityAttributes commodityAttributes;
        CommodityAttributesValue commodityAttributesValue;

        List<CommodityAttributesValue> commodityAttributesValueInsertList = new ArrayList<>();
        for (int i = 0; i < commodityAttributesArray.length; i++) {
            commodityAttributes = getCommodityAttributes(commodityAttributesArray[i], commodityId, sysUserId, now, i);
            commodityAttributes.setName(commodityAttributesArray[i].getName());
            commodityAttributes.setQuantity(commodityAttributesArray[i].getQuantity());
            commodityAttributes.setAmount(commodityAttributesArray[i].getAmount());
            commodityAttributes.setRemark(commodityAttributesArray[i].getRemark());
            commodityAttributes.setFactoryPrice(commodityAttributesArray[i].getFactoryPrice());
            commodityAttributes.setType(commodityAttributesArray[i].getType());
            commodityAttributes.setSelectType(commodityAttributesArray[i].getSelectType());
            commodityAttributesService.save(commodityAttributes);
            //输入类型没有属性值
            if (commodityAttributesArray[i].getType().equals(1)) {
                continue;
            }
            //新增属性值
            CommodityAttributesValue[] commodityAttributesValues = commodityAttributesArray[i].getAttributeValueList();
            if (commodityAttributesValues != null && commodityAttributesValues.length > 0) {
                for (int j = 0; j < commodityAttributesValues.length; j++) {
                    commodityAttributesValue = getCommodityAttributesValue(sysUserId, now, commodityAttributes, commodityAttributesValues, j);
                    commodityAttributesValueInsertList.add(commodityAttributesValue);
                }
            }
        }
        //新增属性值
        if (commodityAttributesValueInsertList.size() > 0) {
            commodityAttributesValueService.saveBatch(commodityAttributesValueInsertList);
        }
    }

    private CommodityAttributesValue getCommodityAttributesValue(Long sysUserId, Date now, CommodityAttributes commodityAttributes, CommodityAttributesValue[] commodityAttributesValues, int j) {
        CommodityAttributesValue commodityAttributesValue = commodityAttributesValues[j];
        commodityAttributesValue.setCommodityAttributesId(commodityAttributes.getId());
        commodityAttributesValue.setSort(j);
        commodityAttributesValue.setCreateBy(sysUserId);
        commodityAttributesValue.setCreateTime(now);
        commodityAttributesValue.setStatus(0);
        return commodityAttributesValue;
    }

    private CommodityAttributes getCommodityAttributes(CommodityAttributesDto commodityAttributesDto, Long commodityId, Long sysUserId, Date now, int i) {
        CommodityAttributes commodityAttributes = new CommodityAttributes();
        commodityAttributes.setCommodityId(commodityId);
        commodityAttributes.setQuantity(commodityAttributesDto.getQuantity());
        commodityAttributes.setCategory(commodityAttributesDto.getCategory());
        commodityAttributes.setStatus(0);
        commodityAttributes.setCreateTime(now);
        commodityAttributes.setCreateBy(sysUserId);
        commodityAttributes.setSort(i);
        return commodityAttributes;
    }

    private void insertCommodityFile(CommodityFile[] commodityFiles, Long sysUserId, Date now, Long commodityId) {
        if (commodityFiles == null || commodityFiles.length == 0) {
            return;
        }
        List<CommodityFile> list = new ArrayList<>();
        CommodityFile commodityFile;
        for (int i = 0; i < commodityFiles.length; i++) {
            commodityFile = commodityFiles[i];
            //只有图片
            commodityFile.setFileType(1);
            commodityFile.setCommodityId(commodityId);
            commodityFile.setCreateSysUserId(sysUserId);
            commodityFile.setCreateTime(now);
            commodityFile.setSequence(i);
            list.add(commodityFile);
        }
        commodityFileService.saveBatch(list);

    }

    /**
     * 添加阶梯价
     *
     * @param commodityLadderArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void insertCommodityLadder(CommodityLadder[] commodityLadderArray, Long commodityId, Long sysUserId, Date now) {
        List<CommodityLadder> ladders = new ArrayList<>();
        for (CommodityLadder commodityLadder : commodityLadderArray) {
            commodityLadder.setCommodityId(commodityId);
            commodityLadder.setCreateTime(now);
            commodityLadder.setCreateBy(sysUserId);
            commodityLadder.setStatus(0);
            ladders.add(commodityLadder);
        }
        if (ladders.size() > 0) {
            commodityLadderService.saveBatch(ladders);
        }
    }

    /**
     * 添加预计到货时间
     *
     * @param commodityExpectArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void insertCommodityExpect(CommodityExpect[] commodityExpectArray, Long commodityId, Long sysUserId, Date now) {
        List<CommodityExpect> expects = new ArrayList<>();
        for (CommodityExpect commodityExpect : commodityExpectArray) {
            commodityExpect.setCommodityId(commodityId);
            commodityExpect.setCreateTime(now);
            commodityExpect.setCreateBy(sysUserId);
            commodityExpect.setStatus(0);
            expects.add(commodityExpect);
        }
        if (expects.size() > 0) {
            commodityExpectedService.saveBatch(expects);
        }
    }

    @Override
    @Transactional
    public void updateCommodity(CommodityDto commodityAddDto, Long sysUserId) {
        //修改商品
        Date now = new Date();
        Commodity commodity = commodityAddDto.getCommodity();
        commodity.setUpdateTime(now);
        commodity.setUpdateSysUserId(sysUserId);
        updateById(commodity);
        //修改轮播图
        updateCommodityFiles(commodityAddDto.getCommodityFiles(), sysUserId, now, commodity.getId());
        //修改商品属性
        updateCommodityAttributes(commodityAddDto.getCommodityAttributesArray(), commodity.getId(), sysUserId, now);
        //修改商品数量属性
        updateCommodityNumberAttributes(commodityAddDto.getCommodityNumberAttributesArray(), commodity, sysUserId, now);
        //修改商品阶梯价
        updateCommodityLadder(commodityAddDto.getCommodityLadderArray(), commodity.getId(), sysUserId, now);
        //修改商品预计到货时间
        updateCommodityExpect(commodityAddDto.getCommodityExpectedArray(), commodity.getId(), sysUserId, now);
    }

    /**
     * 修改商品数量属性
     *
     * @param commodityNumberAttributesDtos 数量属性数组
     * @param commodity                     商品
     * @param sysUserId                     修改管理员Id
     * @param now                           当前时间
     */
    private void updateCommodityNumberAttributes(CommodityNumberAttributesDto[] commodityNumberAttributesDtos, Commodity commodity, Long sysUserId, Date now) {

        List<Long> updateAttributesIds = new ArrayList<>();
        //如果数量选择类型不是3，移除所有
        if (commodity.getNumberSelectType().equals(3)) {
            Arrays.stream(commodityNumberAttributesDtos).forEach(
                    (temp) -> {
                        if (temp.getId() != null) {
                            updateAttributesIds.add(temp.getId());
                        }
                    }
            );
        }
        //逻辑删除不在修改中的属性和属性值
        //先查询需要删除的id
        List<Long> deleteIds = commodityNumberAttributesService.selectNotInIds(updateAttributesIds, commodity.getId());
        if (deleteIds != null && deleteIds.size() > 0) {
            commodityNumberAttributesService.updateStatusInIds(deleteIds);
            commodityNumberAttributesValueService.updateStatusInIds(deleteIds);
        }

        //如果数量选择类型不是3，都移除了，也不会新增
        if (!commodity.getNumberSelectType().equals(3)) {
            return;
        }

        CommodityNumberAttributes commodityNumberAttributes;
        List<CommodityNumberAttributes> updateAttributesList = new ArrayList<>();
        List<CommodityNumberAttributesValue> insertValueList = new ArrayList<>();
        List<CommodityNumberAttributesValue> updateValueList = new ArrayList<>();
        List<Long> updateValueIds = new ArrayList<>();
        for (int i = 0; i < commodityNumberAttributesDtos.length; i++) {
            CommodityNumberAttributesDto dto = commodityNumberAttributesDtos[i];
            CommodityNumberAttributesValue[] numberAttributeValueList = dto.getNumberAttributeValueList();

            commodityNumberAttributes = new CommodityNumberAttributes();
            if (dto.getId() == null) {
                commodityNumberAttributes.setStatus(0);
                commodityNumberAttributes.setSort(i);
                commodityNumberAttributes.setCommodityId(commodity.getId());
                commodityNumberAttributes.setCreateBy(sysUserId);
                commodityNumberAttributes.setCreateTime(now);
                commodityNumberAttributes.setName(dto.getName());
                commodityNumberAttributesService.save(commodityNumberAttributes);

                if (numberAttributeValueList != null && numberAttributeValueList.length > 0) {
                    for (int j = 0; j < numberAttributeValueList.length; j++) {
                        CommodityNumberAttributesValue insertValue = numberAttributeValueList[j];
                        insertValue.setCommodityNumberAttributesId(commodityNumberAttributes.getId());
                        insertValue.setSort(j);
                        insertValue.setStatus(0);
                        insertValue.setCreateTime(now);
                        insertValue.setCreateBy(sysUserId);
                        //加入新增集合
                        insertValueList.add(insertValue);
                    }
                }

            } else {
                commodityNumberAttributes.setId(dto.getId());
                commodityNumberAttributes.setUpdateBy(sysUserId);
                commodityNumberAttributes.setSort(i);
                commodityNumberAttributes.setUpdateTime(now);
                commodityNumberAttributes.setName(dto.getName());
                updateAttributesList.add(commodityNumberAttributes);

                updateValueIds.clear();
                if (numberAttributeValueList != null && numberAttributeValueList.length > 0) {
                    for (int k = 0; k < numberAttributeValueList.length; k++) {
                        CommodityNumberAttributesValue tempValue = numberAttributeValueList[k];
                        if (tempValue.getId() == null) {
                            tempValue.setCommodityNumberAttributesId(commodityNumberAttributes.getId());
                            tempValue.setSort(k);
                            tempValue.setStatus(0);
                            tempValue.setCreateTime(now);
                            tempValue.setCreateBy(sysUserId);
                            //加入新增集合
                            insertValueList.add(tempValue);
                        } else {
                            tempValue.setSort(k);
                            tempValue.setUpdateTime(now);
                            tempValue.setUpdateBy(sysUserId);
                            //加入新增集合
                            updateValueList.add(tempValue);
                            updateValueIds.add(tempValue.getId());
                        }
                    }
                }
                commodityNumberAttributesValueService.updateStatusNotInIds(updateValueIds, commodityNumberAttributes.getId());
            }
        }
        //修改属性
        if (updateAttributesList.size() > 0) {
            commodityNumberAttributesService.updateBatchById(updateAttributesList);
        }
        //新增属性值
        if (insertValueList.size() > 0) {
            commodityNumberAttributesValueService.saveBatch(insertValueList);
        }
        //修改属性值
        if (updateValueList.size() > 0) {
            commodityNumberAttributesValueService.updateBatchById(updateValueList);
        }
    }

    /**
     * 修改商品图片
     *
     * @param commodityFiles
     * @param sysUserId
     * @param now
     * @param commodityId
     */
    private void updateCommodityFiles(CommodityFile[] commodityFiles, Long sysUserId, Date now, Long commodityId) {
        //修改轮播图
        List<CommodityFile> fileAddList = new ArrayList<>();
        List<CommodityFile> fileUpdList = new ArrayList<>();
        for (int i = 0; i < commodityFiles.length; i++) {

            CommodityFile commodityFile = commodityFiles[i];

            if (commodityFile.getId() != null) {
                commodityFile.setUpdateSysUserId(sysUserId);
                commodityFile.setUpdateTime(now);
                commodityFile.setSequence(i);
                fileUpdList.add(commodityFile);
            } else {
                commodityFile.setCommodityId(commodityId);
                commodityFile.setCreateTime(now);
                commodityFile.setCreateSysUserId(sysUserId);
                commodityFile.setSequence(i);
                fileAddList.add(commodityFile);
            }
        }
        //必须先删除
        commodityFileService.deleteNotInBatch(fileUpdList, commodityId);
        if (!CollectionUtils.isEmpty(fileAddList)) {
            commodityFileService.saveBatch(fileAddList);
        }
        if (!CollectionUtils.isEmpty(fileUpdList)) {
            commodityFileService.updateBatchById(fileUpdList);
        }
    }

    /**
     * 修改商品属性
     *
     * @param commodityAttributesArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void updateCommodityAttributes(CommodityAttributesDto[] commodityAttributesArray, Long commodityId, Long sysUserId, Date now) {
        if (commodityAttributesArray == null || commodityAttributesArray.length == 0) {
            return;
        }

        CommodityAttributesDto temp;
        CommodityAttributesValue commodityAttributesValue;
        //商品属性
        CommodityAttributes commodityAttributes;
        //新增属性值集合
        List<CommodityAttributesValue> insertCommodityAttributesValueList = new ArrayList<>();
        //修改属性值集合
        List<CommodityAttributesValue> updateCommodityAttributesValueList = new ArrayList<>();

        //修改属性集合
        List<CommodityAttributes> updateCommodityAttributesList = new ArrayList<>();
        List<Long> updateCommodityAttributesIds = new ArrayList<>();
        List<Long> updateCommodityAttributesValueIds = new ArrayList<>();

        Arrays.stream(commodityAttributesArray).forEach(
                (item) -> {
                    if (item.getId() != null) {
                        updateCommodityAttributesIds.add(item.getId());
                    }
                }
        );

        //先删除已经删除的属性和属性值
        List<Long> deleteCommodityAttributesIds = commodityAttributesService.getNotInIds(commodityId, updateCommodityAttributesIds);
        if (deleteCommodityAttributesIds != null && deleteCommodityAttributesIds.size() > 0) {
            // 删除属性
            commodityAttributesService.updateStatusInIds(deleteCommodityAttributesIds);
            // 删除已经删除的属性 的属性值
            commodityAttributesValueService.updateStatusInCommodityAttributesValueIds(deleteCommodityAttributesIds);
        }

        for (int i = 0; i < commodityAttributesArray.length; i++) {
            temp = commodityAttributesArray[i];
            if (commodityAttributesArray[i].getId() == null) {
                //处理新增属性
                commodityAttributes = new CommodityAttributes();
                commodityAttributes.setCommodityId(commodityId);
                commodityAttributes.setQuantity(temp.getQuantity());
                commodityAttributes.setAmount(temp.getAmount());
                commodityAttributes.setFactoryPrice(temp.getFactoryPrice());
                commodityAttributes.setStatus(0);
                commodityAttributes.setCreateTime(now);
                commodityAttributes.setCreateBy(sysUserId);
                commodityAttributes.setSort(i);
                commodityAttributes.setName(temp.getName());
                commodityAttributes.setCategory(temp.getCategory());
                commodityAttributes.setRemark(temp.getRemark());
                commodityAttributes.setType(temp.getType());
                commodityAttributes.setSelectType(temp.getSelectType());
                //插入属性
                commodityAttributesService.save(commodityAttributes);
                //输入类型没有属性值
                if (temp.getType().equals(1)) {
                    continue;
                }
                CommodityAttributesValue[] commodityAttributesValues = temp.getAttributeValueList();
                if (commodityAttributesValues != null && commodityAttributesValues.length > 0) {
                    //处理新增属性 新增属性值
                    for (int j = 0; j < commodityAttributesValues.length; j++) {
                        commodityAttributesValue = commodityAttributesValues[j];
                        commodityAttributesValue.setCommodityAttributesId(commodityAttributes.getId());
                        commodityAttributesValue.setSort(j);
                        commodityAttributesValue.setCreateBy(sysUserId);
                        commodityAttributesValue.setCreateTime(now);
                        commodityAttributesValue.setStatus(0);
                        insertCommodityAttributesValueList.add(commodityAttributesValue);
                    }
                }

            } else {
                //处理修改属性
                commodityAttributes = new CommodityAttributes();
                commodityAttributes.setId(temp.getId());
                commodityAttributes.setSort(i);
                commodityAttributes.setUpdateTime(now);
                commodityAttributes.setUpdateBy(sysUserId);

                commodityAttributes.setType(temp.getType());
                commodityAttributes.setSelectType(temp.getSelectType());
                commodityAttributes.setName(temp.getName());
                commodityAttributes.setQuantity(temp.getQuantity());
                commodityAttributes.setAmount(temp.getAmount());
                commodityAttributes.setFactoryPrice(temp.getFactoryPrice());
                commodityAttributes.setCategory(temp.getCategory());
                commodityAttributes.setRemark(temp.getRemark());
                commodityAttributes.setName(temp.getName());

                updateCommodityAttributesList.add(commodityAttributes);

                //
                CommodityAttributesValue[] commodityAttributesValues = temp.getAttributeValueList();

                updateCommodityAttributesValueIds.clear();
                //输入类型没有属性值
                if (!temp.getType().equals(1)) {
                    if (commodityAttributesValues != null && commodityAttributesValues.length > 0) {
                        for (int k = 0; k < commodityAttributesValues.length; k++) {
                            commodityAttributesValue = commodityAttributesValues[k];
                            if (commodityAttributesValue.getId() == null) {
                                //处理修改属性 新增属性值
                                commodityAttributesValue.setCommodityAttributesId(temp.getId());
                                commodityAttributesValue.setSort(k);
                                commodityAttributesValue.setCreateBy(sysUserId);
                                commodityAttributesValue.setCreateTime(now);
                                commodityAttributesValue.setStatus(0);
                                insertCommodityAttributesValueList.add(commodityAttributesValue);
                            } else {
                                commodityAttributesValue.setSort(k);
                                commodityAttributesValue.setUpdateBy(sysUserId);
                                commodityAttributesValue.setUpdateTime(now);
                                updateCommodityAttributesValueList.add(commodityAttributesValue);

                                updateCommodityAttributesValueIds.add(commodityAttributesValue.getId());
                            }
                        }
                    }
                }
                commodityAttributesValueService.updateNotInIds(temp.getId(), updateCommodityAttributesValueIds);
            }
        }

        //新增属性值
        if (insertCommodityAttributesValueList.size() > 0) {
            commodityAttributesValueService.saveBatch(insertCommodityAttributesValueList);
        }

        //修改属性值
        if (updateCommodityAttributesValueList.size() > 0) {
            commodityAttributesValueService.updateBatchById(updateCommodityAttributesValueList);
        }

        //新增属性 前面循环中已经新增
        //修改属性
        if (updateCommodityAttributesList.size() > 0) {
            commodityAttributesService.updateBatchById(updateCommodityAttributesList);
        }
    }

    /**
     * 修改商品阶梯价
     *
     * @param commodityLadderArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void updateCommodityLadder(CommodityLadder[] commodityLadderArray, Long commodityId, Long sysUserId, Date now) {
        List<CommodityLadder> updateLadders = new ArrayList<>();
        List<CommodityLadder> insertLadders = new ArrayList<>();
        List<Long> updateLaddersIds = new ArrayList<>();
        for (CommodityLadder commodityLadder : commodityLadderArray) {
            commodityLadder.setCommodityId(commodityId);
            commodityLadder.setStatus(0);
            if (commodityLadder.getId() != null) {
                commodityLadder.setUpdateTime(now);
                commodityLadder.setUpdateBy(sysUserId);
                updateLaddersIds.add(commodityLadder.getId());
                updateLadders.add(commodityLadder);
            } else {
                commodityLadder.setCreateTime(now);
                commodityLadder.setCreateBy(sysUserId);
                insertLadders.add(commodityLadder);
            }
        }
        //删除阶梯价
        if (updateLaddersIds.size() > 0) {
            commodityLadderService.deleteNoId(commodityId, updateLaddersIds);
        }
        if (updateLadders.size() > 0) {
            commodityLadderService.updateBatchById(updateLadders);
        }
        if (insertLadders.size() > 0) {
            commodityLadderService.saveBatch(insertLadders);
        }
    }

    /**
     * 修改商品预计到货时间
     *
     * @param commodityExpectArray
     * @param commodityId
     * @param sysUserId
     * @param now
     */
    private void updateCommodityExpect(CommodityExpect[] commodityExpectArray, Long commodityId, Long sysUserId, Date now) {
        List<CommodityExpect> updateExpects = new ArrayList<>();
        List<CommodityExpect> insertExpects = new ArrayList<>();
        List<Long> updateExpectIds = new ArrayList<>();
        for (CommodityExpect commodityExpect : commodityExpectArray) {
            commodityExpect.setCommodityId(commodityId);
            commodityExpect.setStatus(0);
            if (commodityExpect.getId() != null) {
                commodityExpect.setUpdateTime(now);
                commodityExpect.setUpdateBy(sysUserId);
                updateExpectIds.add(commodityExpect.getId());
                updateExpects.add(commodityExpect);
            } else {
                commodityExpect.setUpdateTime(now);
                commodityExpect.setUpdateBy(sysUserId);
                insertExpects.add(commodityExpect);
            }
        }


        //删除 预计到货时间
        if (updateExpectIds.size() > 0) {
            commodityExpectedService.deleteNoId(commodityId, updateExpectIds);
        }
        if (updateExpects.size() > 0) {
            commodityExpectedService.updateBatchById(updateExpects);
        }
        if (insertExpects.size() > 0) {
            commodityExpectedService.saveBatch(insertExpects);
        }
    }

    @Override
    public void updateBatchStatus(Long[] ids) {
        List<Commodity> commodities = this.baseMapper.selectBatchIds(Arrays.asList(ids));
        for (Commodity commodity : commodities) {
            this.updateById(commodity);
        }

        if (ArrayUtils.isNotEmpty(ids)) {
            baseMapper.updateBatchStatus(ids);
        }
    }

    @Override
    public PageUtils queryPageApi(com.lingkj.common.bean.entity.Page page, Integer type, Long userId, Long typeId) {
        Integer totalCount = this.baseMapper.queryCommodityCount(page.getKey(), type, typeId);
        List<ApiCommodityListDto> list = this.baseMapper.queryCommodityList(page.getLimit(), page.getPageSize(), userId, page.getKey(), type, typeId);
        if(StringUtils.isNotBlank(page.getKey())){
            HotWords hotWords = hotWordsService.selectName(page.getKey());
            if(hotWords!=null){
                hotWords.setNum(hotWords.getNum()+1);
                hotWords.setModifyTime(new Date());
                hotWordsService.updateById(hotWords);
            }else {
                HotWords hotWords1=new HotWords();
                hotWords1.setName(page.getKey());
                hotWords1.setNum(1);
                hotWords1.setModifyTime(new Date());
                hotWordsService.save(hotWords1);
            }
        }

        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    public ApiCommodityDto info(Long id, Long userId, HttpServletRequest request) {
        ApiCommodityDto apiCommodityDto = this.baseMapper.info(id);
        if (apiCommodityDto == null) {
            throw new RRException(messageUtils.getMessage("api.operate.areas.isEmpty", request));
        }
        if (userId != null) {
            Integer integer = this.userCollectionCommodityService.selectRepeat(userId, apiCommodityDto.getId());
            apiCommodityDto.setCollectionStatus(integer);
        }
        List<ApiCommodityFileDto> fileDtos = this.commodityFileService.selectByCommodityId(id);
        apiCommodityDto.setList(fileDtos);
        ApiCommodityTypeDto apiCommodityTypeDto = commodityTypeService.selectTypeDtoById(apiCommodityDto.getCommodityTypeId());
        apiCommodityDto.setCommodityType(apiCommodityTypeDto);
        return apiCommodityDto;
    }

    @Override
    public List<ApiCommodityAttributeDto> queryAttributeApi(Long commodityId) {

        List<ApiCommodityAttributeDto> list = commodityAttributesService.queryAttributeApi(commodityId);
        for (ApiCommodityAttributeDto attributeDto : list) {
            List<ApiCommodityAttributeValueDto> valueDtos = commodityAttributesValueService.queryAttributeValueApi(attributeDto.getId());
            attributeDto.setList(valueDtos);
        }
        return list;
    }

    @Override
    public Map<String, Object> queryExpectedDeliveryApi(Long commodityId) {
        List<ApiCommodityExpectDto> expectDtos = commodityExpectedService.selectByCommodityIdApi(commodityId);
        List<ApiCommodityLadderDto> ladderDtos = commodityLadderService.selectByCommodityIdApi(commodityId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("expectList", expectDtos);
        returnMap.put("ladderList", ladderDtos);
        return returnMap;
    }

    @Override
    public List<ApiCommodityListDto> queryLastCommodityList(Long id, Long userId) {
        return baseMapper.queryLastCommodityList(id, userId);
    }

    @Override
    public Commodity getCommodity1(Long id) {
        return this.baseMapper.queryCommodity(id);
    }

    @Override
    public void updateCommodityNum(Integer num, Date current, Long id) {
        this.baseMapper.updateNum(num, current, id);
    }

    @Override
    public PageUtils queryIntegralPageApi(Page page) {
        Integer totalCount = this.baseMapper.queryAllIntegralCommodityCount();
        List<ApiIntegralCommodityDto> apiIntegralCommodityDtos = this.baseMapper.queryIntegralCommodityList(page.getLimit(), page.getPageSize());
        return new PageUtils(apiIntegralCommodityDtos, totalCount, page.getPageSize(), page.getPage());
    }

    @Override
    public ApiDetailIntegralCommodityDto queryIntegralDetailApi(Map<String, Object> params) {
        List<String> list = baseMapper.queryIntegralCommodityFileList(Integer.valueOf((String) params.get("id")));
        ApiDetailIntegralCommodityDto apiDetailIntegralCommodityDto = baseMapper.queryIntegralDetail(Integer.valueOf((String) params.get("id")));
        apiDetailIntegralCommodityDto.setImageList(list);
        return apiDetailIntegralCommodityDto;
    }


}
