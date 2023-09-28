package com.ti.tubeminer.youtubelistresponse;

import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.global.domain.repository.IBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IYouTubeListResponseRepository extends IBaseRepository<YouTubeListResponse> {

    @Query("SELECT r FROM YouTubeListResponse r " +
            "WHERE r.reponseContentType = :contentType " +
            "ORDER BY r.id DESC LIMIT 1")
    YouTubeListResponse findLatestByContentType(
            @Param("contentType") ContentTypeEnum contentType
    );


}
