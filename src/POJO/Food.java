package POJO;

/**
 * 食物实体类
 * 
 * @author Administrator
 * 
 */
public class Food {
	private int f_id;// 食物编号
	private int f_type;// 食物类型
	private int user_id;// 拥有者编号
	private int f_num;// 拥有数量

	public int getF_id() {
		return f_id;
	}

	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

	public int getF_type() {
		return f_type;
	}

	public void setF_type(int f_type) {
		this.f_type = f_type;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getF_num() {
		return f_num;
	}

	public void setF_num(int f_num) {
		this.f_num = f_num;
	}

}
