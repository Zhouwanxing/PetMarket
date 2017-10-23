package POJO;

/**
 * 宠物商店实体类
 * 
 * @author Administrator
 * 
 */
public class PetStore {
	private int s_id; // 商店id
	private String s_name;// 商店名
	private String s_password;// 商店登录密码
	private int balance;// 商店结余

	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_password() {
		return s_password;
	}

	public void setS_password(String s_password) {
		this.s_password = s_password;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "PetStore [s_id=" + s_id + ", s_name=" + s_name
				+ ", s_password=" + s_password + ", balance=" + balance + "]";
	}
	
}
