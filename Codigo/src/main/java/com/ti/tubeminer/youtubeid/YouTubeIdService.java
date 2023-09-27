package com.ti.tubeminer.youtubeid;

import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YouTubeIdService extends BaseService<IYouTubeIdRepository, YouTubeId> {

    @Autowired
    private IYouTubeIdRepository youTubeIdRepository;

}
