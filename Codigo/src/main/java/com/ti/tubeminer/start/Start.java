package com.ti.tubeminer.start;

import com.ti.tubeminer.video.VideoMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class Start {

    @Autowired
    private VideoMiner videoMiner;

    @EventListener(ApplicationReadyEvent.class)
    public void letsGo() throws Exception {

        videoMiner.request();

    }
}
