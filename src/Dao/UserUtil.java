package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import POJO.Food;
import POJO.Pet;
import POJO.PetOwner;
import POJO.PetStore;

/**
 * 用户方法
 * 
 * @author Administrator
 * 
 */
public class UserUtil {
	private final static String[] FOOD_TYPE = { "小袋口粮", "中袋口粮", "大袋口粮" };
	private final static String[] PLAY_TYPE = { "玩泥巴", "玩球", "河边钓鱼" };
	private final static int[] LESS_HEAL = { 10, 30, 50 };
	private final static int[] ADD_LOVE = { 5, 10, 20 };
	private final static int[] ADD_HEALTH = { 10, 30, 60 };
	private final static int[] BUY_PRICE = { 10, 30, 60 };

	/**
	 * 购买宠物
	 * 
	 * @param user_id
	 */
	public static void buyPet(PetOwner po) {
		Scanner sc = new Scanner(System.in);
		int need = 0;
		// 获取可以购买的宠物信息
		ArrayList<Pet> list = getOnsellPetMess();
		if (list.size() > 0) {
			showOnsellPetMess(list);
			System.out.print("请输入你想购买的宠物编号(无效输入退出购买):");
			int choose = sc.nextInt();
			if (choose > 0 && choose <= list.size()) {
				Pet pet = list.get(choose - 1);
				// 在此之后可在数据库中查询此类宠物，该用户是否存在亲密度大于200的，有则半价可购买选择的宠物
				if (queryIsLove(po, pet)) {
					System.out.println("你要购买：" + pet.toString());
					System.out.println("由于你有宠物的类型为[" + pet.getTypename()
							+ "],并且亲密度大于200,购买此宠物可以打5折");
					need = pet.getPrice() / 2;
					System.out.println("购买需要:" + need);
					System.out.println("你有:" + po.getMoney());
				} else {
					need = pet.getPrice();
					System.out.println("你要购买：" + pet.toString());
					System.out.println("购买此宠物需要:" + need);
					System.out.println("你有:" + po.getMoney());
				}
				if (po.getMoney() >= need) {
					System.out.println("你可以购买,是否确定购买？(1.确定):");
					int ch = sc.nextInt();
					if (ch == 1) {
						// 三个参数,第三个参数为购买此宠物需要的钱数
						buyUpdateMess(pet, po, need);
					} else {
						System.out.println("你选择了取消购买");
					}
				} else {
					System.out.println("你的钱数不够,不能购买");
				}
			} else {
				System.out.println("输入有误");
			}
		} else {
			System.out.println("没有宠物可以购买");
		}
	}

	/**
	 * 查询该po用户是否有pet类型的宠物的爱心值超过200,有就返回true, 没有返回false
	 * 
	 * @param po
	 * @param pet
	 * @return
	 */
	public static boolean queryIsLove(PetOwner po, Pet pet) {
		String sql = "select * from pet where owner_id = " + po.getU_id()
				+ " and typename = '" + pet.getTypename() + "' and love >=200";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				BaseDao.closed();
				return true;
			} else {
				BaseDao.closed();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BaseDao.closed();
			return false;
		}
	}

	/**
	 * 购买宠物修改数据库中的数据
	 * 
	 * @param pet
	 * @param po
	 * @param pay
	 */
	public static void buyUpdateMess(Pet pet, PetOwner po, int pay) {
		try {
			Statement sm = BaseDao.getConn().createStatement();

			// 修改pet表中的store_id为null , owner_id改为购买者的id
			String sql = "update pet set store_id = null , owner_id = "
					+ po.getU_id() + " where p_id = " + pet.getP_id() + "";
			// 记录此账单的信息
			String sql2 = "insert into account values(sq_account.nextval,2,"
					+ pet.getP_id()
					+ ","
					+ pet.getStore_id()
					+ ","
					+ po.getU_id()
					+ ","
					+ pay
					+ ",to_date(to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'yyyy-MM-dd HH24:mi:ss'))";
			// 给商户添加收入
			String sql3 = "update petstore set balance = nvl(balance,0)+" + pay
					+ " where s_id = " + pet.getStore_id() + "";
			// 给用户减少元宝数
			String sql4 = "update petowner set money = money-" + pay
					+ " where u_id = " + po.getU_id() + "";
			sm.addBatch(sql);
			sm.addBatch(sql2);
			sm.addBatch(sql3);
			sm.addBatch(sql4);

			int num[] = sm.executeBatch();
			if (num.length == 4) {
				System.out.println("购买宠物[" + pet.getP_name() + "]成功!");
				BaseDao.commit();
			} else {
				System.out.println("系统异常,购买失败!");
				BaseDao.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("系统异常!");
			BaseDao.rollback();
		}
		BaseDao.closed();
	}

	/**
	 * 卖出宠物
	 * 
	 * @param user_id
	 */
	public static void sellPet(PetOwner po) {
		int spend = 0;
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		// 获取我的宠物信息
		ArrayList<Pet> list = getMyPetMess(po);
		if (list.size() > 0) {
			showMyPetMess(list);
			System.out.print("请输入你想卖出的宠物编号(无效输入退出卖宠物):");
			int choose = sc.nextInt();
			if (choose > 0 && choose <= list.size()) {
				Pet pet = list.get(choose - 1);
				// 判断要售出的宠物健康值是否为0,为0则不能进行出售
				if (pet.getHealth() > 0) {
					// System.out.println("----------宠物商店----------");
					ArrayList<PetStore> list2 = getStoreMess();
					if (list2.size() > 0) {
						while (flag) {
							showStoreMess(list2);
							System.out.println("请选择商店售出:");
							int ch = sc.nextInt();
							if (ch > 0 && ch <= list2.size()) {
								PetStore petStore = list2.get(ch - 1);
								System.out.println("商店有:"
										+ petStore.getBalance());
								System.out.println("宠物健康值为:" + pet.getHealth());
								// 卖宠物,价格为宠物原先的价格*健康的百分比,进行四舍五入
								spend = (int) (pet.getPrice() * pet.getHealth()
										/ 100.0 + 0.5);
								System.out.println("宠物原价:" + pet.getPrice());
								System.out.println("宠物售价:" + spend);
								if (petStore.getBalance() >= spend) {
									System.out
											.println("你可以卖出宠物,是否确定卖出？(1.确定):");
									int ch1 = sc.nextInt();
									if (ch1 == 1) {
										// 卖出宠物，修改数据库中的数据
										sellPetSucc(pet, petStore, po, spend);
										// 已卖出“旺财”，元宝增加100，余额200
										// 重新获取用户信息
										po = getUserMess(po);
										System.out.println("已卖出["
												+ pet.getP_name() + "],元宝增加:"
												+ spend + ",余额:"
												+ po.getMoney() + "");
										flag = false;
									} else {
										System.out.println("你选择了取消卖出");
										flag = false;
									}
								} else {
									System.out.println("你选择的商店不能承受你的宠物的价格!");
									flag = false;
								}
							} else {
								System.out.println("输入有误!");
							}
						}
					} else {
						System.out.println("暂无宠物商店!");
					}
				} else {
					System.out.println("此宠物健康值为0,不能进行售卖");
				}
			} else {
				System.out.println("输入有误!");
			}
		} else {
			System.out.println("没有宠物可以卖出");
		}
		BaseDao.closed();
	}

	/**
	 * 重新获取用户信息
	 * 
	 * @param po
	 * @return
	 */
	public static PetOwner getUserMess(PetOwner po) {
		PetOwner poNew = null;
		String sql = "select * from petowner where u_id = " + po.getU_id() + "";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			if (rs.next()) {
				poNew = new PetOwner();
				poNew.setU_id(rs.getInt(1));
				poNew.setU_name(rs.getString(2));
				poNew.setU_password(rs.getString(3));
				poNew.setMoney(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("重新获取用户信息异常");
		}
		BaseDao.closed();
		return poNew;
	}

	/**
	 * 卖出宠物，修改数据库中的数据
	 * 
	 * @param pet
	 * @param petStore
	 * @param po
	 */
	private static void sellPetSucc(Pet pet, PetStore petStore, PetOwner po,
			int spend) {
		// TODO Auto-generated method stub
		try {
			Statement sm = BaseDao.getConn().createStatement();

			// 修改pet表中的store_id为商店id , owner_id改为null,健康值为100,亲密度为0
			String sql = "update pet set owner_id = null , store_id = "
					+ petStore.getS_id()
					+ " , health = 100 , love = 0 where p_id = "
					+ pet.getP_id() + "";
			// 记录此账单的信息
			String sql2 = "insert into account values(sq_account.nextval,1,"
					+ pet.getP_id()
					+ ","
					+ po.getU_id()
					+ ","
					+ petStore.getS_id()
					+ ","
					+ spend
					+ ",to_date(to_char(sysdate,'yyyy-MM-dd HH24:mi:ss'),'yyyy-MM-dd HH24:mi:ss'))";
			// 给商户减少钱
			String sql3 = "update petstore set balance = nvl(balance,0)-"
					+ spend + " where s_id = " + petStore.getS_id() + "";
			// 给用户增加元宝数
			String sql4 = "update petowner set money = money+" + spend
					+ " where u_id = " + po.getU_id() + "";
			sm.addBatch(sql);
			sm.addBatch(sql2);
			sm.addBatch(sql3);
			sm.addBatch(sql4);

			int num[] = sm.executeBatch();
			if (num.length == 4) {
				System.out.println("卖出成功!");
				BaseDao.commit();
			} else {
				System.out.println("系统异常,卖出失败!");
				BaseDao.rollback();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("卖出宠物异常!");
			BaseDao.rollback();
		}
		BaseDao.closed();
	}

	/**
	 * 获取我的宠物列表
	 */
	public static ArrayList<Pet> getMyPetMess(PetOwner po) {
		// TODO Auto-generated method stub
		ArrayList<Pet> list = new ArrayList<Pet>();
		Pet pt = null;
		String sql = "select * from pet where owner_id = " + po.getU_id() + "";
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
			System.out.println("获取我的宠物列表异常");
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 显示我的宠物列表
	 * 
	 * @param list
	 */
	public static void showMyPetMess(ArrayList<Pet> list) {
		Pet pt = null;
		int i = 1;
		if (list.size() > 0) {
			System.out.println("-------------------我的宠物列表------------------");
			System.out.println("编号\t名字\t类型\t价值\t健康值\t亲密度");
			for (Pet pet : list) {
				System.out.println(i + "\t" + pet.getP_name() + "\t"
						+ pet.getTypename() + "\t" + pet.getPrice() + "\t"
						+ pet.getHealth() + "\t" + pet.getLove());
				i++;
			}
		} else {
			System.out.println("暂无宠物!");
		}
	}

	/**
	 * 获取在售宠物列表
	 */
	public static ArrayList<Pet> getOnsellPetMess() {
		// TODO Auto-generated method stub
		ArrayList<Pet> list = new ArrayList<Pet>();
		Pet pt = null;
		String sql = "select * from pet where store_id > 0";
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
			System.out.println("显示在售宠物列表");
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 显示在售宠物列表
	 * 
	 * @param list
	 */
	public static void showOnsellPetMess(ArrayList<Pet> list) {
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
	 * 获取商店列表
	 */
	public static ArrayList<PetStore> getStoreMess() {
		// TODO Auto-generated method stub
		ArrayList<PetStore> list = new ArrayList<PetStore>();
		PetStore ps = null;
		String sql = "select * from petstore ";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			while (rs.next()) {
				ps = new PetStore();
				ps.setS_id(rs.getInt(1));
				ps.setS_name(rs.getString(2));
				ps.setS_password(rs.getString(3));
				ps.setBalance(rs.getInt(4));
				list.add(ps);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取商店列表异常");
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 显示商店列表
	 * 
	 * @param list
	 */
	public static void showStoreMess(ArrayList<PetStore> list) {
		PetStore pt = null;
		int i = 1;
		if (list.size() > 0) {

			System.out.println("----------宠物商店列表----------");
			System.out.println("编号\t宠物商店名\t\t资金数");
			for (PetStore ps : list) {
				System.out.println(i + "\t" + ps.getS_name() + "\t\t"
						+ ps.getBalance());
				i++;
			}
		} else {
			System.out.println("暂无商店");
		}
	}

	/**
	 * 宠物玩耍
	 * 
	 * @param po
	 */
	public static void petPlay(PetOwner po) {
		// System.out.println("玩耍");
		Scanner sc = new Scanner(System.in);
		String sql = null;
		String sql2 = null;
		String sql3 = null;
		int i = 0;
		ArrayList<Pet> list = getMyPetMess(po);
		if (list.size() > 0) {
			showMyPetMess(list);
			System.out.print("请选择要玩耍的宠物(无效输入退出宠物玩耍):");
			int cid = sc.nextInt();
			if (cid > 0 && cid <= list.size()) {
				Pet pet = list.get(cid - 1);
				if (pet.getHealth() > 0) {
					Random rd = new Random();
					int num = rd.nextInt(100);
					if (num >= 0 && num <= 10) {
						i = 2;
						playPet(i, num % 10 + 1, pet, po);
					} else if (num > 10 && num <= 40) {
						i = 1;
						playPet(i, num % 10 + 1, pet, po);
					} else {
						i = 0;
						playPet(i, num % 10 + 1, pet, po);
					}
				} else {
					System.out.println("宠物健康值为0,不能进行玩耍了!");
					System.out.println("是否进行1.喂食      2.洗澡");
					int ch1 = sc.nextInt();
					switch (ch1) {
					case 1:
						petEat(po, pet);
						break;
					case 2:
						petBath(po, pet);
						break;
					default:
						System.out.println("输入非1/2,回到用户操作界面");
						break;
					}
				}
			} else {
				System.out.println("输入无效!");
			}
		} else {
			System.out.println("暂无宠物可以玩耍");
		}
	}

	/**
	 * 宠物玩耍，随机掉口粮
	 * 
	 * @param i
	 * @param num
	 * @param pet
	 * @param po
	 */
	public static void playPet(int i, int num, Pet pet, PetOwner po) {
		String sql = null;
		String sql2 = null;
		String sql3 = null;
		if (pet.getHealth() >= LESS_HEAL[i]) {
			sql = "select * from food where f_type = " + i + " and user_id = "
					+ po.getU_id();
			ResultSet rs = BaseDao.getResultSet(sql);
			try {
				if (rs.next()) {

					sql2 = "update food set f_num = f_num+" + num
							+ " where f_type = " + i + " and user_id = "
							+ po.getU_id();
				} else {
					sql2 = "insert into food values(sq_food.nextval," + i + ","
							+ po.getU_id() + "," + num + ")";
				}
				sql3 = "update pet set health = health - " + LESS_HEAL[i]
						+ ",love = love +" + ADD_LOVE[i] + " where p_id = "
						+ pet.getP_id();
				Statement createStatement = BaseDao.getConn().createStatement();
				createStatement.addBatch(sql2);
				createStatement.addBatch(sql3);
				int[] executeBatch = createStatement.executeBatch();
				if (executeBatch.length == 2) {
					BaseDao.commit();
					System.out.println("你带着宠物[" + pet.getP_name() + "]"
							+ PLAY_TYPE[i] + ",获得" + FOOD_TYPE[i] + " " + num
							+ "袋,宠物健康值减少" + LESS_HEAL[i] + "点,亲密度增加"
							+ ADD_LOVE[i] + "点");
				} else {
					BaseDao.rollback();
					System.out.println("系统异常,玩耍失败");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("系统异常,玩耍失败");
			}
		} else {
			System.out.println("健康值过低,玩耍失败");
		}
		BaseDao.closed();
	}

	/**
	 * 喂食
	 * 
	 * @param po
	 */
	public static void petEat(PetOwner po, Pet pet) {
		// System.out.println("喂食");
		boolean flag = true;
		String sql = null;
		String sql2 = null;
		String sql3 = null;
		Scanner sc = new Scanner(System.in);
		if (pet == null) {
			ArrayList<Pet> list = getMyPetMess(po);
			showMyPetMess(list);
			if (list.size() > 0) {
				System.out.print("请选择要喂食的宠物:");
				int choose = sc.nextInt();
				if (choose > 0 && choose <= list.size()) {
					pet = new Pet();
					pet = list.get(choose - 1);
				} else {
					System.out.println("输入有误,返回用户操作选择界面");
					flag = false;
				}
			} else {
				System.out.println("暂无宠物可以喂食");
				flag = false;
			}
		}
		while (flag) {
			ArrayList<Food> list2 = getMyFoodMess(po);
			if (list2.size() > 0) {
				showMyFoodMess(list2);
				System.out.print("请选择口粮类型(无效输入则不进行喂养):");
				int ch2 = sc.nextInt();
				if (ch2 > 0 && ch2 <= list2.size()) {
					Food food = list2.get(ch2 - 1);
					if (ADD_HEALTH[food.getF_type()] >= (100 - pet.getHealth())) {
						// 当选择的口粮将添加的健康值大于选择宠物离100之间的差距,提醒用户是否确定使用口粮,确定后健康值变为100,否则取消
						System.out
								.print("如果使用该口粮,你的宠物健康值将达到满值(100),确定使用？1.确定使用       2.重新选择口粮:");
						int choo = sc.nextInt();
						if (choo == 1) {
							sql = "update pet set health = 100 where p_id = "
									+ pet.getP_id();
						} else {
							continue;
						}
					} else {
						sql = "update pet set health = health + "
								+ ADD_HEALTH[food.getF_type()]
								+ " where p_id = " + pet.getP_id();
					}
					sql2 = "update food set f_num = f_num - 1 where f_id = "
							+ food.getF_id();
					// sql3 = "delete from food where f_num = 0";
					try {
						Statement cs = BaseDao.getConn().createStatement();
						cs.addBatch(sql);
						cs.addBatch(sql2);
						// cs.addBatch(sql3);
						int[] executeBatch = cs.executeBatch();
						if (executeBatch.length == 2) {
							BaseDao.commit();
							System.out.println("喂养成功");
							break;
						} else {
							BaseDao.rollback();
							System.out.println("系统异常,喂养失败!");
							break;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("喂养异常");
					}
				} else {
					System.out.println("输入有误!");
					flag = false;
				}
			} else {
				System.out.println("暂无口粮!");
				flag = false;
			}
		}
		BaseDao.closed();
	}

	/**
	 * 返回用户拥有的口粮类型及数量
	 * 
	 * @param po
	 * @return
	 */
	public static ArrayList<Food> getMyFoodMess(PetOwner po) {
		// TODO Auto-generated method stub
		ArrayList<Food> list = new ArrayList<Food>();
		Food fd = null;
		String sql = "select * from food where user_id = " + po.getU_id()
				+ " order by f_type";
		ResultSet rs = BaseDao.getResultSet(sql);
		try {
			while (rs.next()) {
				fd = new Food();
				fd.setF_id(rs.getInt(1));
				fd.setF_type(rs.getInt(2));
				fd.setUser_id(rs.getInt(3));
				fd.setF_num(rs.getInt(4));
				list.add(fd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("返回用户拥有的口粮数异常");
		}
		BaseDao.closed();
		return list;
	}

	/**
	 * 显示我的宠物口粮列表
	 * 
	 * @param list
	 */
	public static void showMyFoodMess(ArrayList<Food> list) {
		int i = 1;
		if (list.size() > 0) {
			System.out.println("--------------我的口粮列表-------------");
			System.out.println("编号\t口粮类型\t数量\t可加健康值数");
			for (Food fd : list) {
				System.out.println(i + "\t" + FOOD_TYPE[fd.getF_type()] + "\t"
						+ fd.getF_num() + "\t" + ADD_HEALTH[fd.getF_type()]);
				i++;
			}
		} else {
			System.out.println("暂无口粮");
		}
	}

	/**
	 * 宠物洗澡
	 * 
	 * @param po
	 */
	public static void petBath(PetOwner po, Pet pet) {
		// System.out.println("洗澡");
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		if (pet == null) {
			ArrayList<Pet> list = getMyPetMess(po);
			if (list.size() > 0) {
				showMyPetMess(list);
				System.out.println("请选择你要洗澡的宠物(无效输入则宠物不进行洗澡)：");
				int choose = sc.nextInt();
				if (choose > 0 && choose <= list.size()) {
					pet = list.get(choose - 1);
				} else {
					System.out.println("输入有误!");
					// petBath(po, null);
					flag = false;
				}
			} else {
				System.out.println("暂无宠物可以洗澡");
				flag = false;
			}
		}
		while (flag) {
			if (pet.getHealth() != 100) {
				System.out.println("你的元宝数:" + po.getMoney());
				System.out.println("洗澡要花费:" + (100 - pet.getHealth()) + "个元宝");
				if (po.getMoney() >= (100 - pet.getHealth())) {
					System.out.println("是否确定给" + pet.getP_name() + "洗澡？(1.确定)");
					int ch = sc.nextInt();
					if (ch == 1) {
						// 修改pet中的健康值加满
						String sql = "update pet set health = 100 where p_id = "
								+ pet.getP_id();
						// 修改petowner中的元宝数减少
						String sql2 = "update petowner set money = money - "
								+ (100 - pet.getHealth()) + " where u_id = "
								+ po.getU_id();
						try {
							Statement createStatement = BaseDao.getConn()
									.createStatement();
							createStatement.addBatch(sql);
							createStatement.addBatch(sql2);
							int[] executeBatch = createStatement.executeBatch();
							if (executeBatch.length == 2) {
								BaseDao.commit();
								System.out.println("洗澡成功," + pet.getP_name()
										+ "的健康值满了");
								break;
							} else {
								System.out.println("系统异常,洗澡失败");
								BaseDao.rollback();
								break;
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("系统异常,洗澡失败");
							break;
						}
					} else {
						System.out.println("你选择了取消给此宠物洗澡!");
						flag = false;
					}
				} else {
					System.out.println("你的元宝数不够,不能给此宠物洗澡!");
				}
			} else {
				System.out.println("健康值为100,,不需要洗澡!");
				flag = false;
			}
		}
		BaseDao.closed();
	}

	/**
	 * 购买口粮
	 * 
	 * @param po
	 */
	public static void petBuyFood(PetOwner po) {
		// System.out.println("购买食物");
		// System.out.println("       欢迎来到宠物之家");
		String sql2 = null;
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			showFoodList();
			System.out.println("请选择要购买商品的编号(无效输入退出购买):");
			int choose = sc.nextInt();
			if (choose > 0 && choose <= FOOD_TYPE.length) {
				System.out.print("请输入要购买的数量(1--100)(无效输入退出购买):");
				int buy_num = sc.nextInt();
				if (buy_num >= 1 && buy_num <= 100) {
					System.out
							.println("需要" + (buy_num * BUY_PRICE[choose - 1]));
					System.out.println("你有" + po.getMoney());
					if (po.getMoney() >= (buy_num * BUY_PRICE[choose - 1])) {
						// 查询之前food表中此用户是否有此类商品
						String sql = "select * from food where f_type = "
								+ (choose - 1) + " and user_id = "
								+ po.getU_id();
						ResultSet rs = BaseDao.getResultSet(sql);
						try {
							if (rs.next()) {
								// 当此用户有此类食物时,修改此食物的数量
								sql2 = "update food set f_num = f_num + "
										+ buy_num + " where user_id = "
										+ po.getU_id() + " and f_type ="
										+ (choose - 1);
							} else {
								// 用户没哟此类食物,添加此类食物到数据库中
								sql2 = "insert into food values(sq_food.nextval,"
										+ (choose - 1)
										+ ","
										+ po.getU_id()
										+ "," + buy_num + ")";
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("购买口粮查询时异常");
						}
						// petowner表中减少元宝数
						String sql3 = "update petowner set money = money - "
								+ (buy_num * BUY_PRICE[choose - 1])
								+ " where u_id = " + po.getU_id();

						try {
							Statement createStatement = BaseDao.getConn()
									.createStatement();
							createStatement.addBatch(sql2);
							createStatement.addBatch(sql3);
							int[] executeBatch = createStatement.executeBatch();
							if (executeBatch.length == 2) {
								System.out.println("购买成功");
								BaseDao.commit();
								break;
							} else {
								System.out.println("系统异常,购买失败");
								BaseDao.rollback();
								break;
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("异常,购买失败");
							flag = false;
						}
					} else {
						System.out.println("你的钱数不够,购买失败");
						break;
					}
				} else {
					System.out.println("输入有误!");
					flag = false;
				}
			} else {
				System.out.println("输入有误!");
				flag = false;
			}
		}
		BaseDao.closed();
	}

	/**
	 * 显示购买口粮的列表
	 */
	public static void showFoodList() {
		System.out.println("-------------口粮列表--------------");
		System.out.println("编号\t类型\t价格\t功效");
		for (int i = 0; i < FOOD_TYPE.length; i++) {
			System.out.println((i + 1) + "\t" + FOOD_TYPE[i] + "\t"
					+ BUY_PRICE[i] + "\t加健康值" + ADD_HEALTH[i]);
		}
	}

	/**
	 * 删除food表中的数量为0的口粮信息
	 */
	public static void deleteFood() {
		String sql = "delete from food where f_num = 0";
		int update = BaseDao.update(sql);
		BaseDao.commit();
		BaseDao.closed();
	}

}
