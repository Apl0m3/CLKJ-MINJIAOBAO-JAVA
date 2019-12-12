package com.lingkj.project.api.transaction.dto;

import com.lingkj.project.transaction.entity.TransactionServiceFile;
import lombok.Data;

import java.util.List;

@Data
public class ApiTransactionServiceAndFileDto {

    private  Integer type;
    private  Long transactionCommodityId;
    private List<TransactionServiceFile> list;
    private  String returnReason;
    private  String returnExplain;
    private  Integer userType;
}
