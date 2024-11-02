package org.example.services;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiClient {
    private static final String BASE_URL = "http://localhost:5000/predict";
    private final OkHttpClient client;

    public ApiClient() {
        this.client = new OkHttpClient();
    }

    public boolean isMutant(String[] secuencia) throws IOException {
        String json = "[{" +
                "\"secuencia\":" + convertToJson(secuencia) + "," +
                "\"etiqueta\":0" +
                "}]";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                return responseData.contains("\"etiqueta\":1");
            } else {
                throw new IOException("Código de respuesta inesperado: " + response);
            }
        }
    }

    private String convertToJson(String[] secuencia) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < secuencia.length; i++) {
            jsonBuilder.append("\"").append(secuencia[i]).append("\"");
            if (i < secuencia.length - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]");
        System.out.println(jsonBuilder);

        return jsonBuilder.toString();
    }
}
