create table profiles
(
  id    bigint primary key,
  bio   text,
  phone_number  varchar(15),
  date_of_birth date,
  loyalty_points int unsigned default 0,
  foreign key (id) references users(id)
);