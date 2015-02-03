package ch.fhnw.edu.rental.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@SuppressWarnings("serial")
public class Role implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Long id;
	
	@Column(name = "role_roleName", unique=true)
	private String roleName;
	
	public Role() {
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}
}
