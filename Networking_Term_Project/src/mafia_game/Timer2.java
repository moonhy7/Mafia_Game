package mafia_game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Timer2 extends JPanel implements Runnable{

   private JLabel label;
   
   public Timer2() {
      //setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);   // x 누르면 실행도 같이 종료
      setLayout(new BorderLayout());
      String time = getCurrentTime();
      label = new JLabel(time);
      label.setFont(new Font("TimesRoman", Font.ITALIC, 30)); // 폰트, 글씨크기
      label.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
      add(label, BorderLayout.CENTER);
      add(new JButton("현재 시간 :" + time),BorderLayout.SOUTH);
//      add(new JButton("남은 일 수 :" +dayCount),BorderLayout.NORTH);
      
      setSize(300, 200);
      Thread t1 = new Thread(this);
      t1.start();
      
      setVisible(true);
   }
   
   
   @Override
   public void run() {
      Timer time = new Timer();
      
      time.scheduleAtFixedRate(new TimerTask() {
         int i = 100;

            public void run() {
               
               label.setText("남은시간: " + i);
                i--;
               
                
                if (i < 0) {
                    time.cancel();
                    label.setText("Time Over");
                }
            }
        }, 0, 1000);
      
   }
   
   public String getCurrentTime() {
      Calendar c = Calendar.getInstance();
      int hour = c.get(Calendar.HOUR_OF_DAY);
      int min = c.get(Calendar.MINUTE);
      int sec = c.get(Calendar.SECOND);
      
      String dayTime = hour+":"+min+":"+sec;
      return dayTime;
   }
   
   public static void main(String[] args) {
      new Timer2();
   }
}