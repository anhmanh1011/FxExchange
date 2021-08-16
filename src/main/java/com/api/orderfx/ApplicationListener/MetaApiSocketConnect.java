package com.api.orderfx.ApplicationListener;

import cloud.metaapi.sdk.clients.meta_api.SynchronizationListener;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderAccountDto;
import cloud.metaapi.sdk.clients.meta_api.models.MetatraderAccountInformation;
import cloud.metaapi.sdk.meta_api.MetaApi;
import cloud.metaapi.sdk.meta_api.MetaApiConnection;
import cloud.metaapi.sdk.meta_api.MetatraderAccount;
import cloud.metaapi.sdk.util.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
public class MetaApiSocketConnect {

    @Value("${meta.api.bds.token}")
    private String token;
    @Value("${meta.api.bds.account}")
    private String accountId;

    public static MetaApiConnection connection = null;

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent() {
        try {
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

            SynchronizationImpl quoteListener = new SynchronizationImpl(connection);
            connection.addSynchronizationListener(quoteListener);

            System.out.println("Waiting for SDK to synchronize to terminal state "
                    + "(may take some time depending on your history size)");
            connection.waitSynchronized().get();
            // trade

            CompletableFuture<MetatraderAccountInformation> accountInformation = connection.getAccountInformation();
            System.out.println(asJson(accountInformation.get()));
        } catch (Exception err) {
            err.printStackTrace();
            log.error(err);
        }
    }

    public static MetaApiConnection getConnection() {
        return connection;
    }

    public static void setConnection(MetaApiConnection connection) {
        MetaApiSocketConnect.connection = connection;
    }

    private static String getEnvOrDefault(String name, String defaultValue) {
        String result = System.getenv(name);
        return (result != null ? result : defaultValue);
    }

    private static class QuoteListener extends SynchronizationListener {
    }

    private static String asJson(Object object) throws JsonProcessingException {
        return JsonMapper.getInstance().writeValueAsString(object);
    }

}
