package createfiles;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * �����ļ�����
 * @author zcr
 *
 */
public class GenerateFileName{
    /**
     * �����ļ���������ļ�������,�ļ�������������:�ļ�Ŀ¼/����ʱ��-uuid��ȫ��Ψһ���룩.�ļ����
     * @param fileDir  �ļ��Ĵ洢·��
     * @param fileType �ļ������
     * @return                 �ļ�������  
     */
    public String generateFileName(String fileDir,String fileType){
        String saveFileName = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        saveFileName += format.format(Calendar.getInstance().getTime());
        
        UUID uuid = UUID.randomUUID();  //ȫ��Ψһ����
        saveFileName += "-" + uuid.toString();
        saveFileName += "." + fileType;
        saveFileName = fileDir + File.separator + saveFileName;
        
        return saveFileName;
    }
}
