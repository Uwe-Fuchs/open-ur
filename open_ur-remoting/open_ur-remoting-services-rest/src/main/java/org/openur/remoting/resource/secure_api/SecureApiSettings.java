package org.openur.remoting.resource.secure_api;

/**
 * all possible security settings for building a basic filter chain when starting a Open-UR-Server-App.
 * 
 * @author info@uwefuchs.com
 */
public enum SecureApiSettings
{
	NO_SECURITY, BASIC_AUTH, BASIC_AUTH_PERMCHECK, DIGEST_AUTH, DIGEST_AUTH_PERMCHECK
}
