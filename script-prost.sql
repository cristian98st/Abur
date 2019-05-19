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
    after update on accounts
    begin
    update accounts
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_items
    after update on items
    begin
    update items
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_games
    after update on games
    begin
    update games
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_specific_items
    after update on specific_items
    begin
    update specific_items
    set updated_at = sysdate;
    end;
    /
    
  create or replace trigger updated_at_owned_items
    after update on owned_items
    begin
    update owned_items
    set updated_at = sysdate;
    end;
    /
    
     create or replace trigger updated_at_auction
    after update on auction
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