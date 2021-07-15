package com.api.orderfx.ApplicationListener;

import api.message.command.APICommandFactory;
import api.message.records.*;
import api.message.response.LoginResponse;
import api.message.response.TickPricesResponse;
import api.streaming.StreamingListener;
import api.sync.Credentials;
import api.sync.ServerData;
import api.sync.SyncAPIConnector;
import com.api.orderfx.RestClientRequest.FXCMRequestClient;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.repository.OrderRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class XTBSocketConnect implements
        ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FXCMRequestClient fxcmRequestClient;

    @Autowired
    Environment properties;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BeanUtilsBean beanUtilsBean;

    public  static SyncAPIConnector connector;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        String login = "12408862";
        String password = "xoh82802";
        Credentials credentials = new Credentials(login, password, null, null);

        try {
            connector = new SyncAPIConnector(ServerData.ServerEnum.DEMO);
            LoginResponse loginResponse = APICommandFactory.executeLoginCommand(connector, credentials);
            System.out.println(loginResponse);
            if (loginResponse != null && loginResponse.getStatus()) {
                StreamingListener sl = new StreamingListener() {
                    @Override
                    public void receiveTradeRecord(STradeRecord tradeRecord) {
                        log.debug("Stream trade record: " + tradeRecord);
                    }

                    @Override
                    public void receiveTickRecord(STickRecord tickRecord) {
                        log.debug("Stream tick record: " + tickRecord);
                    }

                    @Override
                    public void receiveKeepAliveRecord(SKeepAliveRecord keepAliveRecord) {
                        log.debug("Stream tick KeepAliveRecord: " + JsonUtils.ObjectToJson(keepAliveRecord));
                    }

                    @Override
                    public void receiveTradeStatusRecord(STradeStatusRecord tradeStatusRecord) {
                        log.debug("Stream TradeStatusRecord: " + tradeStatusRecord);
                    }
                };
                connector.connectStream(sl);
                System.out.println("Stream connected.");

                connector.subscribeTrades();
                connector.subscribeTradeStatus();
                connector.subscribeKeepAlive();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


}
