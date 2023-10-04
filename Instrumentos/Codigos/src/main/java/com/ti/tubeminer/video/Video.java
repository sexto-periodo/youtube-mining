package com.ti.tubeminer.video;

import com.ti.tubeminer.comment.Comment;
import com.ti.tubeminer.enums.ContentTypeEnum;
import com.ti.tubeminer.enums.KindEnum;
import com.ti.tubeminer.enums.ProgrammingLanguageEnum;
import com.ti.tubeminer.global.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_video", uniqueConstraints = {
        @UniqueConstraint(name = "uk_video_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_video", columnNames = {"id"}),
        @UniqueConstraint(name = "uk_video_youtube_id", columnNames = {"video_id"})
})
@SQLDelete(sql = "UPDATE t_video SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class Video extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(name = "url")
    private String url;

    @Column(name = "etag")
    private String etag;

    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private KindEnum kind;

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "published_at")
    private Date publishedAt;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "channel_title")
    private String channelTitle;

    @Column(name = "title", columnDefinition = "VARCHAR(512)")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "response_content_type")
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;

    @Column(name = "programming_language")
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguageEnum programmingLanguage;

    @Builder.Default
    @Column(name = "mined_comments")
    private boolean minedComments = Boolean.FALSE;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
