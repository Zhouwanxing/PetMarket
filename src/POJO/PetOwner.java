package POJO;

/**
 * 宠物用户实体类
 * 
 * @author Administrator
 * 
 */
public class PetOwner {
	private int u_id;// 用户id
	private String u_name;// 用户名
	private String u_password; // 用户密码
	private int money; // 用户元宝数

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "PetOwner [u_id=" + u_id + ", u_name=" + u_name
				+ ", u_password=" + u_password + ", money=" + money + "]";
	}
	
}
