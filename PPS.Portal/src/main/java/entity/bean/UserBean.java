package entity.bean;

import java.util.List;

import entity.SelectNode;

public class UserBean extends ObjectBean {
	private String id;
	private String given_name;
	private String name;
	private String nick_name;
	private String img_blob;
	private boolean canModifyPassword;
	private boolean canModifyCompany;
	private boolean canModifyGroup;
	private int company;
	private int group;
	private List<SelectNode> companyList;
	private List<SelectNode> groupList;
	private String company_name;
	private String group_name;
	private String type;
	private String active;

	boolean passwordcheck;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getImg_blob() {
		return img_blob;
	}

	public void setImg_blob(String img_blob) {
		this.img_blob = img_blob;
	}

	public boolean isCanModifyPassword() {
		return canModifyPassword;
	}

	public void setCanModifyPassword(boolean canModifyPassword) {
		this.canModifyPassword = canModifyPassword;
	}

	public boolean isCanModifyCompany() {
		return canModifyCompany;
	}

	public void setCanModifyCompany(boolean canModifyCompany) {
		this.canModifyCompany = canModifyCompany;
	}

	public boolean isCanModifyGroup() {
		return canModifyGroup;
	}

	public void setCanModifyGroup(boolean canModifyGroup) {
		this.canModifyGroup = canModifyGroup;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public List<SelectNode> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<SelectNode> companyList) {
		this.companyList = companyList;
	}

	public List<SelectNode> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<SelectNode> groupList) {
		this.groupList = groupList;
	}

	public boolean isPasswordcheck() {
		return passwordcheck;
	}

	public void setPasswordcheck(boolean passwordcheck) {
		this.passwordcheck = passwordcheck;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
