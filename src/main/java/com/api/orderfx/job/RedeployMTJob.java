package com.api.orderfx.job;

import com.api.orderfx.ApplicationListener.MetaApiSocketConnect;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RedeployMTJob {

    @Autowired
    MetaApiSocketConnect metaApiSocketConnect;

    @Scheduled(fixedDelay = 1000*60*15)
    public void redeployMtJob() {
        boolean connected = metaApiSocketConnect.connection.getTerminalState().isConnected();
        log.info("RedeployMTJob ===== " + connected);
        if (!connected) {
            log.info("RUNNING JOB ------------------- RedeployMTJob");
            metaApiSocketConnect.onApplicationEvent();
            log.info("CLODE JOB ------------------- RedeployMTJob");
        }


    }
}
