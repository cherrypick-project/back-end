-- user
INSERT INTO USER (ID, PROVIDER_ID, PASSWORD, NICKNAME, ACTIVATED, AUTHORITY) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1, 'ROLE_ADMIN');
INSERT INTO USER (ID, PROVIDER_ID, PASSWORD, NICKNAME, ACTIVATED, AUTHORITY, EMAIL, JOB, CAREER , KNOWN_PATH) VALUES (2, 'user', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'user', 1, 'ROLE_USER', 'user@naver.com','백엔드','LESS_THAN_3YEARS','SEARCH');
INSERT INTO USER (ID, PROVIDER_ID, PASSWORD, NICKNAME, ACTIVATED, AUTHORITY, EMAIL) VALUES (3, 'unSignedUser', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'user', 1, 'ROLE_NEED_MORE_INFO','unSignedUser@naver.com');
INSERT INTO USER (ID, PROVIDER_ID, PASSWORD, NICKNAME, ACTIVATED, AUTHORITY, EMAIL, JOB, CAREER , KNOWN_PATH) VALUES (4, 'nonActiveUser', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'nonActiveUser', 0, 'ROLE_USER', 'nonActiveUser@naver.com','프론트엔드','LESS_THAN_3YEARS','SEARCH');

-- lecture
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (1,'desktop_img_url','java,effectiveJava,backend,whiteship','이펙티브자바 강의',0,1,'mobile_img_url','이펙티브자바 완벽 공략 1부','https://www.inflearn.com/course/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94-1','88000','tablet_img_url','Inflean','김영한,백기선');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (2,'desktop_img_url','java,refactoring,backend,whiteship','리팩토링 강의',0,1,'mobile_img_url','코딩으로 학습하는 리팩토링','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','77000','tablet_img_url','Inflean','백기선');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (3,'desktop_img_url','java,basic,backend,whiteship','자바 기초 강의',0,1,'mobile_img_url','자바 기초로 공부하기','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','77000','tablet_img_url','fastCampus','패캠강사1');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (4,'desktop_img_url','javascript,backend','자바스크립트 백엔드',0,1,'mobile_img_url','자바스크립트 백엔드 기초','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','67000','tablet_img_url','fastCampus','패캠강사1,패캠강사2');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (5,'desktop_img_url','frontend,react,javascript','리액트 프론트강의',0,1,'mobile_img_url','리액트 시작하기','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','57000','tablet_img_url','Inflean','조훈,김영한');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (6,'desktop_img_url','devops,aws,gcp,cloud','데브옵스 강의 1편',0,1,'mobile_img_url','데브옵스 강의 1편','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','79000','tablet_img_url','Inflean','김영한');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (7,'desktop_img_url','frontend,react,javascript','리팩토링 강의',0,1,'mobile_img_url','리팩토링 리액트 자바스크립트 강의','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','71000','tablet_img_url','Inflean','백기선');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (8,'desktop_img_url','devops,aws,gcp,cloud','데브옵스 강의 2편',0,1,'mobile_img_url','데브옵스 강의 2편','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','72000','tablet_img_url','Inflean','조훈');
insert into lecture (id, desktop_img_url, hash_tag_list, info, is_offline, is_opened, mobile_img_url, name,origin_link, price, tablet_img_url, lecture_company, lecturer)
values (9,'desktop_img_url','devops,aws,gcp,cloud','데브옵스 강의 3편',0,1,'mobile_img_url','데브옵스 강의 3편','https://www.inflearn.com/course/%EB%A6%AC%ED%8C%A9%ED%86%A0%EB%A7%81','73000','tablet_img_url','fastCampus','패캠강사2');

-- review
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (1,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','READY','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (2,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (3,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (4,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','4');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (5,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (6,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','REJECT','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','4');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (7,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (8,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','4');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (9,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','2');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (10,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','1','4');
insert into review (id, cost_performance, one_line_comment, rating, recommendation, status, strength_comment, weakness_comment, lecture_id,user_id)
values (11,'VERY_SATISFACTION','아주 좋은 강의예여','5.0','GOOD','APPROVE','기초부터 잘 알려줍니다.','마이크가 조금 안들려요','4','4');


-- bookmark
insert into bookmark (id, lecture_id, user_id) values(1,1,2);
insert into bookmark (id, lecture_id, user_id) values(2,2,2);
insert into bookmark (id, lecture_id, user_id) values(3,3,2);
