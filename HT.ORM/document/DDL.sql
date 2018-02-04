create table USERINFO (
	id varchar(255) not null,
	givenName varchar(255) not null,
	name varchar(255) not null,
	nickname varchar(255),
	img varchar(255),
	background_img varchar(255),

	PRIMARY KEY(id)
)

create table COOKIEINFO(
    id varchar(255) not null,
    cookiekey varchar(255) not null,
    ipaddress varchar(100),
    createdate date,
    
    PRIMARY KEY(id, cookiekey),
    FOREIGN KEY (id) REFERENCES USERINFO (id) ON DELETE CASCADE ON UPDATE RESTRICT
)