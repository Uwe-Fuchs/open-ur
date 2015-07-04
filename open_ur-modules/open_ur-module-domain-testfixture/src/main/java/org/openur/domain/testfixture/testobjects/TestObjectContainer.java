package org.openur.domain.testfixture.testobjects;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

/**
 * 
 * @author uwe
 *
 */
public class TestObjectContainer
{
	public static final OpenURApplication APP_A;
	public static final OpenURApplication APP_B;
	public static final OpenURApplication APP_C;
	
	public static final OpenURPermission PERMISSION_1_A;
	public static final OpenURPermission PERMISSION_2_A;
	public static final OpenURPermission PERMISSION_1_B;
	public static final OpenURPermission PERMISSION_2_B;
	public static final OpenURPermission PERMISSION_1_C;
	public static final OpenURPermission PERMISSION_2_C;
	
	public static final OpenURRole ROLE_X;
	public static final OpenURRole ROLE_Y;
	public static final OpenURRole ROLE_Z;

	public static final String PERSON_UUID_1 = UUID.randomUUID().toString();
	public static final String PERSON_UUID_2 = UUID.randomUUID().toString();
	public static final String PERSON_UUID_3 = UUID.randomUUID().toString();
	public static final String PERSON_NUMBER_1 = "111aaa";
	public static final String PERSON_NUMBER_2 = "222bbb";
	public static final String PERSON_NUMBER_3 = "333ccc";
	public static final Person PERSON_1;
	public static final Person PERSON_2;
	public static final Person PERSON_3;

	public static final String ROOT_OU_UUID = UUID.randomUUID().toString();
	public static final String SUPER_OU_UUID_1 = UUID.randomUUID().toString();
	public static final String SUPER_OU_UUID_2 = UUID.randomUUID().toString();
	public static final String ORG_UNIT_UUID_A = UUID.randomUUID().toString();
	public static final String ORG_UNIT_UUID_B = UUID.randomUUID().toString();
	public static final String ORG_UNIT_UUID_C = UUID.randomUUID().toString();
	public static final String ROOT_OU_NUMBER = "rootOuNo";
	public static final String SUPER_OU_NUMBER_1 = "superOuNo_1";
	public static final String SUPER_OU_NUMBER_2 = "superOuNo_2";
	public static final String ORG_UNIT_NUMBER_A = "aaa";
	public static final String ORG_UNIT_NUMBER_B = "bbb";
	public static final String ORG_UNIT_NUMBER_C = "ccc";
	public static final AuthorizableOrgUnit ROOT_OU;
	public static final AuthorizableOrgUnit SUPER_OU_1;
	public static final AuthorizableOrgUnit SUPER_OU_2;
	public static final AuthorizableOrgUnit ORG_UNIT_A;
	public static final AuthorizableOrgUnit ORG_UNIT_B;
	public static final AuthorizableOrgUnit ORG_UNIT_C;
	
	static
	{
		// applications:
		APP_A = new OpenURApplicationBuilder("app_A")
				.creationDate(LocalDateTime.now())
				.build();
		APP_B = new OpenURApplicationBuilder("app_B")
				.creationDate(LocalDateTime.now())
				.build();
		APP_C = new OpenURApplicationBuilder("app_C")
				.creationDate(LocalDateTime.now())
				.build();
		
		// permissions:
		PERMISSION_1_A = new OpenURPermissionBuilder("permName_1_A", APP_A)
				.permissionScope(PermissionScope.SELECTED)
				.description("permission description 1A")
				.creationDate(LocalDateTime.now())
				.build();

		PERMISSION_2_A = new OpenURPermissionBuilder("permName_2_A", APP_A)
				.permissionScope(PermissionScope.SELECTED_SUB)
				.description("permission description 2A")
				.creationDate(LocalDateTime.now())
				.build();

		PERMISSION_1_B = new OpenURPermissionBuilder("permName_1_B", APP_B)
				.permissionScope(PermissionScope.SELECTED_SUB)
				.description("permission description 1B")
				.creationDate(LocalDateTime.now())
				.build();

		PERMISSION_2_B = new OpenURPermissionBuilder("permName_2_B", APP_B)
				.permissionScope(PermissionScope.SELECTED)
				.description("permission description 2B")
				.creationDate(LocalDateTime.now())
				.build();
		
		PERMISSION_1_C = new OpenURPermissionBuilder("permName_1_C", APP_C)
				.permissionScope(PermissionScope.SELECTED)
				.description("permission description 1C")
				.creationDate(LocalDateTime.now())
				.build();
		
		PERMISSION_2_C = new OpenURPermissionBuilder("permName_2_C", APP_C)
				.permissionScope(PermissionScope.SELECTED_SUB)
				.description("permission description 2C")
				.creationDate(LocalDateTime.now())
				.build();
		
		// roles:
		ROLE_X = new OpenURRoleBuilder("role_X")
				.description("description role_X")
				.creationDate(LocalDateTime.now())
				.permissions(new HashSet<OpenURPermission>(Arrays.asList(PERMISSION_1_A, PERMISSION_1_B)))
				.build();

		ROLE_Y = new OpenURRoleBuilder("role_Y")
				.description("description role_Y")
				.creationDate(LocalDateTime.now())
				.permissions(new HashSet<OpenURPermission>(Arrays.asList(PERMISSION_1_C, PERMISSION_2_A)))
				.build();

		ROLE_Z = new OpenURRoleBuilder("role_Z")
				.description("description role_Z")
				.creationDate(LocalDateTime.now())
				.permissions(new HashSet<OpenURPermission>(Arrays.asList(PERMISSION_2_B, PERMISSION_2_C)))
				.build();
		
		// persons:
		// person 1:
		Name name_1 = Name.create(Gender.MALE, "male_first_name_1", "last_name_1");
		
		Address address = new AddressBuilder("11")
				.country(Country.byCode("DE"))
				.city("city_1")
				.street("street_1")
				.streetNo("11")
				.poBox("poBox_1")
				.build();

		Set<OpenURApplication> applications = new HashSet<>(Arrays.asList(APP_A, APP_A));
		
		PERSON_1 = new PersonBuilder(PERSON_NUMBER_1, name_1)
				.identifier(PERSON_UUID_1)
				.creationDate(LocalDateTime.now())
				.status(Status.ACTIVE)
				.emailAddress(EMailAddress.create("office@name_1.com"))
				.phoneNumber("0049111111111")
				.faxNumber("0049111111112")
				.mobileNumber("0049111111113")
				.homeAddress(address)
				.homePhoneNumber("0049111111114")
				.homeEmailAddress(EMailAddress.create("home@name_1.de"))
				.applications(applications)
				.build();
		
		// person 2:
		Name name_2 = Name.create(Gender.MALE, Title.DR, "male_first_name_2", "last_name_2");
		
		address = new AddressBuilder("22")
				.country(Country.byCode("UK"))
				.city("city_2")
				.street("street_2")
				.streetNo("22")
				.poBox("poBox_2")
				.build();

		applications = new HashSet<>(Arrays.asList(APP_B, APP_C));
		
		PERSON_2 = new PersonBuilder(PERSON_NUMBER_2, name_2)
				.identifier(PERSON_UUID_3)
				.creationDate(LocalDateTime.now())
				.status(Status.ACTIVE)
				.emailAddress(EMailAddress.create("office@name_2.com"))
				.phoneNumber("00442222222221")
				.faxNumber("00442222222222")
				.mobileNumber("00442222222223")
				.homeAddress(address)
				.homePhoneNumber("00442222222224")
				.homeEmailAddress(EMailAddress.create("home@name_2.co.uk"))
				.applications(applications)
				.build();		
		
		// person 3:
		Name name_3 = Name.create(Gender.FEMALE, "female_first_name_3", "last_name_3");
		
		address = new AddressBuilder("33")
				.country(Country.byCode("FR"))
				.city("city_3")
				.street("street_3")
				.streetNo("33")
				.poBox("poBox_3")
				.build();

		applications = new HashSet<>(Arrays.asList(APP_B, APP_C));
		
		PERSON_3 = new PersonBuilder(PERSON_NUMBER_3, name_3)
				.identifier(PERSON_UUID_3)
				.creationDate(LocalDateTime.now())
				.status(Status.ACTIVE)
				.emailAddress(EMailAddress.create("office@name_3.com"))
				.phoneNumber("0033333333331")
				.faxNumber("0033333333332")
				.mobileNumber("0033333333333")
				.homeAddress(address)
				.homePhoneNumber("0033333333334")
				.homeEmailAddress(EMailAddress.create("home@name_3.fr"))
				.applications(applications)
				.build();
		
		// org-units:		
		address = new AddressBuilder("55555")
				.country(Country.byCode("DE"))
				.city("city_company")
				.street("street_company")
				.streetNo("house_no_company")
				.poBox("poBox_company")
				.build();
		
		ROOT_OU = new AuthorizableOrgUnitBuilder(ROOT_OU_NUMBER, "rootOu")
				.identifier(ROOT_OU_UUID)
				.shortName("short_name_root_ou")
				.address(address)
				.build();
		
		SUPER_OU_1 = new AuthorizableOrgUnitBuilder(SUPER_OU_NUMBER_1, "superOu_1")
				.identifier(SUPER_OU_UUID_1)
				.shortName("short_name_super_ou_1")
				.rootOrgUnit(ROOT_OU)
			  .superOrgUnit(ROOT_OU)
				.build();
		
		SUPER_OU_2 = new AuthorizableOrgUnitBuilder(SUPER_OU_NUMBER_2, "superOu_2")
				.identifier(SUPER_OU_UUID_2)
				.shortName("short_name_super_ou_2")
				.rootOrgUnit(ROOT_OU)
			  .superOrgUnit(ROOT_OU)
				.build();
		
		// org-unit 1:
		AuthorizableMember member_1_A = new AuthorizableMemberBuilder(PERSON_1, ORG_UNIT_UUID_A)
				.roles(Arrays.asList(ROLE_X))
				.build();	
		AuthorizableMember member_2_A = new AuthorizableMemberBuilder(PERSON_2, ORG_UNIT_UUID_A)
				.roles(Arrays.asList(ROLE_Y))
				.build();
		
		ORG_UNIT_A = new AuthorizableOrgUnitBuilder(ORG_UNIT_NUMBER_A, "name_org_unit_A")
				.identifier(ORG_UNIT_UUID_A)
				.creationDate(LocalDateTime.now())
				.status(Status.ACTIVE)
				.shortName("short_name_ou_A")
				.description("description_ou_A")
				.rootOrgUnit(ROOT_OU)
				.superOrgUnit(SUPER_OU_1)
				.authorizableMembers(Arrays.asList(member_1_A, member_2_A))
				.emailAddress(EMailAddress.create("org_unit_A@company.com"))
				.build();
		
		// org-unit 2:
		AuthorizableMember member_1_B = new AuthorizableMemberBuilder(PERSON_1, ORG_UNIT_UUID_B)
				.roles(Arrays.asList(ROLE_Y))
				.build();	
		AuthorizableMember member_3_B = new AuthorizableMemberBuilder(PERSON_3, ORG_UNIT_UUID_B)
				.roles(Arrays.asList(ROLE_Z))
				.build();
		
		ORG_UNIT_B = new AuthorizableOrgUnitBuilder(ORG_UNIT_NUMBER_B, "name_org_unit_B")
				.identifier(ORG_UNIT_UUID_B)
				.creationDate(LocalDateTime.now())
				.status(Status.ACTIVE)
				.shortName("short_name_ou_B")
				.description("description_ou_B")
				.rootOrgUnit(ROOT_OU)
				.superOrgUnit(SUPER_OU_1)
				.authorizableMembers(Arrays.asList(member_1_B, member_3_B))
				.emailAddress(EMailAddress.create("org_unit_B@company.com"))
				.build();
		
		// org-unit 3:
		ORG_UNIT_C = new AuthorizableOrgUnitBuilder(ORG_UNIT_NUMBER_C, "name_org_unit_C")
				.identifier(ORG_UNIT_UUID_C)
				.status(Status.INACTIVE)
				.shortName("short_name_ou_C")
				.description("description_ou_C")
				.rootOrgUnit(ROOT_OU)
				.superOrgUnit(SUPER_OU_2)
				.emailAddress(EMailAddress.create("org_unit_C@company.com"))
				.build();		
	}
}
