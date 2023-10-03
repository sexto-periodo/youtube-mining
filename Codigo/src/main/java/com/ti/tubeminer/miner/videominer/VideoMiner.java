package com.ti.tubeminer.miner.videominer;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.ti.tubeminer.enums.ProgrammingLanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;


@Service
public class VideoMiner {

    @Value("${application.enviroment.google.api_key}")
    private String DEVELOPER_KEY;

    @Value("${application.mining.programming_language}")
    private ProgrammingLanguageEnum PROGRAMMING_LANGUAGE;

    @Value("${application.mining.search_terms}")
    private String SEARCH_TERMS;
    
    @Autowired
    private VideoDataProcessor videoDataProcessor;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */

    public YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName("Teste")
                .build();
    }

    public void request()
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Search.List request = youtubeService.search()
                .list("snippet");
        SearchListResponse response = request.setKey(DEVELOPER_KEY)
                .setMaxResults(100L)
                .setQ("Python for beginners")
                .setType("video")
                .execute();

        System.out.println(response);

        videoDataProcessor.proccessResponse(response);
    }
}
