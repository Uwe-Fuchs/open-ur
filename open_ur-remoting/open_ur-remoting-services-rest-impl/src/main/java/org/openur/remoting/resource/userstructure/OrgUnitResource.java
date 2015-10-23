package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.IOrgUnitServices;

@Path(OrgUnitResource.ORGUNIT_RESOURCE_PATH)
public class OrgUnitResource
	implements IOrgUnitServices
{
	public static final String ORGUNIT_RESOURCE_PATH = IUserResource.USER_RESOURCE_PATH + "orgunit/";
	public static final String ORGUNIT_PER_ID_RESOURCE_PATH = "id/";
	public static final String ORGUNIT_PER_NUMBER_RESOURCE_PATH = "number/";
	public static final String ALL_ORGUNITS_RESOURCE_PATH = "all";
	public static final String SUB_ORGUNITS_RESOURCE_PATH = "sub/";
	public static final String ALL_ROOT_ORGUNITS_RESOURCE_PATH = "root";
	
	@Inject
	private IOrgUnitServices orgUnitServices;

	@Override
	@GET
	@Path(ORGUNIT_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitById(@PathParam("id") String orgUnitId, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.findOrgUnitById(orgUnitId, inclMembersRoles);
	}

	@Override
	@GET
	@Path(ORGUNIT_PER_NUMBER_RESOURCE_PATH + "{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitByNumber(@PathParam("number") String orgUnitNumber, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.findOrgUnitByNumber(orgUnitNumber, inclMembersRoles);
	}

	@Override
	@GET
	@Path(ALL_ORGUNITS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		return orgUnitServices.obtainAllOrgUnits();
	}

	@Override
	@GET
	@Path(SUB_ORGUNITS_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(@PathParam("id") String orgUnitId, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.obtainSubOrgUnitsForOrgUnit(orgUnitId, inclMembersRoles);
	}

	@Override
	@GET
	@Path(ALL_ROOT_ORGUNITS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		return orgUnitServices.obtainRootOrgUnits();
	}
}
