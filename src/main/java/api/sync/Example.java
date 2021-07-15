package api.sync;

import api.message.records.SKeepAliveRecord;
import api.message.records.STickRecord;
import api.message.records.STradeRecord;
import api.streaming.StreamingListener;
import com.api.orderfx.Utils.JsonUtils;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import api.message.command.APICommandFactory;
import api.message.records.TickRecord;
import api.message.response.LoginResponse;
import api.message.response.TickPricesResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Example {

    public void runExample(ServerData.ServerEnum server, Credentials credentials){
        try {
            SyncAPIConnector connector = new SyncAPIConnector(server);
            LoginResponse loginResponse = APICommandFactory.executeLoginCommand(connector, credentials);
            System.out.println(loginResponse);
            if (loginResponse != null && loginResponse.getStatus()) {
                StreamingListener sl = new StreamingListener() {
                    @Override
                    public void receiveTradeRecord(STradeRecord tradeRecord) {
                        System.out.println("Stream trade record: " + tradeRecord);
                    }

                    @Override
                    public void receiveTickRecord(STickRecord tickRecord) {
                        System.out.println("Stream tick record: " + tickRecord);
                    }

                    @Override
                    public void receiveKeepAliveRecord(SKeepAliveRecord keepAliveRecord) {
                        System.out.println("Stream tick KeepAliveRecord: " + JsonUtils.ObjectToJson(keepAliveRecord));
                    }
                };
                String symbol = "DE30";


                connector.connectStream(sl);
                System.out.println("Stream connected.");

                connector.subscribePrice(symbol);
                connector.subscribeTrades();
                connector.subscribeKeepAlive();

                for (int i = 0; i < 5; i++) {
                    TickPricesResponse resp = APICommandFactory.executeTickPricesCommand(connector, 0L, symbol, 0L);
                    for (TickRecord tr : resp.getTicks()) {
                        System.out.println("TickPrices result: " + tr.getSymbol() + " - ask: " + tr.getAsk()+ "time" + tr.getTimestamp());
                    }
                }

            }
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    protected Map<String, Server> getAvailableServers() {
        return ServerData.getProductionServers();
    }
}
