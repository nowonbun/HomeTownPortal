package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="MST_ROLE")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLE")
	private String role;

	@Column(name="NAME")
	private String name;
	
	@ManyToMany
	@JoinTable(	name = "MAP_ACTION_ROLE_COMPANY", 
				joinColumns = { @JoinColumn(name = "ROLE_CODE") }, 
				inverseJoinColumns = { @JoinColumn(name = "COMPANY_ID") })
	private List<Company> companies;

	@ManyToMany
	@JoinTable(	name = "MAP_ACTION_ROLE_GROUP", 
				joinColumns = { @JoinColumn(name = "ROLE_CODE") }, 
				inverseJoinColumns = {	@JoinColumn(name = "GROUP_ID") })
	private List<Group> groups;

	@ManyToMany
	@JoinTable(	name = "MAP_ACTION_ROLE_USER", 
				joinColumns = { @JoinColumn(name = "ROLE_CODE") }, 
				inverseJoinColumns = {	@JoinColumn(name = "USER_ID") })
	private List<User> users;

	public Role() {
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}