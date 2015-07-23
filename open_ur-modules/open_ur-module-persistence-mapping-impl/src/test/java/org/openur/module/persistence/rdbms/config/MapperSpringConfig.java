package org.openur.module.persistence.rdbms.config;

import org.openur.module.persistence.mapper.rdbms.AddressMapper;
import org.openur.module.persistence.mapper.rdbms.ApplicationMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper.OrgUnitMemberMapper;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value="testMappers")
public class MapperSpringConfig
{
	public MapperSpringConfig()
	{
		super();
	}

	@Bean(name = "addressMapper")
	public AddressMapper addressMapper()
	{		
		return new AddressMapper();
	}

	@Bean(name = "applicationMapper")
	public ApplicationMapper applicationMapper()
	{		
		return new ApplicationMapper();
	}

	@Bean(name = "personMapper")
	public PersonMapper personMapper()
	{		
		return new PersonMapper();
	}

	@Bean(name = "technicalUserMapper")
	public TechnicalUserMapper technicalUserMapper()
	{		
		return new TechnicalUserMapper();
	}

	@Bean(name = "orgUnitMapper")
	public OrganizationalUnitMapper orgUnitMapper()
	{		
		return new OrganizationalUnitMapper();
	}

	@Bean(name = "orgUnitMemberMapper")
	public OrgUnitMemberMapper orgUnitMemberMapper()
	{		
		return new OrgUnitMemberMapper();
	}

	@Bean(name = "permissionMapper")
	public PermissionMapper permissionMapper()
	{		
		return new PermissionMapper();
	}

	@Bean(name = "roleMapper")
	public RoleMapper roleMapper()
	{		
		return new RoleMapper();
	}
}
