create table Member (
	idx int AUTO_INCREMENT,
	id varchar(255) not null,
	givenName varchar(255) not null,
	name varchar(255) not null,
	nickname varchar(255),
	img varchar(255),
	background_img varchar(255),
	cookie_key varchar(255),

	PRIMARY KEY(idx, id)
)
