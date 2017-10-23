package Main;

import java.util.Scanner;
import Dao.FindPwdUtil;
import Dao.LoginUtil;
import Dao.RegisterUtil;

public class Pet_Market_Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\t欢迎访问宠物商店");
		while (true) {
			System.out.println("*************主菜单******************");
			System.out.print("     1.登录       2.注册       3.找回密码       4.退出");
			System.out.println("\n***********************************");
			System.out.print("请选择你的操作:");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				// 登录
				LoginUtil.login();
				break;
			case 2:
				// 注册
				RegisterUtil.register();
				break;
			case 3:
				// 找回密码
				FindPwdUtil.findPwd();
				break;
			case 4:
				System.out.println("欢迎下次使用!");
				System.exit(0);
			default:
				System.out.println("输入有误!");
				break;
			}
		}
	}
}