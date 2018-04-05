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
