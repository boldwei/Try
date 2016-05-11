package createfiles;

import java.util.ArrayList;
import java.util.List;

public class Testpdf {
	public static void main(String[] args){
        System.out.println("begin");
        
        String [] head = {"name","sex","job","pay","address"};
        
        List<User> list = new ArrayList<User>();
        User user1 = new User("小明", "男", "公务员", "5000", "山西");
        User user2 = new User("小红", "女", "助理", "3000", "山东");
        
        list.add(user1);
        list.add(user2);
        
        String filePath = new CreatePdf().generatePDFs(head,list);
        System.out.println(filePath);
        System.out.println("end");
    }
}
