insert into category
    (id, parent, name) values
    (1000, null, '알콜'),
    (1001, null, '음료'),
    (1002, null, '베이커리'),
    (1003, 1001, '커피'),
    (1004, 1001, '플랫치노'),
    (1005, 1003, '카페인'),
    (1006, 1003, '디카페인');

insert into menu
    (id, name, price, category_id, available) values
    (2001, '아메리카노', 1500, 1005, true),
    (2002, '라떼', 2500, 1005, true),
    (2003, '레몬에이드', 2500, 1004, true),
    (2004, '민트초코', 3000, 1004, true),
    (2005, '소금빵', 2000, 1002, true);

insert into option
    (id, name, type, menu_id, category_id, display_order) values
    (3001, '아이스/핫', 'CATEGORY', null, 1001, 1),
    (3002, '샷', 'CATEGORY', null, 1003, 1),
    (3003, '원두', 'CATEGORY', null, 1003, 2),
    (3004, '당도', 'CATEGORY', null, 1004, 1),
    (3005, '원유', 'MENU', 2002, null, 1);

insert into sub_option
    (id, name, price, display_order, option_id) values
    (4001, '아이스', 500, 1, 3001),
    (4002, '핫', 0, 2, 3001),
    (4003, '기본', 0, 1, 3002),
    (4004, '1샷', 500, 2, 3002),
    (4005, '2샷', 1000, 3, 3002),
    (4006, '다크', 0, 1, 3003),
    (4007, '산미', 0, 2, 3003),
    (4008, '더 달게', 0, 1, 3004),
    (4009, '덜 달게', 0, 2, 3004),
    (4010, '우유', 0, 1, 3005),
    (4011, '두유', 0, 2, 3005),
    (4012, '오트밀', 0, 3, 3005);
