package com.invoice.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpTool {
	private static Logger logger = Logger.getLogger(HttpTool.class);
	private static int ConnectTimeout = 10000;
	private static int ConnectionRequestTimeout = 10000;
	private static int SocketTimeout = 10000;

	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	static {
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
	}
	private static CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();

	/**
	 * http post send request body
	 * 
	 * @param url
	 * @param entityStr
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String entityStr) throws Exception {
		HttpPost post = new HttpPost(url);
		config(post);
		StringEntity entity = new StringEntity(entityStr, Consts.UTF_8);
		post.setEntity(entity);
		logger.info("POST:" + url + "\r\nBODY:" + entityStr);
		return execute(post);
	}

	/**
	 * http get
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		HttpGet get = new HttpGet(url);
		config(get);
		logger.info("GET[" + url + "]");
		return execute(get);
	}

	/**
	 * set request header
	 * 
	 * @param request
	 * @throws Exception
	 */
	private static void config(HttpRequestBase request) {
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.CONTENTTYPE_JSON);
		request.setHeader(HttpHeaders.ACCEPT, Constants.CONTENTTYPE_JSON);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(ConnectTimeout)
				.setConnectionRequestTimeout(ConnectionRequestTimeout).setSocketTimeout(SocketTimeout).build();
		request.setConfig(requestConfig);
	}

	/**
	 * 执行http请求并获取结果
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static String execute(HttpRequestBase request) throws Exception {
		/*
		 * CloseableHttpResponse response = client.execute(request); return
		 * getResult(response);
		 */
		// CloseableHttpClient client = HttpClients.createDefault();

		try {
			CloseableHttpResponse response = client.execute(request);
			return getResult(response);
		} finally {
			/*
			 * try { client.close(); } catch (IOException e) { logger.error(
			 * "close http client failed!", e); }
			 */
		}
	}

	/**
	 * 根据response获取结果
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private static String getResult(CloseableHttpResponse response) throws Exception {
		String result = "";
		try {
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (entity != null) {
				result = EntityUtils.toString(entity, Consts.UTF_8);
				logger.debug("RESULT:" + result);
			}
			if (statusCode != HttpStatus.SC_OK) {
				throw new HttpException("send to API failed! StatusCode:" + statusCode);
			}
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("close response failed!", e);
			}
		}
		return result;
	}

	/**
	 * 访问api失败后设置返回给前端的状态码
	 * 
	 * @param response
	 */
	public static void error(HttpServletResponse response) {
		response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}

	
	public static void returnJson(HttpServletResponse response, String json) {
		try {
			response.setContentType(Constants.CONTENTTYPE_JSON);
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
	}

}
