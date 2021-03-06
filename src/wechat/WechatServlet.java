/**
 * @author chenwei
 *
 */
package wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;


public class WechatServlet {
    //获取微信返回的access_token
    private String getAccess_token() {
        String access_token=null;
        StringBuffer action =new StringBuffer();
        action.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential")
        .append("&appid=***************") //设置服务号的appid
        .append("&secret=*********************************"); //设置服务号的密匙
        URL url;
        try {
        	//模拟get请求
            url = new URL(action.toString());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();        
            http.setRequestMethod("GET");        
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoInput(true);
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] buf=new byte[size];
            is.read(buf);
            String resp =new String(buf,"UTF-8");
            JSONObject json = JSONObject.fromObject(resp);
            Object object =json.get("access_token");
            if(object !=null){
                  access_token =String.valueOf(object);
            }
            System.out.println("getAccess_token:"+access_token);
            return access_token;
        } catch (MalformedURLException e) {
            e.printStackTrace();
             return access_token;
             
        } catch (IOException e) {
            e.printStackTrace();
             return access_token;
         
        }
    }
    //获取该服务号下的用户组
    public JSONArray getOpenids(){
        JSONArray array =null;
        String urlstr ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        urlstr =urlstr.replace("ACCESS_TOKEN", getAccess_token());
        urlstr =urlstr.replace("NEXT_OPENID", "");
        URL url;
        try {
            url = new URL(urlstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();        
            http.setRequestMethod("GET");        
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoInput(true);
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] buf=new byte[size];
            is.read(buf);
            String resp =new String(buf,"UTF-8");
            JSONObject jsonObject =JSONObject.fromObject(resp);
            System.out.println("getOpenids:"+jsonObject.toString());
            array =jsonObject.getJSONObject("data").getJSONArray("openid");
            return array;
        } catch (MalformedURLException e) {
            e.printStackTrace();
             return array;
             
        } catch (IOException e) {
            e.printStackTrace();
             return array;
        }
    }
    //根据用户组的openId获取用户详细数据
    public JSONObject getUserOpenids(String openId){
        String urlstr ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        urlstr =urlstr.replace("ACCESS_TOKEN", getAccess_token());
        urlstr =urlstr.replace("OPENID", openId);
        urlstr =urlstr.replace("NEXT_OPENID", "");
        URL url;
        try {
            url = new URL(urlstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();        
            http.setRequestMethod("GET");        
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            http.setDoInput(true);
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] buf=new byte[size];
            is.read(buf);
            String resp =new String(buf,"UTF-8");
            JSONObject jsonObject =JSONObject.fromObject(resp);
            System.out.println("getUserOpenids:"+jsonObject.toString());
            return jsonObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
             return null;
             
        } catch (IOException e) {
            e.printStackTrace();
             return null;
        }
    }
    //主方法,只有服务号才能使用客服图文消息,实现点击图文链接,直接跳转到指定URL
    @Test
    public void testsendTextByOpenids(){
        //String urlstr ="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; //群发图文消息
        String urlstr ="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; //发送客服图文消息
        urlstr =urlstr.replace("ACCESS_TOKEN", getAccess_token());
        String reqjson =createGroupText(getOpenids());
        try {
            URL httpclient =new URL(urlstr);
            HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
            conn.setDoOutput(true);        
            conn.setDoInput(true);
            conn.connect();
            OutputStream os= conn.getOutputStream();    
            System.out.println("ccccc:"+reqjson);
            os.write(reqjson.getBytes("UTF-8"));//传入参数    
            os.flush();
            os.close();
            
            InputStream is =conn.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            String message=new String(jsonBytes,"UTF-8");
            System.out.println("testsendTextByOpenids:"+message);
         
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    //创建发送的数据
    private String createGroupText(JSONArray array){
         JSONObject gjson =new JSONObject();
         //JSONObject json = getUserOpenids(array.get(3).toString()); //array参数是用户组所有的用户,该方法打印array其中一个用户的详细信息
         gjson.put("touser", array.get(3));
         gjson.put("msgtype", "news");
         JSONObject news =new JSONObject();
         JSONArray articles =new JSONArray();
         JSONObject list =new JSONObject();
         list.put("title","title"); //标题
         list.put("description","description"); //描述
         list.put("url","http://"); //点击图文链接跳转的地址
         list.put("picurl","http://"); //图文链接的图片
         articles.add(list);
         news.put("articles", articles);
         JSONObject text =new JSONObject();
         gjson.put("text", text);
         gjson.put("news", news);
         
        return gjson.toString();
    }
}