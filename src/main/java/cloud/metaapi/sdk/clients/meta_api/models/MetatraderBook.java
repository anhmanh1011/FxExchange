package cloud.metaapi.sdk.clients.meta_api.models;

import cloud.metaapi.sdk.clients.models.IsoTime;

import java.util.List;

/**
 * MetaTrader order book
 */
public class MetatraderBook {
  /**
   * Symbol (e.g. a currency pair or an index)
   */
  public String symbol;
  /**
   * Time
   */
  public IsoTime time;
  /**
   * Time, in broker timezone, YYYY-MM-DD HH:mm:ss.SSS format
   */
  public String brokerTime;
  /**
   * List of order book entries
   */
  public List<MetatraderBookEntry> book;
}
