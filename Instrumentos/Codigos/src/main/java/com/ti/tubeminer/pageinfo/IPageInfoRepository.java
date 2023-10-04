package com.ti.tubeminer.pageinfo;

import com.ti.tubeminer.global.domain.repository.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPageInfoRepository  extends IBaseRepository<PageInfo> {
}
