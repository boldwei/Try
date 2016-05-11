package createfiles;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GenerateFold{
    /**
     * ��ѯ��ǰ���ɵ�excel��Ҫ�������ĸ�·�������������洢����Ӧ��λ�ã��������ɸ�Ŀ¼�� ÿ������һ���ļ��У��ļ��е���������Ϊ �����յ�ʱ���
     * @param foldName  ����excel����·��
     * @return            ���ڵ�excel��Ҫ����·��
     */
    public  String getFold(String foldName){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        
        String todayStr = format.format(Calendar.getInstance().getTime());
        
        String foldPath = foldName + File.separator + todayStr; 
        
        File file = new File(foldPath);
        
        if(!file.exists() && !file.isDirectory()){
            System.out.println("������");
            file.mkdirs();
        }else{
            System.out.println("����");
        }
        return  foldPath;
    }

}
