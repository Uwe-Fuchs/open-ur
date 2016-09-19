package org.openur.remoting.resource.secure_api;

import javax.ws.rs.core.Configurable;

public interface ISecurityFilterChainBuilder<C extends Configurable<C>>
{
	/**
	 * 
	 * 
	 * @param settings
	 * @param configurable
	 */
	default void buildSecurityFilterChain(SecureApiSettings settings, C configurable)
	{
		switch (settings)
		{
			case BASIC_AUTH:
				buildFilterChainBasicAuth(configurable);
				break;

			case PRE_AUTH_PERMCHECK:
				buildFilterChainBasicAuth(configurable);
				break;

			case BASIC_AUTH_PERMCHECK:
				buildFilterChainBasicAuthPermCheck(configurable);
				break;
				
			case PRE_BASIC_AUTH:
				buildFilterChainPreBasicAuth(configurable);
				break;
				
			case PRE_BASIC_AUTH_PERMCHECK:
				buildFilterChainPreBasicAuthPermCheck(configurable);
				break;
				
			case DIGEST_AUTH:
				buildFilterChainDigestAuth(configurable);
				break;
				
			case DIGEST_AUTH_PERMCHECK:
				buildFilterChainDigestAuthPermCheck(configurable);
				break;
				
			case PRE_DIGEST_AUTH:
				buildFilterChainPreDigestAuth(configurable);
				break;
				
			case PRE_DIGEST_AUTH_PERMCHECK:
				buildFilterChainPreDigestAuthPermCheck(configurable);
				break;
				
			default:
				break;
		}
	}
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainPreAuthPermCheck(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainBasicAuth(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainPreBasicAuth(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainBasicAuthPermCheck(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainPreBasicAuthPermCheck(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainDigestAuth(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainPreDigestAuth(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainDigestAuthPermCheck(C configurable);
	
	/**
	 * 
	 * 
	 * @param configurable
	 */
	void buildFilterChainPreDigestAuthPermCheck(C configurable);
}
