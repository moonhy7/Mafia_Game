package mafia_game;

import java.util.Scanner;

import mafia_game.TcpApplication;

public class TcpServer {
	public static void main(String[] args) {
		for(int i=0; i<15; i++) {
			System.out.println("");
		}
		
		Scanner sc = new Scanner(System.in);
		
		showMenu();
		
		for(int i=0; i<15; i++) {
			System.out.println("");
		}
		
		System.out.printf("          >");
		int select = sc.nextInt();
		
		switch (select) {
		case 1:
			System.out.printf("    1 → Start     \n");
			System.out.println();
			break;

		case 0:
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		
		/*
		 * TCP/IP 어플리케이션 생성
		 */
		TcpApplication app = new AppServer();
		
		// 설정 초기화
		app.init();
		
		// 어플리케이션 실행
		app.start();
		
		sc.close();
	}

	public static void showMenu() {
		System.out.printf("                                      \n");
		System.out.printf("          ■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		System.out.printf("                                      \n");
		System.out.printf("            [마피아 게임 시작]   \n");
		System.out.printf("                                      \n");
		System.out.printf("          ■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		System.out.printf("                                      \n");

		
		System.out.printf("          1 → 게임 입장           \n");
		System.out.printf("          0 → 게임 퇴장           \n");
	}
}
