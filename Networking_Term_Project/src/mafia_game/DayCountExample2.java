package mafia_game;

import java.util.Timer;
import java.util.TimerTask;

import mafia_game.TcpApplication;

public class DayCountExample2 implements Runnable { 
   
   int day = 5;
   
      @Override
      public void run() {
         System.out.println("시작 시간 : " + TcpApplication.timeStamp());
         System.out.println();
         
         for(int i = 0; i < 5; i++) {
            try {
               System.out.println("밤이 되었습니다.");
               
               Thread.sleep(60000);
               day--;
               System.out.println(TcpApplication.timeStamp());
               System.out.println("남은 일수는 " + day + "일 입니다.");
               System.out.println();
               
               
               
            } catch (InterruptedException e) {
               
               e.printStackTrace();
            } finally {
               System.out.println("아침이 되었습니다.");
               try {
                  Thread.sleep(60000);
                  System.out.println(TcpApplication.timeStamp());
                  System.out.println();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
               
            } // finally 
      } // for 
         System.out.println("종료");
   } // run 
}   