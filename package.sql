-- PACKAGE SPECIFICATION
create or replace package ifsales_pkg
as
function verify_user_exists(p_user_id in ifsales.users.id%type) return boolean;
function verify_salesperson_exists(p_salesperson_id in ifsales.salespersons.id%type) return boolean;
function verify_category_exists(p_category_id in ifsales.categories.id%type) return boolean;
function rec_average_ticket return number;
function rec_average_discount return number;
function rec_total_sales return number;
function rec_total_products_sold return number;
procedure insert_user(
    p_email    in ifsales.users.email%type
   ,p_password in ifsales.users.password%type
);
procedure insert_salesperson(
    p_name    in ifsales.salespersons.name%type
   ,p_email   in ifsales.salespersons.email%type
   ,p_phone   in ifsales.salespersons.phone%type
   ,p_active  in ifsales.salespersons.active%type
);
procedure insert_customer(
    p_cpf                 in ifsales.customers.cpf%type
   ,p_region_id           in ifsales.customers.region_id%type
   ,p_first_name          in ifsales.customers.first_name%type
   ,p_last_name           in ifsales.customers.last_name%type
   ,p_birth_date          in ifsales.customers.birth_date%type
   ,p_income              in ifsales.customers.income%type
   ,p_mobile              in ifsales.customers.mobile%type
   ,p_professional_status in ifsales.customers.professional_status%type
);
procedure insert_product(
    p_brand       in ifsales.products.brand%type
   ,p_model       in ifsales.products.model%type
   ,p_model_year  in ifsales.products.model_year%type
   ,p_price       in ifsales.products.price%type
   ,p_category_id in ifsales.products.category_id%type
);
procedure insert_category(
    p_category_name  in ifsales.categories.category_name%type
   ,p_description    in ifsales.categories.description%type
);
procedure insert_region(
    p_region_name in ifsales.regions.region_name%type
   ,p_city        in ifsales.regions.city%type
   ,p_estate      in ifsales.regions.state%type
);
procedure insert_store(
    p_store_name in ifsales.stores.store_name%type
   ,p_store_cnpj in ifsales.stores.store_cnpj%type
   ,p_region_id  in ifsales.stores.region_id%type
   ,p_address    in ifsales.stores.address%type
   ,p_phone      in ifsales.stores.phone%type
);
procedure insert_funnel(
    p_customer_id      in ifsales.funnel.customer_id%type
   ,p_salesperson_id   in ifsales.funnel.salesperson_id%type
   ,p_store_id         in ifsales.funnel.store_id%type
   ,p_product_id       in ifsales.funnel.product_id%type
   ,p_paid_date        in ifsales.funnel.paid_date%type
   ,p_discount         in ifsales.funnel.discount%type
   ,p_product_quantity in ifsales.funnel.product_quantity%type
);
procedure delete_user(p_user_id in ifsales.users.id%type);
end ifsales_pkg;
/

-- PACKAGE BODY
create or replace package body ifsales_pkg
as
--------------------------------------------------------------------------------------------------------
-- função para verificar se o usuário existe
function verify_user_exists(p_user_id in ifsales.users.id%type)
return boolean
is
v_count number;
begin
    select count(*) into v_count from users where id = p_user_id;

    -- retorna verdadeiro se o usuário existe (count > 0)
    return (v_count > 0);
exception
    when others then raise_application_error(-20001, 'ifsales_pkg.verify_user_exists: ' || sqlerrm);
end verify_user_exists;
--------------------------------------------------------------------------------------------------------
-- função para verificar se o vendedor existe
function verify_salesperson_exists(p_salesperson_id in ifsales.salespersons.id%type)
return boolean
is
v_count number;
begin
    select count(*) into v_count from salespersons where id = p_salesperson_id;

    -- retorna verdadeiro se o vendedor existe
    return (v_count > 0);
exception
    when others then raise_application_error(-20001, 'ifsales_pkg.verify_salesperson_exists: ' || sqlerrm);
end verify_salesperson_exists;
--------------------------------------------------------------------------------------------------------
-- função para verificar se a categoria existe
function verify_category_exists(p_category_id in ifsales.categories.id%type)
return boolean
is
v_count number;
begin
    select count(*) into v_count from categories where id = p_category_id;

    -- retorna verdadeiro se a categoria existe
    return (v_count > 0);
exception
    when others then raise_application_error(-20001, 'ifsales_pkg.verify_category_exists: ' || sqlerrm);
end verify_category_exists;
--------------------------------------------------------------------------------------------------------
-- função para recuperar o ticket médio geral
function rec_average_ticket return number
is
v_avg_ticket number;
begin
    select nvl(avg(fun.product_quantity * (prd.price - fun.discount)), 0) into v_avg_ticket
    from ifsales.funnel   fun
       ,ifsales.products prd
    where fun.product_id = prd.id;

    return v_avg_ticket;
exception
    when no_data_found then return 0;
    when others then raise_application_error(-20001, 'ifsales_pkg.total_average_ticket: ' || sqlerrm);
end rec_average_ticket;
--------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------
-- função para recuperar o desconto médio
function rec_average_discount return number
is
v_avg_discount number;
begin
    select nvl(avg(fun.discount), 0) into v_avg_discount
    from ifsales.funnel fun;

    return v_avg_discount;
exception
    when no_data_found then return 0;
    when others then raise_application_error(-20001, 'ifsales_pkg.rec_average_discount: ' || sqlerrm);
end rec_average_discount;
--------------------------------------------------------------------------------------------------------
-- função para recuperar o total de vendas
function rec_total_sales return number
is
v_total_sales number;
begin
    select nvl(sum(fun.product_quantity * (prd.price - fun.discount)), 0)
    into v_total_sales
    from ifsales.funnel   fun
       ,ifsales.products prd
    where fun.product_id = prd.id;

    return v_total_sales;
exception
    when no_data_found then return 0;
    when others then raise_application_error(-20001, 'ifsales_pkg.rec_total_sales: ' || sqlerrm);
end rec_total_sales;
--------------------------------------------------------------------------------------------------------
-- função para recuperar o total de produtos vendidos
function rec_total_products_sold return number
is
v_total_products_sold number;
begin
    select nvl(sum(fun.product_quantity), 0) into v_total_products_sold
    from ifsales.funnel fun;

    return v_total_products_sold;
exception
    when no_data_found then return 0;
    when others then raise_application_error(-20001, 'ifsales_pkg.rec_total_products_sold: ' || sqlerrm);
end rec_total_products_sold;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir um novo usuário
procedure insert_user(p_email    in ifsales.users.email%type
                     ,p_password in ifsales.users.password%type)
    is
begin
    insert into users (id, email, password)
    values (users_seq.nextval, p_email, p_password);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_user: ' || sqlerrm);
end insert_user;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir um novo vendedor
procedure insert_salesperson(p_name    in ifsales.salespersons.name%type
                            ,p_email   in ifsales.salespersons.email%type
                            ,p_phone   in ifsales.salespersons.phone%type
                            ,p_active  in ifsales.salespersons.active%type)
as
begin
    insert into salespersons(id, name, email, phone, active)
    values (salespersons_seq.nextval, p_name, p_email, p_phone, nvl(p_active, 1));
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_salesperson: ' || sqlerrm);
end insert_salesperson;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir um novo produto
procedure insert_product(
    p_brand       in ifsales.products.brand%type
   ,p_model       in ifsales.products.model%type
   ,p_model_year  in ifsales.products.model_year%type
   ,p_price       in ifsales.products.price%type
   ,p_category_id in ifsales.products.category_id%type
)
as
begin
    insert into products(id, brand, model, model_year, price, category_id)
    values (products_seq.nextval, p_brand, p_model, p_model_year, p_price, p_category_id);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_product: ' || sqlerrm);
end insert_product;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir uma nova categoria
procedure insert_category(
    p_category_name  in ifsales.categories.category_name%type
   ,p_description    in ifsales.categories.description%type
)
as
begin
    insert into categories(id, category_name, description)
    values (categories_seq.nextval, p_category_name, p_description);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_category: ' || sqlerrm);
end;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir uma nova região
procedure insert_region(
    p_region_name in ifsales.regions.region_name%type
   ,p_city        in ifsales.regions.city%type
   ,p_estate      in ifsales.regions.state%type
)
as
begin
    insert into regions(id, region_name, city, state)
    values (regions_seq.nextval, p_region_name, p_city, p_estate);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_region: ' || sqlerrm);
end;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir uma nova loja
procedure insert_store(
    p_store_name in ifsales.stores.store_name%type
   ,p_store_cnpj in ifsales.stores.store_cnpj%type
   ,p_region_id  in ifsales.stores.region_id%type
   ,p_address    in ifsales.stores.address%type
   ,p_phone      in ifsales.stores.phone%type
)
as
begin
    insert into stores(id, store_name, store_cnpj, region_id, address, phone)
    values (stores_seq.nextval, p_store_name, p_store_cnpj, p_region_id, p_address, p_phone);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_store: ' || sqlerrm);
end;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir um novo cliente
procedure insert_customer(
    p_cpf                 in ifsales.customers.cpf%type
   ,p_region_id           in ifsales.customers.region_id%type
   ,p_first_name          in ifsales.customers.first_name%type
   ,p_last_name           in ifsales.customers.last_name%type
   ,p_birth_date          in ifsales.customers.birth_date%type
   ,p_income              in ifsales.customers.income%type
   ,p_mobile              in ifsales.customers.mobile%type
   ,p_professional_status in ifsales.customers.professional_status%type
)
as
begin
    insert into customers(id, cpf, region_id, first_name, last_name, birth_date, income, mobile,
                          professional_status)
    values (customers_seq.nextval, p_cpf, p_region_id, p_first_name
           , p_last_name, p_birth_date, p_income
           ,p_mobile, p_professional_status);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_customer: ' || sqlerrm);
end insert_customer;
--------------------------------------------------------------------------------------------------------
-- procedimento para inserir um novo registro no funil de vendas
procedure insert_funnel(
    p_customer_id      in ifsales.funnel.customer_id%type
   ,p_salesperson_id   in ifsales.funnel.salesperson_id%type
   ,p_store_id         in ifsales.funnel.store_id%type
   ,p_product_id       in ifsales.funnel.product_id%type
   ,p_paid_date        in ifsales.funnel.paid_date%type
   ,p_discount         in ifsales.funnel.discount%type
   ,p_product_quantity in ifsales.funnel.product_quantity%type
)
as
begin
    insert into funnel(id, customer_id, salesperson_id, store_id, product_id, paid_date, discount, product_quantity)
    values (funnel_seq.nextval, p_customer_id
           ,p_salesperson_id, p_store_id
           ,p_product_id, p_paid_date, p_discount, p_product_quantity);
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.insert_funnel: ' || sqlerrm);
end insert_funnel;
--------------------------------------------------------------------------------------------------------
-- procedimento para deletar um usuário verificando se o mesmo existe
procedure delete_user(p_user_id in ifsales.users.id%type)
is
begin
    if verify_user_exists(p_user_id => p_user_id) = true then
        delete from users where id = p_user_id;
    else
        raise_application_error(-20001, 'ifsales_pkg.delete_user: O USUÁRIO INFORMADO NÃO EXISTE!');
    end if;
exception
    when others then
        raise_application_error(-20001, 'ifsales_pkg.delete_user: ' || sqlerrm);
end delete_user;
--------------------------------------------------------------------------------------------------------
end ifsales_pkg;
/