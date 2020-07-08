package com.lmf.order.entity;
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
@Table(name="t_category")
public class ProductCategory {
	@Id
	@Column(name="category_id")
    private int categoryId;

    //类目名字
	@Column(name="category_name")
    private String categoryName;

    //类目编号
	@Column(name="category_type")
    private int categoryType;


}

