DROP TABLE IF EXISTS Category;
CREATE TABLE Category(
    category VARCHAR (32) PRIMARY KEY,
);
DROP TABLE IF EXISTS Users;
CREATE TABLE Users (
    username VARCHAR (64) PRIMARY KEY,
    password VARCHAR (32) NOT NULL,
    first_name VARCHAR (128) NOT NULL,
    last_name VARCHAR (128) NOT NULL,
    email VARCHAR (256) NOT NULL,
    birthday DATE,
    experience_points INTEGER,
    level INTEGER,
    enable BIT NOT NULL DEFAULT 1
);
DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles(
    role VARCHAR (32) NOT NULL ,
    username VARCHAR (64) NOT NULL,
    FOREIGN KEY (username) REFERENCES Users(username)
);
DROP TABLE IF EXISTS Posts;
CREATE TABLE Posts (
    post_id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(64)  NOT NULL,
    description VARCHAR (1024) NOT NULL,
    creationDay DATE NOT NULL,
    likes INTEGER,
    enable BIT NOT NULL,
    son_type VARCHAR (36),
    starting_date DATE,
    deadline DATE,
    post_id_ref VARCHAR (36),
    hasBest BIT,
    category VARCHAR(36),
    username VARCHAR(64),
    FOREIGN KEY (username) REFERENCES Users(username),
    FOREIGN KEY (category) REFERENCES Category(category),
    FOREIGN KEY (post_id_ref) REFERENCES Posts(post_id)
);
DROP TABLE IF EXISTS Exercises;
CREATE TABLE Exercises(
    exercise_id VARCHAR (36) PRIMARY KEY,
    description VARCHAR (1024) NOT NULL,
    enable BIT NOT NULL,
    difficulty INTEGER NOT NULL,
    experience_points INTEGER,
    drag BIT,
    son_type VARCHAR (36),
    post_id VARCHAR (36),
    FOREIGN KEY (post_id) REFERENCES Posts(post_ID)
);
DROP TABLE IF EXISTS  Questions;
CREATE TABLE Questions (
    question_id VARCHAR (36)  PRIMARY KEY,
    texts VARCHAR (1024) NOT NULL,
    enable BIT NOT NULL,
    exercise_id VARCHAR (36),
    FOREIGN KEY (exercise_id) REFERENCES Exercises(exercise_id)
);
DROP TABLE IF EXISTS Solutions ;
CREATE TABLE Solutions(
    solution_id VARCHAR (36) PRIMARY KEY,
    position INTEGER,
    texts VARCHAR (1024) NOT NULL,
    correct BIT,
    enable BIT NOT NULL,
    question_id VARCHAR (36),
    FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);
DROP TABLE IF EXISTS Comments;
CREATE TABLE Comments(
    comment_id VARCHAR (36) PRIMARY KEY,
    comment VARCHAR (2048) NOT NULL,
    creation_day DATE NOT NULL,
    likes INTEGER,
    best BIT,
    enable BIT  NOT NULL,
    post_id VARCHAR (36),
    username VARCHAR(64) NOT NULL,
    comment_id_fk VARCHAR (36),
    FOREIGN KEY (username) REFERENCES Users(username),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id),
    FOREIGN KEY (comment_id_fk) REFERENCES Comments(comment_id)
);
DROP TABLE IF EXISTS Reply;
CREATE TABLE Reply(
    comment_id VARCHAR (36) PRIMARY KEY,
    comment VARCHAR (2048) NOT NULL,
    creation_day DATE NOT NULL,
    likes INTEGER,
    best BIT,
    enable BIT  NOT NULL,
    username VARCHAR(64) NOT NULL,
    comment_id_fk VARCHAR (36),
    FOREIGN KEY (username) REFERENCES Users(username),
    FOREIGN KEY (comment_id_fk) REFERENCES Comments(comment_id)
);
DROP TABLE IF EXISTS UserCategory;
CREATE TABLE UserCategory(
  category VARCHAR (32),
  user VARCHAR(32),
  PRIMARY KEY (category,user),
  FOREIGN KEY (category) REFERENCES Category(category),
  FOREIGN KEY (user)REFERENCES Users(username)
);
DROP TABLE IF EXISTS Messages;
CREATE TABLE Messages(
    message_id VARCHAR (36) PRIMARY KEY,
    content VARCHAR (1024) NOT NULL,
    user_sender VARCHAR (64),
    user_receiver VARCHAR (64),
    FOREIGN KEY (user_sender) REFERENCES Users(username),
    FOREIGN KEY (user_receiver) REFERENCES Users(username)
);
DROP TABLE IF EXISTS Submissions;
CREATE TABLE Submissions(
    mark FLOAT NOT NULL,
    username VARCHAR (64),
    exercise VARCHAR (36),
    creation_date TIMESTAMP,
    pass BIT,
    PRIMARY KEY (username,exercise,creation_date),
    FOREIGN KEY (username) REFERENCES Users(username),
    FOREIGN KEY (exercise) REFERENCES Exercises(exercise_ID)
);
