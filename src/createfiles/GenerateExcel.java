package createfiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * ����excel
 * @author CHENWEI
 *
 */
public class GenerateExcel{
    /**
     * ͨ���ؼ��ֲ�ѯproperties�ļ���Ӧ�ļ��Ĵ洢λ�ã����ݱ�ͷ˳�����ݱ��浽��Ӧ�ļ�·����xls�ļ���, �ļ�������������ʱ�����һ��ȫ��Ψһ����
     * @param fileDir                         //�����ļ��洢��Ŀ¼
     * @param head                           //��ͷ
     * @param list                           //����
     * @return                               //�ļ��ı���·���������ֵ��ַ���
     */
    public <T> String generateExcels(String fileDir,String [] head,List<T> list) {
        //��ô洢��·��
        //String savePath = new GetFilePlace().getFileDirFromProperties(key);
        
        //�ļ��洢����
        String saveFileName = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        saveFileName += format.format(Calendar.getInstance().getTime());
        
        UUID uuid = UUID.randomUUID();  //ȫ��Ψһ����
        
        saveFileName += "-" + uuid.toString();
        
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0,"APP����");  //���ñ����������
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        HSSFRow titleRow = sheet.createRow(0);
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)(head.length-1)));
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("AAP����____ ");
        titleCell.setCellStyle(cellStyle);
        HSSFRow row1 = sheet.createRow(1);
        
        
        //���ñ�ͷ
        for(int i = 0 ; i < head.length ; i++){
            HSSFCell cell = row1.createCell(i);
            cell.setCellValue(head[i]);  //����ֵ
            cell.setCellStyle(cellStyle);//������ʽ
        }
        
        if(null != list && list.size() > 0){
            int size = list.size(); 
            Class classType = list.get(0).getClass();
            for(int i = 0,rowNum=2 ; i < size ; i ++,rowNum++){
                HSSFRow rows = sheet.createRow(rowNum);
                T t = list.get(i);
                
                //���������
                for(int j = 0 ; j < head.length ; j++){
                    //�������ĸ
                    String firstLetter = head[j].substring(0,1).toUpperCase(); 
                    
                    //���get����,getName,getAge��
                    String getMethodName = "get" + firstLetter + head[j].substring(1);
                   
                    Method method;
                    try{
                        //ͨ����������Ӧ��get���������ڻ����Ӧ������ֵ
                        method = classType.getMethod(getMethodName, new Class[]{});
                        
                        HSSFCell dataCell = rows.createCell(j);
                        try{
                             System.out.print(getMethodName +":" + method.invoke(t, new Class[]{}) +",");
                             dataCell.setCellValue(method.invoke(t, new Class[]{}).toString());
                        }catch (IllegalArgumentException e){
                            e.printStackTrace();
                        }catch (IllegalAccessException e){
                            e.printStackTrace();
                        }
                        catch (InvocationTargetException e){
                            e.printStackTrace();
                        }  //����ֵ
                        dataCell.setCellStyle(cellStyle);//������ʽ
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                    catch (NoSuchMethodException e){
                        e.printStackTrace();
                    }
                   
                }
                System.out.println();
            }
        }else{
            System.out.println("û������");
        }
        
        //����ļ��洢·��
        //String fileDir = new GetFilePlace().getFileDirFromProperties(key);
        saveFileName += ".xls";
        String saveFilePathAndName = fileDir + File.separator + saveFileName;
        OutputStream out = null;
        try{
            out = new FileOutputStream(saveFilePathAndName);
            try{
                workbook.write(out);//�����ļ�
            }catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (FileNotFoundException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try{
                out.close();
            }catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return saveFilePathAndName;
    }

    
    /**
     * �ṩ�����õĽӿڣ�������headΪ��ͷ��listΪ���ݵ�excel
     * @param head  //���ݱ�ͷ
     * @param list  //����
     * @return        //excel���ڵ�·��
     */
    public <T> String generateExcel(String [] head,List<T> list){
        final String FilePath = "filePath";
        String saveFilePathAndName = "";
    
        //��ô洢�ĸ�Ŀ¼
        String savePath = new GetFilePlace().getFileDirFromProperties(FilePath);
        
        //��õ���洢��·��
        String realSavePath = new GenerateFold().getFold(savePath);
        
        //����excel�����洢��·�����أ������ļ�����
        saveFilePathAndName = generateExcels(realSavePath, head, list);
        
        return saveFilePathAndName;
    }
}
