package com.api.orderfx.common;

import api.message.command.APICommandFactory;
import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.LoginResponse;
import api.sync.Credentials;
import api.sync.ServerData;
import api.sync.SyncAPIConnector;
import com.api.orderfx.Utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ConnectorFactory {

    @Value("${xtb.api.userid}")
    String userId;

    @Value("${xtb.api.pass}")
    String password;
    @Value("${xtb.api.server}")
    String server;

    public SyncAPIConnector getConnector() throws BaseException {
        SyncAPIConnector connector = null;
        Credentials credentials = new Credentials(userId, password, null, null);
        try {
            connector = new SyncAPIConnector(ServerData.getServerEnumByString(server));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginResponse loginResponse = null;
        try {
            loginResponse = APICommandFactory.executeLoginCommand(connector, credentials);
        } catch (APICommandConstructionException | APICommunicationException | APIReplyParseException | APIErrorResponse | IOException e) {
            e.printStackTrace();
        }
        log.info("get Connector: " + JsonUtils.ObjectToJson(loginResponse));
        if (connector == null) {
            throw new BaseException(HttpStatus.SERVICE_UNAVAILABLE.value(), "Không thể kết nối đến broker");
        }
        return connector;

    }

}
