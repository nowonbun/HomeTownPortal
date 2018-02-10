create table MST_GROUP(
	idx int not null AUTO_INCREMENT,
	name varchar(255) not null,
	authority varchar(255) not null,
	
	PRIMARY KEY(idx)
);

create table TSN_USER (
	id varchar(255) not null,
	givenName varchar(255) not null,
	name varchar(255) not null,
	nickname varchar(255),
	img varchar(255),
	background_img varchar(255),
	groupidx int,

	PRIMARY KEY(id),
	FOREIGN KEY (groupidx) REFERENCES MST_GROUP (idx)-- ON DELETE CASCADE ON UPDATE RESTRICT
);

create table TSN_COOKIE(
    id varchar(255) not null,
    cookiekey varchar(255) not null,
    ipaddress varchar(100),
    createdate date,
    lastconnecteddate date,
    
    PRIMARY KEY(id, cookiekey),
    FOREIGN KEY (id) REFERENCES TSN_USER (id)-- ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE MST_PAGE_TAB(
    idx int not null AUTO_INCREMENT,
    PAGE_TAB varchar(200) not null,
    
    PRIMARY KEY(idx)
);

CREATE TABLE TSN_CARD(
    idx int not null  AUTO_INCREMENT,
    name varchar(200) not null,
    page_tab int not null,
    href varchar(200) not null,
    img blob,
    icon varchar(50),
    color varchar(50),
    
    PRIMARY KEY(idx),
    FOREIGN KEY (page_tab) REFERENCES MST_PAGE_TAB (idx)
);

CREATE TABLE MAP_CARD_GROUP(
    group_idx int not null,
    card_idx int not null,
    
    primary key(group_idx,card_idx),
    FOREIGN KEY (group_idx) REFERENCES MST_GROUP (idx),
    FOREIGN KEY (card_idx) REFERENCES TSN_CARD (idx)
);