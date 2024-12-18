DROP TABLE IF EXISTS content;
drop table if exists  food_consumption;
drop table if exists users;
create table users(
                      user_id int AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(128) NOT NULL,
                      username VARCHAR(100) NOT NULL,
                      password VARCHAR(128) NOT NULL,
                      name VARCHAR(100),
                      token VARCHAR(100),
                      token_expired_at BIGINT,
                      UNIQUE (token)
)ENGINE InnoDB;



CREATE TABLE content(
                        id_content INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        title TEXT,
                        author VARCHAR(256) NOT NULL,
                        body_content TEXT NOT NULL,
                        created_at DATETIME NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB;


CREATE TABLE food_consumption(
    food_id INT AUTO_INCREMENT PRIMARY KEY ,
    user_id INT,
    food_name VARCHAR(128),
    calories double(5,2),
    consumption_date DATETIME,
    quantity int,
    FOREIGN KEY (user_id) REFERENCES  users(user_id)
)ENGINE =InnoDB;

desc food_consumption;

desc content;

DESC USERS;
