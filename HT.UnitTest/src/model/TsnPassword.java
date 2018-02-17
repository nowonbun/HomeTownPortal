package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TSN_PASSWORD database table.
 * 
 */
@Entity
@Table(name="TSN_PASSWORD")
@NamedQuery(name="TsnPassword.findAll", query="SELECT t FROM TsnPassword t")
public class TsnPassword implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TsnPasswordPK id;

	@Column(name="PASSWORD")
	private String password;

	//bi-directional many-to-one association to TsnUser
	@ManyToOne
	@JoinColumn(name="ID")
	private TsnUser tsnUser;

	//bi-directional many-to-one association to TsnStateInfo
	@ManyToOne
	@JoinColumn(name="STATE")
	private TsnStateInfo tsnStateInfo;

	public TsnPassword() {
	}

	public TsnPasswordPK getId() {
		return this.id;
	}

	public void setId(TsnPasswordPK id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TsnUser getTsnUser() {
		return this.tsnUser;
	}

	public void setTsnUser(TsnUser tsnUser) {
		this.tsnUser = tsnUser;
	}

	public TsnStateInfo getTsnStateInfo() {
		return this.tsnStateInfo;
	}

	public void setTsnStateInfo(TsnStateInfo tsnStateInfo) {
		this.tsnStateInfo = tsnStateInfo;
	}

}