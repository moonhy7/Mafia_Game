package mafia_game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class ClientGui extends JFrame implements ActionListener, Runnable{
	// 클라이언트 화면용
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	Container container = getContentPane();
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(textArea);
	JTextField textField = new JTextField();
	
	Timer2 timer = new Timer2();
	Image image = new Image();
	
	JLabel label2 = new JLabel("이미지");
	JLabel label3 = new JLabel("직업표");
	JTable jb_table = new JTable();
	
	
	// 통신용
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String str; 		// 채팅 문자열 저장
	
	public ClientGui(String ip, int port) {
		// frame 기본 설정
		setTitle("마피아 게임");
		setSize(1300, 900);
		setLocation(300, 300);
		init();
		start();
		setVisible(true);
		// 통신 초기화
		initNet(ip, port);
		System.out.println("ip = " + ip);
	}
	// 통신 초기화
	private void initNet(String ip, int port) {
		try {
			// 서버에 접속 시도
			socket = new Socket(ip, port);
			// 통신용 input, output 클래스 설정
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ture : auto flush 설정
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (UnknownHostException e) {
			System.out.println("IP 주소가 다릅니다.");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("접속 실패");
			//e.printStackTrace();
		}
		// 쓰레드 구동
		Thread thread = new Thread(this); // run 함수 -> this
		thread.start();
	}

	
	// 직업 테이블 생성 및 업데이트 기능 구현 메소드
	void TableTest(){

        String []a = {"플레이어","직업"};
        String [][]b = {
				{"다현","마피아"},{"하윤","의사"},{"용원","경찰"},{"원준","시민"},{"비트","시민"}
		};
        
        //1. 모델과 데이터를 연결
        DefaultTableModel model = new DefaultTableModel(b,a);
        
        //2. Model을 매개변수로 설정, new JTable(b,a)도 가능하지만 
        //삽입 삭제를 하기 위해 해당 방법을 사용합니다
        JTable table = new JTable(model); //
        
        //3. 결과적으로는 JScrollPane를 추가합니다.
        JScrollPane sc = new JScrollPane(table);
        
        //4. 컴포넌트에  Table 추가
        container.add("East", sc);
        
        //테이블에 데이터 추가하기
        //원본데이터를 건들지 않고 table의 매개변수인 model에 있는 데이터를 변경합니다
        DefaultTableModel m = (DefaultTableModel)table.getModel();
        //모델에 데이터 추가 , 1번째 출에 새로운 데이터를 추가합니다
        // m.insertRow(1, new Object[]{"d1","d2","d3"});
        //추가를 마치고 데이터 갱신을 알립니다.
        table.updateUI();
     
	}
	
	 // 테이블 내용 가운데 정렬하기
	 public void tableCellCenter(JTable table){
		   
		      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
		      dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로
		     
		      TableColumnModel tcm = table.getColumnModel() ; // 정렬할 테이블의 컬럼모델을 가져옴
		     
		      //전체 열에 지정
//		      for(int i = 0 ; i < tcm.getColumnCount() ; i++){
//		      tcm.getColumn(i).setCellRenderer(dtcr);  
		       //컬럼모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여 각각의 셀렌더러를 아까 생성한 dtcr에 set해줌
//		      }
		       
		      //특정 열에 지정
		       tcm.getColumn(0).setCellRenderer(dtcr);  
		       tcm.getColumn(1).setCellRenderer(dtcr);
		    }

	
	
	
	
	/**이미지 추가*/
	class Image extends JPanel {
		public void paintComponent(Graphics g) {
			Dimension d = getSize();
			ImageIcon image = new ImageIcon("C:\\Temp\\mafia.png");
			g.drawImage(image.getImage(), 0, 0, 1300, 100, null);
			setSize(1300,100);
		}
	}
	
	
	// 레이아웃
	private void init() {
		container.setLayout(new BorderLayout(10,90));
		container.add("Center", scrollPane); // 스크롤
		container.add("South", textField); // 채팅 입력 필드
		container.add("West", timer); // 타이머
		container.add("North", image); // 그림
//		container.add("East", sc); // 직업표
		TableTest();

	}
	
	private void start() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textField.addActionListener(this);
	}
	// 응답 대기
	// -> 서버로부터 응답으로 전달된 문자열을 읽어서, textArea에 출력하기
	@Override
	public void run() {
		while(true) {
			try {
				str = in.readLine();
				textArea.append(str + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// textField의 문자열을 읽어와서 서버로 전송함
		str = textField.getText();
		out.println(str);
		// textField 초기화
		textField.setText("");
	}
}