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
@Table(name="t_item")
public class ProductInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2639948886793207193L;
	//id
	@Id	
	@Column(name="product_id")
    private String productId;
    //名字
	@Column(name="product_name")
    private String productName;
    //单价
	@Column(name="product_price")
    private BigDecimal productPrice;
    //库存
	@Column(name="product_stock")
    private int productStock;
    //商品描述
	@Column(name="product_description")
    private String productDescription;
    //商品图标，是一个链接地址
	@Column(name="product_icon")
    private String productIcon;
    //商品状态。0为正常，1为下架
	@Column(name="product_status")
    private int productStatus;
    //所属类目编号
	@Column(name="category_type")
    private int categoryType;

}
