package api.message.codes;

import api.message.codes.CODE;

public class PROFIT_MODE extends CODE {

	public static final api.message.codes.PROFIT_MODE FOREX = new api.message.codes.PROFIT_MODE(5L);
	public static final api.message.codes.PROFIT_MODE CFD = new api.message.codes.PROFIT_MODE(6L);

    public PROFIT_MODE(long code) {
        super(code);
    }
}
