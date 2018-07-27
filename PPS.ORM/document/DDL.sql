DROP DATABASE portal;
CREATE DATABASE portal;
-- ON DELETE CASCADE ON UPDATE RESTRICT
USE portal;


CREATE TABLE MST_CARD_STEP(
    STEP NCHAR(4) NOT NULL,
    NAME NVARCHAR(200) NOT NULL,
    
    PRIMARY KEY (STEP)
);

CREATE TABLE MST_CARD_TYPE(
	CARD_TYPE NCHAR(3) NOT NULL,
	NAME NVARCHAR(255) NOT NULL,
	
	PRIMARY KEY (CARD_TYPE)
);

CREATE TABLE MST_ROLE(
	ROLE NCHAR(4) NOT NULL,
	NAME NVARCHAR(255) NOT NULL,
	
	PRIMARY KEY (ROLE)
);

CREATE TABLE MST_CARD(
    CODE NCHAR(4) NOT NULL,
    NAME NVARCHAR(200) NOT NULL,
    HREF NVARCHAR(200),
    STEP NCHAR(4) NOT NULL,
    TITLE NVARCHAR(255),
    DESCRIPTION NVARCHAR(255),
    IMG LONGBLOB,
    ICON NVARCHAR(50),
    COLOR NVARCHAR(50),
    CARD_TYPE NCHAR(3),
    CONTROL NVARCHAR(100),
    TEMPLATE NVARCHAR(100),
    ORDER_SEQ INT,
    
    PRIMARY KEY (CODE),
    FOREIGN KEY (STEP) REFERENCES MST_CARD_STEP (STEP),
    FOREIGN KEY (CARD_TYPE) REFERENCES MST_CARD_TYPE (CARD_TYPE)
);

CREATE TABLE MST_STATE(
	STATE INT NOT NULL,
	NAME NVARCHAR(255) NOT NULL,
	
	PRIMARY KEY(STATE)
);

CREATE TABLE TSN_STATE_INFO(
    IDX INT NOT NULL AUTO_INCREMENT,
    CREATER NVARCHAR(255) NOT NULL,
    CREATE_DATE DATETIME NOT NULL,
    LAST_UPDATER NVARCHAR(255) NULL,
    LAST_UPDATE DATETIME NULL,
    IS_DELETE BOOLEAN DEFAULT FALSE,
    STATE INT NULL,
    
    PRIMARY KEY (IDX),
    FOREIGN KEY (STATE) REFERENCES MST_STATE (STATE)
);

CREATE TABLE TSN_COMPANY(
	ID  INT NOT NULL AUTO_INCREMENT,
	NAME NVARCHAR(255) NOT NULL,
	STATE INT NOT NULL,
	
	PRIMARY KEY (ID),
    FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX) 
);

CREATE TABLE TSN_GROUP(
	ID  INT NOT NULL AUTO_INCREMENT,
	NAME NVARCHAR(255) NOT NULL,
    COMPANY_ID INT NOT NULL,
	STATE INT NOT NULL,
	
	PRIMARY KEY (ID),
    FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX),
   	FOREIGN KEY (COMPANY_ID) REFERENCES TSN_COMPANY (ID)
);

CREATE TABLE TSN_USER (
	ID NVARCHAR(255) NOT NULL,
	GIVEN_NAME NVARCHAR(255) NOT NULL,
    NAME NVARCHAR(255) NOT NULL,
    NICK_NAME NVARCHAR(255) NULL,
	IMG_BLOB LONGBLOB NULL,
    BACKGROUND_IMG NVARCHAR(255) NULL,
    COMPANY_ID INT NOT NULL,
	GROUP_ID INT NOT NULL,
    STATE INT NOT NULL,

	PRIMARY KEY (ID),
	FOREIGN KEY (COMPANY_ID) REFERENCES TSN_COMPANY (ID),
	FOREIGN KEY (GROUP_ID) REFERENCES TSN_GROUP (ID),
    FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX) 
);

CREATE TABLE TSN_PASSWORD(
    IDX INT NOT NULL AUTO_INCREMENT,
    ID NVARCHAR(255) NOT NULL,
    PASSWORD NVARCHAR(255) NOT NULL,
    STATE INT NOT NULL,

    PRIMARY KEY (IDX,ID),
    FOREIGN KEY (ID) REFERENCES TSN_USER(ID),
    FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX)
);

CREATE TABLE TSN_COOKIE(
    ID NVARCHAR(255) NOT NULL,
    COOKIEKEY NVARCHAR(255) NOT NULL,
    IPADDRESS NVARCHAR(100) NULL,
    LAST_CONNECT_DATE DATETIME NULL,
    STATE INT NOT NULL,
    
    PRIMARY KEY (ID, COOKIEKEY),
    FOREIGN KEY (ID) REFERENCES TSN_USER (ID),
    FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX)
);

CREATE TABLE TSN_COMMENT(
	IDX INT NOT NULL AUTO_INCREMENT,
	COMMENT TEXT NULL,
	STATE INT NOT NULL,
	
	PRIMARY KEY (IDX),
	FOREIGN KEY (STATE) REFERENCES TSN_STATE_INFO (IDX)
);

CREATE TABLE MAP_VIEW_ROLE_COMPANY(
	CARD_CODE NCHAR(4) NOT NULL,
	COMPANY_ID INT NOT NULL,
	
	PRIMARY KEY (CARD_CODE,COMPANY_ID),
	FOREIGN KEY (CARD_CODE) REFERENCES MST_CARD(CODE),
	FOREIGN KEY (COMPANY_ID) REFERENCES TSN_COMPANY(ID)
);

CREATE TABLE MAP_VIEW_ROLE_GROUP(
	CARD_CODE NCHAR(4) NOT NULL,
	GROUP_ID INT NOT NULL,
	
	PRIMARY KEY (CARD_CODE,GROUP_ID),
	FOREIGN KEY (CARD_CODE) REFERENCES MST_CARD(CODE),
	FOREIGN KEY (GROUP_ID) REFERENCES TSN_GROUP(ID)
);


CREATE TABLE MAP_VIEW_ROLE_USER(
	CARD_CODE NCHAR(4) NOT NULL,
	USER_ID NVARCHAR(255) NOT NULL,
	
	PRIMARY KEY (CARD_CODE,USER_ID),
	FOREIGN KEY (CARD_CODE) REFERENCES MST_CARD(CODE),
	FOREIGN KEY (USER_ID) REFERENCES TSN_USER(ID)
);

CREATE TABLE MAP_ACTION_ROLE_COMPANY(
	ROLE_CODE NCHAR(4) NOT NULL,
	COMPANY_ID INT NULL,
	
	PRIMARY KEY (ROLE_CODE,COMPANY_ID),
	FOREIGN KEY (ROLE_CODE) REFERENCES MST_ROLE(ROLE),
	FOREIGN KEY (COMPANY_ID) REFERENCES TSN_COMPANY(ID)
);

CREATE TABLE MAP_ACTION_ROLE_GROUP(
	ROLE_CODE NCHAR(4) NOT NULL,
	GROUP_ID INT NOT NULL,
	
	PRIMARY KEY (ROLE_CODE,GROUP_ID),
	FOREIGN KEY (ROLE_CODE) REFERENCES MST_ROLE(ROLE),
	FOREIGN KEY (GROUP_ID) REFERENCES TSN_GROUP(ID)
);


CREATE TABLE MAP_ACTION_ROLE_USER(
	ROLE_CODE NCHAR(4) NOT NULL,
	USER_ID NVARCHAR(255) NOT NULL,
	
	PRIMARY KEY (ROLE_CODE,USER_ID),
	FOREIGN KEY (ROLE_CODE) REFERENCES MST_ROLE(ROLE),
	FOREIGN KEY (USER_ID) REFERENCES TSN_USER(ID)
);

CREATE TABLE MST_LOOK_UP(
	`KEY` NVARCHAR(20),
	`VALUE` NVARCHAR(255),
	
	PRIMARY KEY (`KEY`)
);
