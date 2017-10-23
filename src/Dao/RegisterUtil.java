package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 注册
 * 
 * @author Administrator
 * 
 */
public class RegisterUtil {
	/**
	 * 注册
	 */
	public static void register() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("*************注册选择**************");
			System.out.print("     1.用户      2.商户      3.返回主界面");
			System.out.println("\n********************************");
			System.out.print("请选择你的操作:");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				userRegister();
				flag = false;
				break;
			case 2:
				storeRegister();
				flag = false;
				break;
			case 3:
				flag = false;
				break;
			default:
				System.out.println("输入有误!");
				break;
			}
		}
	}

	/**
	 * 用户注册
	 */
	public static void userRegister() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.print("请输入你要注册的用户名:");
			String name = sc.next();
			String sql = "select count(*) from petowner where u_name = '"
					+ name + "'";
			ResultSet rs = BaseDao.getResultSet(sql);
			try {
				rs.next();
				if (rs.getInt(1) == 0) {
					while (true) {
						System.out.println("请设置登录密码:");
						String pwd = sc.next();
						if (pwd.length() < 6) {
							System.out.println("密码太简单了");
							continue;
						} else {
							System.out.println("请再次输入密码:");
							String pwdAgain = sc.next();
							if (pwd.equals(pwdAgain)) {
								String sql1 = "insert into petowner(u_id,u_name,u_password,money) values(sq_petowner.nextval,'"
										+ name + "','" + pwd + "',1000)";
								int res = BaseDao.update(sql1);
								if (res == 1) {
									System.out.println("注册成功");
									BaseDao.commit();
									flag = false;
									break;
								} else {
									System.out.println("注册失败,请重新设置!");
								}
							} else {
								System.out.println("两次输入的密码不一致，请重新设置!");
							}
						}
					}
				} else {
					System.out.println("当前用户名存在,请重新设置其他用户名!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("系统异常，请重新设置");
			}
		}
		BaseDao.closed();
	}

	/**
	 * 商店注册
	 */
	public static void storeRegister() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.print("请输入你要注册的商店名:");
			String name = sc.next();
			String sql = "select count(*) from petstore where s_name = '"
					+ name + "'";
			ResultSet rs = BaseDao.getResultSet(sql);
			try {
				rs.next();
				if (rs.getInt(1) == 0) {
					while (true) {
						System.out.println("请设置登录密码:");
						String pwd = sc.next();
						if (pwd.length() < 6) {
							System.out.println("密码太简单了");
							continue;
						} else {
							System.out.println("请再次输入密码:");
							String pwdAgain = sc.next();
							if (pwd.equals(pwdAgain)) {
								String sql1 = "insert into petstore(s_id,s_name,s_password,balance) values(sq_petstore.nextval,'"
										+ name + "','" + pwd + "',0)";
								int res = BaseDao.update(sql1);
								if (res == 1) {
									System.out.println("注册成功");
									BaseDao.commit();
									flag = false;
									break;
								} else {
									System.out.println("注册失败,请重新设置!");
								}
							} else {
								System.out.println("两次输入的密码不一致，请重新设置!");
							}
						}
					}
				} else {
					System.out.println("当前商店存在,请重新设置其他用户名!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("系统异常，请重新设置");
			}
		}
		BaseDao.closed();
	}
}
