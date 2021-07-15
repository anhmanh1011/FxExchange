package api.message.codes;

import api.message.codes.CODE;

public class SWAP_ROLLOVER_TYPE extends CODE {

	public static final api.message.codes.SWAP_ROLLOVER_TYPE MONDAY = new api.message.codes.SWAP_ROLLOVER_TYPE(0L);
	public static final api.message.codes.SWAP_ROLLOVER_TYPE TUESDAY = new api.message.codes.SWAP_ROLLOVER_TYPE(1L);
	public static final api.message.codes.SWAP_ROLLOVER_TYPE WEDNSDAY = new api.message.codes.SWAP_ROLLOVER_TYPE(2L);
	public static final api.message.codes.SWAP_ROLLOVER_TYPE THURSDAY = new api.message.codes.SWAP_ROLLOVER_TYPE(3L);
	public static final api.message.codes.SWAP_ROLLOVER_TYPE FRIDAY = new api.message.codes.SWAP_ROLLOVER_TYPE(4L);

    public SWAP_ROLLOVER_TYPE(long code) {
		super(code);
	}
}
