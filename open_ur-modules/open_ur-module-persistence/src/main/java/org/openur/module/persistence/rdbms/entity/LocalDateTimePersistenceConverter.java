package org.openur.module.persistence.rdbms.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;

public class LocalDateTimePersistenceConverter
	implements AttributeConverter<LocalDateTime, Timestamp>
{
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dbData)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
