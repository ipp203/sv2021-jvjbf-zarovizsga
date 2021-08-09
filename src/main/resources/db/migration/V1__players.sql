create table players (id bigint not null auto_increment primary key,
	birth_date date,
	name varchar(255),
	position varchar(255),
	team_id bigint);