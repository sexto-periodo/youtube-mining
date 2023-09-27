package com.ti.tubeminer.video;

import com.ti.tubeminer.global.domain.entity.BaseEntity;
import com.ti.tubeminer.snippet.Snippet;
import com.ti.tubeminer.youtubeid.YouTubeId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.ti.tubeminer.utils.HashUtils.generateHash;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_video", uniqueConstraints = {
        @UniqueConstraint(name = "uk_video_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_video", columnNames = {"id"})
})
@SQLDelete(sql = "UPDATE t_video SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class Video extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name = "url")
    private String url;

    @Column(name = "etag")
    private String etag;

    @Column(name = "kind")
    private String kind;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "fk_youtube_id_id")
    private YouTubeId youTubeId;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "fk_snippet_id")
    private Snippet snippet;
}
