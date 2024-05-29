package com.meli.mla.util;

import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Configuration
public class ConsumoGenericoUtil {

    private final String className = getClass().getName();

    public String consumoGenericoApi(String url, String condiciones) throws MsCouponMlaException {

        URI uri = URI.create(url + condiciones);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            if (e.getClass() == InterruptedException.class) {
                Thread.currentThread().interrupt();
            }
            throw new MsCouponMlaException("Error creating connection with api: " + className,
                new ExceptionDTO("Ocurrio un error inesperado", "CONNECTION"),
                e);
        }
        return response.body();
    }
}
