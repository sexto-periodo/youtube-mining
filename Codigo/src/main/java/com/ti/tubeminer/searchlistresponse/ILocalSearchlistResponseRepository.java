package com.ti.tubeminer.searchlistresponse;

import com.ti.tubeminer.global.domain.repository.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalSearchlistResponseRepository extends IBaseRepository<LocalSearchListResponse> {
}
