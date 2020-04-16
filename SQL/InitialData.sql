INSERT INTO PERMISSIONS (NAME) VALUES 
('can_create_user'),
('can_update_user'),
('can_read_user'),
('can_delete_user');

INSERT INTO ROLES (NAME) VALUES 
		('role_admin'),('role_user');

INSERT INTO PERMISSION_ROLE (PERMISSION_ID, ROLE_ID) VALUES 
    (1,1), /* can_create_user assigned to role_admin */
    (2,1), /* can_update_user assigned to role_admin */
    (3,1), /* can_read_user assigned to role_admin */
    (4,1), /* can_delete_user assigned to role_admin */
    (3,2);  /* can_read_user assigned to role_user */
    
INSERT INTO USERS (
    USERNAME,PASSWORD,
    EMAIL,ENABLED,ACCOUNT_EXPIRED,CREDENTIALS_EXPIRED,ACCOUNT_LOCKED) VALUES (
    'a','a',
    'william@gmail.com',true,false,false,false),
    ('user','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
    'john@gmail.com',true,false,false,false);
     
INSERT INTO ROLE_USER (ROLE_ID, USER_ID)
    VALUES 
    (1, 1) /* role_admin assigned to admin user */,
    (2, 2) /* role_user assigned to user user */ ;
    

=========================== Database design queries ================================
create table APP_INSTANCE_DATA
(
     APP_ID SERIAL PRIMARY KEY,
     APP_NAME CHARACTER VARYING(255) NOT NULL,
     APP_TYPE CHARACTER VARYING(255) NOT NULL
     
);

create table APP_INSTANCE_PAYLOAD
(   
    APP_ID INTEGER NOT NULL,
    PAYLOAD BYTEA NOT NULL
);

create table TEAM_APPS
(
APP_ID INTEGER NOT NULL,
TEAM_ID INTEGER NOT NULL
);

create table TEAMS
(
    TEAM_ID INTEGER NOT NULL,
    TEAM_NAME CHARACTER VARYING(255) NOT NULL,
    TEAM_DL CHARACTER VARYING(255) NOT NULL
);

create table USER_TEAM_RELATION
(
USER_ID INTEGER NOT NULL,
TEAM_ID INTEGER NOT NULL
);

create table PERSONAL_APPS
(
USER_ID INTEGER NOT NULL,
APP_ID INTEGER NOT NULL
);

create table GLOBAL_APPS
(
ID SERIAL PRIMARY KEY,
APP_ID INTEGER NOT NULL
);

create table CONTEXT_MENU_OPTIONS
(
ID SERIAL PRIMARY KEY,
TYPE CHARACTER VARYING(255) NOT NULL,
OPTION_LIST CHARACTER VARYING(255) NOT NULL
);
(
APP_ID INTEGER NOT NULL
);

=================== Samples insert query for CONTEXT_MENU_OPTIONS =====================
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (1,'desktop-wallpaper','New Folder,Upload,Refresh,Copy,Paste');
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (2,'task-bar','Task bar option 1,Task bar option 2,Task bar option 3,Task bar option 4');
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (3,'start-menu-button','My Bookmarks,My Notes,Logout');
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (4,'file','Download,Update,Bookmark,Copy,Rename,Delete,History');
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (5,'folder','Open folder,Open folder in new window,Bookmark folder,Copy folder,Rename folder,Delete folder');
insert into CONTEXT_MENU_OPTIONS (id,type,option_list) values (6,'desktop-item-view','New Sprint 1,New User Story,Refresh,Copy,Paste,Upload');


drop table PERMISSIONS;
drop table ROLES;
drop table PERMISSION_ROLE;
drop table USERS;
drop table ROLE_USER;
drop table APP_INSTANCE_DATA;
drop table APP_INSTANCE_PAYLOAD;
drop table TEAM_APPS;
drop table TEAMS;
drop table USER_TEAM_RELATION;
drop table PERSONAL_APPS;
drop table GLOBAL_APPS;
drop table CONTEXT_MENU_OPTIONS;