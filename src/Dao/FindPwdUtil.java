package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import POJO.PetOwner;
import POJO.PetStore;

public class FindPwdUtil {
	/**
	 * 找回密码
	 */
	public static void findPwd() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.println("*************找回密码选择***********");
			System.out.print("     1.用户      2.商户      3.返回主菜单");
			System.out.println("\n********************************");
			System.out.print("请选择你的操作:");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				findUserPwd();
				flag = false;
				break;
			case 2:
				findStorePwd();
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
	 * 用户修改密码
	 */
	public static void findUserPwd() {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			PetOwner po = null;
			System.out.print("请输入你的用户名:");
			String name = sc.next();
			String sql = "select * from petowner where u_name = '" + name + "'";
			ResultSet rs = BaseDao.getResultSet(sql);
			try {
				if (rs.next()) {
					// System.out.println(rs.getString(2));
					po = new PetOwner();
					po.setU_id(rs.getInt(1));
					po.setU_name(rs.getString(2));
					po.setMoney(rs.getInt(4));
					System.out.print("请输入你的元宝数:");
					int mon = sc.nextInt();
					if (mon == po.getMoney()) {
						System.out.println("验证成功");
						while (true) {
							System.out.print("请输入你的新密码:");
							String pwd = sc.next();
							if (pwd.length() >= 6) {
								System.out.print("请再次输入新密码:");
								String pwd1 = sc.next();
								if (pwd.equals(pwd1)) {
									String sql2 = "update petowner set u_password ='"
											+ pwd
											+ "' where u_name ='"
											+ name
											+ "'";
									int res = BaseDao.update(sql2);
									if (res == 1) {
										BaseDao.commit();
										System.out.println("修改成功!");
										flag = false;
										break;
									} else {
										System.out.println("系统异常,修改失败");
									}
								} else {
									System.out.println("两次输入的不一致!");
								}
							} else {
								System.out.println("密码太短了!");
							}
						}
					} else {
						System.out.println("验证失败,退出找回密码!");
						break;
					}
				} else {
					System.out.println("没有此用户名的用户");
					System.out.println("继续输入用户名请按1,否则退出找回密码!");
					int ch1 = sc.nextInt();
					if (ch1 == 1) {
						continue;
					} else {
						break;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("用户找回密码异常");
			}
		}
		BaseDao.closed();
	}

	/**
	 * 店铺修改密码
	 */
	public static void findStorePwd() {
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			PetStore ps = null;
			System.out.print("请输入你的店铺名:");
			String name = sc.next();
			String sql = "select * from petstore where s_name = '" + name + "'";
			ResultSet rs = BaseDao.getResultSet(sql);
			try {
				if (rs.next()) {
					// System.out.println(rs.getString(2));
					ps = new PetStore();
					ps.setS_id(rs.getInt(1));
					ps.setS_name(rs.getString(2));
					ps.setBalance(rs.getInt(4));
					System.out.print("请输入你的店铺元宝数:");
					int mon = sc.nextInt();
					if (mon == ps.getBalance()) {
						System.out.println("验证成功");
						while (true) {
							System.out.print("请输入你的新密码:");
							String pwd = sc.next();
							if (pwd.length() >= 6) {
								System.out.print("请再次输入新密码:");
								String pwd1 = sc.next();
								if (pwd.equals(pwd1)) {
									String sql2 = "update petstore set s_password ='"
											+ pwd
											+ "' where s_name ='"
											+ name
											+ "'";
									int res = BaseDao.update(sql2);
									if (res == 1) {
										BaseDao.commit();
										System.out.println("修改成功!");
										flag = false;
										break;
									} else {
										System.out.println("系统异常,修改失败");
									}
								} else {
									System.out.println("两次输入的不一致!");
								}
							} else {
								System.out.println("密码太短了!");
							}
						}
					} else {
						System.out.println("验证失败,退出找回密码!");
						break;
					}
				} else {
					System.out.println("没有此店铺名");
					System.out.println("继续输入店铺名请输入1,否则退出找回密码!");
					int ch1 = sc.nextInt();
					if (ch1 == 1) {
						continue;
					} else {
						break;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("商店找回密码异常");
			}
		}
		BaseDao.closed();
	}
}
