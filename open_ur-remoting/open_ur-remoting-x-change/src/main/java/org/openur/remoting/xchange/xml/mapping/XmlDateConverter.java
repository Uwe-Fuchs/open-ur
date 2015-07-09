package org.openur.remoting.xchange.xml.mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class XmlDateConverter
{
	private DatatypeFactory df;

	public XmlDateConverter(DatatypeFactory df)
	{
		this.df = df;
	}

	public XmlDateConverter()
	{
		try
		{
			this.df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	public XMLGregorianCalendar convertDateToXml(LocalDate otherDate)
	{
		if (otherDate == null)
		{
			return null;
		}
		
		return df.newXMLGregorianCalendarDate(otherDate.getYear(), otherDate.getMonth().getValue(), otherDate.getDayOfMonth(), 0).normalize();
	}

	public LocalDate convertDateFromXml(XMLGregorianCalendar otherDate)
	{
		if (otherDate == null)
		{
			return null;
		}
		
		return LocalDate.of(otherDate.getYear(), otherDate.getMonth(), otherDate.getDay());
	}

	public XMLGregorianCalendar convertDateTimeToXml(LocalDateTime dateTime)
	{
		if (dateTime == null)
		{
			return null;
		}
		
		return df.newXMLGregorianCalendar(
				dateTime.getYear(), dateTime.getMonth().getValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(), 
				dateTime.getSecond(), 0, 0)
			.normalize();
	}

	public LocalDateTime convertDateTimeFromXml(XMLGregorianCalendar timestamp)
	{
		return LocalDateTime.of(
				timestamp.getYear(), timestamp.getMonth(), timestamp.getDay(), timestamp.getHour(), timestamp.getMinute(), timestamp.getSecond(), 0);
	}

}
