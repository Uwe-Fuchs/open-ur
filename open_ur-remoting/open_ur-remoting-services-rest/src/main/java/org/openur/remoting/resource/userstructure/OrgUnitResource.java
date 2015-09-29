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

@Path("userstructure/orgunit/")
public class OrgUnitResource
	implements IOrgUnitServices
{
	@Inject
	private IOrgUnitServices orgUnitServices;

	@Override
	@GET
	@Path("id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitById(@PathParam("id") String orgUnitId, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.findOrgUnitById(orgUnitId, inclMembersRoles);
	}

	@Override
	@GET
	@Path("number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitByNumber(@PathParam("number") String orgUnitNumber, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.findOrgUnitByNumber(orgUnitNumber, inclMembersRoles);
	}

	@Override
	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		return orgUnitServices.obtainAllOrgUnits();
	}

	@Override
	@GET
	@Path("sub/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(@PathParam("id") String orgUnitId, 
		@DefaultValue("false") @QueryParam("inclMembers") Boolean inclMembersRoles)
	{
		return orgUnitServices.obtainSubOrgUnitsForOrgUnit(orgUnitId, inclMembersRoles);
	}

	@Override
	@GET
	@Path("root")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		return orgUnitServices.obtainRootOrgUnits();
	}
}
