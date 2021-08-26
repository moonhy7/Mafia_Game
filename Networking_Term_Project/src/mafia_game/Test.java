package mafia_game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;


public class Test {

	static HashMap<String, Integer> map = new HashMap<>();

	ArrayList<String> arrList = new ArrayList<>();
	


	public void run() {
		String[] data = {"가", "다", "라", "나", "마"};

		for(int i=0; i<data.length; i++) {
			if(map.containsKey(data[i])) {
				int value = (int)map.get(data[i]);
				map.put(data[i], value + 1);
			} else {
				map.put(data[i], 1);
			}
		}


		Iterator it = TcpServerHandler.sendMap.entrySet().iterator();

		while(it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			arrList.add((String) e.getKey());
		}
		
		System.out.println(arrList);
		
//		int arrListSize = arrList.size();
//		String arr[] = arrList.toArray(new String[arrListSize]);
//		
//		for(int i=0; i<5; i++) {
//			System.out.println(Arrays.toString(arr));
//		}
		
	}
}