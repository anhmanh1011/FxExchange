package api.message.codes;

import api.message.codes.CODE;

public class SWAP_TYPE extends CODE {

	public static final api.message.codes.SWAP_TYPE SWAP_BY_POINTS = new api.message.codes.SWAP_TYPE(0L);
	public static final api.message.codes.SWAP_TYPE SWAP_BY_DOLLARS = new api.message.codes.SWAP_TYPE(1L);
	public static final api.message.codes.SWAP_TYPE SWAP_BY_INTEREST = new api.message.codes.SWAP_TYPE(2L);
	public static final api.message.codes.SWAP_TYPE SWAP_BY_MARGIN_CURRENCY = new api.message.codes.SWAP_TYPE(3L);

    public SWAP_TYPE(long code) {
		super(code);
	}
}
