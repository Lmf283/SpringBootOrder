package com.lmf.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lmf.order.entity.OrderDetail;
import com.lmf.order.enums.OrderStatusEnum;
import com.lmf.order.enums.PayStatusEnum;

import lombok.Data;
@Data
public class OrderDTO {
	
    private String orderId;

    
    private String buyerName;

   
    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private int orderStatus= OrderStatusEnum.NEW.getCode();

    private int payStatus= PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
    
    private List<OrderDetail> detailList;
}
