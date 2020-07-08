package com.lmf.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_detail")
public class OrderDetail implements Serializable{
	@Id
	@Column(name="detail_id")
    private String detailId;

    //订单id
	@Column(name="order_id")
    private String orderId;

    //商品id
	@Column(name="product_id")
    private String productId;

    //商品名称
	@Column(name="product_name")
    private String productName;

    //单价
	@Column(name="product_price")
    private BigDecimal  productPrice;

    //商品数量
	@Column(name="product_quantity")
    private int productQuantity;

    //商品图标
	@Column(name="product_icon")
    private String productIcon;

	
    
}
