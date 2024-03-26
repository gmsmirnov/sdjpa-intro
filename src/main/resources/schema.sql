drop table if exists book;

create table if not exists book
(
    id        bigint not null auto_increment,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    primary key (id)
) engine = InnoDB;

insert
into
    book
(isbn, publisher, title)
values
    ('123', 'RandomHouse', 'Domain Driven Design');

insert
into
    book
(isbn, publisher, title)
values
    ('234234', 'Mining', 'Spring in Action');