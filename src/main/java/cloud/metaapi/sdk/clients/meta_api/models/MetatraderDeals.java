package cloud.metaapi.sdk.clients.meta_api.models;

import lombok.Data;

import java.util.List;

/**
 * MetaTrader history deals search query response
 */
@Data
public class MetatraderDeals {
    /**
     * List of history deals returned
     */
    public List<MetatraderDeal> deals;
    /**
     * Flag indicating that deal initial synchronization is still in progress
     * and thus search results may be incomplete
     */
    public boolean synchronizing;
}
