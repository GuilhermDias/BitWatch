
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    document VARCHAR(14) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);