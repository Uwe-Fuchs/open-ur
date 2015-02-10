CREATE TABLE hibernate_sequences (
 sequence_name varchar(255),
 sequence_next_hi_value integer 
);

CREATE TABLE APPLICATION (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  APPLICATION_NAME varchar(255) NOT NULL
);

ALTER TABLE APPLICATION ADD PRIMARY KEY (ID);

CREATE UNIQUE INDEX UNQ_APPLICATION_NAME ON APPLICATION (APPLICATION_NAME);


CREATE TABLE ADDRESS (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  CARE_OF varchar(255) DEFAULT NULL,
  CITY varchar(255) DEFAULT NULL,
  COUNTRY_CODE varchar(255) NOT NULL,
  PO_BOX varchar(255) DEFAULT NULL,
  POST_CODE varchar(255) NOT NULL,
  STREET varchar(255) DEFAULT NULL,
  STREET_NO varchar(255) DEFAULT NULL
);

ALTER TABLE ADDRESS ADD PRIMARY KEY (ID);


CREATE TABLE USER_STRUCTURE_BASE (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  NUMBER varchar(50) NOT NULL,
  STATUS int(11) NOT NULL
);

ALTER TABLE USER_STRUCTURE_BASE ADD PRIMARY KEY (ID);

CREATE UNIQUE INDEX UNQ_NUMBER ON USER_STRUCTURE_BASE (NUMBER);


CREATE TABLE PERSON (
  ID bigint(20) NOT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  FAX_NO varchar(20) DEFAULT NULL,
  FIRSTNAME varchar(50) DEFAULT NULL,
  GENDER varchar(255) DEFAULT NULL,
  HOME_EMAIL varchar(255) DEFAULT NULL,
  HOME_PHONE_NO varchar(20) DEFAULT NULL,
  LASTNAME varchar(50) NOT NULL,
  MOBILE_NO varchar(20) DEFAULT NULL,
  PHONE_NO varchar(20) DEFAULT NULL,
  TITLE varchar(255) DEFAULT NULL,
  HOME_ADDRESS_ID bigint(20) DEFAULT NULL
);

ALTER TABLE PERSON ADD PRIMARY KEY (ID);
ALTER TABLE PERSON ADD FOREIGN KEY (ID) REFERENCES USER_STRUCTURE_BASE (ID);

CREATE INDEX IDX_PERSON_ADDRESS ON PERSON (HOME_ADDRESS_ID);
ALTER TABLE PERSON ADD FOREIGN KEY (HOME_ADDRESS_ID) REFERENCES ADDRESS (ID);


CREATE TABLE PERSONS_APPS (
  ID_PERSON bigint(20) NOT NULL,
  ID_APPLICATION bigint(20) NOT NULL
);

ALTER TABLE PERSONS_APPS ADD PRIMARY KEY (ID_PERSON, ID_APPLICATION);
ALTER TABLE PERSONS_APPS ADD FOREIGN KEY (ID_PERSON) REFERENCES PERSON (ID);
ALTER TABLE PERSONS_APPS ADD FOREIGN KEY (ID_APPLICATION) REFERENCES APPLICATION (ID);


CREATE TABLE TECHNICAL_USER (
  ID bigint(20) NOT NULL
);

ALTER TABLE TECHNICAL_USER ADD PRIMARY KEY (ID);
ALTER TABLE TECHNICAL_USER ADD FOREIGN KEY (ID) REFERENCES USER_STRUCTURE_BASE (ID);


CREATE TABLE ORGANIZATIONAL_UNIT (
  ID bigint(20) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  NAME varchar(50) NOT NULL,
  SHORT_NAME varchar(20) DEFAULT NULL,
  ADDRESS_ID bigint(20) DEFAULT NULL,
  ROOT_OU_ID bigint(20) DEFAULT NULL,
  SUPER_OU_ID bigint(20) DEFAULT NULL
);

ALTER TABLE ORGANIZATIONAL_UNIT ADD PRIMARY KEY (ID);
ALTER TABLE ORGANIZATIONAL_UNIT ADD FOREIGN KEY (ID) REFERENCES USER_STRUCTURE_BASE (ID);

CREATE INDEX IDX_ORG_UNIT_SUPER_ORG_UNIT ON ORGANIZATIONAL_UNIT (SUPER_OU_ID);
ALTER TABLE ORGANIZATIONAL_UNIT ADD FOREIGN KEY (SUPER_OU_ID) REFERENCES ORGANIZATIONAL_UNIT (ID);

CREATE INDEX IDX_ORG_UNIT_ROOT_ORG_UNIT ON ORGANIZATIONAL_UNIT (ROOT_OU_ID);
ALTER TABLE ORGANIZATIONAL_UNIT ADD FOREIGN KEY (ROOT_OU_ID) REFERENCES ORGANIZATIONAL_UNIT (ID);

CREATE INDEX IDX_ORG_UNIT_ADDRESS ON ORGANIZATIONAL_UNIT (ADDRESS_ID);
ALTER TABLE ORGANIZATIONAL_UNIT ADD FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS (ID);


CREATE TABLE ORG_UNIT_MEMBER (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  ORG_UNIT_ID bigint(20) NOT NULL,
  PERSON_ID bigint(20) NOT NULL
);

ALTER TABLE ORG_UNIT_MEMBER ADD PRIMARY KEY (ID);

CREATE INDEX IDX_ORG_UNIT_MEMBER_ORG_UNIT ON ORG_UNIT_MEMBER (ORG_UNIT_ID);
ALTER TABLE ORG_UNIT_MEMBER ADD FOREIGN KEY (ORG_UNIT_ID) REFERENCES ORGANIZATIONAL_UNIT (ID);

CREATE INDEX IDX_ORG_UNIT_MEMBER_PERSON ON ORG_UNIT_MEMBER (PERSON_ID);
ALTER TABLE ORG_UNIT_MEMBER ADD FOREIGN KEY (PERSON_ID) REFERENCES PERSON (ID);


CREATE TABLE PERMISSION (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  DESCRIPTION varchar(255) DEFAULT NULL,
  PERMISSION_NAME varchar(50) NOT NULL,
  PERMISSION_SCOPE varchar(255) NOT NULL,
  APPLICATION_ID bigint(20) NOT NULL
);

ALTER TABLE PERMISSION ADD PRIMARY KEY (ID);

CREATE UNIQUE INDEX UNQ_PERMISSION_NAME ON PERMISSION (PERMISSION_NAME);

CREATE INDEX IDX_PERMISSION_APPLICATION ON PERMISSION (APPLICATION_ID);
ALTER TABLE PERMISSION ADD FOREIGN KEY (APPLICATION_ID) REFERENCES APPLICATION (ID);


CREATE TABLE ROLE (
  ID bigint(20) NOT NULL,
  CREATED datetime NOT NULL,
  MODIFIED datetime,
  DESCRIPTION varchar(255) DEFAULT NULL,
  ROLE_NAME varchar(50) NOT NULL
);

ALTER TABLE ROLE ADD PRIMARY KEY (ID);

CREATE UNIQUE INDEX UNQ_ROLE_NAME ON ROLE (ROLE_NAME);


CREATE TABLE ROLES_PERMISSIONS (
  ID_ROLE bigint(20) NOT NULL,
  ID_PERMISSION bigint(20) NOT NULL
);

ALTER TABLE ROLES_PERMISSIONS ADD PRIMARY KEY (ID_ROLE, ID_PERMISSION);
ALTER TABLE ROLES_PERMISSIONS ADD FOREIGN KEY (ID_ROLE) REFERENCES ROLE (ID);
ALTER TABLE ROLES_PERMISSIONS ADD FOREIGN KEY (ID_PERMISSION) REFERENCES PERMISSION (ID);


CREATE TABLE ROLES_MEMBERS (
  ID_MEMBER bigint(20) NOT NULL,
  ID_ROLE bigint(20) NOT NULL
);

ALTER TABLE ROLES_MEMBERS ADD PRIMARY KEY (ID_MEMBER, ID_ROLE);
ALTER TABLE ROLES_MEMBERS ADD FOREIGN KEY (ID_MEMBER) REFERENCES ORG_UNIT_MEMBER (ID);
ALTER TABLE ROLES_MEMBERS ADD FOREIGN KEY (ID_ROLE) REFERENCES ROLE (ID);
