package com.app.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	private static Log logger = LogFactory.getLog(HttpClientUtil.class);

	public static String getContent(String url, String encode) {
		return getContent(url, encode, null);
	}
	
	public static String getContent(String url, String encode, Map<String, String> header) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始抓取网页：" + url);
		}
		HttpGet post = new HttpGet( url );
		post.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.1) ");
		post.setHeader("Content-Type", "text/html;encode=" + encode);
		post.setHeader("Connection", "close");
		if(header != null) {
			for(Entry<String, String> entry : header.entrySet()) {
				post.setHeader(entry.getKey(), entry.getValue());
			}
		}
		return HttpClientUtil.getContent(HttpClientUtil.getInstance(null), post, null);
	}
	
	/**
	 * 以post方式抓取网页内容
	 * @param url
	 * @param params
	 * @return
	 * @throws BaseException 读取失败的话
	 */
	public static String postContent(String url, String encode, Map<String, String> params) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始抓取网页：" + url);
		}
		return postContent(url, encode, params, 10, null);
	}
	public static String postContent(String url, String encode, Map<String, String> params,Map<String, String> header) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始抓取网页：" + url);
		}
		return postContent(url, encode, params, 10, null,header);
	}
	public static String postContent(String url, String encode, String contentType, Map<String, String> params) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始抓取网页：" + url);
		}
		return postContent(url, encode, contentType, params, 10, null);
	}
	
	/**
	 * 以post方式抓取网页内容(带有cookie信息)
	 * @param url
	 * @param encode
	 * @param params
	 * @param connectionTimeoutSeconds
	 * @param request
	 * @return
	 */
	public static String postContent(String url, String encode, Map<String, String> params, int connectionTimeoutSeconds, CookieStore cookieStore) {
		return postContent(url, encode, "application/x-www-form-urlencoded", params, connectionTimeoutSeconds, cookieStore);
	}
	public static String postContent(String url, String encode, Map<String, String> params, int connectionTimeoutSeconds, CookieStore cookieStore,Map<String, String> header) {
		return postContent(url, encode, "application/x-www-form-urlencoded", params, connectionTimeoutSeconds, cookieStore,header);
	}
	/**
	 * 以post方式抓取网页内容(带有cookie信息)
	 * @param url
	 * @param encode
	 * @param contentType
	 * @param params
	 * @param connectionTimeoutSeconds
	 * @param request
	 * @return
	 */
	public static String postContent(String url, String encode, String contentType, Map<String, String> params, int connectionTimeoutSeconds, CookieStore cookieStore) {
		return postContent(url, encode, contentType, params, connectionTimeoutSeconds, cookieStore, null);
	}
	
	/**
	 * 以post方式抓取网页内容(带有cookie信息)
	 * @param url
	 * @param encode
	 * @param contentType
	 * @param params
	 * @param connectionTimeoutSeconds
	 * @param request
	 * @return
	 */
	public static String postContent(String url, 
			String encode, String contentType, Map<String, String> params, int connectionTimeoutSeconds, CookieStore cookieStore, Map<String, String> header) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始抓取网页：" + url);
		}
		HttpParams httparams= new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httparams, connectionTimeoutSeconds * 1000);
		HttpConnectionParams.setSoTimeout(httparams, connectionTimeoutSeconds * 1000);
		
		DefaultHttpClient client = getInstance(httparams);

		HttpContext httpContext = null;
		if(cookieStore != null) {
			client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			client.setCookieStore(cookieStore);
			
			httpContext = new BasicHttpContext();
			httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		}
		HttpPost post = new HttpPost( url );
		post.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.1) ");
		post.setHeader("Content-Type", contentType);
		post.setHeader("Connection", "close");
		if(header != null) {
			for(Entry<String, String> entry : header.entrySet()) {
				post.setHeader(entry.getKey(), entry.getValue());
			}
		}
		setParameters(post, params);
		
		return getContent(client, post, httpContext);
	}
	
	public static void setParameters(HttpPost post, Map<String, String> params) {
		if(params != null && !params.isEmpty()) {
			List<NameValuePair> clientParams = new ArrayList<NameValuePair>();
			for (Entry<String, String> param : params.entrySet()) {
				clientParams.add(new BasicNameValuePair( param.getKey() , param.getValue() ));
			}
			try {
				post.setEntity(new UrlEncodedFormEntity(clientParams, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.debug("编码失败", e);
			}
		}
	}
	
	
	public static String getContent(HttpClient client, HttpPost post, HttpContext httpContext) {
		String result="";
		try {
			HttpResponse response;
			if(httpContext != null) {
				response = client.execute(post, httpContext);
			}else{
				response = client.execute(post);
			}
			if(response.getStatusLine().getStatusCode() != 200) {
				post.abort();
				logger.error("抓取网页【"+ post.getURI() +"】内容错误：" + response.getStatusLine().getStatusCode());
				return null;
			}
			 result = EntityUtils.toString(response.getEntity());
			if(logger.isDebugEnabled()) {
				logger.debug("抓取网页内容：" + result);
			}
			EntityUtils.consume(response.getEntity());
			return result;
		} catch (Exception e) {
			post.abort();
			logger.debug("读取网页"+ post.getURI() +"出错"+e.getMessage());
		} finally{
			client.getConnectionManager().shutdown();
		}
		return result;
	}
	
	public static String getContent(HttpClient client, HttpGet get, HttpContext httpContext) {
		String result="";
		try {
			HttpResponse response;
			if(httpContext != null) {
				response = client.execute(get, httpContext);
			}else{
				response = client.execute(get);
			}
			if(response.getStatusLine().getStatusCode() != 200) {
				get.abort();
				logger.error("抓取网页【"+ get.getURI() +"】内容错误：" + response.getStatusLine().getStatusCode());
				return null;
			}
			 result = EntityUtils.toString(response.getEntity());
			if(logger.isDebugEnabled()) {
				logger.debug("抓取网页内容：" + result);
			}
			EntityUtils.consume(response.getEntity());
			return result;
		} catch (Exception e) {
			get.abort();
			logger.debug("读取网页"+ get.getURI() +"出错"+e.getMessage());
		} finally{
			client.getConnectionManager().shutdown();
		}
		return result;
	}

	public static DefaultHttpClient getInstance(HttpParams params) {
		DefaultHttpClient httpClient = new DefaultHttpClient(params);

		httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {                
			public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
				boolean isRedirect=false;
				try {
					isRedirect = super.isRedirected(request, response, context);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!isRedirect) {
					int responseCode = response.getStatusLine().getStatusCode();
					if (responseCode == HttpStatus.SC_MOVED_PERMANENTLY || responseCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						return true;
					}
				}
				return isRedirect;
			}
		});
		return httpClient;
	}

	public static void main(String[] args) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("auth_url", "http://www.lzxsoft.com:8082/datacenter/authentication/daxing.do");
		params.put("auth_key", "00000000000000000000");
		params.put("auth_logining", "1");
		params.put("sys_id", "1");
		params.put("auth_username", "zzzayj");
		params.put("auth_password", "Zzzayj751005");
		System.out.println(postContent("http://58.130.149.243:8081/AuthNewToThirdAction.a", "utf-8", params));
	}

}
