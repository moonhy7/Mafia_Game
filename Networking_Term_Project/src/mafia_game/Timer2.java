package mafia_game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
			public int time = 30;

			public void run() {

				label.setText("남은시간: " + time);
				time--;

				if (time < 0) {
					//                    time.cancel();
					label.setText("투표 시간이 되었습니다.");
					//Button btn = new Button();
					//btn.popUp();

					time = 30;
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

		//Button button = new Button();
		//button.popUp();

	}
}




// 투표 버튼 생성
class Button {

	public void popUp() {

//		String[] keys = new String[TcpServerHandler.sendMap.size()];

		String[] data = {};
		
		
		
		for(int i = 0; i < TcpServerHandler.userMap.size(); i++) {
			data[i] = TcpServerHandler.userMap.get(""+i);
		}
		
		int num = JOptionPane.showOptionDialog(null, "죽일 사람을 선택해주세요", "토론 종료",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, data, "두 번째값");

//		ClientGui.out.println(num);

	}		

}



//        List<String> list = new ArrayList<>();
//
//		
//		Iterator it = TcpServerHandler.sendMap.entrySet().iterator();
//		
//		while(it.hasNext()) {
//			Map.Entry e = (Map.Entry)it.next();
//			list.add((String) e.getKey());



//		String[] buttons = {};
//		for(int i=0; i<5; i++) {
//			buttons[i] = list<>(i);
//		}




//	}







