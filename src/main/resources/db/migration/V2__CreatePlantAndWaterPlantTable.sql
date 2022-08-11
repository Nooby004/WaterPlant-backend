CREATE TABLE plant (
    id serial PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100),
    water_frequency INT,
    picture_url VARCHAR,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE water_plant (
    id serial PRIMARY KEY,
    plant_id INT NOT NULL,
    picture_url VARCHAR,
    creation_date TIMESTAMP,
    CONSTRAINT fk_plant FOREIGN KEY(plant_id) REFERENCES plant(id)
);