package com.ti.tubeminer.miner.commentminer;

import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.ti.tubeminer.comment.Comment;
import com.ti.tubeminer.comment.CommentService;
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
public class ComentDataProcessor {

    @Autowired
    private CommentService commentService;

    @Autowired
    private YouTubeListResponseService youTubeListResponseService;

    @Autowired
    private PageInfoService pageInfoService;

    @Autowired
    private VideoService videoService;


    @Value("${application.mining.programming_language}")
    private ProgrammingLanguageEnum PROGRAMMING_LANGUAGE;

    private ContentTypeEnum CONTENT_TYPE = ContentTypeEnum.COMMENT;


    public void proccessResponse(CommentThreadListResponse response, String videoId) {

        PageInfo pageInfo = PageInfo.builder()
                .resultsPerPage(response.getPageInfo().getResultsPerPage())
                .totalResults(response.getPageInfo().getTotalResults())
                .build();
        PageInfo savedPageInfo = pageInfoService.save(pageInfo);

        YouTubeListResponse youTubeListResponse = YouTubeListResponse.builder()
                .etag(response.getEtag())
                .videoId(videoId)
                .eventId(response.getEventId())
                .kind(KindEnum.YOUTUBE_COMMENT_LIST)
                .nextPageToken(response.getNextPageToken())
                .visitorId(response.getVisitorId())
                .reponseContentType(CONTENT_TYPE)
                .programmingLanguage(PROGRAMMING_LANGUAGE)
                .pageInfo(savedPageInfo)
                .build();

        YouTubeListResponse savedYouTubeListResponse = youTubeListResponseService.save(youTubeListResponse);

        List<Comment> comments = response.getItems().stream().map(responseComment -> {

            Date publishedAt = Date.from(
                    Instant.parse(
                            responseComment.
                                    getSnippet()
                                    .getTopLevelComment()
                                    .getSnippet()
                                    .getPublishedAt()
                                    .toStringRfc3339()
                    ));

            Date updatedAt = Date.from(
                    Instant.parse(
                            responseComment.
                                    getSnippet()
                                    .getTopLevelComment()
                                    .getSnippet()
                                    .getUpdatedAt()
                                    .toStringRfc3339()
                    ));
            Comment comment = Comment.builder()
                    .totalReplyCount(responseComment.getSnippet().getTotalReplyCount().intValue())
                    .authorDisplayName(responseComment.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName())
                    .channelId(responseComment.getSnippet().getChannelId())
                    .videoId(responseComment.getSnippet().getVideoId())
                    .etag(responseComment.getEtag())
                    .kind(KindEnum.YOUTUBE_COMMENT_LIST)
                    .contentType(CONTENT_TYPE)
                    .programmingLanguage(PROGRAMMING_LANGUAGE)
                    .textOriginal(responseComment.getSnippet().getTopLevelComment().getSnippet().getTextOriginal())
                    .textDisplay(responseComment.getSnippet().getTopLevelComment().getSnippet().getTextDisplay())
                    .publisheAt(updatedAt)
                    .updatedAt(updatedAt)
                    .build();

            return comment;
        }).collect(Collectors.toList());


        Video video = videoService.findByYouTubeId(savedYouTubeListResponse.getVideoId());

        video.setComments(comments);
        video.setMinedComments(true);
        commentService.saveAll(comments);
        videoService.save(video);
    }
}
