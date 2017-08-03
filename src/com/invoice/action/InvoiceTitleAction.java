package com.invoice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.invoice.entity.InvoiceTitle;
import com.invoice.service.InvoiceTitleService;
import com.invoice.util.HttpTool;

@Controller
@RequestMapping(value = "service/inviceTitle")
public class InvoiceTitleAction {
	protected final static Logger logger = LoggerFactory
			.getLogger(InvoiceTitleAction.class);

	@Autowired
	private InvoiceTitleService invoiceTitleService;

	@RequestMapping(value = "get", method = { RequestMethod.GET })
	public String list(HttpServletResponse response, HttpServletRequest request) {
		String openid = request.getParameter("openid");
		HttpTool.returnJson(response, JSON.toJSONString(invoiceTitleService.get(openid)));
		return null;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(HttpServletResponse response, HttpServletRequest request) {
		InvoiceTitle invoiceTitle = new InvoiceTitle();
		Integer result = invoiceTitleService.add(invoiceTitle);
		JSONObject json = new JSONObject();
		json.put("result", result.toString());
		HttpTool.returnJson(response, json.toJSONString());
		return null;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletResponse response,
			HttpServletRequest request) {
		InvoiceTitle invoiceTitle = new InvoiceTitle();
		Integer result = invoiceTitleService.update(invoiceTitle);
		JSONObject json = new JSONObject();
		json.put("result", result.toString());
		HttpTool.returnJson(response, json.toJSONString());
		return null;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(HttpServletResponse response,
			HttpServletRequest request) {
		String id = request.getParameter("id");
		Integer result = invoiceTitleService.delete(id);
		JSONObject json = new JSONObject();
		json.put("result", result.toString());
		HttpTool.returnJson(response, json.toJSONString());
		return null;
	}

	

}