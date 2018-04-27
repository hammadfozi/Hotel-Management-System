/* POPULATE IMPORTANT DATABASE */

/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE (type)
VALUES ('USER');

INSERT INTO USER_PROFILE (type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE (type)
VALUES ('MANAGER');

INSERT INTO USER_PROFILE (type)
VALUES ('UNVERIFIED');


/* Populate one Admin User which will further create other users for the application using GUI */
INSERT INTO APP_USER (username, password, first_name, last_name, email, token)
VALUES
  ('admin', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Test', 'Admin', 'admin@hyatt.com', '');
INSERT INTO APP_USER (username, password, first_name, last_name, email, token)
VALUES
  ('manager', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Test', 'Manager', 'manager@hyatt.com',
   '');
INSERT INTO APP_USER (username, password, first_name, last_name, email, token)
VALUES
  ('customer', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Test', 'Customer', 'customer@test.com',
   '');


/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT
    usr.id,
    profile.id
  FROM APP_USER AS usr, USER_PROFILE AS profile
  where usr.username = 'admin' and profile.type = 'ADMIN';

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT
    usr.id,
    profile.id
  FROM APP_USER AS usr, USER_PROFILE AS profile
  where usr.username = 'manager' and profile.type = 'MANAGER';

INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT
    usr.id,
    profile.id
  FROM APP_USER AS usr, USER_PROFILE AS profile
  where usr.username = 'customer' and profile.type = 'USER';


INSERT INTO ROOM_TYPE (id, base_price, type)
VALUES (1, 1500, 'FAMILY');

INSERT INTO ROOM_TYPE (id, base_price, type)
VALUES (2, 2000, 'DELUXE');

INSERT INTO ROOM_TYPE (id, base_price, type)
VALUES (3, 2500, 'EXECUTIVE');