package cn.jcm.core.configuration.http;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.security.cert.X509Certificate;

/**
 * 配置忽略 HTTPS 认证。
 *
 * @author changming.jiang
 */
public class CustomClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
        try {
            if (!(connection instanceof HttpsURLConnection)) {
                super.prepareConnection(connection, httpMethod);
            } else {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }

                            public void checkClientTrusted(X509Certificate[] certs, String
                                    authType) {
                            }

                            public void checkServerTrusted(X509Certificate[] certs, String
                                    authType) {
                            }
                        }
                }, new java.security.SecureRandom());
                httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());

                httpsConnection.setHostnameVerifier((hostname, session) -> true);

                super.prepareConnection(httpsConnection, httpMethod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
