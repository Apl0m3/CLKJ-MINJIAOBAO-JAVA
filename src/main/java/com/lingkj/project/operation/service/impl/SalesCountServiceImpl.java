package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionCommodityLadderDto;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.operation.entity.SalesAcount;
import com.lingkj.project.operation.mapper.SalesAcountMapper;
import com.lingkj.project.operation.service.SalesCountService;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.service.TransactionCommodityLadderService;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XXX <XXX@163.com>
 * @version V1.0.0
 * @description
 * @date 2019/11/2 15:06
 */
@Service
public class SalesCountServiceImpl extends ServiceImpl<SalesAcountMapper, SalesAcount> implements SalesCountService {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private TransactionCommodityLadderService transactionCommodityLadderService;

    @Override
    public Map<String,Object> queryPage(Map<String, Object> params) {
        //获取查询条件
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        String commodityNameId = (String)params.get("commodityNameId");

        Map<String, Object> map = new HashMap<>(3);
        PageUtils pageUtils = commodityService.queryPage(params);
        List<Commodity> commodityList;
        //查询所有普通商品

        if (!"".equals(commodityNameId) && commodityNameId != null) {
            commodityList = commodityService.list(new QueryWrapper<Commodity>().eq(StringUtils.isNotBlank(commodityNameId), ("id"), commodityNameId));
        } else {
            commodityList = (List<Commodity>) pageUtils.getList();
        }



        //遍历商品集合,通过id查询 该商品所有订单数
        for (Commodity commodity : commodityList){
            if(commodity != null){
                int totalNum=0;
                //得到商品id
                Long commodityId = commodity.getId();
                //通过商品id查询订单表中该商品所有订单
                List<TransactionCommodity> transactionCommodityList =
                        transactionCommodityService.list(new QueryWrapper<TransactionCommodity>().eq("commodity_id", commodityId)
                        .ge(StringUtils.isNotBlank(startDate),"create_time",startDate).le(StringUtils.isNotBlank(endDate),"create_time",endDate));
                if(transactionCommodityList.size()>0){
                    //遍历该商品的所有订单
                    for (TransactionCommodity transactionCommodity:transactionCommodityList){
                        //获得该商品的一条订单
                        if(transactionCommodity != null ){
                            Integer numberSelectType = transactionCommodity.getNumberSelectType();
                            if(numberSelectType != null){
                                //numberSelectType 数量填写方式 1：输入，2：选择，3：服装
                                if(numberSelectType == 1 || numberSelectType == 3){
                                    //输入,服装的方式 每个订单销售数量放在当前实体的commodity_num
                                    if (transactionCommodity.getCommodityNum() != null){
                                        Integer commodityNum = transactionCommodity.getCommodityNum();
                                        totalNum+=commodityNum;
                                    }
                                }else{
                                    //服装 每个订单销售数量放在表transaction_commodity_ladder的num字段 通过订单id查询
                                    //得到订单id
                                    Long transactionCommodityId = transactionCommodity.getId();
                                    //得到该订单销售量
                                    ApiTransactionCommodityLadderDto transactionCommodityLadderDto = transactionCommodityLadderService.selectByCommodityId(transactionCommodityId);
                                    if(transactionCommodityLadderDto != null){
                                        if (transactionCommodityLadderDto.getNum() != null){
                                            Integer num = transactionCommodityLadderDto.getNum();
                                            totalNum+=num;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                commodity.setSoldNum(totalNum);

            }
        }
        pageUtils.setList(commodityList);
        map.put("page",pageUtils);
        return map;
    }
}
