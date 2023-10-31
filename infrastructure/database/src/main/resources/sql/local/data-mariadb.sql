insert into category
    (id, parent, name) values
    (1, null, '음료'),
    (2, null, '베이커리'),
    (3, 1, '커피'),
    (4, 1, '플랫치노'),
    (5, 3, '카페인'),
    (6, 3, '디카페인');

insert into menu
    (id, name, price, category_id, available) values
    (1, '아메리카노', 1500, 5, true),
    (2, '라떼', 2500, 5, true),
    (3, '레몬에이드', 2500, 4, true),
    (4, '민트초코', 3000, 4, true),
    (5, '소금빵', 2000, 2, true);

insert into option
    (id, name, type, menu_id, category_id, display_order) values
    (1, '아이스/핫', 'CATEGORY', null, 1, 1),
    (2, '샷', 'CATEGORY', null, 3, 1),
    (3, '원두', 'CATEGORY', null, 3, 2),
    (4, '당도', 'CATEGORY', null, 4, 1),
    (5, '원유', 'MENU', 2, null, 1);

insert into sub_option
    (id, name, price, display_order, option_id) values
    (1, '아이스', 500, 1, 1),
    (2, '핫', 0, 2, 1),
    (3, '기본', 0, 1, 2),
    (4, '1샷', 500, 2, 2),
    (5, '2샷', 1000, 3, 2),
    (6, '다크', 0, 1, 3),
    (7, '산미', 0, 2, 3),
    (8, '더 달게', 0, 1, 4),
    (9, '덜 달게', 0, 2, 4),
    (10, '우유', 0, 1, 5),
    (11, '두유', 0, 2, 5),
    (12, '오트밀', 0, 3, 5);
