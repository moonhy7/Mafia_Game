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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	// Ŭ���̾�Ʈ ȭ���
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	Container container = getContentPane();
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(textArea);
	JTextField textField = new JTextField();
	
	
	Timer2 timer = new Timer2();
	Image image = new Image();
	
	JLabel label2 = new JLabel("�̹���");
	JLabel label3 = new JLabel("����ǥ");
	JTable jb_table = new JTable();
	
	JButton btn1 = new JButton("��ǥ�ϱ�");

	
	
	
	
	// ��ſ�
	Socket socket;
	static PrintWriter out;
	BufferedReader in;
	String str; 		// ä�� ���ڿ� ����
	
	public ClientGui(String ip, int port) {
		// frame �⺻ ����
		setTitle("���Ǿ� ����");
		setSize(1300, 900);
		setLocation(50, 50);
		init();
		start();
		setVisible(true);
		
		// jb_table.setSize(10, 90);
		
		
		jb_table.setPreferredSize(new Dimension(200,200));
		
		
		
		// ��� �ʱ�ȭ
		initNet(ip, port);
		System.out.println("ip = " + ip);
	}
	// ��� �ʱ�ȭ
	private void initNet(String ip, int port) {
		try {
			// ������ ���� �õ�
			socket = new Socket(ip, port);
			// ��ſ� input, output Ŭ���� ����
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ture : auto flush ����
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (UnknownHostException e) {
			System.out.println("IP �ּҰ� �ٸ��ϴ�.");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("���� ����");
			//e.printStackTrace();
		}
		// ������ ����
		Thread thread = new Thread(this); // run �Լ� -> this
		thread.start();
	}

	
	// ���� ���̺� ���� �� ������Ʈ ��� ���� �޼ҵ�
	void TableTest(){

        String []a = {"�÷��̾�","����"};
         
        String [][]b = {
				{"����","���Ǿ�"},{"����","�ǻ�"},{"���","����"},{"����","�ù�"},{"��Ʈ","�ù�"}
		};
        
        //1. �𵨰� �����͸� ����
        DefaultTableModel model = new DefaultTableModel(b,a);
        
        //2. Model�� �Ű������� ����, new JTable(b,a)�� ���������� 
        //���� ������ �ϱ� ���� �ش� ����� ����մϴ�
        JTable table = new JTable(model); //
        
        //3. ��������δ� JScrollPane�� �߰��մϴ�.
        JScrollPane sc = new JScrollPane(table);
        
		container.add("East", sc);

        
        //4. ������Ʈ��  Table �߰�
        
        
        //���̺� ������ �߰��ϱ�
        //���������͸� �ǵ��� �ʰ� table�� �Ű������� model�� �ִ� �����͸� �����մϴ�
        DefaultTableModel m = (DefaultTableModel)table.getModel();
        //�𵨿� ������ �߰� , 1��° �⿡ ���ο� �����͸� �߰��մϴ�
        // m.insertRow(1, new Object[]{"d1","d2","d3"});
        //�߰��� ��ġ�� ������ ������ �˸��ϴ�.
        table.updateUI();
     
	}
	
	 // ���̺� ���� ��� �����ϱ�
	 public void tableCellCenter(JTable table){
		   
		      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
		      dtcr.setHorizontalAlignment(SwingConstants.CENTER); // �������� ���������� CENTER��
		     
		      TableColumnModel tcm = table.getColumnModel() ; // ������ ���̺��� �÷����� ������
		     
		      //��ü ���� ����
//		      for(int i = 0 ; i < tcm.getColumnCount() ; i++){
//		      tcm.getColumn(i).setCellRenderer(dtcr);  
		       //�÷��𵨿��� �÷��� ������ŭ �÷��� ������ for���� �̿��Ͽ� ������ ���������� �Ʊ� ������ dtcr�� set����
//		      }
		       
		      //Ư�� ���� ����
		       tcm.getColumn(0).setCellRenderer(dtcr);  
		       tcm.getColumn(1).setCellRenderer(dtcr);
		    }

	
	 

	/**�̹��� �߰�*/
	class Image extends JPanel {
		public void paintComponent(Graphics g) {
			Dimension d = getSize();
			ImageIcon image = new ImageIcon("C:\\Temp\\mafia.png");
			g.drawImage(image.getImage(), 0, 0, 1300, 100, null);
			setSize(1300,100);
		}
	}
	




	// ��ǥ ��ư ����
	class Button {

		public void popUp() {

//			String[] keys = new String[TcpServerHandler.sendMap.size()];

			String[] data = {};
			
			
			
			for(int i = 0; i < TcpServerHandler.userMap.size(); i++) {
				data[i] = TcpServerHandler.userMap.get(""+i);
			}
			
//			int num = JOptionPane.showOptionDialog(null, "���� ����� �������ּ���", "��� ����",
//					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, data, "�� ��°��");
			int n = JOptionPane.showOptionDialog(null,
					"���� ����� �������ּ���", "��� ����",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,     //do not use a custom Icon
				    data,  //the titles of buttons
				    "�� ��°��");
//			ClientGui.out.println(num);
			
			//System.out.println(data[0]);

		}		

	}
	
	
	
	
	
	// ���̾ƿ�
	private void init() {		
		btn1.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //your actions
		    	Button btn = new Button();
		    	btn.popUp();
		    }
		});
		container.setLayout(new BorderLayout(10,90));
		container.add("Center", scrollPane); // ��ũ��
		container.add("South", textField); // ä�� �Է� �ʵ�
		container.add("West", timer); // Ÿ�̸�
		container.add("North", image); // �׸�
		container.add("East", btn1); // ��ư
//		container.setLayout(new GridLayout(0,3));
		// �����̳� - ������, 
//		container.add("East", panel);
		 
		
		
		
//		container.add("East", sc); // ����ǥ
//		 TableTest();

	}
	
	private void start() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textField.addActionListener(this);
	}
	// ���� ���
	// -> �����κ��� �������� ���޵� ���ڿ��� �о, textArea�� ����ϱ�
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
		// textField�� ���ڿ��� �о�ͼ� ������ ������
		str = textField.getText();
		out.println(str);
		/*
		 * if(str.indexOf("���� ����") > 0) { Timer2 tm2 = new Timer2(); tm2.run(); }
		 */
		// textField �ʱ�ȭ
		textField.setText("");
	}
}