drop table changes;
create table changes (
id int not null primary key,
game_id int not null,
price number(5,2),
added_at date
)
/

CREATE OR REPLACE PROCEDURE create_account
  (username in varchar2,pass in varchar2,email in varchar2,coins in number)
  IS
  id_max int :=0;
  BEGIN
  select max(id)+1 into id_max from accounts;
   if pass = lower(pass) or username = null or length(pass)<6 or pass = upper(pass) or pass = translate(pass,'0123456789','') then
    dbms_output.put_line('FAILED');
  else
  insert into accounts values(id_max,username,pass,email,coins,sysdate,sysdate);
  dbms_output.put_line('DONE');
  end if;
  END;
  /
  
create or replace procedure create_game
  (title in varchar2,price in number,launch_date in date)
  IS
  id_max int :=0;
  BEGIN
  select max(id)+1 into id_max from games;
  insert into games values(id_max,title,price,launch_date,sysdate,sysdate);
  dbms_output.put_line('DONE');
  END;
  /
    
create or replace procedure create_auction
  (id_gamer in number,id_item in number,price in number,exp_date in date)
  IS
  BEGIN
  insert into auction values(id_gamer,id_item,price,exp_date+2,sysdate,sysdate);
  dbms_output.put_line('DONE');
  END;
  /
  
    create or replace trigger updated_at_accounts
    after update of username,pass,email,coins on accounts
    begin
    update accounts
    set updated_at = sysdate;
    end;
    /

  create or replace trigger price_change
    after update or insert of price on games
    for each row
    declare
    new_id int := 0;
    begin
    select max(id) into new_id from changes;
    if new_id = NULL then
      new_id:=0;
    end if;
    insert into changes values(new_id+1,:NEW.id,:NEW.price,sysdate);
    end;
    /
    
  create or replace trigger updated_at_items
    after update of item_name,class,typeof,wear,rarity,price on items
    begin
    update items
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_games
    after update of title,price,launch_date on games
    begin
    update games
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_specific_items
    after update of id_game,id_item on specific_items
    begin
    update specific_items
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_owned_items
    after update of id_owner,id_item on owned_items
    begin
    update owned_items
    set updated_at = sysdate;
    end;
    /
    
     create or replace trigger updated_at_auction
    after update of id_gamer,id_item,price,exp_date on auction
    begin
    update auction
    set updated_at = sysdate;
    end;
    /
  
  create or replace trigger password_alphanumeric
    before update of pass on accounts
    for each row
    declare
    wrong_pass EXCEPTION;
    old_pass VARCHAR2(50) := '';
    begin
    dbms_output.put_line('Password is ' || :NEW.pass);
    old_pass := :NEW.pass;
      if old_pass = lower(old_pass) or length(old_pass)<6 or old_pass = upper(old_pass) or old_pass = translate(old_pass,'0123456789','') then
        rollback;
        dbms_output.put_line('Wrong type of password');
        end if;
    end;
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

/

create or replace PROCEDURE SELL_ITEM 
(
  SELLER_ID IN NUMBER 
, ITEM_ID IN NUMBER 
, PRICE IN NUMBER
, RESULT OUT VARCHAR2
) AS 
BEGIN
  INSERT INTO auction VALUES (SELLER_ID, ITEM_ID, PRICE, sysdate + 30, sysdate, sysdate);
  DELETE FROM owned_items WHERE id_owner = seller_id and ITEM_ID = ID_ITEM;
  RESULT := 'Item added to auction.';
END SELL_ITEM;

/

create or replace PROCEDURE ELIMINATE_FROM_AUCTION 
(
  SELLER_ID IN NUMBER 
, ITEM_ID IN NUMBER 
, RESULT OUT VARCHAR2
) AS 
BEGIN
  DELETE FROM auction WHERE ID_GAMER = SELLER_ID AND ITEM_ID = ID_ITEM;
  INSERT INTO owned_items Values(SELLER_ID, ITEM_ID, sysdate, sysdate);
  RESULT := 'Done';
END ELIMINATE_FROM_AUCTION;
