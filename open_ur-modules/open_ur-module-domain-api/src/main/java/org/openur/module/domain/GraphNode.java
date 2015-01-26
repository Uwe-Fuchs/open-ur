package org.openur.module.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Implementors of domain-interfaces should use this as root-object for their own domain-classes.
 * 
 * @author info@uwefuchs.com
 */
public abstract class GraphNode
	implements Serializable
{
	private static final long serialVersionUID = 1L;
  
	@Override
  public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/**
	 * equals-method must be overwritten in implementaion-classes. {@link IIdentifiableEntity} delivers an Implementation
	 * that could act as a role-model for a meaningful implemenation.
	 */
	@Override
	public abstract boolean equals(Object obj);
}
