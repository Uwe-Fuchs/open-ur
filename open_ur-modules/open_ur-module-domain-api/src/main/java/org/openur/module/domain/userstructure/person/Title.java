package org.openur.module.domain.userstructure.person;

public enum Title
{
	NONE,	DR,	PROF;
	
	public boolean isPartOfName() {
		return this == DR;
	}
	
	public String getResourceBundleLookupKey() {
		return "title." + this.name().toLowerCase();
	}
}
