package org.openur.module.service.security;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURRole;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.persistence.security.ISecurityDao;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestSpringConfig.class})
public class SecurityRelatedUserServicesTest
{
	@Inject
	private ISecurityDao dao;
	
	@Inject
	private ISecurityRelatedUserServices securityRelatedUserServices;

	@Before
	public void setUp()
		throws Exception
	{
	}

	@Test
	public void testObtainAllRoles()
	{
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1");
		OpenURRole role1 = new OpenURRole(rb);
		rb = new OpenURRoleBuilder("role2");
		OpenURRole role2 = new OpenURRole(rb);
		List<IRole> roles = new ArrayList<>(2);
		roles.addAll(Arrays.asList(role1, role2));
		
		Mockito.when(dao.obtainAllRoles()).thenReturn(roles);
		
		Set<IRole> result = securityRelatedUserServices.obtainAllRoles();
		assertTrue(CollectionUtils.isNotEmpty(result));
	}

//	@Test
//	public void testFindRoleById()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindRoleByName()
//	{
//		fail("Not yet implemented");
//	}
}
