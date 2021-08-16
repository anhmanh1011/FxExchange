package cloud.metaapi.sdk.clients.models;

import cloud.metaapi.sdk.clients.models.Error;

/**
 * Contains an error message from websocket. For more about error 
 * types see https://metaapi.cloud/docs/client/websocket/errors/
 */
public class WebsocketError extends Error {
    /**
     * Request id the response relates to
     */
    public String requestId;
}
