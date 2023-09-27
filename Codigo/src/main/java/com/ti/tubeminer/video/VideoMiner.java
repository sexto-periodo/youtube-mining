package com.ti.tubeminer.video;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.ti.tubeminer.pageinfo.PageInfo;
import com.ti.tubeminer.pageinfo.PageInfoService;
import com.ti.tubeminer.searchlistresponse.LocalSearchListResponse;
import com.ti.tubeminer.searchlistresponse.LocalSearchListResponseService;
import com.ti.tubeminer.snippet.Snippet;
import com.ti.tubeminer.snippet.SnippetService;
import com.ti.tubeminer.youtubeid.YouTubeId;
import com.ti.tubeminer.youtubeid.YouTubeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;


@Service
public class VideoMiner {

    @Value("${application.enviroment.google.api_key}")
    private String DEVELOPER_KEY;


    @Autowired
    private LocalSearchListResponseService localSearchListResponseService;

    @Autowired
    private SnippetService snippetService;

    @Autowired
    private PageInfoService pageInfoService;

    @Autowired
    private YouTubeIdService youTubeIdService;

    @Autowired
    private VideoService videoService;

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
                .execute();

        System.out.println(response);

        proccessResponse(response);
    }

    private void proccessResponse(SearchListResponse response) {

        PageInfo pageInfo = PageInfo.builder()
                .resultsPerPage(response.getPageInfo().getResultsPerPage())
                .totalResults(response.getPageInfo().getTotalResults())
                .build();
        PageInfo savedPageInfo = pageInfoService.save(pageInfo);

        LocalSearchListResponse localSearchListResponse = LocalSearchListResponse.builder()
                .etag(response.getEtag())
                .eventId(response.getEventId())
                .kind(response.getKind())
                .nextPageToken(response.getNextPageToken())
                .prevPageToken(response.getPrevPageToken())
                .visitorId(response.getVisitorId())
                .regionCode(response.getRegionCode())
                .pageInfo(savedPageInfo)
                .build();

        LocalSearchListResponse savedLocalSearchListResponse = localSearchListResponseService.save(localSearchListResponse);

        response.getItems().forEach(responseVideo -> {

            Snippet snippet = Snippet.builder()
                    .channelId(responseVideo.getSnippet().getChannelId())
                    .description(responseVideo.getSnippet().getDescription())
                    .channelTitle(responseVideo.getSnippet().getChannelTitle())
                    .title(responseVideo.getSnippet().getTitle())
                    .publishedAt(responseVideo.getSnippet().getPublishedAt())
                    .build();

            Snippet savedSnippet = snippetService.save(snippet);

            YouTubeId youTubeId = YouTubeId.builder()
                    .videoId(responseVideo.getId().getVideoId())
                    .kind(responseVideo.getId().getKind())
                    .build();

            YouTubeId savedYouTubeId = youTubeIdService.save(youTubeId);

            Video video = Video.builder()
                    .etag(responseVideo.getEtag())
                    .kind(responseVideo.getKind())
                    .snippet(snippet)
                    .youTubeId(youTubeId)
                    .build();

            Video savedVideo = videoService.save(video);
        });


    }
}
