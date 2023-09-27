package com.ti.tubeminer.snippet;

import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnippetService extends BaseService<ISnippetRepository, Snippet> {

    @Autowired
    private ISnippetRepository snippetRepository;

}
