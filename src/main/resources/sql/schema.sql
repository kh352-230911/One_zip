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

-- 상품
CREATE TABLE tb_product (
                            id number NOT NULL,
                            biz_member_id varchar2(10) NOT NULL,
                            product_name varchar2(100) NOT NULL,
                            product_typecode varchar2(30) NOT NULL,
                            product_price number NOT NULL,
                            discount_rate number DEFAULT 0 NOT NULL,
                            reg_date DATE NOT NULL,
                            CONSTRAINT pk_tb_product PRIMARY KEY (id),
                            CONSTRAINT fk_tb_product_member FOREIGN KEY (biz_member_id) REFERENCES tb_member(member_id)
);

-- 리뷰
CREATE TABLE tb_review(
                          id number NOT NULL,
                          member_id varchar2(10) NOT NULL,
                          product_no number NOT NULL,
                          review_content varchar(1500) NOT NULL,
                          review_regdate date NOT NULL,
                          star_point number DEFAULT 5 NOT NULL,
                          CONSTRAINT pk_tb_review PRIMARY KEY (id),
                          CONSTRAINT fk_tb_review_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id),
                          CONSTRAINT fk_tb_review_product FOREIGN KEY (product_no) REFERENCES tb_product(id)
);
-- 상품질문
CREATE TABLE tb_pquestions(
                              id number NOT NULL,
                              member_id varchar2(10) NOT NULL,
                              id2 number NOT NULL,
                              q_content varchar2(1500) NOT NULL,
                              q_regdate date NOT NULL,
                              CONSTRAINT pk_tb_pquestions PRIMARY KEY (id),
                              CONSTRAINT fk_tb_pquestions_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id),
                              CONSTRAINT fk_tb_pquestions_product FOREIGN KEY (id2) REFERENCES tb_product(id)
);

-- 상품답변
CREATE TABLE tb_qanwers(
                           id number NOT NULL,
                           pquestions_no number NOT NULL,
                           biz_member_id varchar2(10) NOT NULL,
                           a_content varchar2(1500) NOT NULL,
                           a_regdate date NOT NULL,
                           CONSTRAINT pk_tb_qanwers PRIMARY KEY (id),
                           CONSTRAINT fk_tb_qanwers_pquestions FOREIGN KEY (pquestions_no) REFERENCES tb_pquestions(id),
                           CONSTRAINT fk_tb_qanwers_biz FOREIGN KEY (biz_member_id) REFERENCES tb_businessmember (biz_member_id)
);

-- 주문
CREATE TABLE tb_plog(
                        id number NOT NULL,
                        member_id varchar2(10) NOT NULL,
                        purchase_date varchar2(10) NOT NULL,
                        shipping_state varchar2(1) DEFAULT 'R' NOT NULL,
                        refund_check varchar2(1) DEFAULT 'N' NOT NULL,
                        memo varchar2(90) DEFAULT '소중한 택배입니다.' NOT NULL,
                        fixed_date date	NULL,
                        arr_addr varchar2(100) DEFAULT '회원 주소' NOT NULL,
                        total_pay_amount number	DEFAULT 0 NOT NULL,
                        CONSTRAINT pk_tb_plog PRIMARY KEY (id),
                        CONSTRAINT fk_tb_plog_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id)
);

-- 상품 옵션
CREATE TABLE tb_poption(
                           id number NOT NULL,
                           product_no number NOT NULL,
                           option_name varchar2(100) DEFAULT '--------',
                           total_stock number DEFAULT 0 NOT NULL,
                           option_cost number DEFAULT 0 NOT NULL,
                           ne_option number DEFAULT 0 NOT NULL,
                           CONSTRAINT pk_tb_poption PRIMARY KEY (id),
                           CONSTRAINT fk_tb_poption_product FOREIGN KEY (product_no) REFERENCES tb_product(id)
);

-- 상품 이미지
CREATE TABLE tb_pimage(
                          id number NOT NULL,
                          product_no number NOT NULL,
                          original_filename varchar2(100) NOT NULL,
                          image_key varchar2(100) NOT NULL,
                          Field varchar2(100) NOT NULL,
                          image_type varchar2(1) DEFAULT 'D' NOT NULL,
                          CONSTRAINT pk_tb_pimage PRIMARY KEY (id),
                          CONSTRAINT fk_tb_pimage_product FOREIGN KEY (product_no) REFERENCES tb_product(id)
);

-- 장바구니
CREATE TABLE tb_cart (
                         id number NOT NULL,
                         member_id varchar2(10) NOT NULL,
                         product_no number NOT NULL,
                         product_name varchar2(100) NOT NULL,
                         cart_quantity number DEFAULT 0 NOT NULL,
                         cart_status varchar2(1) DEFAULT 'Y' NOT NULL CHECK(cart_status in('Y', 'N')),
                         total_stock number DEFAULT 0 NOT NULL,
                         poption_name varchar2(100) DEFAULT '--------',
                         option_cost number DEFAULT 0 NOT NULL,
                         CONSTRAINT pk_tb_cart PRIMARY KEY (id),
                         CONSTRAINT fk_tb_cart_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id),
                         CONSTRAINT fk_tb_cart_product FOREIGN KEY (product_no) REFERENCES tb_product(id)
);

-- 결제
CREATE TABLE tb_payment(
                           id number NOT NULL,
                           id2 number NOT NULL,
                           partner_id number NOT NULL,
                           tid varchar2(20) NULL,
                           CONSTRAINT pk_tb_payment PRIMARY KEY (id),
                           CONSTRAINT fk_tb_payment_id2 FOREIGN KEY (id2) REFERENCES tb_plog(id)
);

-- 사업자 회원
CREATE TABLE tb_businessmember (
                                   biz_member_id varchar2(10) NOT NULL,
                                   biz_password varchar2(50) NOT NULL,
                                   biz_name varchar2(15) NOT NULL,
                                   biz_phone varchar2(50) NOT NULL,
                                   biz_reg_date Date NOT NULL,
                                   biz_addr varchar2(100) NOT NULL,
                                   biz_origin_profile_name	varchar2(300) NULL,
                                   biz_renamed_profile_name varchar2(300) NULL,
                                   biz_license	varchar2(255) NOT NULL,
                                   biz_reg_no varchar2(50) NOT NULL,
                                   biz_reg_status varchar2(1) NOT NULL,
                                   constraint pk_tb_businessmember_biz_member_id primary key(biz_member_id),
                                   constraint uq_tb_businessmember_biz_phone_biz_reg_no unique(biz_phone, biz_reg_no)
);

-- 선물내역
CREATE TABLE tb_gifts (
                          gift_id number NOT NULL,
                          plog_id number NOT NULL,
                          sender_id varchar2(20) NOT NULL,
                          receiver_id varchar2(20) NOT NULL,
                          approve char(5) NOT NULL,
                          reg_date timestamp DEFAULT systimestamp NOT NULL,
                          CONSTRAINT pk_tb_gifts PRIMARY KEY (gift_id),
                          CONSTRAINT fk_tb_gifts_plog FOREIGN KEY (plog_id) REFERENCES tb_plog(id),
                          CONSTRAINT fk_tb_gifts_sender FOREIGN KEY (sender_id) REFERENCES tb_member(member_id),
                          CONSTRAINT fk_tb_gifts_receiver FOREIGN KEY (receiver_id) REFERENCES tb_member(member_id)
);

-- 주문 상품
CREATE TABLE tb_order_product (
                                  id number NOT NULL,
                                  plog_no number NOT NULL,
                                  product_no number NOT NULL,
                                  poption_no number NOT NULL,
                                  purchase_quantity number DEFAULT 1 NOT NULL,
                                  pay_amount number NOT NULL,
                                  CONSTRAINT pk_tb_order_product PRIMARY KEY (id),
                                  CONSTRAINT fk_tb_order_product_plog FOREIGN KEY (plog_no) REFERENCES tb_plog(id),
                                  CONSTRAINT fk_tb_order_product_product FOREIGN KEY (product_no) REFERENCES tb_product(id),
                                  CONSTRAINT fk_tb_order_product_poption FOREIGN KEY (poption_no) REFERENCES tb_poption(id)
);

-- 찜
CREATE TABLE tb_steam (
                          id number NOT NULL,
                          member_id varchar2(10) NOT NULL,
                          product_no number NOT NULL,
                          CONSTRAINT pk_tb_steam PRIMARY KEY (id),
                          CONSTRAINT fk_tb_steam_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id),
                          CONSTRAINT fk_tb_steam_product FOREIGN KEY (product_no) REFERENCES tb_product(id)
);

-- 배송지 주소록
CREATE TABLE tb_shipping_address (
                                     id number NOT NULL,
                                     member_id varchar2(10) NOT NULL,
                                     addr_nickname varchar(30) DEFAULT '기본 주소지' NOT NULL,
                                     shipping_addr varchar2(100) NOT NULL,
                                     CONSTRAINT pk_tb_shipping_address PRIMARY KEY (id),
                                     CONSTRAINT fk_tb_shipping_address_member FOREIGN KEY (member_id) REFERENCES tb_member(member_id)
);

