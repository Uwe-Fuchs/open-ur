package org.openur.module.domain.application;


public interface IApplication
	extends Comparable<IApplication>
{
	/**
	 * the application-literal.
	 * 
	 * @return String
	 */
	String getApplicationName();
}
