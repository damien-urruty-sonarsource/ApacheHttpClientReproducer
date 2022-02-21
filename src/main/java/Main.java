import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.auth.SystemDefaultCredentialsProvider;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.util.Timeout;

public class Main {

  private static final Timeout CONNECTION_TIMEOUT = Timeout.ofSeconds(30);
  private static final Timeout RESPONSE_TIMEOUT = Timeout.ofMinutes(10);

  private static final CloseableHttpAsyncClient client = HttpAsyncClients.custom()
    .setConnectionManager(PoolingAsyncClientConnectionManagerBuilder.create().build())
    .setUserAgent("SonarLint IntelliJ")
    .setVersionPolicy(HttpVersionPolicy.FORCE_HTTP_1)
    .setDefaultCredentialsProvider(new SystemDefaultCredentialsProvider())
    .setDefaultRequestConfig(
      RequestConfig.copy(RequestConfig.DEFAULT)
        .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
        .setResponseTimeout(RESPONSE_TIMEOUT)
        .build()
    ).build();

  public static void main(String[] args) throws InterruptedException {
    client.start();
    System.out.println("Apache HTTP Client started, waiting forever...");
    Thread.currentThread().join();
  }
}
