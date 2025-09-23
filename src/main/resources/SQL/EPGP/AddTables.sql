create sequence players_seq;
CREATE TABLE players (
    id		serial primary key,
    name 	varchar(40) not null,
    rank	varchar(40) not null,
    ep 		numeric(8, 2),
    gp		numeric(8, 2),
    active  boolean,
    constraint name_unique unique (name),
    constraint minimum_gp check(gp >= 10)
);

create sequence raids_seq;
CREATE TABLE raids (
    id		serial primary key,
    name 	varchar(40) not null
);

create sequence raids_rewards_seq;
CREATE TABLE raids_rewards (
    id		serial primary key,
	raid integer references raids,
    rewardtype 		varchar(40) not null,
    rewardvalue 	integer not null
);

create sequence bosses_seq;
create table bosses (
	id		serial primary key,
	name varchar(40) not null,
	raid integer references raids
);

create sequence items_seq;
create table items (
	id		serial primary key,
	name varchar(60) not null,
	rarity integer not null
);

create sequence items_bosses_seq;
create table items_bosses (
	id		serial primary key,
	item integer references items,
	boss integer references bosses
);

create sequence characters_seq;
create table characters (
	id		serial primary key,
	player integer references players,
	name varchar(40) not null,
	classification varchar(40) not null,
	active bool,
	CHECK(classification = 'twink' or classification = 'main'),
	unique (name)
);

create sequence raidruns_seq;
create table raidruns (
	id		serial primary key,
	raid integer references raids
);

create sequence raidruns_players_seq;
create table raidruns_players (
	id		serial primary key,
	raidrun integer references raidruns,
	player integer references players
);

create sequence raidruns_bosses_seq;
create table raidruns_bosses (
	id		serial primary key,
	raidrun integer references raidruns,
	boss integer references bosses,
	defeated bool
);

create sequence logs_seq;
create table logs (
    id serial primary key,
    time timestamp,
    message text
);

CREATE TABLE settings (
    name 	varchar(40) primary key,
    value	integer
);