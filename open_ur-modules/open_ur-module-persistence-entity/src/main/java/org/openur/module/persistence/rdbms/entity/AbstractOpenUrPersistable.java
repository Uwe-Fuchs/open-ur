package org.openur.module.persistence.rdbms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class AbstractOpenUrPersistable
	implements Persistable<Long>
{
	private static final long serialVersionUID = -8187381143373256632L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;

	@Column(name="CREATED", nullable=false)
	private LocalDateTime creationDate;

	@Column(name="MODIFIED")
	private LocalDateTime lastModifiedDate;

	protected AbstractOpenUrPersistable()
	{
		// jpa
		super();
	}

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public boolean isNew()
	{
		return (this.getId() == null);
	}
	
	@Transient
	public String getIdentifier()
	{
		return (this.getId() != null) ? this.getId().toString() : null;
	} 

	public LocalDateTime getCreationDate()
	{
		return creationDate;
	}

	public LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	@PrePersist
	void setCreationDate()
	{
		this.creationDate = LocalDateTime.now();
	}

	@PreUpdate
	void setLastModifiedDate()
	{
		this.lastModifiedDate = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !this.getClass().equals(obj.getClass()))
		{
			return false;
		}

		if (this == obj)
		{
			return true;
		}

		AbstractOpenUrPersistable that = (AbstractOpenUrPersistable) obj;

		return ((this.getId() == null) ? false : this.getId().equals(that.getId()));
	}

  @Override
  public int hashCode()
  {
      int hashCode = 17;

      hashCode += (this.getId() == null ? 0 : this.getId().hashCode() * 31);

      return hashCode;
  }
  
	@Override
  public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
