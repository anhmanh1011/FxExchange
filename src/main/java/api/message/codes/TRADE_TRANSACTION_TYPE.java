package api.message.codes;

import api.message.codes.CODE;

public class TRADE_TRANSACTION_TYPE extends CODE {

    public static final api.message.codes.TRADE_TRANSACTION_TYPE OPEN = new api.message.codes.TRADE_TRANSACTION_TYPE(0L);
    public static final api.message.codes.TRADE_TRANSACTION_TYPE CLOSE = new api.message.codes.TRADE_TRANSACTION_TYPE(2L);
    public static final api.message.codes.TRADE_TRANSACTION_TYPE MODIFY = new api.message.codes.TRADE_TRANSACTION_TYPE(3L);
    public static final api.message.codes.TRADE_TRANSACTION_TYPE DELETE = new api.message.codes.TRADE_TRANSACTION_TYPE(4L);

    public TRADE_TRANSACTION_TYPE(long code) {
		super(code);
	}
}
