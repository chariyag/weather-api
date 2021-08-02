CREATE TABLE IF NOT EXISTS weather (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    main varchar(256),
    description varchar(256),
    city varchar(256),
    country varchar(256)
);