drop user ifsales cascade;

create user ifsales identified by admin
default tablespace users temporary tablespace temp
quota unlimited on users;

grant create session, create table, create view, create procedure, create trigger, create public synonym to ifsales;
grant insert any table to ifsales;

-- drop all tables and sequences if exist
begin
for table_name in (
        select table_name
        from user_tables
        where table_name in (
            'USERS'
           ,'SALESPERSONS'
           ,'REGIONS'
           ,'STORES'
           ,'CATEGORIES'
           ,'CUSTOMERS'
           ,'PRODUCTS'
           ,'FUNNEL'
        )
    )
    loop
        execute immediate 'DROP TABLE ifsales.' || table_name.table_name || ' CASCADE CONSTRAINTS';
end loop;

for sequence_name in (
        select sequence_name
        from user_sequences
        where sequence_name in (
            'USERS_SEQ'
           ,'SALESPERSONS_SEQ'
           ,'REGIONS_SEQ'
           ,'STORES_SEQ'
           ,'CATEGORIES_SEQ'
           ,'PRODUCTS_SEQ'
           ,'CUSTOMERS_SEQ'
           ,'FUNNEL_SEQ'
        )
    )
    loop
        execute immediate 'DROP SEQUENCE ifsales.' || sequence_name.sequence_name;
end loop;
end;
/

create sequence ifsales.users_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.salespersons_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.regions_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.stores_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.categories_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.products_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.customers_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.funnel_seq start with 1 increment by 1 nocycle nocache;

create table ifsales.users (
     id int primary key
    ,email varchar2(100) not null
    ,password varchar(255) not null
);

create table ifsales.salespersons (
     id int primary key
    ,name varchar2(100) not null
    ,email varchar2(100) not null
    ,phone varchar2(16) not null
    ,active number(1) default 1 not null
);

create table ifsales.regions (
     id int primary key
    ,region_name varchar2(50) not null
    ,city varchar2(50) not null
    ,state varchar2(50) not null
);

create table ifsales.stores (
     id int primary key
    ,store_name varchar2(100) not null
    ,store_cnpj varchar2(100) not null
);

create table ifsales.categories (
     id int primary key
    ,category_name varchar2(100) not null
    ,description varchar2(255) not null
);

create table ifsales.products (
     id int primary key
    ,brand varchar2(100) not null
    ,model varchar2(100) not null
    ,model_year int not null
    ,price number(19, 2) not null
    ,category_id int not null
    ,foreign key (category_id) references ifsales.categories(id)
);

create table ifsales.customers (
     id int primary key
    ,cpf varchar2(15) not null
    ,region_id int not null
    ,first_name varchar2(50) not null
    ,last_name varchar2(50) not null
    ,birth_date date not null
    ,income number(19, 2) not null
    ,mobile varchar2(16) not null
    ,professional_status varchar2(50) not null
    ,foreign key (region_id) references ifsales.regions(id)
);

create table ifsales.funnel (
     id int primary key
    ,customer_id int not null
    ,salesperson_id int not null
    ,store_id int not null
    ,product_id int not null
    ,paid_date date not null
    ,discount number(5,2)
    ,foreign key (customer_id) references ifsales.customers(id)
    ,foreign key (salesperson_id) references ifsales.salespersons(id)
    ,foreign key (store_id) references ifsales.stores(id)
    ,foreign key (product_id) references ifsales.products(id)
);

insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Caua Rufino', 'caua@gmail.com', '(16) 99633-7792', 1);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Nathan Ligabo', 'nathzinho@gmail.com', '(16) 99522-7194', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Giovana Trevizan', 'trevizan.gio@gmail.com', '(16) 92334-7142', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Igor filipi', 'f.igor@gmail.com', '(16) 99331-4564', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Tonhao recordista', 'recordista@gmail.com', '(16) 99999-9999', 1);

select * from ifsales.users;
select * from ifsales.salespersons;
