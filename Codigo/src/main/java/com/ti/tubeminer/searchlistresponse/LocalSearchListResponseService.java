package com.ti.tubeminer.searchlistresponse;


import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalSearchListResponseService extends BaseService<ILocalSearchlistResponseRepository, LocalSearchListResponse> {

    @Autowired
    private ILocalSearchlistResponseRepository localsearchlistResponseRepository;
}
