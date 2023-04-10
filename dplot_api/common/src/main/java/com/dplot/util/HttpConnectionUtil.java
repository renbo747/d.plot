package com.dplot.util;

import com.dplot.common.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionUtil.class);

    public static Map<String, Object> httpRequest(String url, String method, Map<String,Object> param) {
        return httpRequest(url, method, param,null);
    }

    public static Map<String, Object> httpRequest(String url, String method, Map<String,Object> param, Map<String, Object> header) {
        Map<String, Object> resultMap = null;
        try {

            URL target = new URL(url);
            URLConnection urlConn = target.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) urlConn;
            httpConnection.setRequestMethod(method);
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setDefaultUseCaches(false);

            if(header == null) {
                httpConnection.setRequestProperty("Content-Type", "application/json");
            } else {
                for(String key : header.keySet()){
                    httpConnection.setRequestProperty(key, header.get(key).toString());
                }
            }

            if(method.equalsIgnoreCase("POST") && param != null && !param.isEmpty()){
                JSONObject json = new JSONObject();
                json.putAll(param);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream(), StandardCharsets.UTF_8));
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }

            if(Status.OK.getKey() == httpConnection.getResponseCode() || Status.CREATED.getKey() == httpConnection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    builder.append(inputLine);
                }
                resultMap = new ObjectMapper().readValue(builder.toString(), HashMap.class);
                in.close();
            } else {
                resultMap = new HashMap<>();
            }
            resultMap.put("httpCode", httpConnection.getResponseCode());
            resultMap.put("httpMessage", httpConnection.getResponseMessage());

            httpConnection.disconnect();

        } catch(Exception e){
            e.printStackTrace();
            logger.debug(e.getMessage());
        }

        return resultMap;
    }

    public static Map<String, Object> httpsRequest(String url, String method, Map<String,Object> param, Map<String, Object> header) {
        Map<String, Object> resultMap = null;
        try {

            if(url.startsWith("https")){
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            }

            URL target = new URL(url);
            URLConnection urlConn = target.openConnection();
            HttpsURLConnection httpConnection = (HttpsURLConnection) urlConn;

            httpConnection.setRequestMethod(method);
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setDefaultUseCaches(false);

            if(header == null) {
                httpConnection.setRequestProperty("Content-Type", "application/json");
            } else {
                for(String key : header.keySet()){
                    httpConnection.setRequestProperty(key, header.get(key).toString());
                }
            }

            if(method.equalsIgnoreCase("POST") && param != null && !param.isEmpty()){
                if(httpConnection.getRequestProperty("Content-Type").contains("application/json")){
                    JSONObject json = new JSONObject();
                    json.putAll(param);

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream(), StandardCharsets.UTF_8));
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();
                } else if("application/x-www-form-urlencoded".equals(httpConnection.getRequestProperty("Content-Type"))) {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream(), StandardCharsets.UTF_8));
                    for (String key : param.keySet()) {
                        writer.write(String.format("%s=%s", key, param.get(key)));
                        writer.write("&");
                    }
                    writer.flush();
                    writer.close();
                }
            }

            if(Status.OK.getKey() == httpConnection.getResponseCode() || Status.CREATED.getKey() == httpConnection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    builder.append(inputLine);
                }
                resultMap = new ObjectMapper().readValue(builder.toString(), HashMap.class);
                in.close();

            } else {
                resultMap = new HashMap<>();
            }

            resultMap.put("httpCode", httpConnection.getResponseCode());
            resultMap.put("httpMessage", httpConnection.getResponseMessage());

            httpConnection.disconnect();

        } catch(Exception e){
            e.printStackTrace();
            logger.debug(e.getMessage());
        }

        return resultMap;
    }
}
