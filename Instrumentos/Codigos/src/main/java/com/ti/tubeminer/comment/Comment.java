package com.ti.tubeminer.comment;


import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.enums.KindEnum;
import com.ti.tubeminer.enums.ProgrammingLanguageEnum;
import com.ti.tubeminer.global.domain.entity.BaseEntity;
import com.ti.tubeminer.video.Video;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_comment", uniqueConstraints = {
        @UniqueConstraint(name = "uk_comment_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_comment", columnNames = {"id"})
})
@SQLDelete(sql = "UPDATE t_video SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(name = "etag")
    private String etag;

    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private KindEnum kind;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "text_display", columnDefinition = "TEXT")
    private String textDisplay;

    @Column(name = "text_original", columnDefinition = "TEXT")
    private String textOriginal;

    @Column(name = "author_display_name", columnDefinition = "TEXT")
    private String authorDisplayName;

    @Column(name = "published_at", nullable = false, updatable = false)
    private Date publisheAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "total_reply_count")
    private int totalReplyCount;

    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;

    @Column(name = "programming_language")
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguageEnum programmingLanguage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_video")
    private Video video;


}
