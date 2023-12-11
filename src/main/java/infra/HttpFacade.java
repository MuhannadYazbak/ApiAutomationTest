package infra;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpFacade {
    private final CloseableHttpClient httpClient;

    public HttpFacade() {
        this.httpClient = HttpClients.createDefault();
    }

    public CloseableHttpResponse executeGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return httpClient.execute(httpGet);
    }

    public void close() throws IOException {
        httpClient.close();
    }
}
