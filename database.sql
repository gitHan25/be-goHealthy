DROP TABLE IF EXISTS content;

CREATE TABLE content(
                        id_content INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        title TEXT,
                        author VARCHAR(256) NOT NULL,
                        body_content TEXT NOT NULL,
                        created_at DATETIME NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
) ENGINE=InnoDB;

