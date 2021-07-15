package api.message.error;

public class ERR_CODE {
    
    private String stringCode;

    public ERR_CODE(String code) {
        this.stringCode = code;
    }

    public String stringValue() {
        return stringCode;
    }

	public String getStringCode() {
		return stringCode;
	}

	public void setStringCode(String stringCode) {
		this.stringCode = stringCode;
	}

	@Override
	public String toString() {
		return "ERR_CODE [stringCode=" + stringCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stringCode == null) ? 0 : stringCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		api.message.error.ERR_CODE other = (api.message.error.ERR_CODE) obj;
		if (stringCode == null) {
			if (other.stringCode != null)
				return false;
		} else if (!stringCode.equals(other.stringCode))
			return false;
		return true;
	}
}
