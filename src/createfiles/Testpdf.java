package createfiles;

import java.util.ArrayList;
import java.util.List;

public class Testpdf {
	public static void main(String[] args){
        System.out.println("begin");
        
        String [] head = {"name","sex","job","pay","address"};
        
        List<User> list = new ArrayList<User>();
        User user1 = new User("С��", "��", "����Ա", "5000", "ɽ��");
        User user2 = new User("С��", "Ů", "����", "3000", "ɽ��");
        
        list.add(user1);
        list.add(user2);
        
        String filePath = new CreatePdf().generatePDFs(head,list);
        System.out.println(filePath);
        System.out.println("end");
    }
}
