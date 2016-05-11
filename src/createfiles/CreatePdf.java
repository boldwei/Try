package createfiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * ����pdf
 * @author chenwei
 * 
 */
public class CreatePdf{
    Document document = new Document();// ����һ��Document����

    private static Font headfont;// ���������С
    private static Font keyfont;// ���������С
    private static Font textfont;// ���������С

    static{
        //���ĸ�ʽ
        BaseFont bfChinese;
        try{
            // ����������ʾ
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            headfont = new Font(bfChinese, 10, Font.BOLD);// ���������С
            keyfont = new Font(bfChinese, 8, Font.BOLD);// ���������С
            textfont = new Font(bfChinese, 8, Font.NORMAL);// ���������С
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * �ĳ��ļ�
     * @param file �����ɵ��ļ���
     */
    public CreatePdf(File file){
        document.setPageSize(PageSize.A4);// ����ҳ���С
        try{
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public CreatePdf() {
        
    }
    
    public void initFile(File file){
        document.setPageSize(PageSize.A4);// ����ҳ���С
        try{
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    int maxWidth = 520;
    
    /**
     * Ϊ������һ������
     * @param value           ֵ
     * @param font            ����
     * @param align            ���뷽ʽ
     * @return                ��ӵ��ı���
     */
    public PdfPCell createCell(String value, Font font, int align){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    
    /**
     * Ϊ������һ������
     * @param value           ֵ
     * @param font            ����
     * @return                ��ӵ��ı���
     */
    public PdfPCell createCell(String value, Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * Ϊ������һ������
     * @param value           ֵ
     * @param font            ����
     * @param align            ���뷽ʽ
     * @param colspan        ռ������
     * @return                ��ӵ��ı���
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * Ϊ������һ������
     * @param value           ֵ
     * @param font            ����
     * @param align            ���뷽ʽ
     * @param colspan        ռ������
     * @param boderFlag        �Ƿ����б߿�
     * @return                ��ӵ��ı���
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan,
            boolean boderFlag){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag){
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }

    /**
     * ����һ��������
     * @param colNumber  ��������
     * @return              ���ɵı�����
     */
    public PdfPTable createTable(int colNumber){
        PdfPTable table = new PdfPTable(colNumber);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public PdfPTable createTable(float[] widths){
        PdfPTable table = new PdfPTable(widths);
        try{
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return table;
    }

    public PdfPTable createBlankTable(){
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }

    public <T> void generatePDF(String [] head,List<T> list,int colNum){
        Class classType = list.get(0).getClass();
        
        // ����һ��ֻ��5�еı��
        PdfPTable table = createTable(colNum);

        // ��ӱ�ע,���󣬲���ʾ�߿�
        table.addCell(createCell("APP��Ϣ�б�", keyfont, Element.ALIGN_LEFT, colNum,false));
        
        //���ñ�ͷ
        for(int i = 0 ; i < colNum ; i++){
            table.addCell(createCell(head[i], keyfont, Element.ALIGN_CENTER));
        }
        
        if(null != list && list.size() > 0){
            int size = list.size();
            for(int i = 0 ; i < size ; i++){
                T t = list.get(i);
                for(int j = 0 ; j < colNum ; j ++){
                    //�������ĸ
                    String firstLetter = head[j].substring(0,1).toUpperCase(); 
                    
                    //���get����,getName,getAge��
                    String getMethodName = "get" + firstLetter + head[j].substring(1);
                   
                    Method method;
                    try{
                        //ͨ����������Ӧ��get���������ڻ����Ӧ������ֵ
                        method = classType.getMethod(getMethodName, new Class[]{});
                        try{
                             System.out.print(getMethodName +":" + method.invoke(t, new Class[]{}) +",");
                             //�������
                             table.addCell(createCell(method.invoke(t, new Class[]{}).toString(), textfont));
                        }catch (IllegalArgumentException e){
                            e.printStackTrace();
                        }catch (IllegalAccessException e){
                            e.printStackTrace();
                        }catch (InvocationTargetException e){
                            e.printStackTrace();
                        }  
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }catch (NoSuchMethodException e){
                        e.printStackTrace();
                    }
                }
                
                System.out.println("");
            }
        }
        
        try{
            //�������ӵ��ĵ���
            document.add(table);
        }catch (DocumentException e){
            e.printStackTrace();
        }
        //�ر���
        document.close();
    }   
    
    /**
     * �ṩ�����õĽӿڣ�������headΪ��ͷ��listΪ���ݵ�pdf
     * @param head  //���ݱ�ͷ
     * @param list  //����
     * @return        //excel���ڵ�·��
     */
    public <T> String generatePDFs(String [] head,List<T> list){
        final String FilePath = "pdfPath";
        String saveFilePathAndName = "";
    
        //��ô洢�ĸ�Ŀ¼
        String savePath = new GetFilePlace().getFileDirFromProperties(FilePath);
        
        //��õ���洢��·��,�����������ɵ�����ļ���
        String realSavePath = new GenerateFold().getFold(savePath);
        
        saveFilePathAndName = new GenerateFileName().generateFileName(realSavePath,"pdf");
        
        File file = new File(saveFilePathAndName);
        try{
            file.createNewFile();
        }catch (IOException e1){
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            initFile(file);
        try{
            file.createNewFile();  //����һ��pdf�ļ�
        }catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new CreatePdf(file).generatePDF(head,list,head.length);
        
        return saveFilePathAndName;
    }

}