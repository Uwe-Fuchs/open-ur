package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.application.IApplication;
import org.openur.module.persistence.dao.IApplicationDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ApplicationDaoImpl
	implements IApplicationDao
{
	@Inject
	private IEntityDomainObjectMapper<PApplication, ? extends IApplication> applicationMapper;
	
	@Inject
	private ApplicationRepository applicationRepository;

	public ApplicationDaoImpl()
	{
		super();
	}

	/**
	 * searches an application user via it's unique identifier.
	 * 
	 * @param applicationId
	 *          : the unique identifier of the application.
	 * 
	 * @return the application or null, if no application is found.
	 * 
	 * @throws NumberFormatException
	 *           , if applicationId cannot be casted into a long-value.
	 */
	@Override
	public IApplication findApplicationById(String applicationId)
	{
		long applicationIdL = Long.parseLong(applicationId);

		PApplication persistable = applicationRepository.findOne(applicationIdL);

		if (persistable == null)
		{
			return null;
		}

		return applicationMapper.mapFromEntity(persistable);
	}

	@Override
	public IApplication findApplicationByName(String applicationName)
	{
		PApplication persistable = applicationRepository.findApplicationByApplicationName(applicationName);

		if (persistable == null)
		{
			return null;
		}

		return applicationMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IApplication> obtainAllApplications()
	{
		List<PApplication> apps = applicationRepository.findAll();

		return apps
				.stream()
				.map(applicationMapper::mapFromEntity)
				.collect(Collectors.toList());
	}
}
