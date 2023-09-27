package com.ti.tubeminer.youtubeid;

import com.ti.tubeminer.global.domain.entity.BaseEntity;
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
@Table(name = "t_youtube_id", uniqueConstraints = {
        @UniqueConstraint(name = "uk_youtube_id_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_youtube_id", columnNames = {"id"})
})
@SQLDelete(sql = "UPDATE t_youtube_id SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class YouTubeId extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name = "kind")
    private String kind;

    @Column(name = "video_id")
    private String videoId;
}