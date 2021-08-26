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
   
   
   // �� �ο� ���� ��
   static final int NUMBER = 5;
   
      /* Ŭ���̾�Ʈ ID�� Ű(K)�� �ϴ� ���(V)�� ���� �� �ڷᱸ��
       * 
       */
      static HashMap<String, PrintWriter> sendMap = new HashMap<>();
      
      static HashMap<String, String> userMap = new HashMap<String, String>();
      
      
      //Ŭ���̾�Ʈ�� ����� ���� ��ü
      private Socket sock;
      
      // Ŭ���̾�Ʈ IP �ּ� 
      private String cAddr;
      
      // Ŭ���̾�Ʈ ID
      private String id;
      
      
      /* ������
       * �޾� �� ������ �ʿ� ����
       */
      
      
      public TcpServerHandler(Socket socket) {
         sock = socket;
      }

     
      
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
            
            
            // ����
            if(sendMap.size() < 5) {
               broadCast(TcpApplication.timeStamp() +
                     "[" + id + "] ���� ��� ���̽��ϴ�.");
               System.out.println(TcpApplication.timeStamp() + cAddr + " <- connected");
               System.out.println(TcpApplication.timeStamp()+ 
                     "�����ο�: " + sendMap.size() + "/" + NUMBER  + "��");
               broadCast("�����.... �����ο�: " + sendMap.size() + "��");

               //����� ä�� ���
               String line = null;
               while((line = br.readLine()) != null) {
                  String msg = "[" + id + "]" + " " + line;
                  TcpServerHandler.broadCast(msg);
               }
            } 


            
            // �ο� full
            broadCast(TcpApplication.timeStamp() +
                  "[" + id + "] ���� ��� ���̽��ϴ�.");
            System.out.println(TcpApplication.timeStamp()+ 
                  "�����ο�: " + sendMap.size() + "/" + NUMBER  + "��");
            broadCast("�����.... �����ο�:" + sendMap.size() + "��");
            broadCast("��� �� ������ ���۵˴ϴ�.");

            try {
            Thread.sleep(3 * 1000);
            }catch(InterruptedException e) {};
            
            
            broadCast(" ");
            broadCast("------------���� ����------------");
            broadCast(" ");
            
            
            /* 1. ���� ����Ʈ ���� */
            
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
            broadCast(" ");
            
            for(int i = 0; i < playerList.size(); i++) {
            	userMap.put("" + i, playerList.get(i));
            }
            
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
            
             
             
             
          // �ý��ۿ��� �ӼӸ��� ���� ����ڿ��� ���� �˷��ֱ�
             
             for(int i=0; i<5; i++) {
                
                notice("/to " + newplayerList.get(i) + " " + jobList.get(i));
                
             }
             
             
             
             

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
//            TcpServerHandler.sendMap.remove(id);
//            System.out.println(TcpApplication.timeStamp()+ 
//                  "�����ο�: " + sendMap.size() + "��");
            
            pw.close();
            br.close();
         //   sock.close();
            
            
//            voting();
            
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      
      
      
      
      /*
       * ��ǥ �޼ҵ�
       */
//      public void voting() {
//      String[] buttons = {"ù ��°��", "�� ��°��", "�� ��°��", "�� ��°��","�ټ���°��"};
//      int num = JOptionPane.showOptionDialog(null, "���� ����� �������ּ���", "��� ����",
//              JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "�� ��°��");
//
//      System.out.println("��ǥ ��� :" + num);
//      
//      }
//      
      
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
      
      
      
      
      //���ο��� ������ �˷��ִ� �޼ҵ�
      public void notice(String msg) {
          int start = msg.indexOf(" ") + 1;
          int end = msg.indexOf(" ", start);
          
          if(end != -1) {
             String id = msg.substring(start,end);
             String secret = msg.substring(end+1);
             
             PrintWriter pw = TcpServerHandler.sendMap.get(id);
             
             if(pw != null) {
                pw.println("����� ������ " + secret + "�Դϴ�." );
                pw.flush();
             }
             
             
          }
       }
      
      
      
      
      
         
         
      
   }