package com.ti.tubeminer.miner.videominer;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.enums.KindEnum;
import com.ti.tubeminer.enums.ProgrammingLanguageEnum;
import com.ti.tubeminer.pageinfo.PageInfo;
import com.ti.tubeminer.pageinfo.PageInfoService;
import com.ti.tubeminer.video.Video;
import com.ti.tubeminer.video.VideoService;
import com.ti.tubeminer.youtubelistresponse.YouTubeListResponse;
import com.ti.tubeminer.youtubelistresponse.YouTubeListResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoDataProcessor {

    @Autowired
    private YouTubeListResponseService youTubeListResponseService;

    @Autowired
    private PageInfoService pageInfoService;

    @Autowired
    private VideoService videoService;

    @Value("${application.mining.programming_language}")
    private ProgrammingLanguageEnum PROGRAMMING_LANGUAGE;

    private ContentTypeEnum CONTENT_TYPE = ContentTypeEnum.VIDEO;

    public void proccessResponse(SearchListResponse response) {

        PageInfo pageInfo = PageInfo.builder()
                .resultsPerPage(response.getPageInfo().getResultsPerPage())
                .totalResults(response.getPageInfo().getTotalResults())
                .build();
        PageInfo savedPageInfo = pageInfoService.save(pageInfo);

        YouTubeListResponse youTubeListResponse = YouTubeListResponse.builder()
                .etag(response.getEtag())
                .eventId(response.getEventId())
                .kind(KindEnum.YOUTUBE_SEARCH_LIST)
                .nextPageToken(response.getNextPageToken())
                .prevPageToken(response.getPrevPageToken())
                .visitorId(response.getVisitorId())
                .regionCode(response.getRegionCode())
                .programmingLanguage(PROGRAMMING_LANGUAGE)
                .reponseContentType(CONTENT_TYPE)
                .pageInfo(savedPageInfo)
                .build();

        YouTubeListResponse savedYouTubeListResponse = youTubeListResponseService.save(youTubeListResponse);

        List<Video> videos = response.getItems().stream().map(responseVideo -> {

            SearchResultSnippet snippet = responseVideo.getSnippet();
            Date publishedAt = Date.from(Instant.parse(snippet.getPublishedAt().toStringRfc3339()));
            Video video = Video.builder()
                    .etag(responseVideo.getEtag())
                    .kind(KindEnum.YOUTUBE_SEARCH_LIST)
                    .videoId(responseVideo.getId().getVideoId())
                    .channelId(snippet.getChannelId())
                    .channelTitle(snippet.getChannelTitle())
                    .contentType(CONTENT_TYPE)
                    .programmingLanguage(PROGRAMMING_LANGUAGE)
                    .description(snippet.getDescription())
                    .title(snippet.getChannelTitle())
                    .publishedAt(publishedAt)
                    .build();

            return video;
        }).collect(Collectors.toList());

        videoService.saveAll(videos);
    }
}
