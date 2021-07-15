package api.message.codes;

import api.message.codes.CODE;

public class STREAMING_TRADE_TYPE extends CODE {

    public static final api.message.codes.STREAMING_TRADE_TYPE OPEN = new api.message.codes.STREAMING_TRADE_TYPE(0L);
    public static final api.message.codes.STREAMING_TRADE_TYPE PENDING = new api.message.codes.STREAMING_TRADE_TYPE(1L);
    public static final api.message.codes.STREAMING_TRADE_TYPE CLOSE = new api.message.codes.STREAMING_TRADE_TYPE(2L);

    public STREAMING_TRADE_TYPE(long code) {
		super(code);
	}
}
