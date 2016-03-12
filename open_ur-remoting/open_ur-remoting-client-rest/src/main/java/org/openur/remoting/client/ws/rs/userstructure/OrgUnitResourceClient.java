package org.openur.remoting.client.ws.rs.userstructure;

import static org.openur.remoting.resource.userstructure.OrgUnitResource.ALL_ORGUNITS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ALL_ROOT_ORGUNITS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ORGUNIT_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.ORGUNIT_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.OrgUnitResource.SUB_ORGUNITS_RESOURCE_PATH;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;

public class OrgUnitResourceClient
	extends AbstractResourceClient
	implements IOrgUnitServices
{
	@Inject
	public OrgUnitResourceClient(String baseUrl)
	{
		super(baseUrl, OrgUnitProvider.class, IdentifiableEntitySetProvider.class);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(ORGUNIT_PER_ID_RESOURCE_PATH)
				.append(orgUnitId)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, AuthorizableOrgUnit.class);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(ORGUNIT_PER_NUMBER_RESOURCE_PATH)
				.append(orgUnitNumber)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();

		return performRestCall_GET(url, MediaType.APPLICATION_JSON, AuthorizableOrgUnit.class);
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		return performRestCall_GET(
				ALL_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<IAuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(SUB_ORGUNITS_RESOURCE_PATH)
				.append(orgUnitId)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();
		
		return performRestCall_GET(
					url, MediaType.APPLICATION_JSON, new GenericType<Set<IAuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));
	}

	@Override
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		return performRestCall_GET(
				ALL_ROOT_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<IAuthorizableOrgUnit>>(new ParameterizedTypeImpl(Set.class, AuthorizableOrgUnit.class)));
	}
}
