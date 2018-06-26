package entity.bean;

import java.util.List;

import entity.SelectNode;

public class UserBean extends ObjectBean{
	String given_name;
	String name;
	String nick_name;
	boolean is_img_blob;
	String img_url;
	byte[] img_blob;
	boolean canModifyPassword;
	boolean canModifyCompany;
	boolean canModifyGroup;
	int company;
	int group;
	List<SelectNode> companyList;
	List<SelectNode> groupList;
	boolean passwordcheck;

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

	public boolean isIs_img_blob() {
		return is_img_blob;
	}

	public void setIs_img_blob(boolean is_img_blob) {
		this.is_img_blob = is_img_blob;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public byte[] getImg_blob() {
		return img_blob;
	}

	public void setImg_blob(byte[] img_blob) {
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
}
