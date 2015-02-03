package ch.fhnw.edu.rental.model;


public class Role {
	private Long id;
	
	private String roleName;
	
	public Role() {
		// public default constructor required by Ibatis
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
