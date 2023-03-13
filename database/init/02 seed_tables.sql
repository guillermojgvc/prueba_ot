-- Insert currency
INSERT INTO ontop.tbl_currency 
(currency_name,currency_abbreviate) 
VALUES('US DOLLAR','USD');

INSERT INTO ontop.tbl_currency 
(currency_name,currency_abbreviate) 
VALUES('EURO','EURO');

-- Insert user
INSERT INTO ontop.tbl_user
(name, identification)
VALUES('ONTOP INC', '1234567890');

-- Insert status
INSERT INTO ontop.tbl_status
(id, status_name)
VALUES(1, 'COMPLETED');

INSERT INTO ontop.tbl_status
(id, status_name)
VALUES(2, 'FAILED');

INSERT INTO ontop.tbl_status
(id, status_name)
VALUES(3, 'IN PROGRESS');

INSERT INTO ontop.tbl_status
(id, status_name)
VALUES(4, 'REFUND');

-- Insert wallet
INSERT INTO ontop.tbl_wallet
(balance, account_number, routing_number, id_tbl_currency, id_tbl_user)
VALUES(0, '0245253419','028444018', (SELECT id FROM ontop.tbl_currency WHERE currency_abbreviate = 'USD') , (SELECT id FROM ontop.tbl_user WHERE name = 'ONTOP INC'));