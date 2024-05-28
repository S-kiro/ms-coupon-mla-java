package com.meli.mla.util;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Configuration
public class ConsumoGenericoUtil {

    public String consumoGenericoApi(String url, String condiciones) throws IOException, InterruptedException {

        URI uri = URI.create(url + condiciones);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
