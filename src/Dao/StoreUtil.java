package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import POJO.Account;
import POJO.Pet;
import POJO.PetOwner;
import POJO.PetStore;

/**
 * 商店方法
 * 
 * @author Administrator
 * 
 */
public class StoreUtil {
	/**
	 * 删除宠物
	 */
	public static void deletePet(int storeid) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while (true) {
			ArrayList<Pet> list = getOnsellMess(storeid);
			if (list.size() > 0) {
				showOnsellMess(list);
				System.out.print("请选择要删除的宠物编号(无效输入退出删除宠物):");
				int num_p = sc.nextInt();
				if (num_p > 0 && num_p <= list.size()) {
					Pet pet = list.get(num_p - 1);
					String sql = "delete from pet where p_id=" + pet.getP_id()
							+ "";
					int res = BaseDao.update(sql); // 错误

					if (res == 1) {
						System.out.println("删除宠物[" + pet.getP_name() + "]成功");
						BaseDao.commit();
						showOnsellMess(getOnsellMess(storeid));
						break;
					} else {
						System.out.println("删除宠物[" + pet.getP_name() + "]失败");
						BaseDao.rollback();
						break;
					}
				} else {
					System.out.println("输入无效");
					break;
				}
			} else {
				System.out.println("没有宠物可以删除");
				break;
			}
		}
		BaseDao.closed();
	}

	/**
	 * 更新宠物信息
	 */
	public static void updatePetMess(int storeid) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while (true) {
			ArrayList<Pet> list = getOnsellMess(storeid);
			if (list.size() > 0) {
				showOnsellMess(list);
				System.out.print("请选择要修改的宠物编号(无效输入退出修改信息):");
				int num_p = sc.nextInt();
				if (num_p > 0 && num_p <= list.size()) {
					Pet pet = list.get(num_p - 1);
					System.out.println(pet.toString());
					System.out.print("更改价格为:");
					int price = sc.nextInt();
					String sql = "update pet set price = " + price
							+ " where p_name= '" + pet.getP_name() + "'";
					int res = BaseDao.update(sql);
					if (res == 1) {
						BaseDao.commit();
						System.out.println("修改成功");
						System.out.println("---------修改前:");
						System.out.println("宠物姓名:" + pet.getP_name());
						System.out.println("宠物价格:" + pet.getPrice());
						System.out.println("---------修改后:");
						System.out.println("宠物姓名:" + pet.getP_name());
						System.out.println("宠物价格:" + price);
						showOnsellMess(getOnsellMess(storeid));
						break;
					} else {
						System.out.println("修改失败");
						BaseDao.rollback();
					}
					break;
				} else {
					System.out.println("输入有误!");
					break;
				}
			} else {
				System.out.println("暂无宠物");
				break;
			}
		}
		BaseDao.closed();
	}

	/**
	 * 培育新宠物
	 */
	public static void raiseNewPet(int storeid) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入新宠物的姓名:");
		String name = scanner.next();
		System.out.print("请输入新宠物的类型:");
		String type = scanner.next();
		System.out.print("请输入新宠物的价格:");
		int pric = scanner.nextInt();
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String birth = sdf.format(dt);
		String sql = "insert into pet(p_id,p_name,typename,health,birthday,price,store_id,love) values(sq_pet.nextval,'"
				+ name
				+ "','"
				+ type
				+ "',100,to_date ( '"
				+ birth
				+ "' , 'YYYY-MM-DD' )," + pric + "," + storeid + ",0)";
		int res = BaseDao.update(sql);
		if (res == 1) {
			System.out.println("培育新宠物[" + name + "]成功");
			BaseDao.commit();
			showOnsellMess(getOnsellMess(storeid));
		} else {
			System.out.println("培育新宠物失败");
			BaseDao.rollback();
		}
		BaseDao.closed();
	}

	/**
	 * 获取在售宠物列表
	 */
	public static ArrayList<Pet> getOnsellMess(int storeId) {
		// TODO Auto-generated method stub
		ArrayList<Pet> list = new ArrayList<Pet>();
		Pet pt = null;
		String sql = "select * from pet where store_id = '" + storeId + "'";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			while (rs.next()) {
				pt = new Pet();
				pt.setP_id(rs.getInt(1));
				pt.setP_name(rs.getString(2));
				pt.setTypename(rs.getString(3));
				pt.setHealth(rs.getInt(4));
				pt.setLove(rs.getInt(5));
				Date date = rs.getDate(6);
				pt.setBirthday(date);
				pt.setOwner_id(rs.getInt(7));
				pt.setPrice(rs.getInt(8));
				pt.setStore_id(rs.getInt(9));
				list.add(pt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取在售宠物列表异常");
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 显示在售宠物列表
	 * 
	 * @param list
	 */
	public static void showOnsellMess(ArrayList<Pet> list) {
		Pet pt = null;
		int i = 1;
		if (list.size() > 0) {
			System.out.println("----------在售宠物列表----------");
			System.out.println("编号\t名字\t类型\t元宝");
			for (Pet pet : list) {
				System.out.println(i + "\t" + pet.getP_name() + "\t"
						+ pet.getTypename() + "\t" + pet.getPrice());
				i++;
			}
		} else {
			System.out.println("暂无宠物在售");
		}
	}

	/**
	 * 重新获取一遍商店信息
	 * 
	 * @param po
	 * @return
	 */
	public static PetStore myMess(PetStore ps) {
		String sql = "select * from petstore where s_id = " + ps.getS_id();
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				ps.setS_id(rs.getInt(1));
				ps.setS_name(rs.getString(2));
				ps.setS_password(rs.getString(3));
				ps.setBalance(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("重新获取商店信息异常");
		}
		BaseDao.closed();
		return ps;
	}

	/**
	 * 显示我的账单
	 * 
	 * @param ps
	 */
	public static void myBill(PetStore ps) {
		// System.out.println("今日账单");
		// String sql = "select * from account where seller_id = " +
		// ps.getS_id()
		// + " or buyer_id = " + ps.getS_id();
		// 卖家为商家,即收入
		// String sql1 = "select * from account where sell_id = " +
		// ps.getS_id();
		// 买家为商家,即支出
		// String sql2 = "select * from account where buyer_id = " +
		// ps.getS_id();
		Account at = null;
		int shou = 0;
		int chu = 0;
		ArrayList<Account> list1 = getInMoneyList(ps);
		ArrayList<Account> list2 = getOutMoneyList(ps);
		if (list1 != null && list1.size() > 0) {
			System.out.println("-------------收入账单--------------");
			for (int i = 0; i < list1.size(); i++) {
				at = list1.get(i);
				Date deal_time = at.getDeal_time();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy年MM月dd日  HH:mm");
				String format = simpleDateFormat.format(deal_time);
				System.out.println((i + 1) + "," + format + ",用户["
						+ userName(at.getBuyer_id()) + "]购买了本商店的宠物["
						+ petName(at.getPet_id()) + "],收入:" + at.getPrice()
						+ "个元宝");
				shou += at.getPrice();
			}
			System.out.println("总收入:" + shou);
		} else {
			System.out.println("无收入账单!");
		}
		if (list2 != null && list2.size() > 0) {
			System.out.println("-------------支出账单--------------");
			for (int i = 0; i < list2.size(); i++) {
				at = list2.get(i);
				Date deal_time = at.getDeal_time();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy年MM月dd日  HH:mm");
				String format = simpleDateFormat.format(deal_time);
				System.out.println((i + 1) + "," + format + ",用户["
						+ userName(at.getSeller_id()) + "]卖出了宠物["
						+ petName(at.getPet_id()) + "]到本商店,支出:" + at.getPrice()
						+ "个元宝");
				chu += at.getPrice();
			}
			System.out.println("总支出:" + chu);
		} else {
			System.out.println("无支出账单!");
		}
	}

	/**
	 * 返回收入的账单信息
	 * 
	 * @param ps
	 * @return
	 */
	public static ArrayList<Account> getInMoneyList(PetStore ps) {
		ArrayList<Account> list = new ArrayList<Account>();
		Account at = null;
		// 卖家为商家,即收入
		String sql1 = "select * from account where seller_id = " + ps.getS_id()
				+ " order by deal_time";
		ResultSet rs = BaseDao.getResultSet(sql1);
		try {
			while (rs.next()) {
				at = new Account();
				at.setA_id(rs.getInt(1));
				at.setDeal_type(rs.getInt(2));
				at.setPet_id(rs.getInt(3));
				at.setSeller_id(rs.getInt(4));
				at.setBuyer_id(rs.getInt(5));
				at.setPrice(rs.getInt(6));
				// Time time = rs.getTime(7);
				// long time2 = time.getTime();
				// Date dt1 = rs.getDate(7);
				// long time3 = dt1.getTime();
				// long time4 = time2 + time3 + 8 * 3600 * 1000;
				// Date date = new Date(time4);
				// // at.setDeal_time(rs.getDate(7));
				// at.setDeal_time(date);
				// rs.getDate() 返回的是日期
				// rs.getTime() 返回的是时间
				// rs.getTimestamp() 返回的是日期加时间
				Timestamp timestamp = rs.getTimestamp(7);
				at.setDeal_time(timestamp);
				list.add(at);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("收入账单信息异常");
			BaseDao.closed();
			return null;
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 返回支出的账单信息
	 * 
	 * @param ps
	 * @return
	 */
	public static ArrayList<Account> getOutMoneyList(PetStore ps) {
		ArrayList<Account> list = new ArrayList<Account>();
		Account at = null;
		// 卖家为商家,即收入
		String sql1 = "select * from account where buyer_id = " + ps.getS_id();
		ResultSet rs = BaseDao.getResultSet(sql1);
		try {
			while (rs.next()) {
				at = new Account();
				at.setA_id(rs.getInt(1));
				at.setDeal_type(rs.getInt(2));
				at.setPet_id(rs.getInt(3));
				at.setSeller_id(rs.getInt(4));
				at.setBuyer_id(rs.getInt(5));
				at.setPrice(rs.getInt(6));
				// Time time = rs.getTime(7);
				// long time2 = time.getTime();
				// Date dt1 = rs.getDate(7);
				// long time3 = dt1.getTime();
				// long time4 = time2 + time3 + 8 * 3600 * 1000;
				// Date date = new Date(time4);
				// at.setDeal_time(date);
				// // at.setDeal_time(rs.getDate(7));
				Timestamp timestamp = rs.getTimestamp(7);
				at.setDeal_time(timestamp);
				list.add(at);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("支出账单信息异常");
			BaseDao.closed();
			return null;
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 通过宠物id返回宠物名
	 * 
	 * @param id
	 * @return
	 */
	public static String petName(int id) {
		String name = null;
		String sql = "select p_name from pet where p_id = " + id;
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BaseDao.closed();
		return name;
	}

	/**
	 * 通过用户id返回用户名
	 * 
	 * @param id
	 * @return
	 */
	public static String userName(int id) {
		String name = null;
		String sql = "select u_name from petowner where u_id = " + id;
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BaseDao.closed();
		return name;
	}
}
