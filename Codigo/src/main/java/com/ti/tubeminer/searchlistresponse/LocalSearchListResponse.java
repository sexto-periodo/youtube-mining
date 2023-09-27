package com.ti.tubeminer.searchlistresponse;


import com.ti.tubeminer.global.domain.entity.BaseEntity;
import com.ti.tubeminer.pageinfo.PageInfo;
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
@Table(name = "t_search_list_response", uniqueConstraints = {
        @UniqueConstraint(name = "uk_search_list_response_hash_id", columnNames = {"hash_id"}),
        @UniqueConstraint(name = "uk_search_list_response", columnNames = {"id"})
})
@SQLDelete(sql = "UPDATE t_search_list_response SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@ToString(of = {"id", "hashId"})
@EqualsAndHashCode(of = "id", callSuper = false)
public class LocalSearchListResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name = "etag")
    private String etag;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "kind")
    private String kind;

    @Column(name = "next_page_token")
    private String nextPageToken;

    @Column(name = "prev_page_token")
    private String prevPageToken;

    @OneToOne
    @JoinColumn(name = "fk_page_info")
    private PageInfo pageInfo;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "visitor_id")
    private String visitorId;


}
