package com.redcrystal.example.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_history database table.
 * 
 */
@Entity
@Table(name="user_history")
@NamedQuery(name="UserHistory.findAll", query="SELECT u FROM UserHistory u")
public class UserHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int userHistoryId;

	@Column(length=1024)
	private String dataFrom;

	@Column(length=1024)
	private String dataTo;

	@Column(nullable=false, length=45)
	private String field;

	@Temporal(TemporalType.TIMESTAMP)
	private Date recCreated;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RecCreatedBy", nullable=false)
	private User recCreatedBy;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ReferenceId", nullable=false)
	private User reference;

	public UserHistory() {
	}

	public int getUserHistoryId() {
		return this.userHistoryId;
	}

	public void setUserHistoryId(int userHistoryId) {
		this.userHistoryId = userHistoryId;
	}

	public String getDataFrom() {
		return this.dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getDataTo() {
		return this.dataTo;
	}

	public void setDataTo(String dataTo) {
		this.dataTo = dataTo;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Date getRecCreated() {
		return this.recCreated;
	}

	public void setRecCreated(Date recCreated) {
		this.recCreated = recCreated;
	}

	public User getRecCreatedBy() {
		return this.recCreatedBy;
	}

	public void setRecCreatedBy(User recCreatedBy) {
		this.recCreatedBy = recCreatedBy;
	}

	public User getReference() {
		return this.reference;
	}

	public void setReference(User reference) {
		this.reference = reference;
	}

}