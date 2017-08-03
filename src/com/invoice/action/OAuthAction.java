package com.invoice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.invoice.util.Constants;
import com.invoice.util.HttpTool;
import com.invoice.util.PropertyUtil;

@Controller
@RequestMapping(value = "service/oauth")
public class OAuthAction {
	protected final static Logger logger = LoggerFactory.getLogger(OAuthAction.class);
    
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String getOpenid(HttpServletResponse response, HttpServletRequest request) {
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
				+ "?appid=" + PropertyUtil.getProperty(Constants.APPID)
				+ "&secret=" + PropertyUtil.getProperty(Constants.APPSECRET)
				+ "&code=" +code + "&grant_type=authorization_code";
		String result;
		String openId = "";
		try {
			result = HttpTool.get(url);
			JSONObject json1 = JSONObject.parseObject(result);
			openId = json1.getString("openid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("openId", openId);
		HttpTool.returnJson(response, json.toJSONString());
		return null;
	}

}