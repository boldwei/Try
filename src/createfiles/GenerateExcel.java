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
 * 生成excel
 * @author CHENWEI
 *
 */
public class GenerateExcel{
    /**
     * 通过关键字查询properties文件相应文件的存储位置，根据表头顺序将数据保存到相应文件路径的xls文件中, 文件的命名规则是时间戳加一串全球唯一编码
     * @param fileDir                         //查找文件存储根目录
     * @param head                           //表头
     * @param list                           //数据
     * @return                               //文件的保存路径及其名字的字符串
     */
    public <T> String generateExcels(String fileDir,String [] head,List<T> list) {
        //获得存储的路径
        //String savePath = new GetFilePlace().getFileDirFromProperties(key);
        
        //文件存储名字
        String saveFileName = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
        saveFileName += format.format(Calendar.getInstance().getTime());
        
        UUID uuid = UUID.randomUUID();  //全球唯一编码
        
        saveFileName += "-" + uuid.toString();
        
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0,"APP数据");  //设置表格工作簿名称
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        HSSFRow titleRow = sheet.createRow(0);
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)(head.length-1)));
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("AAP数据____ ");
        titleCell.setCellStyle(cellStyle);
        HSSFRow row1 = sheet.createRow(1);
        
        
        //设置表头
        for(int i = 0 ; i < head.length ; i++){
            HSSFCell cell = row1.createCell(i);
            cell.setCellValue(head[i]);  //设置值
            cell.setCellStyle(cellStyle);//设置样式
        }
        
        if(null != list && list.size() > 0){
            int size = list.size(); 
            Class classType = list.get(0).getClass();
            for(int i = 0,rowNum=2 ; i < size ; i ++,rowNum++){
                HSSFRow rows = sheet.createRow(rowNum);
                T t = list.get(i);
                
                //添加数据行
                for(int j = 0 ; j < head.length ; j++){
                    //获得首字母
                    String firstLetter = head[j].substring(0,1).toUpperCase(); 
                    
                    //获得get方法,getName,getAge等
                    String getMethodName = "get" + firstLetter + head[j].substring(1);
                   
                    Method method;
                    try{
                        //通过反射获得相应的get方法，用于获得相应的属性值
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
                        }  //设置值
                        dataCell.setCellStyle(cellStyle);//设置样式
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
            System.out.println("没有数据");
        }
        
        //获得文件存储路径
        //String fileDir = new GetFilePlace().getFileDirFromProperties(key);
        saveFileName += ".xls";
        String saveFilePathAndName = fileDir + File.separator + saveFileName;
        OutputStream out = null;
        try{
            out = new FileOutputStream(saveFilePathAndName);
            try{
                workbook.write(out);//保存文件
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
     * 提供外界调用的接口，生成以head为表头，list为数据的excel
     * @param head  //数据表头
     * @param list  //数据
     * @return        //excel所在的路径
     */
    public <T> String generateExcel(String [] head,List<T> list){
        final String FilePath = "filePath";
        String saveFilePathAndName = "";
    
        //获得存储的根目录
        String savePath = new GetFilePlace().getFileDirFromProperties(FilePath);
        
        //获得当天存储的路径
        String realSavePath = new GenerateFold().getFold(savePath);
        
        //生成excel并将存储的路径返回（包含文件名）
        saveFilePathAndName = generateExcels(realSavePath, head, list);
        
        return saveFilePathAndName;
    }
}
