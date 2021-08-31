package com.api.orderfx.common;

import cloud.metaapi.sdk.clients.meta_api.models.MetatraderAccountDto;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderAccountInformation;
import cloud.metaapi.sdk.meta_api.MetaApi;
import cloud.metaapi.sdk.meta_api.MetaApiConnection;
import cloud.metaapi.sdk.meta_api.MetatraderAccount;
import com.api.orderfx.ApplicationListener.SynchronizationImpl;
import com.api.orderfx.Utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class MetaApiConnectorFactory {
    @Value("${meta.api.bds.token}")
    private String token;
    @Value("${meta.api.bds.account}")
    private String accountId;

    public MetaApiConnection getConnection() throws InterruptedException, ExecutionException, IOException {
        try {
            MetaApiConnection connection = null;
            MetaApi api = new MetaApi(token);
            // Add test MetaTrader account
            MetatraderAccount account = api.getMetatraderAccountApi().getAccount(accountId).get();
            MetatraderAccountDto.DeploymentState state = account.getState();
            if (state.equals(MetatraderAccountDto.DeploymentState.UNDEPLOYED)) {
                System.out.println("Deploying");
                account.deploy().get();
            }

            System.out.println("Waiting for API server to connect to broker (may take couple of minutes)");
            account.waitConnected().get();

            // connect to MetaApi API
            connection = account.connect().get();


            System.out.println("Waiting for SDK to synchronize to terminal state "
                    + "(may take some time depending on your history size)");
            connection.waitSynchronized().get();
            // trade
            return connection;
        } catch (Exception exception) {
            log.error("Lỗi khi kết nối đến meta api " + JsonUtils.ObjectToJson(exception));
            throw exception;
        }


    }

}
