package com.ti.tubeminer.video;

import com.ti.tubeminer.global.domain.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVideoRepository extends IBaseRepository<Video> {

    @Query("SELECT v FROM Video v WHERE v.minedComments = false ORDER BY v.id DESC")
    List<Video> findLatestVideosWithUnminedComments();

    Optional<Video> findFirstByVideoId(String videoId);
}
