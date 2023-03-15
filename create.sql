CREATE SCHEMA IF NOT EXISTS moro;

CREATE TABLE moro.rating
(
    id      bigserial not null,
    book_id int8      not null,
    month   int4      not null,
    rating  float8    not null,
    review  text,
    primary key (id)
);

TRUNCATE TABLE moro.RATING RESTART IDENTITY;

INSERT INTO moro.RATING (id, book_id, month, rating, review)
VALUES (default, 1513, 12, 4.7, 'One of the best'),
       (default, 1513, 11, 4.5, 'Great one'),
       (default, 1513, 10, 4, 'Cool book'),
       (default, 6593, 9, 3, 'Wanted more'),
       (default, 6593, 8, 3.5, 'Have read better ones'),
       (default, 6593, 7, 4, 'Not bad'),
       (default, 1259, 6, 3, 'Small book'),
       (default, 1259, 5, 3.9, 'Did not like the end'),
       (default, 1259, 4, 4, 'Loved the scenario')