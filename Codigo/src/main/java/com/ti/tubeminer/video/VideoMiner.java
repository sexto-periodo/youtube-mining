package com.ti.tubeminer.video;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;


@Service
public class VideoMiner {
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    @Value("${application.enviroment.google.api_key}")
    private String DEVELOPER_KEY;

    public void request() throws Exception {
// Inicialize o serviço do YouTube
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        YouTube youtube = new YouTube.Builder(httpTransport, new JsonObjectParser())
                .setApplicationName("YourApplicationName")
                .build();

        // Crie a solicitação para a YouTube Data API
        YouTube.Search.List search = youtube.search().list("snippet");
        search.setKey("YOUR_API_KEY"); // Substitua pelo seu próprio API Key
        search.setMaxResults(100L);
        search.setQ("Python for beginners");

        // Execute a solicitação e obtenha a resposta
        SearchListResponse response = search.execute();

        // Imprima a resposta (você pode fazer o que quiser com os resultados aqui)
        System.out.println(response.toPrettyString());
    }
}
