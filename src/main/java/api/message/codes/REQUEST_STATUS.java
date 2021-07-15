package api.message.codes;

import api.message.codes.CODE;

public class REQUEST_STATUS extends CODE {

    public static final api.message.codes.REQUEST_STATUS ERROR = new api.message.codes.REQUEST_STATUS(0L);
    public static final api.message.codes.REQUEST_STATUS PENDING = new api.message.codes.REQUEST_STATUS(1L);
    public static final api.message.codes.REQUEST_STATUS ACCEPTED = new api.message.codes.REQUEST_STATUS(3L);
    public static final api.message.codes.REQUEST_STATUS REJECTED = new api.message.codes.REQUEST_STATUS(4L);

	public REQUEST_STATUS(long code) {
		super(code);
	}
}
