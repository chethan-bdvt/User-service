CREATE TABLE users(
	id UUID PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	email VARCHAR(50) NOT NULL UNIQUE,
	mobile_number VARCHAR(15) UNIQUE,
	state_id UUID,
	district_id UUID,
	subscription_type VARCHAR(15),
	subscription_start_date TIMESTAMP,
	subscription_end_date TIMESTAMP,
	active BOOLEAN NOT NULL DEFAULT TRUE,
	subscription_active BOOLEAN NOT NULL DEFAULT FALSE,
	created_at TIMESTAMP,
	updated_at TIMESTAMP
);