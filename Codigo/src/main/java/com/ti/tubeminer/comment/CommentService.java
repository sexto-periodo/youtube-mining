package com.ti.tubeminer.comment;


import com.ti.tubeminer.global.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends BaseService<ICommentRepository, Comment> {

    @Autowired
    private ICommentRepository commentRepository;
}
