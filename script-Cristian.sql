/
CREATE OR REPLACE PROCEDURE DO_AUCTION_TRANZACTION 
(
  SELLER_ID IN NUMBER
, BUYER_ID IN NUMBER
, ITEM_ID IN NUMBER 
, RESULT OUT VARCHAR2 
) AS 
v_coins NUMBER;
v_price NUMBER;
BEGIN
  SELECT coins INTO v_coins FROM accounts WHERE ID = BUYER_ID;
  SELECT price INTO v_price FROM auction WHERE ID_ITEM = ITEM_ID;
  IF v_coins < v_price THEN
    RESULT := 'Not enough coins';
  ELSE
    DELETE FROM auction WHERE ID_GAMER = SELLER_ID AND ID_ITEM = ITEM_ID;
    DELETE FROM owned_items WHERE ID_OWNER = SELLER_ID AND ID_ITEM = ITEM_ID;
    INSERT INTO owned_items VALUES(BUYER_ID, ITEM_ID, sysdate, sysdate);
    RESULT := 'Done';
  END IF;
END DO_AUCTION_TRANZACTION;

/

CREATE OR REPLACE PROCEDURE BUY_GAME 
(
  BUYER_ID IN NUMBER 
, GAME_ID IN NUMBER 
, RESULT OUT VARCHAR2 
) AS 
v_coins NUMBER;
v_price NUMBER;
BEGIN
  SELECT coins INTO v_coins FROM accounts WHERE ID = BUYER_ID;
  SELECT price INTO v_price FROM games WHERE ID = GAME_ID;
  
  IF v_coins < v_price THEN
    RESULT := 'Not enough coins';
  ELSE
    INSERT INTO owned_games VALUES(BUYER_ID, GAME_ID, sysdate, sysdate);
    UPDATE accounts SET coins = coins-v_price WHERE ID = BUYER_ID;
    RESULT := 'Done';
  END IF;
END BUY_GAME;

/

CREATE OR REPLACE PROCEDURE BUY_ITEM 
(
  BUYER_ID IN NUMBER 
, ITEM_ID IN NUMBER 
, RESULT OUT VARCHAR2 
) AS 
v_coins NUMBER;
v_price NUMBER;
BEGIN
  SELECT coins INTO v_coins FROM accounts WHERE ID = BUYER_ID;
  SELECT price INTO v_price FROM items WHERE ID = ITEM_ID;
  
  IF v_coins < v_price THEN
    RESULT := 'Not enough coins';
  ELSE
    INSERT INTO owned_items VALUES(BUYER_ID, ITEM_ID, sysdate, sysdate);
    UPDATE accounts SET coins = coins-v_price WHERE ID = BUYER_ID;
    RESULT := 'Done';
  END IF;
END BUY_ITEM;
