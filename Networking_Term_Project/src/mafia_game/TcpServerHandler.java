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
   
   
   // 방 인원 제한 수
   static final int NUMBER = 5;
   
      /* 클라이언트 ID를 키(K)로 하는 출력(V)을 위한 맵 자료구조
       * 
       */
      static HashMap<String, PrintWriter> sendMap = new HashMap<>();
      
      static HashMap<String, String> userMap = new HashMap<String, String>();
      
      
      //클라이언트와 연결된 소켓 객체
      private Socket sock;
      
      // 클라이언트 IP 주소 
      private String cAddr;
      
      // 클라이언트 ID
      private String id;
      
      
      /* 생성자
       * 받아 온 소켓을 맵에 저장
       */
      
      
      public TcpServerHandler(Socket socket) {
         sock = socket;
      }

     
      
      @Override
      public void run() {
         
         try {
            // 1. 송신 스트림 얻기
            PrintWriter pw = new PrintWriter(
                           new OutputStreamWriter(sock.getOutputStream()));
            
            // 2. 수신 스트림 얻기
            BufferedReader br = new BufferedReader(
                           new InputStreamReader(
                           sock.getInputStream()));
            
            
            
            // 클라이언트 접속정보 저장 및 브로드캐스팅 
            id = br.readLine();
            sendMap.put(id, pw);
            
            
            // 대기방
            if(sendMap.size() < 5) {
               broadCast(TcpApplication.timeStamp() +
                     "[" + id + "] 님이 들어 오셨습니다.");
               System.out.println(TcpApplication.timeStamp() + cAddr + " <- connected");
               System.out.println(TcpApplication.timeStamp()+ 
                     "참여인원: " + sendMap.size() + "/" + NUMBER  + "명");
               broadCast("대기중.... 현재인원: " + sendMap.size() + "명");

               //대기중 채팅 기능
               String line = null;
               while((line = br.readLine()) != null) {
                  String msg = "[" + id + "]" + " " + line;
                  TcpServerHandler.broadCast(msg);
               }
            } 


            
            // 인원 full
            broadCast(TcpApplication.timeStamp() +
                  "[" + id + "] 님이 들어 오셨습니다.");
            System.out.println(TcpApplication.timeStamp()+ 
                  "참여인원: " + sendMap.size() + "/" + NUMBER  + "명");
            broadCast("대기중.... 현재인원:" + sendMap.size() + "명");
            broadCast("잠시 후 게임이 시작됩니다.");

            try {
            Thread.sleep(3 * 1000);
            }catch(InterruptedException e) {};
            
            
            broadCast(" ");
            broadCast("------------게임 시작------------");
            broadCast(" ");
            
            
            /* 1. 직업 리스트 생성 */
            
            List<String> jobList = Arrays.asList("마피아", "의사", "경찰", "시민", "시민");
            List<String> playerList = new ArrayList<>();
            
            Set<Map.Entry<String, PrintWriter>>set = sendMap.entrySet();
            Iterator<Map.Entry<String, PrintWriter>> it = set.iterator();
            
            while(it.hasNext()) {
               Map.Entry<String, PrintWriter> entry = it.next();
               String id = entry.getKey();
               playerList.add(id);
               
            }
            
            broadCast("직업 리스트 : " + jobList);
            broadCast("참여자 리스트 : " + playerList);
            broadCast(" ");
            
            for(int i = 0; i < playerList.size(); i++) {
            	userMap.put("" + i, playerList.get(i));
            }
            
            /* 3. 0 ~ 5 랜덤 숫자 배열 생성 (중복X) */
            
            int randomList[] = new int[5];
            int index = 0;
            while(index != 5) {
               // random() 이용하여 5개 숫자 추출 (중복X)
               randomList[index] = (int)(Math.random()*5);
               for(int i=0; i<index; i++) {
                  if(randomList[i] == randomList[index]) {
                     index--;
                     break; // 중복 제거 과정
                  }
               }
               index++;
               
            } // random() 이용하여 뽑아낸 숫자 출력
            
            
            
            
            /* 4. 참가자 명단 순서 랜덤으로 다시 저장 (List 이용)*/
            List<String> newplayerList = new ArrayList<>();
             for(int i=0; i<5; i++) {
                newplayerList.add(playerList.get(randomList[i]));
             }
            
             
             
             
          // 시스템에서 귓속말을 통해 사용자에게 직업 알려주기
             
             for(int i=0; i<5; i++) {
                
                notice("/to " + newplayerList.get(i) + " " + jobList.get(i));
                
             }
             
             
             
             

            // 5. 수신/송신
            String line = null;
            while((line = br.readLine()) != null) {
               
               // 귓속말 하는 경우
               if(line.indexOf("/to") > -1) {
                  whisper(id,line);
               }
               //일반 메시지 전송
               else {
                  String msg = "[" + id + "]" + " " + line;
                  TcpServerHandler.broadCast(msg);
               }
            }

            // 맵 삭제
//            TcpServerHandler.sendMap.remove(id);
//            System.out.println(TcpApplication.timeStamp()+ 
//                  "참여인원: " + sendMap.size() + "명");
            
            pw.close();
            br.close();
         //   sock.close();
            
            
//            voting();
            
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      
      
      
      
      /*
       * 투표 메소드
       */
//      public void voting() {
//      String[] buttons = {"첫 번째값", "두 번째값", "세 번째값", "네 번째값","다섯번째값"};
//      int num = JOptionPane.showOptionDialog(null, "죽일 사람을 선택해주세요", "토론 종료",
//              JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "두 번째값");
//
//      System.out.println("투표 결과 :" + num);
//      
//      }
//      
      
      /* 귓속말 전송 메서드
       * name : 보내는 클라이언트 id
       * msg : 보낼 메시지 (/to ID msg )
       * 
       */
      private void whisper(String name, String msg) {
         int start = msg.indexOf(" ") + 1; // 시작 위치는 첫 공백의 +1
         int end = msg.indexOf(" ", start); // start 위치부터 다음 공백이 나오는 위치
         
         if(end != -1) {
            // id : 보낼 클라이언트 id
            String id      = msg.substring(start, end);
            String secret = msg.substring(end+1);
            
            // sendMap으로부터  키<id>에 해당하는 PrintWriter 객체를 얻어온다.
            PrintWriter pw = TcpServerHandler.sendMap.get(id);
            // 보낼 메시지 전송
            if(pw != null) {
               pw.println(name + "님의 귓속말 : " + secret);
               pw.flush();
            }
         }
         
      }
      
      /* 메시지 일괄 전송 메서드
       * : 모든 참여자에게 일괄적으로 전송
       */
      public static void broadCast(String message) {
         //sendMap에 여러 스레드가 접근하므로 동기화(synchronized) 처리 필요
         synchronized (sendMap) {
            
            //접속한 모든 클라이언트들에게 메시지 전송
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
      
      
      
      
      //개인에게 직업을 알려주는 메소드
      public void notice(String msg) {
          int start = msg.indexOf(" ") + 1;
          int end = msg.indexOf(" ", start);
          
          if(end != -1) {
             String id = msg.substring(start,end);
             String secret = msg.substring(end+1);
             
             PrintWriter pw = TcpServerHandler.sendMap.get(id);
             
             if(pw != null) {
                pw.println("당신의 직업은 " + secret + "입니다." );
                pw.flush();
             }
             
             
          }
       }
      
      
      
      
      
         
         
      
   }