package org.openur.module.util.data;

public enum Title
{
	NONE, DR, PROF;
	
	public boolean isPartOfName() {
		return this == DR;
	}
	
	public String getResourceBundleLookupKey() {
		return "title." + this.name().toLowerCase();
	}
}
