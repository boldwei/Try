package createfiles;

import java.util.ArrayList;
import java.util.List;

public class Testexcel {
	public static void main(String[] args){
		String [] head = {"name","sex","job","pay","address"};
        
        List<User> list = new ArrayList<User>();
        User user1 = new User("С��", "��", "����Ա", "5000", "ɽ��");
        User user2 = new User("С��", "Ů", "����", "3000", "ɽ��");
        
        list.add(user1);
        list.add(user2);
        
        System.out.println(new GenerateExcel().generateExcel(head,list));
        //System.out.println(new GenerateExcel().generateExcels("E:\\appData\\20151104",head,list));
    }
}
