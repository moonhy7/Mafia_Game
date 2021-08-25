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
      //setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);   // x ������ ���൵ ���� ����
      setLayout(new BorderLayout());
      String time = getCurrentTime();
      label = new JLabel(time);
      label.setFont(new Font("TimesRoman", Font.ITALIC, 30)); // ��Ʈ, �۾�ũ��
      label.setHorizontalAlignment(JLabel.CENTER); // ��� ����
      add(label, BorderLayout.CENTER);
      add(new JButton("���� �ð� :" + time),BorderLayout.SOUTH);
//      add(new JButton("���� �� �� :" +dayCount),BorderLayout.NORTH);
      
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
               
               label.setText("�����ð�: " + i);
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