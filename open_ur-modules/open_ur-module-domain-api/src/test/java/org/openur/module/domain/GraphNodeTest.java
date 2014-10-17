package org.openur.module.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

public class GraphNodeTest
{
	private String id = "obj-id";
	private String objName = "obj-name";
	private GraphNodeFakeImpl obj = new GraphNodeFakeImpl(this.id, this.objName);
	
	@Test
	public void testToString()
	{
		String s = obj.toString();
		assertTrue("should contain class-Name of GraphNodeFakeImpl-instance",
			s.startsWith(this.getClass().getSimpleName() + ".GraphNodeFakeImpl"));
		assertTrue("should contain objName-property",
			s.indexOf("objName=" + obj.objName) > -1);
		assertTrue("should contain uuid", s.indexOf("id=" + obj.id) > -1);
	}

	@Test
	public void testEquals()
	{
		GraphNodeFakeImpl otherObj = new GraphNodeFakeImpl("other-obj-id", "other-obj-name");
		assertFalse("the two apps should be different", otherObj.equals(this.obj));
		otherObj = new GraphNodeFakeImpl(this.id, this.objName);
		assertEquals("equals-method should return true", otherObj, this.obj);
		assertNotSame("should be two different objects anyway", otherObj, this.obj);
	}

	@SuppressWarnings("serial")
	private static class GraphNodeFakeImpl
		extends GraphNode
	{
		private final String id;
		private final String objName;

		public GraphNodeFakeImpl(String id, String objName)
		{
			super();
			this.id = id;
			this.objName = objName;
		}

		@Override
		public boolean equals(Object obj)
		{
			return EqualsBuilder.reflectionEquals(this, obj);
		}
	}
}
