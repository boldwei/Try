package createfiles;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetFilePlace {
    /**
     * ��ȡ�ļ�����ȡexcel����ĸ�Ŀ¼
     * @return  excel����ĸ�Ŀ¼
     */
    public   String getFilePath(){
        String dir = System.getProperty("user.dir");  //���tomcat���ڵĹ���·��  
        //��ȡ���洢���ļ��洢λ�õ�filedir.properties �ļ�·��   --->java Project���ļ�·��
        String realDir = dir + File.separator + "src" + File.separator +"META-INF" + File.separator + "filedir.properties";
        //Web project�洢·��
        /*String realDir = dir.substring(0, dir.length()-4) + File.separator +"webapps" + File.separator + "generateExcels" 
                      + File.separator + "classes" + File.separator + "META-INF" + File.separator + "config" + File.separator + "filedir.properties";
         */
        return realDir;
    }
    
    /**
     * ��ȡfilePath·����properities�ļ�����key��Ӧ��ֵ��
     * @param filePath properities�ļ�·��������properities�ļ���
     * @param key Ҫ���ҵ�keyֵ
     * @return key��Ӧ��value
     */
     public   String GetValueByKey(String filePath, String key){
         Properties pps = new Properties();
         try {
              InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
              pps.load(in);
             String value = pps.getProperty(key);
             in.close();
             return value;
             
         }catch (IOException e) {
             e.printStackTrace();
             return null;
         }
     }
    
    /**
     * ��ѯproperities�ļ��п��Զ�Ӧ�Ĵ洢�ص�
     * @param key ��ѯ����
     * @return    key��Ӧ�Ĵ洢��ַ
     */
    public  String getFileDirFromProperties(String key){
        return GetValueByKey(getFilePath(),key);
    }

}
