package com.ti.tubeminer.youtubelistresponse;


import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouTubeListResponseService extends BaseService<IYouTubeListResponseRepository, YouTubeListResponse> {

    @Autowired
    private IYouTubeListResponseRepository youTubeListResponseRepository;


    public YouTubeListResponse findLatestByContentType( ContentTypeEnum contentType) {
        return youTubeListResponseRepository.findLatestByContentType(contentType);
    }
}
