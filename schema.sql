-- domínio: sistema de gerenciamento de vendas com usuários, vendedores, regiões, lojas, categorias, produtos e clientes.
-- inclui um funil de vendas e um log de ações para rastrear mudanças.

-- criação do usuário ifsales

drop user ifsales cascade;

create user ifsales identified by admin
default tablespace users temporary tablespace temp
quota unlimited on users;

grant create session, create table, create view
    , create procedure, create trigger, create sequence
    , select any table, insert any table, drop any table
    , create public synonym to ifsales;

-- remoção de tabelas e sequências existentes

begin
    for table_name in (
        select table_name from user_tables
        where table_name in ('USERS', 'SALESPERSONS', 'REGIONS', 'STORES', 'CATEGORIES',
                             'CUSTOMERS', 'PRODUCTS', 'FUNNEL', 'ACTION_LOGS')
        ) loop
            execute immediate 'drop table ifsales.' || table_name.table_name || ' cascade constraints';
        end loop;

    for sequence_name in (
        select sequence_name from user_sequences
        where sequence_name in ('USERS_SEQ', 'SALESPERSONS_SEQ', 'REGIONS_SEQ', 'STORES_SEQ',
                                'CATEGORIES_SEQ', 'PRODUCTS_SEQ', 'CUSTOMERS_SEQ', 'FUNNEL_SEQ', 'ACTION_LOGS_SEQ')
        ) loop
            execute immediate 'drop sequence ifsales.' || sequence_name.sequence_name;
        end loop;
end;
/

-- criação de sequências para auto incremento

create sequence ifsales.users_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.salespersons_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.regions_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.stores_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.categories_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.products_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.customers_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.funnel_seq start with 1 increment by 1 nocycle nocache;
create sequence ifsales.action_logs_seq start with 1 increment by 1 nocycle nocache;

-- criação das tabelas

create table ifsales.users (
     id int primary key
    ,email varchar2(100) not null
    ,password varchar(255) not null
    ,constraint uq_user_email unique (email)
);

create table ifsales.salespersons (
     id int primary key
    ,name varchar2(100) not null
    ,email varchar2(100) not null
    ,phone varchar2(16) not null
    ,active number(1) default 1 not null
    ,constraint uq_salesperson_email unique (email)
);

create table ifsales.regions (
     id int primary key
    ,region_name varchar2(50) not null
    ,city varchar2(50) not null
    ,state varchar2(50) not null
    ,constraint uq_region_name unique (region_name)
);

create table ifsales.stores (
     id int primary key
    ,store_name varchar2(100) not null
    ,store_cnpj varchar2(100) not null
    ,region_id  int not null
    ,address    varchar2(100) not null
    ,phone      varchar2(16) not null
    ,foreign key (region_id) references ifsales.regions(id)
);

create table ifsales.categories (
     id int primary key
    ,category_name varchar2(100) not null
    ,description varchar2(255) not null
    ,constraint uq_category_name unique (category_name)
);

create table ifsales.products (
     id int primary key
    ,brand varchar2(100) not null
    ,model varchar2(100) not null
    ,model_year int not null
    ,price number(19, 2) not null
    ,category_id int not null
    ,total_sales number(10) default 0
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
    ,constraint uq_customer_cpf unique (cpf)
);

create table ifsales.funnel (
     id int primary key
    ,customer_id int not null
    ,salesperson_id int not null
    ,store_id int not null
    ,product_id int not null
    ,paid_date date not null
    ,discount number(5,2)
    ,product_quantity number(10) default 1 not null
    ,foreign key (customer_id) references ifsales.customers(id)
    ,foreign key (salesperson_id) references ifsales.salespersons(id)
    ,foreign key (store_id) references ifsales.stores(id)
    ,foreign key (product_id) references ifsales.products(id)
    ,constraint uq_funnel unique (customer_id, salesperson_id, store_id, product_id, paid_date)
);

create table ifsales.action_logs (
    id int primary key,
    table_name varchar2(100) not null,
    action varchar2(50) not null,
    action_date timestamp default current_timestamp,
    details varchar2(4000)
);

-- trigger para atualização da quantidade de vendas de produtos

create or replace trigger ifsales.trg_update_product_sales
    after insert or update or delete on ifsales.funnel
    for each row
begin
    if deleting then
        update ifsales.products
        set total_sales = total_sales - :old.product_quantity
        where id = :old.product_id;
    end if;
    if inserting then
        update ifsales.products
        set total_sales = total_sales + :new.product_quantity
        where id = :new.product_id;
    end if;
    if updating then
        update ifsales.products
        set total_sales = total_sales - :old.product_quantity + :new.product_quantity
        where id = :new.product_id;
    end if;
end;
/

-- inserção de dados iniciais

insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Caua Rufino', 'caua@gmail.com', '(16) 99633-7792', 1);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Nathan Ligabo', 'nathzinho@gmail.com', '(16) 99522-7194', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Giovana Trevizan', 'trevizan.gio@gmail.com', '(16) 92334-7142', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Igor filipi', 'f.igor@gmail.com', '(16) 99331-4564', 0);
insert into ifsales.salespersons values (ifsales.salespersons_seq.nextval, 'Tonhao recordista', 'recordista@gmail.com', '(16) 99999-9999', 1);
/

select * from ifsales.users;
select * from ifsales.salespersons;