package org.openur.module.domain.userstructure;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.openur.module.domain.GraphNode;
import org.openur.module.util.exception.OpenURRuntimeException;

/**
 * Simple wrapper to model an e-mail address. This class can create an
 * InternetAddress on request.
 * 
 * @author uwe@uwefuchs.de
 */
public class EMailAddress
	extends GraphNode
	implements Comparable<EMailAddress>
{
	private static final long serialVersionUID = -3031588741480667424L;

	private final String email;

	public EMailAddress(String email)
	{
		this.email = email;

		try
		{
			new InternetAddress(email, true);
		} catch (AddressException e)
		{
			throw new IllegalArgumentException("email address is not valid: " + email, e);
		}
	}

	public InternetAddress convertToInternetAddress(String name)
	{
		try
		{
			return new InternetAddress(this.email, name, "UTF8");
		} catch (UnsupportedEncodingException e)
		{
			throw new OpenURRuntimeException("encoding not supported", e);
		}
	}

	public String getAsPlainEMailAddress()
	{
		return this.email;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof EMailAddress))
		{
			return false;
		}

		EMailAddress adr = (EMailAddress) obj;

		return this.getAsPlainEMailAddress().equalsIgnoreCase(adr.getAsPlainEMailAddress());
	}

	@Override
	public String toString()
	{
		return this.getAsPlainEMailAddress();
	}

	@Override
	public int compareTo(EMailAddress adr)
	{
		return this.getAsPlainEMailAddress().compareToIgnoreCase(adr.getAsPlainEMailAddress());
	}
}
