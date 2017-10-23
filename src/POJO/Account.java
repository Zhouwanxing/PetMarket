package POJO;

import java.util.Date;

/**
 * 账单实体类
 * 
 * @author Administrator
 * 
 */
public class Account {
	private int a_id; // 账单id
	private int deal_type;// 交易类型
	private int pet_id;// 宠物id
	private int seller_id;// 卖家id
	private int buyer_id;// 买家id
	private int price;// 交易价格
	private Date deal_time;// 交易时间

	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}

	public int getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(int deal_type) {
		this.deal_type = deal_type;
	}

	public int getPet_id() {
		return pet_id;
	}

	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}

	public int getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}

	public int getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(Date deal_time) {
		this.deal_time = deal_time;
	}

}
