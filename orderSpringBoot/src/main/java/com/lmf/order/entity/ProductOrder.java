package com.lmf.order.entity;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lmf.order.enums.OrderStatusEnum;
import com.lmf.order.enums.PayStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_order")
public class ProductOrder {

	 
		@Id
		@Column(name="order_id")
	    private String orderId;

	    //买家名字
		@Column(name="buyer_name")
	    private String buyerName;

	    //买家手机号
		@Column(name="buyer_phone")
	    private String buyerPhone;

	    //买家地址
		@Column(name="buyer_address")
	    private String buyerAddress;

	    //买家微信
		@Column(name="buyer_openid")
	    private String buyerOpenid;

	    //订单总金额
		@Column(name="order_amount")
	    private BigDecimal orderAmount;

	    //订单状态,默认为新下单
		@Column(name="order_status")
	    private int orderStatus= OrderStatusEnum.NEW.getCode();

	    //支付状态,默认为0，未支付
		@Column(name="pay_status")
	    private int payStatus= PayStatusEnum.WAIT.getCode();

	    //创建时间
		@Column(name="create_time")
	    private Date createTime;

	    //更新时间
		@Column(name="update_time")
	    private Date updateTime;

}
