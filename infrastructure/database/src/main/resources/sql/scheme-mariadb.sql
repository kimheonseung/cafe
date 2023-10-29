alter table if exists category
    drop foreign key if exists FK_CATEGORY_PARENT_CATEGORY_ID;
alter table if exists member
    drop foreign key if exists FK_MEMBER_AUTHORITY_ID_AUTHORITY_ID;
alter table if exists menu
    drop foreign key if exists FK_MENU_CATEGORY_ID_CATEGORY_ID;
alter table if exists option
    drop foreign key if exists FK_OPTION_CATEGORY_ID_CATEGORY_ID;
alter table if exists option
    drop foreign key if exists FK_OPTION_MENU_ID_MENU_ID;
alter table if exists sub_option
    drop foreign key if exists FK_SUB_OPTION_OPTION_ID_OPTION_ID;

drop table if exists authority;
drop table if exists category;
drop table if exists member;
drop table if exists menu;
drop table if exists option;
drop table if exists sub_option;

create table authority (
    id bigint not null auto_increment,
    description varchar(30) default NULL,
    name varchar(30) not null,
    primary key (id)
) engine=InnoDB;

create table category (
    id bigint not null auto_increment,
    parent bigint,
    name varchar(50) not null,
    primary key (id)
) engine=InnoDB;

create table member (
    authority_id bigint,
    create_date datetime(6) not null,
    id bigint not null auto_increment,
    update_date datetime(6) not null,
    username varchar(20) not null,
    name varchar(50) not null,
    nickname varchar(50) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table menu (
    available bit not null,
    category_id bigint,
    id bigint not null auto_increment,
    price bigint not null,
    name varchar(50) not null,
    primary key (id)
) engine=InnoDB;

create table option (
    display_order integer not null,
    category_id bigint,
    id bigint not null auto_increment,
    menu_id bigint,
    name varchar(255) not null,
    type enum ('CATEGORY','MENU') not null,
    primary key (id)
) engine=InnoDB;

create table sub_option (
    display_order integer not null,
    id bigint not null auto_increment,
    option_id bigint,
    price bigint not null,
    name varchar(255) not null,
    primary key (id)
) engine=InnoDB;

alter table if exists authority
    add constraint UK_AUTHORITY_NAME unique (name);
alter table if exists category
    add constraint UK_CATEGORY_NAME unique (name);
alter table if exists member
    add constraint UK_MEMBER_USERNAME unique (username);
alter table if exists member
    add constraint UK_MEMBER_EMAIL unique (email);
alter table if exists menu
    add constraint UK_MENU_NAME unique (name);
alter table if exists option
    add constraint UK_OPTION_NAME unique (name);
alter table if exists sub_option
    add constraint UK_SUB_OPTION_NAME unique (name);
alter table if exists category
    add constraint FK_CATEGORY_PARENT_CATEGORY_ID
        foreign key (parent) references category (id);
alter table if exists member
    add constraint FK_MEMBER_AUTHORITY_ID_AUTHORITY_ID
        foreign key (authority_id) references authority (id);
alter table if exists menu
    add constraint FK_MENU_CATEGORY_ID_CATEGORY_ID
        foreign key (category_id) references category (id);
alter table if exists option
    add constraint FK_OPTION_CATEGORY_ID_CATEGORY_ID
        foreign key (category_id) references category (id);
alter table if exists option
    add constraint FK_OPTION_MENU_ID_MENU_ID
        foreign key (menu_id) references menu (id);
alter table if exists sub_option
    add constraint FK_SUB_OPTION_OPTION_ID_OPTION_ID
        foreign key (option_id) references option (id);
