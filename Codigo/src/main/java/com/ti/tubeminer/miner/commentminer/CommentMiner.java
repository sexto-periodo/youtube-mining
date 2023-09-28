package com.ti.tubeminer.miner.commentminer;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.video.Video;
import com.ti.tubeminer.video.VideoService;
import com.ti.tubeminer.youtubelistresponse.YouTubeListResponse;
import com.ti.tubeminer.youtubelistresponse.YouTubeListResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


@Service
public class CommentMiner {

    @Value("${application.enviroment.google.api_key}")
    private String DEVELOPER_KEY;

    @Autowired
    private ComentDataProcessor comentDataProcessor;

    @Autowired
    private VideoService videoService;

    @Autowired
    private YouTubeListResponseService youTubeListResponseService;

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

    private void mineCommentsFromVideo(Video video)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {

        YouTube youtubeService = getService();

        YouTube.CommentThreads.List request = youtubeService.commentThreads().list("snippet, id");

        CommentThreadListResponse firstResponse = request.setKey(DEVELOPER_KEY)
                .setVideoId(video.getVideoId())
                .setMaxResults(100L)
                .execute();
        comentDataProcessor.proccessResponse(firstResponse, video.getVideoId());

        for (int  i = 0 ; i < 1 ; i++){
            YouTubeListResponse lastResponse = youTubeListResponseService.findLatestByContentType(ContentTypeEnum.COMMENT);
            CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                    .setPageToken(lastResponse.getNextPageToken())
                    .setVideoId(video.getVideoId())
                    .setMaxResults(100L)
                    .execute();
            comentDataProcessor.proccessResponse(response, video.getVideoId());
        }
    }

    public void mineComments() {
        List<Video> videos = videoService.findLatestVideosWithUnminedComments();
        videos.parallelStream().forEach(video -> {
            try {
                mineCommentsFromVideo(video);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
