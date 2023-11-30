create table users(
                      id_User bigint primary key auto_increment,
                      username varchar(90) not null unique,
                      email varchar(90) not null unique,
                      password varchar(30) not null
);