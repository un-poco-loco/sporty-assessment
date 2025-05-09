-- V2__Insert_sample_data.sql
INSERT INTO bets (bet_id, user_id, event_id, event_market_id, event_winner_id, bet_amount)
VALUES ('bet1', 'user1', 'event1', 'market1', 'team1', 100.00),
       ('bet2', 'user2', 'event1', 'market1', 'team2', 150.00),
       ('bet3', 'user3', 'event2', 'market1', 'team3', 200.00);