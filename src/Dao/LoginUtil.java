package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import POJO.PetOwner;
import POJO.PetStore;

public class LoginUtil {
	/**
	 * 登录
	 */
	public static void login() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		PetOwner po = null;
		PetStore ps = null;
		boolean flag = true;
		int num_i = 0;
		while (true) {
			System.out.println("*************登录选择**************");
			System.out.print("     1.用户      2.商户      3.返回主菜单");
			System.out.println("\n********************************");
			System.out.print("请选择你的操作:");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				num_i = 0;
				flag = true;
				// 用户登录
				while (num_i < 3) {
					if ((po = userLogin()) != null) {
						System.out.println("登录成功,你的基本信息如下:");
						System.out.println("\t姓名:" + po.getU_name());
						System.out.println("\t元宝:" + po.getMoney());
						flag = false;

						// 您可以购买和卖出宠物，请选择：1.购买2.卖出3.查看我的宠物信息4.宠物娱乐5.喂食6.洗澡7.购买食物8.退出登录
						while (true) {
							System.out.println();
							// 每次进入用户操作界面之前都会执行，删除food表中的数量为0的口粮信息
							UserUtil.deleteFood();
							// 重新获取一遍用户信息,防止在收入和支出后po信息未及时更新
							po = UserUtil.getUserMess(po);
							System.out
									.println("*******************用户操作*******************");
							System.out
									.print("    1.购买宠物        2.卖出        3.我的宠物信息         4.宠物娱乐\n");
							System.out
									.print("    5.喂食                6.洗澡        7.购买食物                 8.退出登录");
							System.out
									.println("\n*******************************************");
							System.out.print("请选择你的操作:");
							// System.out
							// .print("请选择你要进行的操作(1.购买         2.卖出         3.显示我的宠物信息         4.宠物娱乐        5.喂食        6.退出登录):");
							int cho = sc.nextInt();
							switch (cho) {
							case 1:
								UserUtil.buyPet(po);
								break;
							case 2:
								UserUtil.sellPet(po);
								break;
							case 3:
								UserUtil.showMyPetMess(UserUtil
										.getMyPetMess(po));
								break;
							case 4:
								// 宠物娱乐
								UserUtil.petPlay(po);
								break;
							case 5:
								// 喂食
								UserUtil.petEat(po, null);
								break;
							case 6:
								// 洗澡
								UserUtil.petBath(po, null);
								break;
							case 7:
								// 购买食物
								UserUtil.petBuyFood(po);
								break;
							case 8:
								System.out.println("欢迎下次使用");
								break;
							default:
								System.out.println("输入有误!");
								break;
							}
							if (cho == 8) {
								break;
							}
						}
						break; // 登录成功，跳转到选择用户还是商户界面
					} else {
						System.out.println("用户名与密码不匹配");
						num_i++;
					}
				}
				while (flag) {
					System.out.print("你已经三次输入错误,是否找回密码(1.找回密码	2.退出系统):");
					int c = sc.nextInt();
					switch (c) {
					case 1:
						// System.out.println("找回密码");
						FindPwdUtil.findPwd();
						flag = false;
						break;
					case 2:
						System.exit(0);
					default:
						System.out.println("输入有误!");
						break;
					}
					break;
				}
				break;
			case 2:
				// 商户登录
				num_i = 0;
				flag = true;
				while (num_i < 3) {
					if ((ps = storeLogin()) != null) {
						System.out.println("登录成功！");
						flag = false;
						System.out.println("商家信息如下:");
						System.out.println("\t店铺名称:" + ps.getS_name());
						System.out.println("\t店铺余额:" + ps.getBalance());
						// 显示在售宠物列表
						StoreUtil.showOnsellMess(StoreUtil.getOnsellMess(ps
								.getS_id()));
						// 商户菜单 请选择功能：1.培育新宠物2.修改宠物信息3.删除宠物
						while (true) {
							System.out.println();
							// 重新获取店家信息
							ps = StoreUtil.myMess(ps);
							System.out
									.println("*************商户操作**************");
							System.out
									.print("    1.培育新宠物                2.修改宠物信息\n");
							System.out
									.println("    3.删除宠物                    4.显示在售宠物");
							System.out.print("    5.显示我的账单            6.退出登录");
							System.out
									.println("\n********************************");
							System.out.print("请选择你的操作:");
							// System.out
							// .print("请选择功能：1.培育新宠物     2.修改宠物信息     3.删除宠物     4.显示在售宠物     5.退出登录:");
							choose = sc.nextInt();
							switch (choose) {
							case 1:
								StoreUtil.raiseNewPet(ps.getS_id());
								break;
							case 2:
								StoreUtil.updatePetMess(ps.getS_id());
								break;
							case 3:
								StoreUtil.deletePet(ps.getS_id());
								break;
							case 4:
								StoreUtil.showOnsellMess(StoreUtil
										.getOnsellMess(ps.getS_id()));
								break;
							case 5:
								// 我的账单
								StoreUtil.myBill(ps);
								break;
							case 6:
								break;
							default:
								System.out.println("输入有误!");
								break;
							}
							if (choose == 6) {
								System.out.println("欢迎下次使用!");
								break;
							}
						}
						break;// 登录成功，跳转到选择用户还是商户界面
					} else {
						System.out.println("商店名与密码不匹配");
						num_i++;
					}
				}
				while (flag) {
					System.out.print("你已经三次输入错误,是否找回密码(1.找回密码	2.退出系统):");
					int c = sc.nextInt();
					switch (c) {
					case 1:
						// System.out.println("找回密码");
						FindPwdUtil.findPwd();
						flag = false;
						break;
					case 2:
						System.exit(0);
					default:
						System.out.println("输入有误!");
						break;
					}
					break;
				}
				break;
			case 3:

				break;
			default:
				System.out.println("输入有误!");
				break;
			}
			if (choose == 3) {
				break;// 如果输入的是3，返回上级菜单即选择登录还是注册界面
			}
		}
	}

	/**
	 * 用户登录
	 */
	public static PetOwner userLogin() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		PetOwner po = null;
		System.out.print("请输入用户名：");
		String name = sc.next();
		System.out.print("请输入密码:");
		String pwd = sc.next();
		String sql = "select * from petowner where u_name = '" + name
				+ "' and u_password ='" + pwd + "'";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				po = new PetOwner();
				po.setU_id(rs.getInt(1));
				po.setU_name(rs.getString(2));
				po.setU_password(rs.getString(3));
				po.setMoney(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("用户登录异常");
		}
		BaseDao.closed();
		return po;
	}

	/**
	 * 商户登录
	 */
	public static PetStore storeLogin() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		PetStore ps = null;
		System.out.print("请输入商店名:");
		String name = sc.next();
		System.out.print("请输入密码:");
		String pwd = sc.next();
		String sql = "select * from petstore where s_name = '" + name
				+ "' and s_password ='" + pwd + "'";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				ps = new PetStore();
				ps.setS_id(rs.getInt(1));
				ps.setS_name(rs.getString(2));
				ps.setS_password(rs.getString(3));
				ps.setBalance(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("商户登录异常");
		}
		BaseDao.closed();
		return ps;
	}
}
