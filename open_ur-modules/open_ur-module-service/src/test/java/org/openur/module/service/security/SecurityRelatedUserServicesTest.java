package org.openur.module.service.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.security.IRole;
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
	private ISecurityDao securityDao;
	
	@Inject
	private ISecurityRelatedUserServices securityRelatedUserServices;

	@Test
	public void testObtainAllRoles()
	{
		IRole role1 = new OpenURRoleBuilder("role1").build();
		IRole role2 = new OpenURRoleBuilder("role2").build();		
		Mockito.when(securityDao.obtainAllRoles()).thenReturn(Arrays.asList(role1, role2));
		
		Set<IRole> resultSet = securityRelatedUserServices.obtainAllRoles();
		assertTrue(CollectionUtils.isNotEmpty(resultSet));
		assertEquals(2, resultSet.size());
		assertTrue(resultSet.contains(role1));
		assertTrue(resultSet.contains(role2));
	}

	@Test
	public void testFindRoleById()
	{
		final String ROLE_ID_1 = "111aaa";
		final String ROLE_ID_2 = "222bbb";
		IRole role1 = new OpenURRoleBuilder(ROLE_ID_1, "role1").build();
		IRole role2 = new OpenURRoleBuilder(ROLE_ID_2, "role2").build();	
		Mockito.when(securityDao.findRoleById(ROLE_ID_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleById(ROLE_ID_2)).thenReturn(role2);
		
		IRole resultRole = securityRelatedUserServices.findRoleById(ROLE_ID_1);
		assertEquals(resultRole, role1);
		resultRole = securityRelatedUserServices.findRoleById(ROLE_ID_2);
		assertEquals(resultRole, role2);
		resultRole = securityRelatedUserServices.findRoleById("abcdef");
		assertEquals(resultRole, null);
	}

	@Test
	public void testFindRoleByName()
	{
		final String ROLE_NAME_1 = "role1";
		final String ROLE_NAME_2 = "role2";
		IRole role1 = new OpenURRoleBuilder(ROLE_NAME_1).build();
		IRole role2 = new OpenURRoleBuilder(ROLE_NAME_2).build();
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_1)).thenReturn(role1);
		Mockito.when(securityDao.findRoleByName(ROLE_NAME_2)).thenReturn(role2);
		
		IRole resultRole = securityRelatedUserServices.findRoleByName(ROLE_NAME_1);
		assertEquals(resultRole, role1);
		resultRole = securityRelatedUserServices.findRoleByName(ROLE_NAME_2);
		assertEquals(resultRole, role2);
		resultRole = securityRelatedUserServices.findRoleByName("abcdef");
		assertEquals(resultRole, null);	
	}
}
