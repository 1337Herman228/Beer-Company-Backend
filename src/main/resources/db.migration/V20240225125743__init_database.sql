alter table if exists cart
    drop constraint if exists FKoal9xfpdt8bdw8l4thhvkx70a;
alter table if exists cart
    drop constraint if exists FKbut5u81fns8galxagpxo98om4;
alter table if exists product
    drop constraint if exists FK1mtsbur82frn64de7balymq9s;
alter table if exists product
    drop constraint if exists FKr8fh5q1eifwt0uu0chp80mwia;
alter table if exists purchase_item
    drop constraint if exists FKq69apam78dbi0cggl37ge6auf;
alter table if exists purchase_item
    drop constraint if exists FK25tmmd3iddm5kmeg1mcoa3ou6;
alter table if exists purchases
    drop constraint if exists FKm0ndjymn9p747pfp4515pio8i;
alter table if exists users
    drop constraint if exists FKd21kkcigxa21xuby5i3va9ncs;
alter table if exists users
    drop constraint if exists FKp56c1712k691lhsyewcssf40f;
drop table if exists cart cascade;
drop table if exists category cascade;
drop table if exists drink cascade;
drop table if exists person cascade;
drop table if exists product cascade;
drop table if exists purchase_item cascade;
drop table if exists purchases cascade;
drop table if exists roles cascade;
drop table if exists users cascade;
drop sequence if exists category_seq;
drop sequence if exists drink_seq;
drop sequence if exists person_seq;
drop sequence if exists product_seq;
drop sequence if exists roles_seq;
drop sequence if exists users_seq;
create sequence category_seq start with 1 increment by 50;
create sequence drink_seq start with 1 increment by 50;
create sequence person_seq start with 1 increment by 50;
create sequence product_seq start with 1 increment by 50;
create sequence roles_seq start with 1 increment by 50;
create sequence users_seq start with 1 increment by 50;
create table cart
(
    purchase_item_purchase_item_id bigint not null,
    user_user_id                   bigint not null,
    primary key (purchase_item_purchase_item_id, user_user_id)
);
create table category
(
    category_id          bigint not null,
    category_description varchar(1000),
    category_name        varchar(255),
    primary key (category_id)
);
create table drink
(
    drink_id          bigint not null,
    compound          varchar(1000),
    description       varchar(1000),
    short_description varchar(1000),
    image             varchar(25000),
    drink_name        varchar(255),
    primary key (drink_id)
);
create table person
(
    person_id bigint not null,
    email     varchar(255),
    name      varchar(255),
    phone     varchar(255),
    surname   varchar(255),
    primary key (person_id)
);
create table product
(
    category_id     bigint,
    drink_id        bigint,
    product_id      bigint not null,
    possible_volume varchar(455),
    price           varchar(455),
    quantity_left   varchar(455),
    primary key (product_id)
);
create table purchase_item
(
    quantity         integer   not null,
    total_price      float4    not null,
    volume           float4    not null,
    product_id       bigint,
    purchase_id      bigint,
    purchase_item_id bigserial not null,
    primary key (purchase_item_id)
);
create table purchases
(
    date        timestamp(6),
    purchase_id bigserial not null,
    user_id     bigint,
    destination varchar(5000),
    payment     varchar(255),
    final_price float4,
    primary key (purchase_id)
);
create table roles
(
    role_id  bigint not null,
    position varchar(255),
    primary key (role_id)
);
create table users
(
    person_id bigint unique,
    role_id   bigint,
    user_id   bigint not null,
    login     varchar(255) unique,
    password  varchar(255),
    primary key (user_id)
);
alter table if exists cart
    add constraint FKoal9xfpdt8bdw8l4thhvkx70a foreign key (purchase_item_purchase_item_id) references purchase_item;
alter table if exists cart
    add constraint FKbut5u81fns8galxagpxo98om4 foreign key (user_user_id) references users;
alter table if exists product
    add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category;
alter table if exists product
    add constraint FKr8fh5q1eifwt0uu0chp80mwia foreign key (drink_id) references drink;
alter table if exists purchase_item
    add constraint FKq69apam78dbi0cggl37ge6auf foreign key (product_id) references product;
alter table if exists purchase_item
    add constraint FK25tmmd3iddm5kmeg1mcoa3ou6 foreign key (purchase_id) references purchases;
alter table if exists purchases
    add constraint FKm0ndjymn9p747pfp4515pio8i foreign key (user_id) references users;
alter table if exists users
    add constraint FKd21kkcigxa21xuby5i3va9ncs foreign key (person_id) references person;
alter table if exists users
    add constraint FKp56c1712k691lhsyewcssf40f foreign key (role_id) references roles