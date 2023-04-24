package com.dplot.common.service.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaverCaptchaService {
	
	/*@Resource(name="propertiesFactory")
	private Properties prop;*/
	
	private static final Logger logger = LoggerFactory.getLogger(NaverCaptchaService.class);
	
	String clientId = "IuiI7XI6HTs7h9AyZk4k";//prop.getProperty("naver.openapi.clientId"); //애플리케이션 클라이언트 아이디값";
	String clientSecret = "D0IkefF7mn";//prop.getProperty("naver.openapi.clientSecret"); //애플리케이션 클라이언트 시크릿값";
	
	public String captchaNkey(String type) {
		String result = null;
		try {
			
			String keyType = "sound".equals(type) ? "skey" : "nkey"; // sound일 경우 skey, image일 경우 nkey
			String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
			String apiURL = "https://openapi.naver.com/v1/captcha/"+keyType+"?code=" + code;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer sb = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			result = sb.toString().substring(8, 8 + 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @param key 키
	 * @param type image(null일 경우) 또는 sound
	 * @return
	 */
	public String getCaptcha(String type, String key) {
		logger.info("getCapchar === {}, {}", key, type);
		String result = null;
		try {
			
			String mediaType = "image/jpg";
			String capType = "ncaptcha.bin";
			
			if (type != null && "sound".equals(type)) {
				capType = "scaptcha";
				mediaType = "audio/wav";
			}
			
			//String key = "CAPTCHA_KEY"; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
			String apiURL = "https://openapi.naver.com/v1/captcha/"+capType+"?key=" + key;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				InputStream is = con.getInputStream();
				String base64file = IOUtils.toString(new Base64InputStream(is, true, 0, null));
				result = "data:" + mediaType + ";base64," + base64file;
				is.close();
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer sb = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				br.close();
				logger.info(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String captchaNkeyResult(String type, String key, String value) {
		logger.info("captchaNkeyResult === {}, {}", key, value);
		
		String result = null;
		try {
			
			String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
			String keyType = "sound".equals(type) ? "skey" : "nkey"; // sound일 경우 skey, image일 경우 nkey
			
			String apiURL = "https://openapi.naver.com/v1/captcha/"+keyType+"?code=" + code + "&key=" + key + "&value="+ value;

			logger.info("captchaNkeyResult.apiURL === {}", apiURL);
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer sb = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();
			logger.info(sb.toString());
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(sb.toString());
			result = json != null && json.get("result") != null ? json.get("result").toString() : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
