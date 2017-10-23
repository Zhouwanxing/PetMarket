package POJO;

import java.util.Date;

/**
 * 宠物实体类
 * 
 * @author Administrator
 * 
 */
public class Pet {
	private int p_id; // 宠物id
	private String p_name;// 宠物名
	private String typename;// 宠物类型
	private int health;// 宠物健康值
	private int love;// 宠物爱心指数
	private Date birthday;// 宠物出生日期
	private int owner_id;// 宠物主人id
	private int store_id;// 宠物所属商店id
	private int price;// 宠物价格

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "宠物名:" + p_name + ", 宠物类型:" + typename + ", 健康值:" + health
				+ ", 亲密度:" + love + ", 出生日期:" + birthday + ", 原价:" + price;
	}

}
