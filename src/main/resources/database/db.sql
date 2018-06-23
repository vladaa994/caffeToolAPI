USE caffe_tool;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `active` BOOLEAN NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS player;
CREATE TABLE player (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `active` BOOLEAN NOT NULL,
  `win` INT NOT NULL,
  `lost` INT NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

DROP TABLE IF EXISTS game;
CREATE TABLE game (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_time` DATETIME,
  `end_time` DATETIME,
  `table_number` INT NOT NULL ,
  `paid` BOOLEAN NOT NULL,
  `user_id` INT NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `deleted` BOOLEAN NOT NULL,
  `bill` FLOAT,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

DROP TABLE IF EXISTS player_game;
CREATE TABLE player_game (
  `player_id` int not null,
  `game_id` INT NOT NULL,
  PRIMARY KEY (player_id, game_id)
)ENGINE = InnoDB;

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role(
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (user_id, role_id)
)ENGINE = InnoDB;

alter table game add FOREIGN KEY (user_id) REFERENCES user(id);
alter table player_game add FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE ON UPDATE CASCADE;
alter table player_game add FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE ON UPDATE CASCADE;
alter table user_role add FOREIGN KEY (user_id) REFERENCES user(id);
alter table user_role add FOREIGN KEY (role_id) REFERENCES role(id);
#password is 12345
insert into user(username, password, active) values ('vlada', '$2a$10$F8lmAomUtMtg8/vyIZXv.efwx4LfuZ5nKZlstZcCww1zkDdyVJ6PW', true );
insert into role(name) values ('ROLE_SUPERADMIN');
insert into role(name) values ('ROLE_USER');
insert into role(name) values ('ROLE_ADMIN');
insert into user_role values (1, 1);
