package movefile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class moveFile{
    public static void listfile(File file,Map<String,String> map){
    //如果file代表的不是一个文件，而是一个目录
        if(!file.isFile()){
            //列出该目录下的所有文件和目录
            File files[] = file.listFiles();
            //遍历files[]数组
            for(File f : files){
                //递归
                listfile(f,map);
            }
        }else{
            String realName = file.getName();
            map.put(file.toString(), realName);
        }
    }
    public static void display(Map<String,String> map) throws IOException{
        Iterator it2=map.keySet().iterator();
        while(it2.hasNext()){
            Object key = it2.next();
            String value = map.get(key);
            File f=new File(key.toString());
            if(!f.isDirectory()){
                String s="C:/java_eclipsnew/Tiqu_file/temp/"+value;
                copyFile(key.toString(),s);
            }
        }
    }
     public static void copyFile(String src,String dest) throws IOException{
         FileInputStream in=new FileInputStream(src);
         File file=new File(dest);
         if(!file.exists())
             file.createNewFile();
         FileOutputStream out=new FileOutputStream(file);
         int c;
         byte buffer[]=new byte[1024];
         while((c=in.read(buffer))!=-1){
             for(int i=0;i<c;i++)
                 out.write(buffer[i]);        
         }
         in.close();
         out.close();
     }
     public static void main(String[] args) throws IOException {
         String uploadFilePath = "C:/java_eclipsnew/Tiqu_file/temp";
            Map<String,String> fileNameMap = new HashMap<String,String>();
            listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
            display(fileNameMap);
    }
    
}