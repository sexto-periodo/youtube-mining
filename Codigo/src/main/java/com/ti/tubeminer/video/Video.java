package com.ti.tubeminer.video;

import com.ti.tubeminer.global.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import static com.ti.tubeminer.utils.HashUtils.generateHash;


@Data
@Builder
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

    @Builder.Default
    @Column(name = "hash_id")
    private String hashId = generateHash();

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;
}
