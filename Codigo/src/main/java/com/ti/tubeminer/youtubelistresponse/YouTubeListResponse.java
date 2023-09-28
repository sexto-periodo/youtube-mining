package com.ti.tubeminer.youtubelistresponse;

import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.enums.KindEnum;
import com.ti.tubeminer.enums.ProgrammingLanguageEnum;
import com.ti.tubeminer.global.domain.entity.BaseEntity;
import com.ti.tubeminer.pageinfo.PageInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_youtube_list_response", uniqueConstraints = {
        @UniqueConstraint(name = "uk_youtube_list_response_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_youtube_list_response", columnNames = {"id"}),
})
@SQLDelete(sql = "UPDATE t_youtube_list_response SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class YouTubeListResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "prev_page_token")
    private String prevPageToken;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "visitor_id")
    private String visitorId;

    @Column(name = "etag")
    private String etag;

    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private KindEnum kind;

    @Column(name = "next_page_token")
    private String nextPageToken;

    @OneToOne
    @JoinColumn(name = "fk_page_info")
    private PageInfo pageInfo;

    @Column(name = "response_content_type")
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum reponseContentType;

    @Column(name = "programming_language")
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguageEnum programmingLanguage;
}
