package com.ti.tubeminer.video;

import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends BaseService<IVideoRepository, Video> {

    @Autowired
    private IVideoRepository videoRepository;

}
