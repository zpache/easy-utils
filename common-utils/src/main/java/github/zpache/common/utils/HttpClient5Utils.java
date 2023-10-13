package github.zpache.common.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author: zpache
 * @createTime: 2023/7/11 15:47
 */
public class HttpClient5Utils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient5Utils.class);

    public static CloseableHttpClient httpClient;

    static {
        logger.info("httpclient5 init start");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(60))
                .setResponseTimeout(Timeout.ofSeconds(60))
                .build();
        try {
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(httpClientConnectionManager())
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            httpClient = HttpClients.createDefault();
            logger.error("httpclient5 init fail");
        }
        logger.info("httpclient5 init end");
    }

    private static HttpClientConnectionManager httpClientConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(30)
                .setMaxConnPerRoute(200)
                .setSSLSocketFactory(sslConnectionSocketFactory()).build();
    }

    private static SSLConnectionSocketFactory sslConnectionSocketFactory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy trustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, Object> headers, Map<String, Object> params) {
        String resultContent = null;
        HttpGet httpGet = new HttpGet(url);

        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(),entry.getValue());
            }
        }
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            try {
                URI uri = new URIBuilder(new URI(url)).addParameters(nameValuePairList).build();
                httpGet.setUri(uri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            resultContent = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("exec httpclient5 get method fail.{}", e.getMessage(), e);
        }
        return resultContent;
    }

    public static String postJson(String url, String jsonStr) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.error("exec httpclient5 post method fail.{}", e.getMessage(), e);
        }
        return result;
    }

    public static String postJson(String url, String jsonStr, Map<String, Object> headers) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                httpPost.setHeader(key, headers.get(key));
            }
        }
        httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            logger.error("exec httpclient5 post with header method fail.{}", e.getMessage(), e);
        }
        return result;
    }

}
