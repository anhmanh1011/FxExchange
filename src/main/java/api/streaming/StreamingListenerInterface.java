package api.streaming;

import api.message.records.SBalanceRecord;
import api.message.records.SCandleRecord;
import api.message.records.SKeepAliveRecord;
import api.message.records.SNewsRecord;
import api.message.records.SProfitRecord;
import api.message.records.STickRecord;
import api.message.records.STradeRecord;
import api.message.records.STradeStatusRecord;

public interface StreamingListenerInterface {
    public void receiveTradeRecord(STradeRecord tradeRecord);
    public void receiveTickRecord(STickRecord tickRecord);
    public void receiveBalanceRecord(SBalanceRecord balanceRecord);
    public void receiveNewsRecord(SNewsRecord newsRecord);
    public void receiveTradeStatusRecord(STradeStatusRecord tradeStatusRecord);
    public void receiveProfitRecord(SProfitRecord profitRecord);
    public void receiveKeepAliveRecord(SKeepAliveRecord keepAliveRecord);
    public void receiveCandleRecord(SCandleRecord candleRecord);
}
