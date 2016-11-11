package com.demo.springboot.web.https;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类 名: HttpsClientUtil<br/>
 * 描 述: Https请求工具类<br/>
 * 作 者: libin<br/>
 * 创 建： 2016-10-12<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
//@Component
public class HttpsClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpsClientUtil.class);

	private static CloseableHttpClient httpClient = null;

	private static HttpClientBuilder builder = HttpClientBuilder.create();

	// @Value("${https.password}")
	private String password = "123456";

	// @Value("${https.keyStorePath}")
	private String keyStorePath = "client.keystore";

	// @Value("${https.trustStorePath}")
	private String trustStorePath = "client.truststore";

	//@Value("${game.random.da.url}")
	private String url = "https://hlcdn.uuzz.com/game-random/getRandom";

	private static int MAXTOTAL = 500;

	/**
	 * 是否跳过证书验证
	 */
	//@Value("${game.random.skipCer}")
	private boolean skipCer = false;

	/**
	 * 
	 * 描 述：获得SSLSocketFactory.<br/>
	 * 作 者：libin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param password
	 *            密码
	 * @param keyStorePath
	 *            密钥库路径
	 * @param trustStorePath
	 *            信任库路径
	 * @return SSLSocketFactory 返回
	 * @throws Exception 异常
	 */
	private HttpClientConnectionManager getConnectionManager(String password, String keyStorePath, String trustStorePath)
			throws Exception {
		//
		// 实例化SSL上下文
		SSLContext ctx = SSLContext.getInstance("TLS");
		if (skipCer) {
			// 设置是否绕过服务端证书验证
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
		} else {
			// 实例化密钥库
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// 获得密钥库
			KeyStore keyStore = getKeyStore(password, keyStorePath);
			// 初始化密钥工厂
			keyManagerFactory.init(keyStore, password.toCharArray());

			// 实例化信任库
			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			// 获得信任库
			KeyStore trustStore = getKeyStore(password, trustStorePath);
			// 初始化信任库
			trustManagerFactory.init(trustStore);
			// 初始化SSL上下文
			ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
		}

		// 获得SSLSocketFactory
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new NoopHostnameVerifier());

		Registry<ConnectionSocketFactory> register = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(register);
		connectionManager.setMaxTotal(HttpsClientUtil.MAXTOTAL);
		connectionManager.setDefaultMaxPerRoute(connectionManager.getMaxTotal());
		return connectionManager;
	}

	/**
	 * 获得KeyStore.
	 * @param password 密码
	 * @param keyStorePath 密钥库路径
	 * @return 密钥库
	 * @throws Exception 异常
	 */
	private KeyStore getKeyStore(String password, String keyStorePath) throws Exception {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance("JKS");
		// 获得密钥库文件流
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
		try {
			// 加载密钥库
			ks.load(is, password.toCharArray());
		} catch (Exception e) {
			log.error("获得KeyStore异常", e);
			throw e;
		} finally {
			try {
				// 关闭密钥库文件流
				is.close();
			} catch (Exception e2) {
				log.error("关闭流异常", e2);
			}
		}
		return ks;
	}

	/**
	 * 发送https请求
	 * @param min 最小值
	 * @param max 最大值
	 * @param num 获取随机数个数
	 * @param size 获取随机数组数
	 * @param is_repeat 是否可以重复
	 * @return 随机数数据
	 */
	public List<int[]> httpsPost(int min, int max, int num, int size, boolean is_repeat) {
		CloseableHttpResponse httpResponse = null;
		List<int[]> resultList = null;
		try {
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).setTcpNoDelay(false)
					.setRcvBufSize(10000).setSndBufSize(10000).build();

			// set request config
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
					.setConnectionRequestTimeout(10000).build();

			builder.setDefaultSocketConfig(socketConfig).setDefaultRequestConfig(requestConfig).build();

			HttpClientConnectionManager connectionManager = getConnectionManager(password, keyStorePath,
					trustStorePath);

			builder.setConnectionManager(connectionManager);
			httpClient = builder.build();

			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<>();
			parameters.add(new BasicNameValuePair("min", String.valueOf(min)));
			parameters.add(new BasicNameValuePair("max", String.valueOf(max)));
			parameters.add(new BasicNameValuePair("num", String.valueOf(num)));
			parameters.add(new BasicNameValuePair("size", String.valueOf(size)));
			parameters.add(new BasicNameValuePair("is_repeat", String.valueOf(is_repeat)));
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);

			httpResponse = httpClient.execute(httpPost);
			int status = httpResponse.getStatusLine().getStatusCode();
			if (status == 200) {
				String resp = EntityUtils.toString(httpResponse.getEntity());
				log.info("随机数服务返回信息：" + resp);
				resultList = JSON.parseArray(resp, int[].class);
			} else {
				log.info("随机数服务返回非正常状态status=" + status);
			}
		} catch (Exception e) {
			log.error("随机数服务异常", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					log.error("关闭httpResponse异常", e);
				}
			}

		}
		return resultList;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		HttpsClientUtil.httpClient = httpClient;
	}

	public static void main(String[] args) throws Exception{
		HttpsClientUtil util = new HttpsClientUtil();
		List<int[]> list = util.httpsPost(1, 100, 12, 3, false);
		if (list != null) {
			list.forEach(p ->System.out.println(Arrays.toString(p)));
		}
		KeyStore ks = KeyStore.getInstance("JKS");
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("client.keystore"));

	}
}
