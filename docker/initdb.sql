CREATE TABLE city (
    id VARCHAR PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(256) UNIQUE NOT NULL,
    code VARCHAR(4) UNIQUE NOT NULL
);

CREATE TABLE facility (
    id VARCHAR PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(256) UNIQUE NOT NULL,
    type VARCHAR NOT NULL,
    city_id VARCHAR NOT NULL,
    capacity INT,
    CONSTRAINT fk_city_id
      FOREIGN KEY(city_id) 
	    REFERENCES city(id)
);

CREATE TABLE vehicle (
    id VARCHAR PRIMARY KEY UNIQUE NOT NULL,
    type VARCHAR NOT NULL,
    city_id VARCHAR NOT NULL,
    is_parked BOOLEAN NOT NULL,
    facility_id VARCHAR,
    CONSTRAINT fk_city_id
      FOREIGN KEY(city_id) 
	    REFERENCES city(id),
    CONSTRAINT fk_facility_id
      FOREIGN KEY(facility_id) 
	    REFERENCES facility(id)
);
