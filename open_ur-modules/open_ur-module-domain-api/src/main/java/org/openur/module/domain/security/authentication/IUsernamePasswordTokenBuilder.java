package org.openur.module.domain.security.authentication;

/**
 * creates instances with super-type {@link IUsernamePasswordToken}.
 * 
 * @author info@uwefuchs.com
 */
public interface IUsernamePasswordTokenBuilder
	extends IAuthenticationTokenBuilder<IUsernamePasswordToken>
{
	/**
	 * set userName.
	 * 
	 * @param userName
	 * 
	 * @return this instance
	 */
	IUsernamePasswordTokenBuilder userName(String userName);
	
	/**
	 * set passWord.
	 * 
	 * @param passWord
	 * 
	 * @return this instance
	 */
	IUsernamePasswordTokenBuilder passWord(String passWord);
}
