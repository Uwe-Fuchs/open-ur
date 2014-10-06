package org.openur.module.domain.application;

import org.openur.module.domain.IIdentifiableEntity;


public interface IApplication
	extends IIdentifiableEntity, Comparable<IApplication>
{
	/**
	 * the application-literal.
	 * 
	 * @return String
	 */
	String getApplicationName();
}
