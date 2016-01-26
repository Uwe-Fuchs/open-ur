package org.openur.module.util.processing;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ObjectsComparator
{
	public static boolean compareLocalDateTimes(LocalDateTime first, LocalDateTime second)
	{
		if (first == null && second == null)
		{
			return true;
		}

		if (first == null || second == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(first.getYear(), second.getYear())
				.append(first.getMonth(), second.getMonth())
				.append(first.getDayOfMonth(), second.getDayOfMonth())
				.append(first.getHour(), second.getHour())
				.append(first.getMinute(), second.getMinute())
				.append(first.getSecond(), second.getSecond())
				.isEquals();
	}
}
