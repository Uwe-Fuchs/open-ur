package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

@Entity(name="TECHNICAL_USER")
public class PTechnicalUser
	extends PUserStructureBase
{
	private static final long serialVersionUID = -4445671828548847400L;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name="TECH_USERS_PERMISSIONS",
		joinColumns={@JoinColumn(name="ID_TECH_USER", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_PERMISSION", referencedColumnName="ID")}
	)
	private Set<PPermission> permissions = new HashSet<>();
	
	// accessors:
	public Set<PPermission> getPermissions()
	{
		return permissions;
	}
	
	@Transient
	public String getTechUserNumber()
	{
		return super.getNumber();
	}

	@Transient
	public void addPermssion(PPermission permission)
	{
		Validate.notNull(permission, "permission must not be null!");
		this.getPermissions().add(permission);
	}

	public void setPermissions(Set<PPermission> permissions)
	{
		this.permissions = permissions;
	}

	// constructors:
	public PTechnicalUser(String techUserNumber)
	{
		super(techUserNumber);
	}

	@SuppressWarnings("unused")
	private PTechnicalUser()
	{
		// JPA
		super();
	}
}