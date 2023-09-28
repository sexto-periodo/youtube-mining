package com.ti.tubeminer.start;

import com.ti.tubeminer.miner.commentminer.CommentMiner;
import com.ti.tubeminer.miner.videominer.VideoMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Start {

    @Autowired
    private VideoMiner videoMiner;

    @Autowired
    private CommentMiner commentMiner;

    @EventListener(ApplicationReadyEvent.class)
    public void letsGo() throws Exception {

        videoMiner.request();
        commentMiner.mineComments();

    }
}
