package mafia_game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TcpServerHandler implements Runnable{

	   /* Ŭ���̾�Ʈ ID�� Ű(K)�� �ϴ� ���(V)�� ���� �� �ڷᱸ��
	    * 
	    */
	   static HashMap<String, PrintWriter> sendMap = new HashMap<>();
	   
	   
	   //Ŭ���̾�Ʈ�� ����� ���� ��ü
	   private Socket sock;
	   
	   // Ŭ���̾�Ʈ IP �ּ� 
	   private String cAddr;
	   
	   // Ŭ���̾�Ʈ ID
	   private String id;
	   
	   /* ������
	    * �޾� �� ������ �ʿ� ����
	    */
	   
	   static final int NUMBER = 5;
	   
	   public TcpServerHandler(Socket socket) {
	      sock = socket;
	   }

	   /*
	    * ������ ��/��� ����
	    * ��ε�ĳ����
	    * ������ �ۼ��� ����
	    */
	   
	   @Override
	   public void run() {
	      
	      try {
	         // 1. �۽� ��Ʈ�� ���
	         PrintWriter pw = new PrintWriter(
	                        new OutputStreamWriter(sock.getOutputStream()));
	         
	         // 2. ���� ��Ʈ�� ���
	         BufferedReader br = new BufferedReader(
	                        new InputStreamReader(
	                        sock.getInputStream()));
	         
	         
	         
	         // Ŭ���̾�Ʈ �������� ���� �� ��ε�ĳ���� 
	         id = br.readLine();
	         sendMap.put(id, pw);
	         
	         
	         // 4. Ŭ���̾�Ʈ ���������� ��ε�ĳ����
	         
	         if(sendMap.size() < 5) {
	         broadCast(TcpApplication.timeStamp() +
	               "[" + id + "] ���� ��� ���̽��ϴ�.");
	         System.out.println(TcpApplication.timeStamp() + cAddr + " <- connected");
	         System.out.println(TcpApplication.timeStamp()+ 
	               "�����ο�: " + sendMap.size() + "/" + NUMBER  + "��");
	         broadCast("�����.... �����ο�: " + sendMap.size() + "��");
	         
	         String line = null;
	         while((line = br.readLine()) != null) {
	            String msg = "[" + id + "]" + " " + line;
	            TcpServerHandler.broadCast(msg);
	            if(sendMap.size() == 5 && id != null) {
	               broadCast(TcpApplication.timeStamp() +
	                     "[" + id + "] ���� ��� ���̽��ϴ�.");
	               System.out.println(TcpApplication.timeStamp()+ 
	                     "�����ο�: " + sendMap.size() + "/" + NUMBER  + "��");
	               break;
	            }
	         } 
	         }
	         
	         
	         broadCast(" ");
	         broadCast("------------���� ����------------");
	         
	         
	         
	         /* 1. ���� ����Ʈ ���� */
//		 		final List<String> job_list = new ArrayList<>();
//		 		
//		 		job_list.add("���Ǿ�");
//		 		job_list.add("�ǻ�");
//		 		job_list.add("����");
//		 		job_list.add("�ù�");
//		 		job_list.add("�ù�");
	         List<String> jobList = Arrays.asList("���Ǿ�", "�ǻ�", "����", "�ù�", "�ù�");
	         List<String> playerList = new ArrayList<>();
	         
	         Set<Map.Entry<String, PrintWriter>>set = sendMap.entrySet();
	         Iterator<Map.Entry<String, PrintWriter>> it = set.iterator();
	         
	         while(it.hasNext()) {
	        	 Map.Entry<String, PrintWriter> entry = it.next();
	        	 String id = entry.getKey();
	        	 playerList.add(id);
	        	 
	         }
	         
	         broadCast("���� ����Ʈ : " + jobList);
	         broadCast("������ ����Ʈ : " + playerList);
	         
	         
	         /* 3. 0 ~ 5 ���� ���� �迭 ���� (�ߺ�X) */
	         
	         int randomList[] = new int[5];
	         int index = 0;
	         while(index != 5) {
	        	 // random() �̿��Ͽ� 5�� ���� ���� (�ߺ�X)
	        	 randomList[index] = (int)(Math.random()*5);
	        	 for(int i=0; i<index; i++) {
	        		 if(randomList[i] == randomList[index]) {
	        			 index--;
	        			 break; // �ߺ� ���� ����
	        		 }
	        	 }
	        	 index++;
	        	 
	         } // random() �̿��Ͽ� �̾Ƴ� ���� ���
	         
	         
	         
	         
	         /* 4. ������ ��� ���� �������� �ٽ� ���� (List �̿�)*/
	         List<String> newplayerList = new ArrayList<>();
		 		for(int i=0; i<5; i++) {
		 			newplayerList.add(playerList.get(randomList[i]));
		 		}
	         
		 	for(int i=0; i<5; i++) {
		 		broadCast("��Ī �Ϸ� : " + jobList.get(i) + " - " + newplayerList.get(i));
		 	}

	 
	 		
//	 		/* 4-1. ������ ��� ���� �������� �ٽ� ���� (Array �̿�) */
//	 		String[] newplayerList = new String[5];
//	 				
//	 		newplayerList[0] = "";
//	 		newplayerList[1] = "";
//	 		newplayerList[2] = "";
//	 		newplayerList[3] = "";
//	 		newplayerList[4] = "";
//	 	
//	 		for(int i=0; i<assigned_player_list.length; i++) {
//	 			assigned_player_list[randomList[i]] = player_list.get(i);
//	 		}
//	 			
	 		
	 		
	 		
	 		
//	 		for(int elements : randomList) {
//	 			System.out.print(elements + " ");
//	 		}
//	 		System.out.println();

	 		// System.out.println(randomList[0]);
	 		
	 		
	 		
	 		
	 	
	 		/* 5. ������ - ���� ������ ����Ʈ  ��� */
//	 		for(int i=0; i<5; i++) {
////	 			System.out.println(job_list.get(i)  + " - " +assigned_player_list.get(i) );
//	 		}
	 		

//	 		int ranNum = (int)(Math.random()*6); // �ߺ� ���� ���� �߻��� ���� ������ �ϳ��� ����
//	 		
//	 		for(int i=0; i<assigned_player_list.length; i++) {
//	 			assigned_player_list[(job_list.size() - ranNum + i) % 5] = 	player_list.get(i);
//	 		}
//	 		
//	 		// ������ �������� ���� ���� �迭 ���� - ���� for�� �̿�
//	 		for(String elements : assigned_player_list) {
//	 			System.out.println(elements);
//	 		}
	 		
	 
	         
	         
	         
	         
	               
	      
	           

	   
	   
	   
	   
	   
	         // 5. ����/�۽�
	         String line = null;
	         while((line = br.readLine()) != null) {
	            
	            // �ӼӸ� �ϴ� ���
	            if(line.indexOf("/to") > -1) {
	               whisper(id,line);
	            }
	            //�Ϲ� �޽��� ����
	            else {
	               String msg = "[" + id + "]" + " " + line;
	               TcpServerHandler.broadCast(msg);
	            }
	         }

	         // �� ����
//	         TcpServerHandler.sendMap.remove(id);
//	         System.out.println(TcpApplication.timeStamp()+ 
//	               "�����ο�: " + sendMap.size() + "��");
	         
	         pw.close();
	         br.close();
	      //   sock.close();
	         
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   /* �ӼӸ� ���� �޼���
	    * name : ������ Ŭ���̾�Ʈ id
	    * msg : ���� �޽��� (/to ID msg )
	    * 
	    */
	   private void whisper(String name, String msg) {
	      int start = msg.indexOf(" ") + 1; // ���� ��ġ�� ù ������ +1
	      int end = msg.indexOf(" ", start); // start ��ġ���� ���� ������ ������ ��ġ
	      
	      if(end != -1) {
	         // id : ���� Ŭ���̾�Ʈ id
	         String id      = msg.substring(start, end);
	         String secret = msg.substring(end+1);
	         
	         // sendMap���κ���  Ű<id>�� �ش��ϴ� PrintWriter ��ü�� ���´�.
	         PrintWriter pw = TcpServerHandler.sendMap.get(id);
	         // ���� �޽��� ����
	         if(pw != null) {
	            pw.println(name + "���� �ӼӸ� : " + secret);
	            pw.flush();
	         }
	      }
	      
	   }
	   
	   /* �޽��� �ϰ� ���� �޼���
	    * : ��� �����ڿ��� �ϰ������� ����
	    */
	   public static void broadCast(String message) {
	      //sendMap�� ���� �����尡 �����ϹǷ� ����ȭ(synchronized) ó�� �ʿ�
	      synchronized (sendMap) {
	         
	         //������ ��� Ŭ���̾�Ʈ�鿡�� �޽��� ����
	      for(PrintWriter cpw :TcpServerHandler.sendMap.values()) {
	         cpw.println(message);
	         cpw.flush();
	      }
	      }
	      
	   }
	   public static String timeStamp() {
	      SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
	      return sdf.format(new Date());
	   }
	   
	   
	   
	      
	      
	   
	}