package com.lmf.order;


/**
 * Redis 连接实体类
 */

public class Redis {
    /**
     * 地址
     */
    private String address;

    /**
     * 密码
     */
    private String password;

    public Redis(String address) {
        this.address = address;
    }

    public Redis(String address, String password) {
        //连接地址，是由host和port组成的
        this.address = address;
        this.password = password;
    }

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Redis [address=" + address + ", password=" + password + "]";
	}
    
}
