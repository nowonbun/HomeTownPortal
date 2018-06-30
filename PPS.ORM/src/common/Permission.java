package common;

public class Permission {
	private Object code;
	private Object companyid;
	private Object groupid;
	private Object userid;

	public Permission(Object code, Object companyid, Object groupid, Object userid) {
		this.code = code;
		this.companyid = companyid;
		this.groupid = groupid;
		this.userid = userid;
	}

	public String getCode() {
		return (String) code;
	}

	public Integer getCompanyid() {
		return companyid != null ? (int) companyid : null;
	}

	public Integer getGroupid() {
		return groupid != null ? (int) groupid : null;
	}

	public String getUserid() {
		return (String) userid;
	}

}
