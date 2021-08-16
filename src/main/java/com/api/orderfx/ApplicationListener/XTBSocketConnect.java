package com.api.orderfx.ApplicationListener;

import api.message.codes.STREAMING_TRADE_TYPE;
import api.message.command.APICommandFactory;
import api.message.error.APICommandConstructionException;
import api.message.error.APICommunicationException;
import api.message.error.APIReplyParseException;
import api.message.records.SKeepAliveRecord;
import api.message.records.STickRecord;
import api.message.records.STradeRecord;
import api.message.records.STradeStatusRecord;
import api.message.response.APIErrorResponse;
import api.message.response.LoginResponse;
import api.streaming.StreamingListener;
import api.sync.Credentials;
import api.sync.ServerData;
import api.sync.SyncAPIConnector;
import com.api.orderfx.Utils.JsonUtils;
import com.api.orderfx.common.BaseException;
import com.api.orderfx.common.ConnectorFactory;
import com.api.orderfx.common.EStatusTrade;
import com.api.orderfx.entity.PositionInfoEntity;
import com.api.orderfx.entity.ProfitManagementInfoEntity;
import com.api.orderfx.mapper.PositionMapper;
import com.api.orderfx.repository.PositionInfoRepository;
import com.api.orderfx.repository.ProfitManagementRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component
@Log4j2
public class XTBSocketConnect {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BeanUtilsBean beanUtilsBean;


    @Autowired
    ConnectorFactory connectorFactory;

    @Autowired
    PositionMapper positionMapper;
    @Autowired
    PositionInfoRepository positionInfoRepository;

    @Autowired
    ProfitManagementRepository profitManagementRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void run() {

        try {
            SyncAPIConnector connector = getConnector();
            StreamingListener sl = new StreamingListener() {
                @Override
                public void receiveTradeRecord(STradeRecord tradeRecord) {
                    System.out.println("Stream trade record: " + tradeRecord);
                    System.out.println(JsonUtils.ObjectToJson(tradeRecord));
                    if (tradeRecord.getType().getCode() == STREAMING_TRADE_TYPE.OPEN.getCode()) {
                        PositionInfoEntity positionInfoEntity = positionMapper.sTradeRecordXTBToPositionInfoEntity(tradeRecord);
                        PositionInfoEntity entity = positionInfoRepository.findPositionInfoEntityByOrderId(tradeRecord.getOrder2());
                        if (entity != null && tradeRecord.getState().equals("Modified")) {
                            try {
                                beanUtilsBean.copyProperties(entity, positionInfoEntity);
                                entity.setStatus(EStatusTrade.POSITION_SUCCESS);
                                positionInfoRepository.save(entity);

                                ProfitManagementInfoEntity profitManagementInfoEntity = profitManagementRepository.getProfitManagementInfoEntitiesByOrderId(entity.getOrderId());
                                profitManagementInfoEntity.setStatus(EStatusTrade.POSITION_SUCCESS);
                                profitManagementInfoEntity.setPositionId(positionInfoEntity.getPositionId());
                                profitManagementRepository.save(profitManagementInfoEntity);
                                positionInfoRepository.flush();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (entity != null && entity.getStatus().equals(EStatusTrade.POSITION_SUCCESS) && tradeRecord.getState().equals("Deleted")) {
                            entity.setClosed(true);
                            entity.setStatus(EStatusTrade.POSITION_CLOSED);
                            positionInfoRepository.save(entity);

                            ProfitManagementInfoEntity profitManagementInfoEntity = profitManagementRepository.getProfitManagementInfoEntitiesByOrderId(entity.getOrderId());
                            profitManagementInfoEntity.setClosed(true);
                            profitManagementInfoEntity.setStatus(EStatusTrade.POSITION_CLOSED);
                            profitManagementRepository.save(profitManagementInfoEntity);
                        }
                    }
                }

                @Override
                public void receiveTickRecord(STickRecord tickRecord) {
                    log.debug("Stream tick record: " + tickRecord);
                    System.out.println(JsonUtils.ObjectToJson(tickRecord));

                }

                @Override
                public void receiveKeepAliveRecord(SKeepAliveRecord keepAliveRecord) {
//                        log.debug("Stream tick KeepAliveRecord: " + JsonUtils.ObjectToJson(keepAliveRecord));
                    System.out.println(JsonUtils.ObjectToJson(keepAliveRecord));

                }

                @Override
                public void receiveTradeStatusRecord(STradeStatusRecord tradeStatusRecord) {
                    log.debug("Stream TradeStatusRecord: " + tradeStatusRecord);
                    System.out.println(JsonUtils.ObjectToJson(tradeStatusRecord));
                }
            };

            connector.connectStream(sl);
            System.out.println("Stream connected.");

            connector.subscribeTrades();
            connector.subscribeTradeStatus();
            connector.subscribeKeepAlive();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

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
            log.error("Không thể kết nối đến broker");
            throw new BaseException(HttpStatus.SERVICE_UNAVAILABLE.value(), "Không thể kết nối đến broker");
        }
        return connector;

    }
}
