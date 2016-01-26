package org.openur.module.domain.userstructure.person;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.GraphNode;
import org.openur.module.util.data.Gender;
import org.openur.module.util.data.Title;

public class Name
	extends GraphNode
	implements Comparable<Name>
{
	private static final long serialVersionUID = 8697594269628383855L;
	private static ResourceBundle TRANSLATION_HELPER;

	// properties:
	private final Gender gender;
	private final Title title;
	private final String firstName;
	private final String lastName;

	// constructor:
	private Name(Gender gender, Title title, String firstName, String lastName)
	{
		super();
		
		this.gender = gender;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// factory-methods:
	public static Name create(Gender gender, String firstName, String lastName)
	{
		return create(gender, Title.NONE, firstName, lastName);
	}

	public static Name create(Gender gender, Title title, String firstName, String lastName)
	{
		TRANSLATION_HELPER = ResourceBundle.getBundle("org.openur.module.domain.Messages");

		Validate.notEmpty(lastName, "last name must not be empty!");
		Validate.notNull(title, "title must not be null! Use other constructor instead!");

		return new Name(gender, title, firstName, lastName);
	}

	// accessors:
	public String getFirstName()
	{
		return firstName;
	}

	public Gender getGender()
	{
		return gender;
	}

	public String getLastName()
	{
		return lastName;
	}

	public Title getTitle()
	{
		return title;
	}

	public String getFirstAndLastname()
	{
		return new StringBuilder()
			.append(StringUtils.isNotEmpty(firstName) ? firstName : StringUtils.EMPTY)
			.append(StringUtils.isNotEmpty(firstName) ? " " : StringUtils.EMPTY)
			.append(lastName)
			.toString();
	}

	public String getFullNameWithTitle()
	{
		return new StringBuilder()
			.append(this.gender != null ? TRANSLATION_HELPER.getString(this.gender.getResourceBundleLookupKey()) : StringUtils.EMPTY)
			.append(this.gender != null ? " " : StringUtils.EMPTY)
			.append(this.title != null && this.title.isPartOfName() ? TRANSLATION_HELPER.getString(title.getResourceBundleLookupKey()) : StringUtils.EMPTY)
			.append(this.title != null && this.title.isPartOfName() ? " " : StringUtils.EMPTY)
			.append(this.getFirstAndLastname())
			.toString();
	}

	// operations:
	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString()
	{
		return getFirstAndLastname();
	}

	@Override
	public int compareTo(Name o)
	{
		int comparison = new CompareToBuilder()
			.append(this.getLastName(), o.getLastName())
			.append(this.getFirstName(), o.getFirstName())
			.append(this.getGender(), o.getGender())
			.append(this.getFullNameWithTitle(), o.getFullNameWithTitle())
			.toComparison();

		return comparison;
	}
}
