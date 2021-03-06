package org.openur.remoting.resource.secure_api;

/**
 * all possible security settings for building a basic filter chain when
 * starting a Open-UR-Server-App.
 * 
 * @author info@uwefuchs.com
 */
public enum SecureApiSettings
{
	NO_SECURITY, PRE_AUTH, BASIC_AUTH, DIGEST_AUTH, PERMCHECK, 
	PRE_BASIC_AUTH, PRE_DIGEST_AUTH, PRE_AUTH_PERMCHECK, BASIC_AUTH_PERMCHECK, DIGEST_AUTH_PERMCHECK, 
	PRE_BASIC_AUTH_PERMCHECK, PRE_DIGEST_AUTH_PERMCHECK
}
