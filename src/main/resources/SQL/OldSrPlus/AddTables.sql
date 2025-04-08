create sequence players_seq;
CREATE TABLE players (
    id		serial primary key,
    name 	varchar(40) not null,
    rank	varchar(40) not null,
    constraint name_unique unique (name)
);

create sequence raids_seq;
CREATE TABLE Raids (
    id		serial primary key,
    name 	varchar(40) not null
);

create sequence bosses_seq;
create table Bosses (
	id		serial primary key,
	name varchar(40) not null,
	raid integer references Raids
);

create sequence items_seq;
create table Items (
	id		serial primary key,
	name varchar(40) not null,
	rarity integer not null
);

create sequence item_bosses_seq;
create table item_bosses (
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
	CHECK(classification = 'twink' or classification = 'main'),
	unique (name)
);

create sequence softreserves_seq;
create table SoftReserves (
	id		serial primary key,
	char integer references characters,
	item integer references items,
	boss integer references bosses,
	raid integer references Raids,
	bonus integer check (bonus>=0),
	lastupdated date,
	constraint unique_reserve UNIQUE(char, raid)
);

create sequence raidruns_seq;
create table RaidRuns (
	id		serial primary key,
	raid integer references Raids
);

create sequence raidruns_players_seq;
create table RaidRuns_players (
	id		serial primary key,
	raidrun integer references raidruns,
	player integer references players
);

create sequence raidruns_bosses_seq;
create table RaidRuns_bosses (
	id		serial primary key,
	raidrun integer references raidruns,
	boss integer references bosses,
	defeated bool
);