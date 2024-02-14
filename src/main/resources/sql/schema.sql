-- DDL 작성
--------------------------------------------------------- tb_zip
create table tb_zip (
    id number not null,
    member_id varchar2(20) not null,
    content varchar2(1000) not null,
    day_count number default 0,
    total_count number default 0,
    reg_date date default systimestamp,
    color_code varchar(7) not null,
    type varchar2(255) default "ZP",
    constraints pk_tb_zip_id primary key(id),
    constraints fk_tb_zip_member_id foreign key(member_id) references tb_member(member_id) on delete set null
);
create sequence seq_tb_zip_id;

--------------------------------------------------------- tb_attachment
create table tb_attachment (
    id number not null,
    ref_id number not null,
    ref_type varchar2(2) not null,
    original_filename varchar2(255) not null,
    key varchar2(1000) not null,
    url varchar2(1000) not null,
    reg_date date default systimestamp,
    constraints pk_tb_attachment_id primary key(id)
);
create sequence seq_tb_attachment_id start with 1 increment by 50;

--------------------------------------------------------- tb_guest_board
create table tb_guest_board (
    id number not null,
    zip_id number not null,
    member_id varchar2(20) not null,
    guest_content varchar2(1000) not null,
    reg_date date default systimestamp,
    constraints pk_tb_guest_board primary key(id),
    constraints fk_tb_guest_board_zip_id foreign key(zip_id) references tb_zip(id) on delete cascade,
    constraints fk_tb_guest_board_member_id foreign key(member_id) references tb_member(member_id) on delete cascade
);
create sequence seq_tb_guest_board_id;

--------------------------------------------------------- tb_member
create table tb_member (
    member_id VARCHAR2(10) NOT NULL,
    password VARCHAR2(50) NOT NULL,
    name varchar2(15) not null,
    nickname VARCHAR2(15) NOT NULL,
    birthday DATE NOT NULL,
    gender varCHAR(1) CHECK (gender IN ('M', 'F')),
    phone VARCHAR2(50) NOT NULL,
    hobby VARCHAR2(200),
    mbti VARCHAR2(20),
    reg_date date default sysdate,
    member_addr VARCHAR2(255 CHAR) NOT NULL,
    constraints pk_member_memberid primary key(member_id),
    constraints uq_member_nickname unique(nickname)
    );

--------------------------------------------------------- tb_authority
create table  tb_authority(
    id number,
    member_id varchar2(10),
    user_type varchar2(10),
    constraints uq_member_id_type unique(member_id, user_type),
    constraints fk_member_user_type foreign key(member_id) references tb_member(member_id) on delete cascade,
    constraints pk_authority_member_id primary key(id)
);
create sequence seq_tb_authority;
