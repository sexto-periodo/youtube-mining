package com.ti.tubeminer.video;

import com.ti.tubeminer.global.domain.exceptions.EntityNotFoundException;
import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService extends BaseService<IVideoRepository, Video> {

    @Autowired
    private IVideoRepository videoRepository;

    public List<Video> findLatestVideosWithUnminedComments(){
        return videoRepository.findLatestVideosWithUnminedComments();
    };

    public Video findByYouTubeId(String videoId){
        return videoRepository.findFirstByVideoId(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video do Id " + videoId + " não encontrado", HttpStatus.NOT_FOUND));
    };

}
