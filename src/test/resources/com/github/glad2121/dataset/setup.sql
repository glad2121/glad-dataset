drop table if exists T_USER_ROLE;
drop table if exists T_USER;
drop table if exists T_ROLE;
drop table if exists T_SYSTEM;

create table T_SYSTEM (
  CODE        varchar(20)  not null,
  NAME        varchar(40),
  CREATED_BY  varchar(20)  default current_user(),
  CREATED     timestamp    default current_timestamp(),
  UPDATED_BY  varchar(20)  default current_user(),
  UPDATED     timestamp    default current_timestamp(),
  constraint PK_SYSTEM_CODE primary key (CODE)
);

create table T_ROLE (
  ID          bigint       not null,
  SYSTEM_CODE varchar(20)  not null,
  CODE        varchar(20)  not null,
  NAME        varchar(40),
  CREATED_BY  varchar(20)  default current_user(),
  CREATED     timestamp    default current_timestamp(),
  UPDATED_BY  varchar(20)  default current_user(),
  UPDATED     timestamp    default current_timestamp(),
  constraint PK_ROLE_ID     primary key (ID),
  constraint UQ_ROLE_CODE   unique (SYSTEM_CODE, CODE),
  constraint FK_ROLE_SYSTEM foreign key (SYSTEM_CODE) references T_SYSTEM
);

create table T_USER (
  ID          bigint       not null,
  USERNAME    varchar(20)  not null,
  PASSWORD    varchar(20),
  CREATED_BY  varchar(20)  default current_user(),
  CREATED     timestamp    default current_timestamp(),
  UPDATED_BY  varchar(20)  default current_user(),
  UPDATED     timestamp    default current_timestamp(),
  constraint PK_USER_ID       primary key (ID),
  constraint UQ_USER_USERNAME unique (USERNAME)
);

create table T_USER_ROLE (
  USER_ID     bigint       not null,
  ROLE_ID     bigint       not null,
  CREATED_BY  varchar(20)  default current_user(),
  CREATED     timestamp    default current_timestamp(),
  UPDATED_BY  varchar(20)  default current_user(),
  UPDATED     timestamp    default current_timestamp(),
  constraint PK_USER_ROLE_PK   primary key (USER_ID, ROLE_ID),
  constraint FK_USER_ROLE_USER foreign key (USER_ID) references T_USER,
  constraint FK_USER_ROLE_ROLE foreign key (ROLE_ID) references T_ROLE
);

insert into T_SYSTEM (CODE, NAME) values ('GDS', 'GLAD DataSet');

insert into T_ROLE (ID, SYSTEM_CODE, CODE) values (1001, 'GDS', 'admin');
insert into T_ROLE (ID, SYSTEM_CODE, CODE) values (1002, 'GDS', 'user' );

insert into T_USER (ID, USERNAME, PASSWORD) values (1001, 'hagumi',   'hagumi'  );
insert into T_USER (ID, USERNAME, PASSWORD) values (1002, 'takemoto', 'takemoto');

insert into T_USER_ROLE (USER_ID, ROLE_ID) values (1001, 1001);
insert into T_USER_ROLE (USER_ID, ROLE_ID) values (1001, 1002);
insert into T_USER_ROLE (USER_ID, ROLE_ID) values (1002, 1002);
