package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String msg;
	private Date dateCreation;
	private Utilisateur user;
	private Groupe grp;

	public Message() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Groupe getGrp() {
		return grp;
	}

	public void setGrp(Groupe grp) {
		this.grp = grp;
	}

	@Override
	public String toString() {
		return "Twitte [id=" + id + ", msg=" + msg + ", dateCreation="
				+ dateCreation + ", user=" + user + ", grp=" + grp + "]";
	}

}
