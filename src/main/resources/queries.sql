CREATE DATABASE release_demon
	CHARACTER SET utf8mb4
	COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE album (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL DEFAULT '',
  release_date DATE NOT NULL,
  artist_id BIGINT NOT NULL,
  cover VARCHAR(255) DEFAULT NULL,
  mbid VARCHAR(36) DEFAULT NULL,
  type VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE artist (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL DEFAULT '',
  mbid VARCHAR(36) DEFAULT NULL,
  spotify_id VARCHAR(36) DEFAULT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE album
  ADD CONSTRAINT FK_release_artist_id FOREIGN KEY (artist_id)
    REFERENCES artist(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE user (
  id BIGINT NOT NULL AUTO_INCREMENT,
  login VARCHAR(50) NOT NULL DEFAULT '',
  telegramId BIGINT DEFAULT NULL,
  password VARCHAR(255) NOT NULL DEFAULT '',
  email VARCHAR(50) DEFAULT NULL,
  lastfm_username VARCHAR(50) DEFAULT NULL,
  spotify_token VARCHAR(200) DEFAULT NULL,
  last_synchronized DATETIME DEFAULT NULL,
  updated DATETIME DEFAULT NULL,
  created DATETIME DEFAULT NULL,
  telegram_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE user
  ADD UNIQUE INDEX user_lastfm_username_uindex(lastfm_username);

ALTER TABLE user
  ADD UNIQUE INDEX user_login_uindex(login);


CREATE TABLE user_artist (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  artist_id BIGINT NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8,
COLLATE utf8_general_ci;

ALTER TABLE user_artist
  ADD CONSTRAINT user_artist_artist_id_fk FOREIGN KEY (artist_id)
    REFERENCES artist(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_artist
  ADD CONSTRAINT user_artist_user_id_fk FOREIGN KEY (user_id)
    REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE;


CREATE TABLE library_provider (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
)
CHARACTER SET utf8,
COLLATE utf8_general_ci;


CREATE TABLE release_demon.user_library_provider (
  userId BIGINT NOT NULL,
  library_provider BIGINT NOT NULL,
  identifier VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (userId, library_provider)
);

ALTER TABLE release_demon.user_library_provider
  ADD CONSTRAINT FK_user_library_provider_userId FOREIGN KEY (userId)
    REFERENCES release_demon.user(id) ON DELETE NO ACTION;

ALTER TABLE release_demon.user_library_provider
  ADD CONSTRAINT FK_user_library_provider_library_provider FOREIGN KEY (library_provider)
    REFERENCES release_demon.library_provider(id) ON DELETE NO ACTION;