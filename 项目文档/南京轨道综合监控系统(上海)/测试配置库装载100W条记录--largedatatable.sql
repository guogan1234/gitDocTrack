CREATE TABLE largedatatable
(
  id bigint,
  col_1 character varying(10),
  col_2 bigint,
  col_3 character varying(10),
  col_4 character varying(10),
  col_5 bigint,
  col_6 character varying(10),
  col_7 character varying(10),
  col_8 character varying(10),
  col_9 character varying(10),
  col_10 character varying(10),
  col_11 character varying(10),
  col_12 character varying(10),
  col_13 character varying(10),
  col_14 bigint,
  col_15 character varying(10),
  col_16 character varying(10),
  col_17 character varying(10),
  col_18 bigint,
  col_19 character varying(10),
  col_20 character varying(10),
  col_21 character varying(10),
  col_22 character varying(10),
  col_23 character varying(10),
  col_24 character varying(10),
  col_25 character varying(10),
  col_26 character varying(10),
  col_27 character varying(10),
  col_28 character varying(10),
  col_29 character varying(10),
  col_30 character varying(10),
  col_31 character varying(10),
  col_32 character varying(10),
  col_33 character varying(10),
  col_34 character varying(10),
  col_35 character varying(10),
  col_36 character varying(10),
  col_37 character varying(10),
  col_38 character varying(10),
  col_39 character varying(10),
  col_40 character varying(10),
  col_41 character varying(10),
  col_42 character varying(10),
  col_43 character varying(10),
  col_44 character varying(10),
  col_45 character varying(10),
  col_46 character varying(10),
  col_47 character varying(10),
  col_48 character varying(10),
  col_49 character varying(10),
  col_50 character varying(10)
);

---------------------------------

CREATE OR REPLACE FUNCTION largedatatable()
  RETURNS boolean AS
$BODY$  
declare ii integer;  
  begin  
  II:=1;  
  FOR ii IN 1..1000000 LOOP  
  INSERT INTO largedatatable(id,col_1,col_2,col_3,col_4,col_5,col_6,col_7,col_8,col_9,col_10,col_11,col_12,col_13,col_14,col_15,col_16,col_17,col_18,col_19,col_20,col_21,col_22,col_23,col_24,col_25,col_26,col_27,col_28,col_29,col_30,col_31,col_32,col_33,col_34,col_35,col_36,col_37,col_38,col_39,col_40,col_41,col_42,col_43,col_44,col_45,col_46,col_47,col_48,col_49,col_50) VALUES (ii,'123456789',ii,'123456789','123456789',ii,'123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789',ii,'123456789','123456789','123456789',ii,'123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789','123456789');  
  end loop;  
  return true;  
  end;  
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-------------------------------------------

select * from largedatatable()
