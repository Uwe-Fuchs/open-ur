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
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.openur.remoting.client.ws.rs.secure_api.AbstractResourceClient;
import org.openur.remoting.resource.userstructure.OrgUnitResource;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.OrgUnitProvider;

public class OrgUnitResourceClient
	extends AbstractResourceClient
	implements IOrgUnitServices
{
	@Inject
	public OrgUnitResourceClient(String baseUrl)
	{
		super(baseUrl + OrgUnitResource.ORGUNIT_RESOURCE_PATH, OrgUnitProvider.class, IdentifiableEntitySetProvider.class);
	}

	@Override
	public IOrganizationalUnit findOrgUnitById(String orgUnitId, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(ORGUNIT_PER_ID_RESOURCE_PATH)
				.append(orgUnitId)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OrgUnitFull.class);
	}

	@Override
	public IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(ORGUNIT_PER_NUMBER_RESOURCE_PATH)
				.append(orgUnitNumber)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();

		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OrgUnitFull.class);
	}

	@Override
	public Set<IOrganizationalUnit> obtainAllOrgUnits()
	{
		return performRestCall_GET(
				ALL_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<IOrganizationalUnit>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));
	}

	@Override
	public Set<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, Boolean inclMembersRoles)
	{
		String url = new StringBuilder()
				.append(SUB_ORGUNITS_RESOURCE_PATH)
				.append(orgUnitId)
				.append("?inclMembers=")
				.append(inclMembersRoles.toString())
				.toString();
		
		return performRestCall_GET(
					url, MediaType.APPLICATION_JSON, new GenericType<Set<IOrganizationalUnit>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));
	}

	@Override
	public Set<IOrganizationalUnit> obtainRootOrgUnits()
	{
		return performRestCall_GET(
				ALL_ROOT_ORGUNITS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<IOrganizationalUnit>>(new ParameterizedTypeImpl(Set.class, OrgUnitFull.class)));
	}
}
