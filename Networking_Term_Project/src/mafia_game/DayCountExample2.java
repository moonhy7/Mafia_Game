package mafia_game;

import java.util.Timer;
import java.util.TimerTask;

import mafia_game.TcpApplication;

public class DayCountExample2 implements Runnable { 
   
   int day = 5;
   
      @Override
      public void run() {
         System.out.println("���� �ð� : " + TcpApplication.timeStamp());
         System.out.println();
         
         for(int i = 0; i < 5; i++) {
            try {
               System.out.println("���� �Ǿ����ϴ�.");
               
               Thread.sleep(60000);
               day--;
               System.out.println(TcpApplication.timeStamp());
               System.out.println("���� �ϼ��� " + day + "�� �Դϴ�.");
               System.out.println();
               
               
               
            } catch (InterruptedException e) {
               
               e.printStackTrace();
            } finally {
               System.out.println("��ħ�� �Ǿ����ϴ�.");
               try {
                  Thread.sleep(60000);
                  System.out.println(TcpApplication.timeStamp());
                  System.out.println();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
               
            } // finally 
      } // for 
         System.out.println("����");
   } // run 
}   