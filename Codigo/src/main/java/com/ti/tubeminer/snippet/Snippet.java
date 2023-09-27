package com.ti.tubeminer.snippet;

import com.google.api.client.util.DateTime;
import com.ti.tubeminer.global.domain.entity.BaseEntity;
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
@Table(name = "t_snippet", uniqueConstraints = {
        @UniqueConstraint(name = "uk_snippet_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_snippet", columnNames = {"id"})
})
@SQLDelete(sql = "UPDATE t_snippet SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
public class Snippet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name = "published_at")
    private DateTime publishedAt;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "channel_title")
    private String channelTitle;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
}
