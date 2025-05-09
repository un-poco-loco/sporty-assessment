-- V1__Create_tables.sql
CREATE TABLE event_outcomes
(
    event_id        VARCHAR(255) PRIMARY KEY,
    event_name      VARCHAR(255) NOT NULL,
    event_winner_id VARCHAR(255) NOT NULL
);

CREATE TABLE bets
(
    bet_id          VARCHAR(255) PRIMARY KEY,
    user_id         VARCHAR(255)   NOT NULL,
    event_id        VARCHAR(255)   NOT NULL,
    event_market_id VARCHAR(255)   NOT NULL,
    event_winner_id VARCHAR(255)   NOT NULL,
    bet_amount      DECIMAL(19, 2) NOT NULL
);