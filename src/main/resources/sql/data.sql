-- DML 작성
select * from tb_member;
select * from tb_authority;
select * from tb_zip;
select * from tb_attachment;
select * from tb_guest_board;
select * from tb_zip_attachment;

insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'honggd1', '우리집이 처음이야', 0, 0, to_date('12/12/2023', 'MM/DD/RRRR'), '#FFFFFF');

INSERT INTO tb_member VALUES ('user0', 'password1', '테스트', 'test', TO_DATE('1991-03-20', 'YYYY-MM-DD'), 
'M', '010-1111-2222', '운동', 'ISTJ', DEFAULT, '서울시 강서구');

commit;

insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code, type)
values (seq_tb_zip_id.nextval, 'user1', '우리집이 처음이야', 0, 0, to_date('12/12/2023', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user2', '두번째 집이지롱', 0, 0, to_date('12/25/2023', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user3', '세번째다', 0, 0, to_date('12/26/2023', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user4', '넷', 0, 0, to_date('12/27/2023', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user5', '다섯', 0, 0, to_date('12/28/2023', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user6', '여섯', 0, 0, to_date('01/01/2024', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user7', '일곱', 0, 0, to_date('01/02/2024', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user8', '여덟', 0, 0, to_date('02/03/2024', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user9', '아홉', 0, 0, to_date('02/02/2024', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');
insert into tb_zip (id, member_id, content, day_count, total_count, reg_date, color_code)
values (seq_tb_zip_id.nextval, 'user10', '열', 0, 0, to_date('02/06/2024', 'MM/DD/RRRR'), '#FFFFFF', 'ZP');

insert into tb_guest_board (id, zip_id, member_id, guest_content, reg_date)
values (seq_tb_guest_board_id.nextval, '1', 'user2', '내가 첫번째 방명록인가?', to_date('01/01/2024', 'MM/DD/RRRR'));
insert into tb_guest_board (id, zip_id, member_id, guest_content, reg_date)
values (seq_tb_guest_board_id.nextval, '1', 'user3', '내가 두번째 방명록인가?', to_date('01/02/2024', 'MM/DD/RRRR'));
insert into tb_guest_board (id, zip_id, member_id, guest_content, reg_date)
values (seq_tb_guest_board_id.nextval, '1', 'user4', '내가 세번째 방명록인가?', to_date('01/03/2024', 'MM/DD/RRRR'));

INSERT INTO tb_member VALUES ('user1', 'password1', '이철수', 'chulsoo_lee', TO_DATE('1992-03-20', 'YYYY-MM-DD'), 'M', '010-1111-2222', '운동', 'ISTJ', DEFAULT, '서울시 강서구');
INSERT INTO tb_member VALUES ('user2', 'pass123', '박지영', 'jiyoung_park', TO_DATE('1988-07-10', 'YYYY-MM-DD'), 'F', '010-3333-4444', '사진촬영', 'ENFJ', DEFAULT, '서울시 송파구');
INSERT INTO tb_member VALUES ('user3', 'qwerty', '장민호', 'minho_jang', TO_DATE('1993-11-05', 'YYYY-MM-DD'), 'M', '010-5555-6666', '요리', 'ENTP', DEFAULT, '서울시 서초구');
INSERT INTO tb_member VALUES ('user4', 'pass456', '이수진', 'soojin_lee', TO_DATE('1985-04-25', 'YYYY-MM-DD'), 'F', '010-7777-8888', '그림그리기', 'ISFJ', DEFAULT, '서울시 강동구');
INSERT INTO tb_member VALUES ('user5', 'abc123', '김상우', 'sangwoo_kim', TO_DATE('1997-09-15', 'YYYY-MM-DD'), 'M', '010-9999-0000', '독서', 'INTP', DEFAULT, '서울시 강북구');
INSERT INTO tb_member VALUES ('user6', 'pass789', '한미진', 'mijin_han', TO_DATE('1990-12-08', 'YYYY-MM-DD'), 'F', '010-1212-3434', '여행', 'INFJ', DEFAULT, '서울시 동대문구');
INSERT INTO tb_member VALUES ('user7', 'zxcvbn', '손영호', 'youngho_son', TO_DATE('1987-06-30', 'YYYY-MM-DD'), 'M', '010-4545-5656', '음악감상', 'ESFP', DEFAULT, '서울시 중랑구');
INSERT INTO tb_member VALUES ('user8', 'pass000', '이미정', 'mijeong_lee', TO_DATE('1982-01-12', 'YYYY-MM-DD'), 'F', '010-6767-7878', '등산', 'ISTP', DEFAULT, '서울시 노원구');
INSERT INTO tb_member VALUES ('user9', 'qazwsx', '정태호', 'taeho_jung', TO_DATE('1998-04-02', 'YYYY-MM-DD'), 'M', '010-8989-9090', '게임', 'ENFP', DEFAULT, '서울시 은평구');
INSERT INTO tb_member VALUES ('user10', 'pass111', '임수진', 'soojin_lim', TO_DATE('1989-08-22', 'YYYY-MM-DD'), 'F', '010-1010-1212', '자전거타기', 'ENTJ', DEFAULT, '서울시 양천구');

INSERT INTO tb_authority VALUES (1, 'user1', 'ROLE_ADMIN');
INSERT INTO tb_authority VALUES (2, 'user1', 'ROLE_USER');
INSERT INTO tb_authority VALUES (3, 'user2', 'ROLE_USER');
INSERT INTO tb_authority VALUES (4, 'user3', 'ROLE_ADMIN');
INSERT INTO tb_authority VALUES (5, 'user3', 'ROLE_USER');
INSERT INTO tb_authority VALUES (6, 'user4', 'ROLE_USER');
INSERT INTO tb_authority VALUES (7, 'user5', 'ROLE_ADMIN');
INSERT INTO tb_authority VALUES (8, 'user5', 'ROLE_USER');
INSERT INTO tb_authority VALUES (9, 'user6', 'ROLE_USER');
INSERT INTO tb_authority VALUES (10, 'user7', 'ROLE_USER');
INSERT INTO tb_authority VALUES (11, 'user8', 'ROLE_ADMIN');
INSERT INTO tb_authority VALUES (12, 'user8', 'ROLE_USER');
INSERT INTO tb_authority VALUES (13, 'user9', 'ROLE_USER');
INSERT INTO tb_authority VALUES (14, 'user10', 'ROLE_ADMIN');
INSERT INTO tb_authority VALUES (15, 'user10', 'ROLE_USER');

commit;