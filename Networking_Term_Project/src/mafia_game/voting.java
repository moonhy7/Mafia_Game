package mafia_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class voting {
	

   public static void vot() {
	   
	   
	   
      String[] data = {"����", "����", "����", "����", "����"};

      HashMap<String, Integer> map = new HashMap<>();

      for(int i=0; i<data.length; i++) {
         if(map.containsKey(data[i])) {
            int value = (int)map.get(data[i]);
            map.put(data[i], value + 1);
         } else {
            map.put(data[i], 1);
         }
      }

      System.out.println(map);


      int count = 0;
      
      int total = 0;

      List<String> listKeySet = new ArrayList<>(map.keySet());


      Collections.sort(listKeySet, (value1, value2) -> (map.get(value2).compareTo(map.get(value1))));

      for(String key : listKeySet) { 
         //System.out.println("key : " + key + " , " + "value : " + map.get(key));

         total++;
         
         if(map.get(key) > 5/2.0) {
            System.out.println(key + "�� �׿����ϴ�.");
            break;
         }


         else if(map.get(key) == 2){ // 2111 2211

            count++;


            if(count == 1) {
               continue;
            }
            else if(count == 2) {
               System.out.println(" �ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�.");
               break;
            }

            else {
               //    System.out.println(key + "�� �׿����ϴ�.");
            }


         }

         else  { //1 1 1 1 1    2111

            if(count==1) {
               System.out.println(listKeySet.get(total-2) + "�� �׿����ϴ�.");
               break;

            } else {
               System.out.println(" �ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�.");
               break;

            }
         }
      }
   }
}




