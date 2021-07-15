package api.message.codes;

import api.message.codes.CODE;

public class MARGIN_MODE extends CODE {

	public static final api.message.codes.MARGIN_MODE FOREX = new api.message.codes.MARGIN_MODE(101L);
	public static final api.message.codes.MARGIN_MODE CFD_LEVERAGED = new api.message.codes.MARGIN_MODE(102L);
	public static final api.message.codes.MARGIN_MODE CFD = new api.message.codes.MARGIN_MODE(103L);

    public MARGIN_MODE(long code){
        super(code);
    }
}
