package scau.oop.wechat.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * @author:czfshine
 * @date:2018/3/13 23:14
 */

public class Http {

    public  static String waitPost(String urls, Map<String,Object> params){
        String urlParameters = getParamsString(params);
        URL url;
        HttpURLConnection connection = null;
        try {

            url = new URL(urls);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();


            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;




            StringBuffer response = new StringBuffer();
            while(response.length()<=0){
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                sleep(10);
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    //用来发送json
    public  static String sendPost(String url,String Params)throws IOException{
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        String response="";
        try {
            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
            //创建URL
            httpUrl = new URL(url);
            //建立连接
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setUseCaches(false);//设置不要缓存
            conn.setInstanceFollowRedirects(true);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //POST请求
            out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.write(Params);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                response+=lines;
            }
            reader.close();
            // 断开连接
            conn.disconnect();

            //log.info(response.toString());
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return response;
    }

    private static String getParamsString(Map<String, Object> params) {
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sbParams.append(e.getKey());
                sbParams.append("=");
                sbParams.append(e.getValue());
                sbParams.append("&");
            }
        }
        return sbParams.toString();
    }

    public static String post(String urls, Map<String,Object> params){
        String urlParameters = getParamsString(params);
        URL url;
        HttpURLConnection connection = null;
        try {

            url = new URL(urls);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();


            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    public static void downloadFile(String urls,String filepath){
        int bytesum = 0;
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(urls);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return ;
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(filepath);

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            return ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ;
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

}
