package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.service.userstructure.IOrgUnitServices;

@Path("/userstructure")
public class OrgUnitResource
	implements IOrgUnitServices
{
	@Inject
	private IOrgUnitServices orgUnitServices;

	@Override
	@GET
	@Path("/orgunit/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitById(@PathParam("id") String orgUnitId)
	{
		return orgUnitServices.findOrgUnitById(orgUnitId);
	}

	@Override
	@GET
	@Path("/orgunit/number/{number}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IAuthorizableOrgUnit findOrgUnitByNumber(@PathParam("number") String orgUnitNumber)
	{
		return orgUnitServices.findOrgUnitByNumber(orgUnitNumber);
	}

	@Override
	@GET
	@Path("/orgunit/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		return orgUnitServices.obtainAllOrgUnits();
	}

	@Override
	@GET
	@Path("/orgunit/sub")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(@PathParam("id") String orgUnitId, @QueryParam("inclMembers") boolean inclMembers)
	{
		return orgUnitServices.obtainSubOrgUnitsForOrgUnit(orgUnitId, inclMembers);
	}

	@Override
	@GET
	@Path("/orgunit/root")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		return orgUnitServices.obtainRootOrgUnits();
	}
}
