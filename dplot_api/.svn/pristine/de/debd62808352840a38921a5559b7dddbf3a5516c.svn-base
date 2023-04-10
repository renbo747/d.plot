package com.dplot.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dplot.common.SOMap;
import com.dplot.common.Status;
import com.dplot.common.service.InstagramFeedService;
import com.dplot.common.service.util.MallBaseService;
import com.dplot.exception.CustomException;
import com.dplot.mapper.ConfigMapper;
import com.dplot.mapper.InstaFeedMapper;
import com.dplot.util.HttpConnectionUtil;

@Service
public class InstagramFeedServiceImpl extends MallBaseService implements InstagramFeedService {

	@Autowired
	private InstaFeedMapper instaFeedMapper;
	
	@Autowired
	private ConfigMapper configMapper;

	@Resource(name="propertiesFactory")
	private Properties prop;

	@Override
	public SOMap selectInstagramFeed(SOMap param) throws Exception {

		SOMap result = new SOMap();

		String instaClientId = prop.getProperty("instagram.client.id");
		String instaRedirectURI = prop.getProperty("instagram.redirect.uri");
		String instaUserId = prop.getProperty("instagram.user.id");
		String instaGetFeedURL = prop.getProperty("instagram.feed.url");
		String instaGetCodeURL = prop.getProperty("instagram.code.url");

		String fields = "id,media_type,media_url,permalink,thumbnail_url,username,caption,timestamp";
		
		param.put("siteid", cs.getStr("siteid"));
		String instatoken = configMapper.selectConfigForInsta(param).getStr("instatoken");
		//since 기간시작일, until 기간종료일 : 기간검색 파라미터
		String since = (param.get("since") == null) ? "" : param.get("since").toString();
		String until = (param.get("until") == null) ? "" : param.get("until").toString();
		String url = (param.get("next") == null &&  !"".equals(param.get("next")) ) ? String.format("%s/%s/media?fields=%s&access_token=%s&since=%s&until=%s", instaGetFeedURL, instaUserId, fields, instatoken, since, until) : param.get("next").toString();
		Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(url, "GET", null, null);

		if(httpResult != null && Status.OK.getKey() == Integer.parseInt(httpResult.get("httpCode").toString())){
			result.put("feedlist", httpResult.get("data"));
			result.put("paging", httpResult.get("paging"));
			result.put("code", httpResult.get("httpCode"));
			result.put("message", httpResult.get("httpMessage"));
		} else if(httpResult != null && Status.OK.getKey() != Integer.parseInt(httpResult.get("httpCode").toString())){
			String codeUrl = String.format("%s/?client_id=%s&redirect_uri=%s&scope=user_profile,user_media&response_type=code", instaGetCodeURL, instaClientId, instaRedirectURI);
			result.put("openurl", codeUrl);
			result.put("code", httpResult.get("httpCode"));
			result.put("message", httpResult.get("httpMessage"));
		}

		return result;
	}

	@Override
	public SOMap selectMainFeed(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));

		List<SOMap> list = instaFeedMapper.selectInstaFeed(param);
		String fields = "id,media_url";
		String instaGetFeedURL = prop.getProperty("instagram.feed.url");

		for (int i = 0; i < list.size(); i++) {
			String url = String.format("%s/%s?fields=%s&access_token=%s", instaGetFeedURL, list.get(i).getStr("id"), fields, cs.getStr("instatoken"));
			Map<String, Object> httpResult = HttpConnectionUtil.httpsRequest(url, "GET", null, null);
			int code = (int) httpResult.get("httpCode");
			if (200 == code) {
				list.get(i).put("media_url", httpResult.get("media_url"));
				list.get(i).put("isdel", "F");
			}else { 
				list.get(i).put("isdel", "T");
			}
		}
		result.put("list", list);
		return result;
	}

	@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public SOMap insertMainFeed(SOMap param) throws Exception {
		SOMap result = new SOMap();
		param.put("siteid", cs.getStr("siteid"));
		
		try{
			List<Map<String, Object>> saveDataList = (List<Map<String, Object>>) param.get("data");
			List<Map<String, Object>> removeDataList = (List<Map<String, Object>>) param.get("removedata");
			
			saveDataList.forEach(map -> {
				Map<String, Object> data = new HashMap<>();
				data.putAll(map);
				data.put("siteid", param.get("siteid"));
				data.put("userid", param.get("userid"));
				if(map.containsKey("instaidx")){
					instaFeedMapper.updateInstaFeed(data);
				}else{
					instaFeedMapper.insertInstaFeed(data);
				}
			});
			if(removeDataList.size() != 0){
				instaFeedMapper.removeInstaFeed(param);			
			}
		} catch(Exception e) {
			throw new CustomException("인스타피드 저장시 문제가 발생했습니다. 관리자에게 문의해주세요.");
		}
		
		return result;
	}
}
