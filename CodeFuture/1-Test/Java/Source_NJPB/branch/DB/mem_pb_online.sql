--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.15
-- Dumped by pg_dump version 10.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: business; Type: SCHEMA; Schema: -; Owner: mempb
--

CREATE SCHEMA business;


ALTER SCHEMA business OWNER TO mempb;

SET search_path = public, pg_catalog;

--
-- Name: domain_1; Type: DOMAIN; Schema: public; Owner: mempb
--

CREATE DOMAIN domain_1 AS character(10);


ALTER DOMAIN domain_1 OWNER TO mempb;

--
-- Name: fn_update_param_version(); Type: FUNCTION; Schema: public; Owner: mempb
--

CREATE FUNCTION fn_update_param_version() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

BEGIN
  NEW.version = nextval('bussiness.obj_param_version'::regclass); -- table column last_update_time
  RETURN NEW;
END;

$$;


ALTER FUNCTION public.fn_update_param_version() OWNER TO mempb;

--
-- Name: fn_version_owner_upgrade(); Type: FUNCTION; Schema: public; Owner: mempb
--

CREATE FUNCTION fn_version_owner_upgrade() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN


  --NEW.last_update_time = now(); -- table column last_update_time
  INSERT into public.sys_version_owner(owner_id,create_by) values (1, NEW.last_update_by);

  RETURN NEW;



END;


$$;


ALTER FUNCTION public.fn_version_owner_upgrade() OWNER TO mempb;

--
-- Name: fn_version_project_form_template_upgrade(); Type: FUNCTION; Schema: public; Owner: mempb
--

CREATE FUNCTION fn_version_project_form_template_upgrade() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

  -- obj_form_template
  INSERT INTO public.sys_version_project(project_id, version_type_id, create_by) 
    SELECT aftp.project_id, 100::integer, NEW.last_update_by 
      FROM forms.asso_form_template_project aftp WHERE aftp.template_id = NEW.id AND aftp.is_delete IS NULL;

  RETURN NEW;

END;


$$;


ALTER FUNCTION public.fn_version_project_form_template_upgrade() OWNER TO mempb;

--
-- Name: fn_version_project_upgrade(); Type: FUNCTION; Schema: public; Owner: mempb
--

CREATE FUNCTION fn_version_project_upgrade() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

  --obj_estate, asso_form_template_project
  INSERT into public.sys_version_project(project_id, version_type_id, create_by) values (NEW.project_id, 100, NEW.last_update_by);

  RETURN NEW;

END;


$$;


ALTER FUNCTION public.fn_version_project_upgrade() OWNER TO mempb;

SET search_path = business, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: asso_estate_module_type; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE asso_estate_module_type (
    id integer NOT NULL,
    estate_type_id integer NOT NULL,
    module_type_id integer NOT NULL,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    parent_id integer
);


ALTER TABLE asso_estate_module_type OWNER TO mempb;

--
-- Name: COLUMN asso_estate_module_type.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_estate_module_type.estate_type_id IS '设备类型id';


--
-- Name: COLUMN asso_estate_module_type.module_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_estate_module_type.module_type_id IS '模块类型id';


--
-- Name: COLUMN asso_estate_module_type.parent_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_estate_module_type.parent_id IS '父节点id 暂不使用';


--
-- Name: asso_estate_module_type_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE asso_estate_module_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE asso_estate_module_type_id_seq OWNER TO mempb;

--
-- Name: asso_estate_module_type_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE asso_estate_module_type_id_seq OWNED BY asso_estate_module_type.id;


--
-- Name: asso_user_project; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE asso_user_project (
    id integer NOT NULL,
    user_id integer NOT NULL,
    project_id integer NOT NULL,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE asso_user_project OWNER TO mempb;

--
-- Name: TABLE asso_user_project; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE asso_user_project IS '用户项目关系表';


--
-- Name: COLUMN asso_user_project.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_user_project.create_time IS '数据库维护';


--
-- Name: COLUMN asso_user_project.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_user_project.create_by IS '用户ID';


--
-- Name: COLUMN asso_user_project.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_user_project.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN asso_user_project.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_user_project.last_update_by IS '用户ID';


--
-- Name: COLUMN asso_user_project.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN asso_user_project.remove_time IS '删除时间';


--
-- Name: asso_user_project_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE asso_user_project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE asso_user_project_id_seq OWNER TO mempb;

--
-- Name: asso_user_project_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE asso_user_project_id_seq OWNED BY asso_user_project.id;


--
-- Name: obj_barcode_image_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_barcode_image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_barcode_image_id_seq OWNER TO mempb;

--
-- Name: obj_barcode_image; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_barcode_image (
    id integer DEFAULT nextval('obj_barcode_image_id_seq'::regclass) NOT NULL,
    bar_code_path character varying(255),
    bar_code_sn character varying(255),
    bar_code_message character varying(255),
    bar_code_category integer,
    export_time timestamp without time zone,
    activate_time timestamp without time zone,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    relation integer
);


ALTER TABLE obj_barcode_image OWNER TO mempb;

--
-- Name: COLUMN obj_barcode_image.bar_code_category; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.bar_code_category IS '二维码类别 0:站点 1:车辆 ';


--
-- Name: COLUMN obj_barcode_image.export_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.export_time IS '导出时间';


--
-- Name: COLUMN obj_barcode_image.activate_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.activate_time IS '激活时间';


--
-- Name: COLUMN obj_barcode_image.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.create_time IS '数据库维护';


--
-- Name: COLUMN obj_barcode_image.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.create_by IS '用户ID';


--
-- Name: COLUMN obj_barcode_image.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_barcode_image.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_barcode_image.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.remove_time IS '删除时间';


--
-- Name: COLUMN obj_barcode_image.relation; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_barcode_image.relation IS '二维码关联设备 0:未关联 1:已关联';


--
-- Name: obj_corporation; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_corporation (
    id integer NOT NULL,
    corp_name character varying(50),
    corp_no integer,
    corp_addr character varying(50),
    corp_contact_name character varying(20),
    corp_contact_tel character varying(20),
    corp_level integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_corporation OWNER TO mempb;

--
-- Name: TABLE obj_corporation; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_corporation IS '公司';


--
-- Name: COLUMN obj_corporation.id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.id IS 'ID';


--
-- Name: COLUMN obj_corporation.corp_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_name IS '公司名称';


--
-- Name: COLUMN obj_corporation.corp_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_no IS '公司编号';


--
-- Name: COLUMN obj_corporation.corp_addr; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_addr IS '公司地址';


--
-- Name: COLUMN obj_corporation.corp_contact_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_contact_name IS '联系人';


--
-- Name: COLUMN obj_corporation.corp_contact_tel; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_contact_tel IS '联系电话';


--
-- Name: COLUMN obj_corporation.corp_level; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.corp_level IS '公司级别 0：总公司 1：分公司';


--
-- Name: COLUMN obj_corporation.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.create_time IS '数据库维护';


--
-- Name: COLUMN obj_corporation.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.create_by IS '用户ID';


--
-- Name: COLUMN obj_corporation.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_corporation.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_corporation.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_corporation.remove_time IS '删除时间';


--
-- Name: obj_corporation_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_corporation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_corporation_id_seq OWNER TO mempb;

--
-- Name: obj_corporation_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_corporation_id_seq OWNED BY obj_corporation.id;


--
-- Name: obj_device_push; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_device_push (
    id integer NOT NULL,
    device_id character varying(50),
    user_id integer,
    os character varying(25),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    vendor character varying(50)
);


ALTER TABLE obj_device_push OWNER TO mempb;

--
-- Name: TABLE obj_device_push; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_device_push IS '设备推送表';


--
-- Name: COLUMN obj_device_push.device_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.device_id IS '设备deviceID';


--
-- Name: COLUMN obj_device_push.user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.user_id IS '用户ID';


--
-- Name: COLUMN obj_device_push.os; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.os IS '操作系统';


--
-- Name: COLUMN obj_device_push.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.create_time IS '数据库维护';


--
-- Name: COLUMN obj_device_push.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.create_by IS '用户ID';


--
-- Name: COLUMN obj_device_push.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_device_push.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_device_push.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.remove_time IS '删除时间';


--
-- Name: COLUMN obj_device_push.vendor; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_device_push.vendor IS '系统供应商';


--
-- Name: obj_device_push_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_device_push_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_device_push_id_seq OWNER TO mempb;

--
-- Name: obj_device_push_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_device_push_id_seq OWNED BY obj_device_push.id;


--
-- Name: seq_estate_logical_id; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE seq_estate_logical_id
    START WITH 10001000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_estate_logical_id OWNER TO mempb;

--
-- Name: obj_estate; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate (
    id integer NOT NULL,
    estate_name character varying(50) NOT NULL,
    station_id integer,
    category integer,
    estate_type_id integer,
    estate_status_id integer,
    estate_sn character varying(50),
    supplier_id integer,
    estate_batch character varying(50),
    parent_id integer,
    logical_id integer DEFAULT nextval('seq_estate_logical_id'::regclass),
    project_id integer,
    estate_path character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    estate_no integer,
    bike_frame_no integer,
    bicycle_stake_bar_code character varying(255)
);


ALTER TABLE obj_estate OWNER TO mempb;

--
-- Name: COLUMN obj_estate.station_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.station_id IS '站点id';


--
-- Name: COLUMN obj_estate.category; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.category IS '0：设备-站点   1：设备-车辆  ';


--
-- Name: COLUMN obj_estate.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.estate_type_id IS '关联-设备类型表';


--
-- Name: COLUMN obj_estate.estate_sn; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.estate_sn IS '设备SN';


--
-- Name: COLUMN obj_estate.estate_batch; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.estate_batch IS '为入库时间，格式：yyyy-mm-dd';


--
-- Name: COLUMN obj_estate.parent_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.parent_id IS '模块所属设备id   --RESERVED';


--
-- Name: COLUMN obj_estate.logical_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.logical_id IS '从10001000开始';


--
-- Name: COLUMN obj_estate.project_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.project_id IS '项目ID-不对用户可见';


--
-- Name: COLUMN obj_estate.estate_path; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.estate_path IS '使用logical_id关联';


--
-- Name: COLUMN obj_estate.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.create_time IS '数据库维护';


--
-- Name: COLUMN obj_estate.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.create_by IS '用户ID';


--
-- Name: COLUMN obj_estate.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_estate.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_estate.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.remove_time IS '删除时间';


--
-- Name: COLUMN obj_estate.estate_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.estate_no IS '设备编号';


--
-- Name: COLUMN obj_estate.bike_frame_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate.bike_frame_no IS '车架号';


--
-- Name: obj_estate_bak; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate_bak (
    id integer NOT NULL,
    estate_name character varying(50) NOT NULL,
    station_id integer,
    category integer,
    estate_type_id integer,
    estate_status_id integer,
    estate_sn character varying(50),
    supplier_id integer,
    estate_batch character varying(50),
    parent_id integer,
    logical_id integer DEFAULT nextval('seq_estate_logical_id'::regclass),
    project_id integer,
    estate_path character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    estate_no integer,
    bike_frame_no integer
);


ALTER TABLE obj_estate_bak OWNER TO mempb;

--
-- Name: obj_estate_bak_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_bak_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_bak_id_seq OWNER TO mempb;

--
-- Name: obj_estate_bak_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_bak_id_seq OWNED BY obj_estate_bak.id;


--
-- Name: obj_estate_excel; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate_excel (
    id integer NOT NULL,
    estate_name character varying(50) NOT NULL,
    station_id integer,
    category integer,
    estate_type_id integer,
    estate_status_id integer,
    estate_sn character varying(50),
    supplier_id integer,
    estate_batch character varying(50),
    parent_id integer,
    logical_id integer DEFAULT nextval('seq_estate_logical_id'::regclass),
    project_id integer,
    estate_path character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    estate_no integer,
    bike_frame_no integer
);


ALTER TABLE obj_estate_excel OWNER TO mempb;

--
-- Name: obj_estate_excel_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_excel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_excel_id_seq OWNER TO mempb;

--
-- Name: obj_estate_excel_id_seq1; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_excel_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_excel_id_seq1 OWNER TO mempb;

--
-- Name: obj_estate_excel_id_seq1; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_excel_id_seq1 OWNED BY obj_estate_excel.id;


--
-- Name: obj_estate_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_id_seq OWNER TO mempb;

--
-- Name: obj_estate_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_id_seq OWNED BY obj_estate.id;


--
-- Name: obj_estate_status; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate_status (
    id integer NOT NULL,
    status_name character varying(50) NOT NULL,
    status_name_en character varying(50),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_estate_status OWNER TO mempb;

--
-- Name: COLUMN obj_estate_status.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_status.create_time IS '数据库维护';


--
-- Name: COLUMN obj_estate_status.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_status.create_by IS '用户ID';


--
-- Name: COLUMN obj_estate_status.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_status.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_estate_status.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_status.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_estate_status.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_status.remove_time IS '删除时间';


--
-- Name: obj_estate_status_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_status_id_seq OWNER TO mempb;

--
-- Name: obj_estate_status_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_status_id_seq OWNED BY obj_estate_status.id;


--
-- Name: obj_estate_supplier; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate_supplier (
    id integer NOT NULL,
    supplier_name character varying(50) NOT NULL,
    supplier_addr character varying(200),
    supplier_tel character varying(50),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_estate_supplier OWNER TO mempb;

--
-- Name: TABLE obj_estate_supplier; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_estate_supplier IS '设备供应商表';


--
-- Name: COLUMN obj_estate_supplier.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_supplier.create_time IS '数据库维护';


--
-- Name: COLUMN obj_estate_supplier.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_supplier.create_by IS '用户ID';


--
-- Name: COLUMN obj_estate_supplier.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_supplier.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_estate_supplier.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_supplier.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_estate_supplier.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_supplier.remove_time IS '删除时间';


--
-- Name: obj_estate_supplier_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_supplier_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_supplier_id_seq OWNER TO mempb;

--
-- Name: obj_estate_supplier_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_supplier_id_seq OWNED BY obj_estate_supplier.id;


--
-- Name: obj_estate_type; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_estate_type (
    id integer NOT NULL,
    name character varying(50),
    name_en character varying(50),
    category integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    parts_type integer,
    workpoints double precision
);


ALTER TABLE obj_estate_type OWNER TO mempb;

--
-- Name: TABLE obj_estate_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_estate_type IS '设备、模块类型表';


--
-- Name: COLUMN obj_estate_type.category; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.category IS '类别 1：设备类型 2：模块类型';


--
-- Name: COLUMN obj_estate_type.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.create_time IS '数据库维护';


--
-- Name: COLUMN obj_estate_type.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.create_by IS '用户ID';


--
-- Name: COLUMN obj_estate_type.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_estate_type.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_estate_type.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.remove_time IS '删除时间';


--
-- Name: COLUMN obj_estate_type.parts_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.parts_type IS '配件类型 1:站点 2：车辆';


--
-- Name: COLUMN obj_estate_type.workpoints; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_estate_type.workpoints IS '工分';


--
-- Name: obj_estate_type_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_estate_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_estate_type_id_seq OWNER TO mempb;

--
-- Name: obj_estate_type_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_estate_type_id_seq OWNED BY obj_estate_type.id;


--
-- Name: obj_file; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_file (
    id integer NOT NULL,
    file_id character varying(100) NOT NULL,
    file_status integer DEFAULT 0,
    file_name character varying(50) NOT NULL,
    file_size integer,
    thumbnail character varying(100),
    file_type character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    file_url character varying(255)
);


ALTER TABLE obj_file OWNER TO mempb;

--
-- Name: COLUMN obj_file.file_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_id IS '文件MD5';


--
-- Name: COLUMN obj_file.file_status; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_status IS '0:可用1:禁用';


--
-- Name: COLUMN obj_file.file_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_name IS '文件原始名称';


--
-- Name: COLUMN obj_file.file_size; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_size IS '文件大小';


--
-- Name: COLUMN obj_file.thumbnail; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.thumbnail IS '文件缩略图';


--
-- Name: COLUMN obj_file.file_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_type IS '文件后缀';


--
-- Name: COLUMN obj_file.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.create_time IS '数据库维护';


--
-- Name: COLUMN obj_file.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.create_by IS '用户ID';


--
-- Name: COLUMN obj_file.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_file.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_file.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.remove_time IS '删除时间';


--
-- Name: COLUMN obj_file.file_url; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_file.file_url IS '文件url';


--
-- Name: obj_file_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_file_id_seq OWNER TO mempb;

--
-- Name: obj_file_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_file_id_seq OWNED BY obj_file.id;


--
-- Name: obj_inventory_check_record; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_inventory_check_record (
    id integer NOT NULL,
    corp_id integer,
    count integer,
    check_time timestamp without time zone,
    check_user_id integer,
    check_remark character varying(250),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    station_id integer,
    param_version integer
);


ALTER TABLE obj_inventory_check_record OWNER TO mempb;

--
-- Name: COLUMN obj_inventory_check_record.corp_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.corp_id IS '公司ID';


--
-- Name: COLUMN obj_inventory_check_record.count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.count IS '盘点的自行车数量';


--
-- Name: COLUMN obj_inventory_check_record.check_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.check_time IS '盘点时间';


--
-- Name: COLUMN obj_inventory_check_record.check_user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.check_user_id IS '盘点人';


--
-- Name: COLUMN obj_inventory_check_record.check_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.check_remark IS '盘点备注';


--
-- Name: COLUMN obj_inventory_check_record.station_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.station_id IS '站点ID--预留字段';


--
-- Name: COLUMN obj_inventory_check_record.param_version; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_check_record.param_version IS '盘点参数版本';


--
-- Name: obj_inventory_check_record_detail; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_inventory_check_record_detail (
    id integer NOT NULL,
    inventory_check_record_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    estate_sn character varying(50)
);


ALTER TABLE obj_inventory_check_record_detail OWNER TO mempb;

--
-- Name: TABLE obj_inventory_check_record_detail; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_inventory_check_record_detail IS '库存盘点详细表';


--
-- Name: obj_inventory_check_record_detail_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_inventory_check_record_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_inventory_check_record_detail_id_seq OWNER TO mempb;

--
-- Name: obj_inventory_check_record_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_inventory_check_record_detail_id_seq OWNED BY obj_inventory_check_record_detail.id;


--
-- Name: obj_inventory_check_record_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_inventory_check_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_inventory_check_record_id_seq OWNER TO mempb;

--
-- Name: obj_inventory_check_record_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_inventory_check_record_id_seq OWNED BY obj_inventory_check_record.id;


--
-- Name: obj_inventory_record; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_inventory_record (
    id integer NOT NULL,
    corp_id integer,
    estate_type_id integer,
    count integer,
    operation_type integer,
    operator integer,
    stock_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_inventory_record OWNER TO mempb;

--
-- Name: TABLE obj_inventory_record; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_inventory_record IS '库存操作记录表(暂不使用)';


--
-- Name: COLUMN obj_inventory_record.id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.id IS 'ID';


--
-- Name: COLUMN obj_inventory_record.corp_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.corp_id IS '公司ID';


--
-- Name: COLUMN obj_inventory_record.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.estate_type_id IS '设备类型ID';


--
-- Name: COLUMN obj_inventory_record.count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.count IS '数量';


--
-- Name: COLUMN obj_inventory_record.operation_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.operation_type IS '操作类型 1:入库 2:出库';


--
-- Name: COLUMN obj_inventory_record.operator; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.operator IS '操作员';


--
-- Name: COLUMN obj_inventory_record.stock_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.stock_id IS '仓库id';


--
-- Name: COLUMN obj_inventory_record.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.create_time IS '数据库维护';


--
-- Name: COLUMN obj_inventory_record.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.create_by IS '用户ID';


--
-- Name: COLUMN obj_inventory_record.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_inventory_record.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_inventory_record.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record.remove_time IS '删除时间';


--
-- Name: obj_inventory_record_detail; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_inventory_record_detail (
    id integer NOT NULL,
    inventory_record_id integer,
    estate_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_inventory_record_detail OWNER TO mempb;

--
-- Name: TABLE obj_inventory_record_detail; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_inventory_record_detail IS '库存操作记录详情表(暂时不使用)';


--
-- Name: COLUMN obj_inventory_record_detail.id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.id IS 'ID';


--
-- Name: COLUMN obj_inventory_record_detail.inventory_record_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.inventory_record_id IS '操作记录ID';


--
-- Name: COLUMN obj_inventory_record_detail.estate_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.estate_id IS '设备ID';


--
-- Name: COLUMN obj_inventory_record_detail.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.create_time IS '数据库维护';


--
-- Name: COLUMN obj_inventory_record_detail.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.create_by IS '用户ID';


--
-- Name: COLUMN obj_inventory_record_detail.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_inventory_record_detail.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_inventory_record_detail.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_inventory_record_detail.remove_time IS '删除时间';


--
-- Name: obj_inventory_record_detail_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_inventory_record_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_inventory_record_detail_id_seq OWNER TO mempb;

--
-- Name: obj_inventory_record_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_inventory_record_detail_id_seq OWNED BY obj_inventory_record_detail.id;


--
-- Name: obj_inventory_record_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_inventory_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_inventory_record_id_seq OWNER TO mempb;

--
-- Name: obj_inventory_record_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_inventory_record_id_seq OWNED BY obj_inventory_record.id;


--
-- Name: seq_bar_code; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE seq_bar_code
    START WITH 10000000
    INCREMENT BY 1
    MINVALUE 10000000
    MAXVALUE 10000000000
    CACHE 1;


ALTER TABLE seq_bar_code OWNER TO mempb;

--
-- Name: seq_bar_code_message; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE seq_bar_code_message
    START WITH 10000000
    INCREMENT BY 1
    MINVALUE 10000000
    MAXVALUE 10000000000
    CACHE 1;


ALTER TABLE seq_bar_code_message OWNER TO mempb;

--
-- Name: obj_station; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_station (
    id integer NOT NULL,
    project_id integer,
    station_no character varying(50),
    station_sn character varying(50) DEFAULT nextval('seq_bar_code'::regclass),
    station_name character varying(50) NOT NULL,
    station_en character varying(50),
    station_name_short character varying(50),
    longitude double precision,
    latitude double precision,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    principal character varying(10),
    remark character varying(200),
    estate_count integer,
    station_message character varying(255) DEFAULT nextval('seq_bar_code_message'::regclass)
);


ALTER TABLE obj_station OWNER TO mempb;

--
-- Name: TABLE obj_station; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_station IS '站点表';


--
-- Name: COLUMN obj_station.station_sn; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.station_sn IS '暂时不使用该字段，站点不使用二维码';


--
-- Name: COLUMN obj_station.station_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.station_name IS '站点名称';


--
-- Name: COLUMN obj_station.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.create_time IS '数据库维护';


--
-- Name: COLUMN obj_station.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.create_by IS '用户ID';


--
-- Name: COLUMN obj_station.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_station.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_station.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.remove_time IS '删除时间';


--
-- Name: COLUMN obj_station.principal; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.principal IS '站点负责人';


--
-- Name: COLUMN obj_station.remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_station.remark IS '备注';


--
-- Name: obj_station_bak; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_station_bak (
    id integer NOT NULL,
    project_id integer,
    station_no character varying(50),
    station_sn character varying(50),
    station_name character varying(50) NOT NULL,
    station_en character varying(50),
    station_name_short character varying(50),
    longitude double precision,
    latitude double precision,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    principal character varying(10),
    remark character varying(200)
);


ALTER TABLE obj_station_bak OWNER TO mempb;

--
-- Name: obj_station_bak_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_station_bak_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_station_bak_id_seq OWNER TO mempb;

--
-- Name: obj_station_bak_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_station_bak_id_seq OWNED BY obj_station_bak.id;


--
-- Name: obj_station_excel_id_seq1; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_station_excel_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_station_excel_id_seq1 OWNER TO mempb;

--
-- Name: obj_station_excel; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_station_excel (
    id integer DEFAULT nextval('obj_station_excel_id_seq1'::regclass) NOT NULL,
    project_id integer,
    station_no character varying(50),
    station_sn character varying(50),
    station_name character varying(50) NOT NULL,
    station_en character varying(50),
    station_name_short character varying(50),
    longitude double precision,
    latitude double precision,
    estate_count integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    principal character varying(10),
    remark character varying(200)
);


ALTER TABLE obj_station_excel OWNER TO mempb;

--
-- Name: obj_station_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_station_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_station_id_seq OWNER TO mempb;

--
-- Name: obj_station_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_station_id_seq OWNED BY obj_station.id;


--
-- Name: obj_stock; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock (
    id integer NOT NULL,
    stock_name character varying(20),
    corp_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_stock OWNER TO mempb;

--
-- Name: TABLE obj_stock; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock IS '仓库表  --在系统中无实际意义';


--
-- Name: COLUMN obj_stock.id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.id IS 'ID';


--
-- Name: COLUMN obj_stock.stock_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.stock_name IS '仓库名称';


--
-- Name: COLUMN obj_stock.corp_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.corp_id IS '公司ID';


--
-- Name: COLUMN obj_stock.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.create_time IS '数据库维护';


--
-- Name: COLUMN obj_stock.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.create_by IS '用户ID';


--
-- Name: COLUMN obj_stock.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_stock.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_stock.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock.remove_time IS '删除时间';


--
-- Name: obj_stock_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_id_seq OWNER TO mempb;

--
-- Name: obj_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_id_seq OWNED BY obj_stock.id;


--
-- Name: obj_stock_record; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_record (
    id integer NOT NULL,
    estate_type_id integer,
    count integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    corp_id integer
);


ALTER TABLE obj_stock_record OWNER TO mempb;

--
-- Name: TABLE obj_stock_record; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_record IS '库存记录-大仓库';


--
-- Name: COLUMN obj_stock_record.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record.estate_type_id IS '设备类型';


--
-- Name: COLUMN obj_stock_record.count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record.count IS '数量';


--
-- Name: COLUMN obj_stock_record.corp_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record.corp_id IS '公司id';


--
-- Name: obj_stock_record_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_record_id_seq OWNER TO mempb;

--
-- Name: obj_stock_record_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_record_id_seq OWNED BY obj_stock_record.id;


--
-- Name: obj_stock_record_personal; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_record_personal (
    id integer NOT NULL,
    estate_type_id integer,
    count integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    user_id integer
);


ALTER TABLE obj_stock_record_personal OWNER TO mempb;

--
-- Name: TABLE obj_stock_record_personal; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_record_personal IS '个人仓库表';


--
-- Name: COLUMN obj_stock_record_personal.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record_personal.estate_type_id IS '设备类型';


--
-- Name: COLUMN obj_stock_record_personal.count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record_personal.count IS '数量';


--
-- Name: COLUMN obj_stock_record_personal.user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record_personal.user_id IS '用户id';


--
-- Name: obj_stock_record_personal_history; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_record_personal_history (
    id integer NOT NULL,
    estate_type_id integer,
    count integer,
    operation_type integer,
    operation_time timestamp without time zone,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    user_id integer
);


ALTER TABLE obj_stock_record_personal_history OWNER TO mempb;

--
-- Name: TABLE obj_stock_record_personal_history; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_record_personal_history IS '个人仓库历史记录';


--
-- Name: COLUMN obj_stock_record_personal_history.operation_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_record_personal_history.operation_type IS '操作类型 ';


--
-- Name: obj_stock_record_personal_history_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_record_personal_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_record_personal_history_id_seq OWNER TO mempb;

--
-- Name: obj_stock_record_personal_history_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_record_personal_history_id_seq OWNED BY obj_stock_record_personal_history.id;


--
-- Name: obj_stock_record_personal_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_record_personal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_record_personal_id_seq OWNER TO mempb;

--
-- Name: obj_stock_record_personal_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_record_personal_id_seq OWNED BY obj_stock_record_personal.id;


--
-- Name: obj_stock_work_order; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_work_order (
    id integer NOT NULL,
    serial_no character varying(100),
    stock_work_order_type_id integer,
    apply_user_id integer,
    apply_time timestamp without time zone,
    stock_work_order_status_id integer,
    apply_remark character varying(255),
    reject_remark character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    confirm_time timestamp without time zone,
    reject_time timestamp without time zone,
    process_user_id integer,
    stock_process_instance_id character varying(20)
);


ALTER TABLE obj_stock_work_order OWNER TO mempb;

--
-- Name: TABLE obj_stock_work_order; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_work_order IS '库存工单表';


--
-- Name: COLUMN obj_stock_work_order.serial_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.serial_no IS '库存工单编号';


--
-- Name: COLUMN obj_stock_work_order.stock_work_order_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.stock_work_order_type_id IS '库存工单类型id';


--
-- Name: COLUMN obj_stock_work_order.apply_user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.apply_user_id IS '申请人id';


--
-- Name: COLUMN obj_stock_work_order.apply_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.apply_time IS '申请时间';


--
-- Name: COLUMN obj_stock_work_order.stock_work_order_status_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.stock_work_order_status_id IS '库存工单状态';


--
-- Name: COLUMN obj_stock_work_order.apply_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.apply_remark IS '申请备注';


--
-- Name: COLUMN obj_stock_work_order.reject_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.reject_remark IS '拒绝备注
';


--
-- Name: COLUMN obj_stock_work_order.confirm_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.confirm_time IS '确认时间';


--
-- Name: COLUMN obj_stock_work_order.reject_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.reject_time IS '拒绝时间';


--
-- Name: COLUMN obj_stock_work_order.process_user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.process_user_id IS '库存工单处理人';


--
-- Name: COLUMN obj_stock_work_order.stock_process_instance_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order.stock_process_instance_id IS '--库存工单流id';


--
-- Name: obj_stock_work_order_detail; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_work_order_detail (
    id integer NOT NULL,
    stock_work_order_id integer,
    estate_type_id integer,
    count integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_stock_work_order_detail OWNER TO mempb;

--
-- Name: TABLE obj_stock_work_order_detail; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_work_order_detail IS '库存工单详细表';


--
-- Name: COLUMN obj_stock_work_order_detail.stock_work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_detail.stock_work_order_id IS '库存工单id';


--
-- Name: COLUMN obj_stock_work_order_detail.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_detail.estate_type_id IS '设备/模块类型';


--
-- Name: COLUMN obj_stock_work_order_detail.count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_detail.count IS '数量';


--
-- Name: obj_stock_work_order_detail_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_work_order_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_work_order_detail_id_seq OWNER TO mempb;

--
-- Name: obj_stock_work_order_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_work_order_detail_id_seq OWNED BY obj_stock_work_order_detail.id;


--
-- Name: obj_stock_work_order_history; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_work_order_history (
    id integer NOT NULL,
    stock_work_order_id integer,
    stock_work_order_status_id integer,
    process_user_id integer,
    operation_time timestamp without time zone,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    last_operation_time time without time zone
);


ALTER TABLE obj_stock_work_order_history OWNER TO mempb;

--
-- Name: TABLE obj_stock_work_order_history; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_stock_work_order_history IS '库存工单表操作记录表';


--
-- Name: COLUMN obj_stock_work_order_history.stock_work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_history.stock_work_order_id IS '库存工单id';


--
-- Name: COLUMN obj_stock_work_order_history.stock_work_order_status_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_history.stock_work_order_status_id IS '库存工单状态';


--
-- Name: COLUMN obj_stock_work_order_history.process_user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_history.process_user_id IS '库存工单处理人';


--
-- Name: COLUMN obj_stock_work_order_history.operation_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_history.operation_time IS '操作时间';


--
-- Name: COLUMN obj_stock_work_order_history.last_operation_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_stock_work_order_history.last_operation_time IS '上次操作时间';


--
-- Name: obj_stock_work_order_history_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_work_order_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_work_order_history_id_seq OWNER TO mempb;

--
-- Name: obj_stock_work_order_history_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_work_order_history_id_seq OWNED BY obj_stock_work_order_history.id;


--
-- Name: obj_stock_work_order_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_work_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_work_order_id_seq OWNER TO mempb;

--
-- Name: obj_stock_work_order_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_work_order_id_seq OWNED BY obj_stock_work_order.id;


--
-- Name: obj_stock_work_order_resource; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_stock_work_order_resource (
    id integer NOT NULL,
    stock_work_order_id integer,
    file_id integer,
    remark character varying(255),
    category integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_stock_work_order_resource OWNER TO mempb;

--
-- Name: obj_stock_work_order_resource_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_stock_work_order_resource_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_stock_work_order_resource_id_seq OWNER TO mempb;

--
-- Name: obj_stock_work_order_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_stock_work_order_resource_id_seq OWNED BY obj_stock_work_order_resource.id;


--
-- Name: obj_work_order; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_work_order (
    id integer NOT NULL,
    estate_id integer,
    type_id integer,
    report_employee integer,
    report_time timestamp without time zone DEFAULT now(),
    assign_time timestamp without time zone,
    repair_employee integer,
    repair_confirm_time timestamp without time zone,
    repair_start_time timestamp without time zone,
    repair_end_time timestamp without time zone,
    status_id integer,
    fault_description character varying(255),
    repair_remark character varying(255),
    assign_remark character varying(255),
    work_order_source integer,
    maintain_remark character varying(255),
    station_id integer,
    longitude double precision,
    latitude double precision,
    level integer,
    reponse_over_time integer,
    repair_over_time integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    assign_employee integer,
    report_way integer,
    serial_no character varying(100),
    process_instance_id character varying(20),
    back_remark character varying(255),
    fixed boolean,
    response_time_out_date timestamp without time zone,
    repair_time_out_date timestamp without time zone,
    location character varying,
    work_order_score double precision DEFAULT 0.0,
    work_order_score_deduct double precision DEFAULT 0.0,
    score_deduct_remark character varying
);


ALTER TABLE obj_work_order OWNER TO mempb;

--
-- Name: TABLE obj_work_order; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_work_order IS '工单';


--
-- Name: COLUMN obj_work_order.estate_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.estate_id IS '设备编号 引用Estate的id';


--
-- Name: COLUMN obj_work_order.type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.type_id IS '工单类型 引用ref_work_ordertype的id';


--
-- Name: COLUMN obj_work_order.report_employee; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.report_employee IS '报修时选择的用户ID';


--
-- Name: COLUMN obj_work_order.report_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.report_time IS '报修时间';


--
-- Name: COLUMN obj_work_order.repair_employee; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_employee IS '维修人';


--
-- Name: COLUMN obj_work_order.repair_confirm_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_confirm_time IS '维修确认时间';


--
-- Name: COLUMN obj_work_order.repair_start_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_start_time IS '开始维修时间 即到达现场时间';


--
-- Name: COLUMN obj_work_order.repair_end_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_end_time IS '维修完成时间';


--
-- Name: COLUMN obj_work_order.fault_description; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.fault_description IS '故障描述';


--
-- Name: COLUMN obj_work_order.repair_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_remark IS '维修备注';


--
-- Name: COLUMN obj_work_order.assign_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.assign_remark IS '派单备注';


--
-- Name: COLUMN obj_work_order.work_order_source; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.work_order_source IS '工单来源，预留字段';


--
-- Name: COLUMN obj_work_order.maintain_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.maintain_remark IS '保养备注';


--
-- Name: COLUMN obj_work_order.station_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.station_id IS '站点ID';


--
-- Name: COLUMN obj_work_order.longitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.longitude IS '经度';


--
-- Name: COLUMN obj_work_order.latitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.latitude IS '纬度';


--
-- Name: COLUMN obj_work_order.level; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.level IS '工单级别';


--
-- Name: COLUMN obj_work_order.reponse_over_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.reponse_over_time IS '响应超时时间';


--
-- Name: COLUMN obj_work_order.repair_over_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_over_time IS '维修超时时间';


--
-- Name: COLUMN obj_work_order.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.create_time IS '数据库维护';


--
-- Name: COLUMN obj_work_order.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.create_by IS '用户ID';


--
-- Name: COLUMN obj_work_order.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_work_order.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_work_order.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.remove_time IS '删除时间';


--
-- Name: COLUMN obj_work_order.report_way; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.report_way IS '报修方式 1：站点报修 2：车辆报修';


--
-- Name: COLUMN obj_work_order.serial_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.serial_no IS '工单编号';


--
-- Name: COLUMN obj_work_order.process_instance_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.process_instance_id IS '流程id';


--
-- Name: COLUMN obj_work_order.back_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.back_remark IS '退回备注';


--
-- Name: COLUMN obj_work_order.fixed; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.fixed IS '维修是否完成';


--
-- Name: COLUMN obj_work_order.response_time_out_date; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.response_time_out_date IS '工单响应超时时间';


--
-- Name: COLUMN obj_work_order.repair_time_out_date; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.repair_time_out_date IS '工单维修超时时间';


--
-- Name: COLUMN obj_work_order.location; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.location IS '地点位置信息';


--
-- Name: COLUMN obj_work_order.work_order_score; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.work_order_score IS '工单积分';


--
-- Name: COLUMN obj_work_order.work_order_score_deduct; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.work_order_score_deduct IS '扣除积分';


--
-- Name: COLUMN obj_work_order.score_deduct_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order.score_deduct_remark IS '扣除备注';


--
-- Name: obj_work_order_bad_component; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_work_order_bad_component (
    id integer NOT NULL,
    work_order_id integer,
    estate_type_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    replace_count integer
);


ALTER TABLE obj_work_order_bad_component OWNER TO mempb;

--
-- Name: TABLE obj_work_order_bad_component; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_work_order_bad_component IS '工单坏件表';


--
-- Name: COLUMN obj_work_order_bad_component.work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.work_order_id IS '工单ID';


--
-- Name: COLUMN obj_work_order_bad_component.estate_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.estate_type_id IS '坏件类型ID';


--
-- Name: COLUMN obj_work_order_bad_component.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.create_time IS '数据库维护';


--
-- Name: COLUMN obj_work_order_bad_component.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.create_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_bad_component.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_work_order_bad_component.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_bad_component.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.remove_time IS '删除时间';


--
-- Name: COLUMN obj_work_order_bad_component.replace_count; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_bad_component.replace_count IS '更换的数量';


--
-- Name: obj_work_order_bad_component_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_work_order_bad_component_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_work_order_bad_component_id_seq OWNER TO mempb;

--
-- Name: obj_work_order_bad_component_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_work_order_bad_component_id_seq OWNED BY obj_work_order_bad_component.id;


--
-- Name: obj_work_order_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_work_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_work_order_id_seq OWNER TO mempb;

--
-- Name: obj_work_order_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_work_order_id_seq OWNED BY obj_work_order.id;


--
-- Name: obj_work_order_operation; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_work_order_operation (
    id integer NOT NULL,
    work_order_id integer NOT NULL,
    operation_type_id integer,
    operator_id integer,
    operation_remark text,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    last_operation_time timestamp without time zone,
    longitude double precision,
    latitude double precision,
    location character varying,
    work_order_score double precision DEFAULT 0.0,
    work_order_score_deduct double precision DEFAULT 0.0
);


ALTER TABLE obj_work_order_operation OWNER TO mempb;

--
-- Name: TABLE obj_work_order_operation; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_work_order_operation IS '工单操作历史记录表';


--
-- Name: COLUMN obj_work_order_operation.work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.work_order_id IS '关联work_order的id';


--
-- Name: COLUMN obj_work_order_operation.operation_type_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.operation_type_id IS '和bussiness.obj_work_order_status一致';


--
-- Name: COLUMN obj_work_order_operation.operator_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.operator_id IS '操作人';


--
-- Name: COLUMN obj_work_order_operation.operation_remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.operation_remark IS '操作备注';


--
-- Name: COLUMN obj_work_order_operation.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.create_time IS '数据库维护';


--
-- Name: COLUMN obj_work_order_operation.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.create_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_operation.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_work_order_operation.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_operation.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.remove_time IS '删除时间';


--
-- Name: COLUMN obj_work_order_operation.last_operation_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.last_operation_time IS '上次操作时间';


--
-- Name: COLUMN obj_work_order_operation.longitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.longitude IS '经度';


--
-- Name: COLUMN obj_work_order_operation.latitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.latitude IS '纬度';


--
-- Name: COLUMN obj_work_order_operation.location; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_operation.location IS '位置信息';


--
-- Name: obj_work_order_resource; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE obj_work_order_resource (
    id integer NOT NULL,
    work_order_id integer,
    file_id integer,
    remark character varying(255),
    category integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE obj_work_order_resource OWNER TO mempb;

--
-- Name: TABLE obj_work_order_resource; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE obj_work_order_resource IS '工单资源表';


--
-- Name: COLUMN obj_work_order_resource.work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.work_order_id IS '工单ID';


--
-- Name: COLUMN obj_work_order_resource.file_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.file_id IS '文件ID';


--
-- Name: COLUMN obj_work_order_resource.remark; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.remark IS '备注';


--
-- Name: COLUMN obj_work_order_resource.category; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.category IS '类别 1：报修 2：调度 3：维修 4：保养';


--
-- Name: COLUMN obj_work_order_resource.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.create_time IS '数据库维护';


--
-- Name: COLUMN obj_work_order_resource.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.create_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_resource.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN obj_work_order_resource.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.last_update_by IS '用户ID';


--
-- Name: COLUMN obj_work_order_resource.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN obj_work_order_resource.remove_time IS '删除时间';


--
-- Name: obj_work_order_resource_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_work_order_resource_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_work_order_resource_id_seq OWNER TO mempb;

--
-- Name: obj_work_order_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_work_order_resource_id_seq OWNED BY obj_work_order_resource.id;


--
-- Name: obj_workorder_operation_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE obj_workorder_operation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE obj_workorder_operation_id_seq OWNER TO mempb;

--
-- Name: obj_workorder_operation_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE obj_workorder_operation_id_seq OWNED BY obj_work_order_operation.id;


--
-- Name: ref_stock_work_order_type; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE ref_stock_work_order_type (
    id integer NOT NULL,
    name_cn character varying(50) NOT NULL,
    name_en character varying(50) NOT NULL,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE ref_stock_work_order_type OWNER TO mempb;

--
-- Name: ref_stock_work_order_type_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE ref_stock_work_order_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_stock_work_order_type_id_seq OWNER TO mempb;

--
-- Name: ref_stock_work_order_type_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE ref_stock_work_order_type_id_seq OWNED BY ref_stock_work_order_type.id;


--
-- Name: ref_tock_work_order_status; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE ref_tock_work_order_status (
    id integer NOT NULL,
    name_cn character varying(50) NOT NULL,
    name_en character varying(50),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    operation_result integer
);


ALTER TABLE ref_tock_work_order_status OWNER TO mempb;

--
-- Name: COLUMN ref_tock_work_order_status.operation_result; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_tock_work_order_status.operation_result IS '0:待处理，1：已处理';


--
-- Name: ref_tock_work_order_status_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE ref_tock_work_order_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_tock_work_order_status_id_seq OWNER TO mempb;

--
-- Name: ref_tock_work_order_status_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE ref_tock_work_order_status_id_seq OWNED BY ref_tock_work_order_status.id;


--
-- Name: ref_work_order_status; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE ref_work_order_status (
    id integer NOT NULL,
    name_cn character varying(50) NOT NULL,
    name_en character varying(50),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE ref_work_order_status OWNER TO mempb;

--
-- Name: TABLE ref_work_order_status; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE ref_work_order_status IS '工单状态';


--
-- Name: COLUMN ref_work_order_status.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_status.create_time IS '数据库维护';


--
-- Name: COLUMN ref_work_order_status.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_status.create_by IS '用户ID';


--
-- Name: COLUMN ref_work_order_status.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_status.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN ref_work_order_status.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_status.last_update_by IS '用户ID';


--
-- Name: COLUMN ref_work_order_status.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_status.remove_time IS '删除时间';


--
-- Name: ref_work_order_status_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE ref_work_order_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_work_order_status_id_seq OWNER TO mempb;

--
-- Name: ref_work_order_status_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE ref_work_order_status_id_seq OWNED BY ref_work_order_status.id;


--
-- Name: ref_work_order_type; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE ref_work_order_type (
    id integer NOT NULL,
    name_cn character varying(50) NOT NULL,
    name_en character varying(50) NOT NULL,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE ref_work_order_type OWNER TO mempb;

--
-- Name: COLUMN ref_work_order_type.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_type.create_time IS '数据库维护';


--
-- Name: COLUMN ref_work_order_type.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_type.create_by IS '用户ID';


--
-- Name: COLUMN ref_work_order_type.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_type.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN ref_work_order_type.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_type.last_update_by IS '用户ID';


--
-- Name: COLUMN ref_work_order_type.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN ref_work_order_type.remove_time IS '删除时间';


--
-- Name: ref_work_order_type_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE ref_work_order_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_work_order_type_id_seq OWNER TO mempb;

--
-- Name: ref_work_order_type_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE ref_work_order_type_id_seq OWNED BY ref_work_order_type.id;


--
-- Name: sys_message; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_message (
    id integer NOT NULL,
    message_title character varying(250),
    message_author character varying(20),
    message_text text,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    status integer,
    message_file1_url character varying(200),
    message_file2_url character varying(200),
    message_file3_url character varying(200)
);


ALTER TABLE sys_message OWNER TO mempb;

--
-- Name: TABLE sys_message; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_message IS '系统消息表';


--
-- Name: COLUMN sys_message.message_title; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_title IS '消息标题';


--
-- Name: COLUMN sys_message.message_author; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_author IS '消息作者';


--
-- Name: COLUMN sys_message.message_text; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_text IS '消息内容';


--
-- Name: COLUMN sys_message.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.create_time IS '数据库维护';


--
-- Name: COLUMN sys_message.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.create_by IS '用户ID';


--
-- Name: COLUMN sys_message.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN sys_message.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.last_update_by IS '用户ID';


--
-- Name: COLUMN sys_message.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.remove_time IS '删除时间';


--
-- Name: COLUMN sys_message.status; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.status IS '0:已发送，1：保存草稿箱';


--
-- Name: COLUMN sys_message.message_file1_url; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_file1_url IS '消息文件1url';


--
-- Name: COLUMN sys_message.message_file2_url; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_file2_url IS '消息文件2url';


--
-- Name: COLUMN sys_message.message_file3_url; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_message.message_file3_url IS '消息文件3url';


--
-- Name: sys_message_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_message_id_seq OWNER TO mempb;

--
-- Name: sys_message_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_message_id_seq OWNED BY sys_message.id;


--
-- Name: sys_message_send_detail; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_message_send_detail (
    id integer NOT NULL,
    sys_message_id integer,
    send_user_id integer,
    receive_user_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_message_send_detail OWNER TO mempb;

--
-- Name: sys_message_send_detail_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_message_send_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_message_send_detail_id_seq OWNER TO mempb;

--
-- Name: sys_message_send_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_message_send_detail_id_seq OWNED BY sys_message_send_detail.id;


--
-- Name: sys_param; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_param (
    id integer NOT NULL,
    work_order_level_one_response_time integer,
    work_order_level_one_repair_time integer,
    work_order_level_two_response_time integer,
    work_order_level_two_repair_time integer,
    work_order_level_three_response_time integer,
    work_order_level_three_repair_time integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    stock_check_start_time timestamp without time zone,
    stock_check_version integer,
    work_order_level_emergency_response_time integer,
    work_order_level_emergency_repair_time integer,
    work_start_time time without time zone,
    work_end_time time without time zone,
    work_period character(7),
    stock_check_end_time timestamp without time zone
);


ALTER TABLE sys_param OWNER TO mempb;

--
-- Name: TABLE sys_param; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_param IS '系统参数表';


--
-- Name: COLUMN sys_param.work_order_level_one_response_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_one_response_time IS '一级工单响应时间，单位：分';


--
-- Name: COLUMN sys_param.work_order_level_one_repair_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_one_repair_time IS '一级工单维修时间';


--
-- Name: COLUMN sys_param.work_order_level_two_response_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_two_response_time IS '二级工单响应时间';


--
-- Name: COLUMN sys_param.work_order_level_two_repair_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_two_repair_time IS '二级工单维修时间';


--
-- Name: COLUMN sys_param.work_order_level_three_response_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_three_response_time IS '三级工单响应时间';


--
-- Name: COLUMN sys_param.work_order_level_three_repair_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_three_repair_time IS '三级工单维修时间';


--
-- Name: COLUMN sys_param.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.create_time IS '数据库维护';


--
-- Name: COLUMN sys_param.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.create_by IS '用户ID';


--
-- Name: COLUMN sys_param.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN sys_param.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.last_update_by IS '用户ID';


--
-- Name: COLUMN sys_param.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.remove_time IS '删除时间';


--
-- Name: COLUMN sys_param.stock_check_start_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.stock_check_start_time IS '盘点开始时间';


--
-- Name: COLUMN sys_param.stock_check_version; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.stock_check_version IS '库存盘点参数版本';


--
-- Name: COLUMN sys_param.work_order_level_emergency_response_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_emergency_response_time IS '应急工单响应时间';


--
-- Name: COLUMN sys_param.work_order_level_emergency_repair_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_order_level_emergency_repair_time IS '应急工单维修时间';


--
-- Name: COLUMN sys_param.work_start_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_start_time IS '工作开始time';


--
-- Name: COLUMN sys_param.work_end_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_end_time IS '工作结束time';


--
-- Name: COLUMN sys_param.work_period; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_param.work_period IS '工作周期';


--
-- Name: sys_param_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_param_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_param_id_seq OWNER TO mempb;

--
-- Name: sys_param_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_param_id_seq OWNED BY sys_param.id;


--
-- Name: sys_project; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_project (
    id integer NOT NULL,
    project_name character varying(10) NOT NULL,
    crop_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_project OWNER TO mempb;

--
-- Name: TABLE sys_project; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_project IS '项目表';


--
-- Name: COLUMN sys_project.project_name; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.project_name IS '项目名';


--
-- Name: COLUMN sys_project.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.create_time IS '数据库维护';


--
-- Name: COLUMN sys_project.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.create_by IS '用户ID';


--
-- Name: COLUMN sys_project.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN sys_project.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.last_update_by IS '用户ID';


--
-- Name: COLUMN sys_project.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_project.remove_time IS '删除时间';


--
-- Name: sys_project_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_project_id_seq OWNER TO mempb;

--
-- Name: sys_project_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_project_id_seq OWNED BY sys_project.id;


--
-- Name: sys_resource; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_resource (
    id integer NOT NULL,
    resource_no character varying(50),
    resource_code character varying(50) NOT NULL,
    resource_name character varying(50) NOT NULL,
    resource_name_en character varying(50),
    resource_type integer DEFAULT 0,
    resource_status integer,
    parent_id integer,
    resource_grade integer DEFAULT 0,
    resource_sort integer,
    resource_url character varying(200),
    resource_event character varying(30),
    resource_event_func character varying(50),
    resource_image character varying(200),
    resource_class character varying(30),
    resource_style character varying(500),
    resource_remark character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_resource OWNER TO mempb;

--
-- Name: COLUMN sys_resource.resource_type; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_resource.resource_type IS '资源类型 0：根节点 1：app,2：web';


--
-- Name: COLUMN sys_resource.parent_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_resource.parent_id IS '父节点id';


--
-- Name: COLUMN sys_resource.resource_grade; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_resource.resource_grade IS '资源等级';


--
-- Name: COLUMN sys_resource.resource_sort; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_resource.resource_sort IS '资源顺序';


--
-- Name: sys_resource_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_resource_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_resource_id_seq OWNER TO mempb;

--
-- Name: sys_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_resource_id_seq OWNED BY sys_resource.id;


--
-- Name: sys_role; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_role (
    id integer NOT NULL,
    role_name character varying(32),
    group_id integer DEFAULT 0,
    role_grade integer DEFAULT 0,
    role_description character varying(50),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_role OWNER TO mempb;

--
-- Name: COLUMN sys_role.role_grade; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_role.role_grade IS '角色等级 0：自定义角色 1：预制角色 ';


--
-- Name: sys_role_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_role_id_seq OWNER TO mempb;

--
-- Name: sys_role_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_role_id_seq OWNED BY sys_role.id;


--
-- Name: sys_role_resource; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_role_resource (
    id integer NOT NULL,
    resource_id integer,
    role_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_role_resource OWNER TO mempb;

--
-- Name: sys_role_resource_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_role_resource_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_role_resource_id_seq OWNER TO mempb;

--
-- Name: sys_role_resource_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_role_resource_id_seq OWNED BY sys_role_resource.id;


--
-- Name: sys_user; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_user (
    id integer NOT NULL,
    user_account character varying(30),
    user_name character varying(40),
    user_password character varying(100),
    user_surname character varying(50),
    user_status integer DEFAULT 0,
    user_type integer DEFAULT 0,
    user_phone character varying(20),
    whitelist integer DEFAULT 0,
    user_group integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone,
    last_update_by integer,
    title_id integer,
    user_email character varying(50),
    user_qq character varying(50),
    user_wechart character varying(50),
    expiry_time timestamp without time zone,
    remove_time timestamp without time zone,
    corp_id integer
);


ALTER TABLE sys_user OWNER TO mempb;

--
-- Name: COLUMN sys_user.expiry_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user.expiry_time IS '有效期';


--
-- Name: COLUMN sys_user.corp_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user.corp_id IS '公司id';


--
-- Name: sys_user_detail; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_user_detail (
    id integer NOT NULL,
    user_id integer NOT NULL,
    status integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    work_point double precision,
    longitude double precision,
    latitude double precision
);


ALTER TABLE sys_user_detail OWNER TO mempb;

--
-- Name: TABLE sys_user_detail; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_user_detail IS '用户详情表';


--
-- Name: COLUMN sys_user_detail.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.create_time IS '数据库维护';


--
-- Name: COLUMN sys_user_detail.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.create_by IS '用户ID';


--
-- Name: COLUMN sys_user_detail.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN sys_user_detail.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.last_update_by IS '用户ID';


--
-- Name: COLUMN sys_user_detail.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.remove_time IS '删除时间';


--
-- Name: COLUMN sys_user_detail.work_point; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.work_point IS '工分';


--
-- Name: COLUMN sys_user_detail.longitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.longitude IS '经度';


--
-- Name: COLUMN sys_user_detail.latitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_detail.latitude IS '纬度';


--
-- Name: sys_user_detail_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_user_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_user_detail_id_seq OWNER TO mempb;

--
-- Name: sys_user_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_user_detail_id_seq OWNED BY sys_user_detail.id;


--
-- Name: sys_user_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_user_id_seq OWNER TO mempb;

--
-- Name: sys_user_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_user_id_seq OWNED BY sys_user.id;


--
-- Name: sys_user_point_record; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_user_point_record (
    id integer NOT NULL,
    user_id integer,
    point_change character varying(10),
    point_source integer,
    work_order_id integer,
    current_date_str character varying(20),
    point_record_level integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone,
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_user_point_record OWNER TO mempb;

--
-- Name: TABLE sys_user_point_record; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_user_point_record IS '用户工分记录表';


--
-- Name: COLUMN sys_user_point_record.user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.user_id IS '用户ID';


--
-- Name: COLUMN sys_user_point_record.point_change; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.point_change IS '工分加减记录（字符串形式）';


--
-- Name: COLUMN sys_user_point_record.point_source; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.point_source IS '工分来源 待定';


--
-- Name: COLUMN sys_user_point_record.work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.work_order_id IS '工单ID';


--
-- Name: COLUMN sys_user_point_record.current_date_str; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.current_date_str IS '工分产生时间字符串，格式为yyyy.mm';


--
-- Name: COLUMN sys_user_point_record.point_record_level; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_point_record.point_record_level IS '工分记录级别 0：工分产生月总分数 1：工分产生月子分数';


--
-- Name: sys_user_point_record_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_user_point_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_user_point_record_id_seq OWNER TO mempb;

--
-- Name: sys_user_point_record_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_user_point_record_id_seq OWNED BY sys_user_point_record.id;


--
-- Name: sys_user_position_record; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_user_position_record (
    id integer NOT NULL,
    user_id integer,
    longitude double precision,
    latitude double precision,
    position_source integer,
    work_order_id integer,
    record_time timestamp without time zone,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone,
    last_update_by integer,
    remove_time timestamp without time zone,
    location character varying
);


ALTER TABLE sys_user_position_record OWNER TO mempb;

--
-- Name: TABLE sys_user_position_record; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_user_position_record IS '用户轨迹表';


--
-- Name: COLUMN sys_user_position_record.user_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.user_id IS '用户ID';


--
-- Name: COLUMN sys_user_position_record.longitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.longitude IS '经度';


--
-- Name: COLUMN sys_user_position_record.latitude; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.latitude IS '纬度';


--
-- Name: COLUMN sys_user_position_record.position_source; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.position_source IS '位置上报来源 1：工单创建 2：工单确认 3：维修到达 4：工单完成 5：工单遗留';


--
-- Name: COLUMN sys_user_position_record.work_order_id; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.work_order_id IS '工单ID';


--
-- Name: COLUMN sys_user_position_record.record_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.record_time IS '上报时间';


--
-- Name: COLUMN sys_user_position_record.location; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_user_position_record.location IS '位置';


--
-- Name: sys_user_position_record_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_user_position_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_user_position_record_id_seq OWNER TO mempb;

--
-- Name: sys_user_position_record_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_user_position_record_id_seq OWNED BY sys_user_position_record.id;


--
-- Name: sys_user_role; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_user_role (
    id integer NOT NULL,
    role_id integer,
    user_id integer,
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone
);


ALTER TABLE sys_user_role OWNER TO mempb;

--
-- Name: sys_user_role_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_user_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_user_role_id_seq OWNER TO mempb;

--
-- Name: sys_user_role_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_user_role_id_seq OWNED BY sys_user_role.id;


--
-- Name: sys_version_app; Type: TABLE; Schema: business; Owner: mempb
--

CREATE TABLE sys_version_app (
    id integer NOT NULL,
    version_no character varying(50),
    download_url character varying(255),
    create_time timestamp without time zone DEFAULT now(),
    create_by integer,
    last_update_time timestamp without time zone DEFAULT now(),
    last_update_by integer,
    remove_time timestamp without time zone,
    version_note character varying(255)
);


ALTER TABLE sys_version_app OWNER TO mempb;

--
-- Name: TABLE sys_version_app; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON TABLE sys_version_app IS '版本更新表';


--
-- Name: COLUMN sys_version_app.version_no; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.version_no IS '版本编号';


--
-- Name: COLUMN sys_version_app.download_url; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.download_url IS '下载地址';


--
-- Name: COLUMN sys_version_app.create_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.create_time IS '数据库维护';


--
-- Name: COLUMN sys_version_app.create_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.create_by IS '用户ID';


--
-- Name: COLUMN sys_version_app.last_update_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.last_update_time IS '数据库自动维护';


--
-- Name: COLUMN sys_version_app.last_update_by; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.last_update_by IS '用户ID';


--
-- Name: COLUMN sys_version_app.remove_time; Type: COMMENT; Schema: business; Owner: mempb
--

COMMENT ON COLUMN sys_version_app.remove_time IS '删除时间';


--
-- Name: sys_version_app_id_seq; Type: SEQUENCE; Schema: business; Owner: mempb
--

CREATE SEQUENCE sys_version_app_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sys_version_app_id_seq OWNER TO mempb;

--
-- Name: sys_version_app_id_seq; Type: SEQUENCE OWNED BY; Schema: business; Owner: mempb
--

ALTER SEQUENCE sys_version_app_id_seq OWNED BY sys_version_app.id;


--
-- Name: vw_auth_resource; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_auth_resource AS
 SELECT DISTINCT res.id,
    res.resource_code,
    res.resource_type AS resource_type_id,
    usr.id AS user_id,
    usr.user_account,
    res.create_time,
    res.create_by,
    res.remove_time
   FROM ((((sys_resource res
     JOIN sys_role_resource srr ON (((srr.remove_time IS NULL) AND (srr.resource_id = res.id))))
     JOIN sys_role r ON (((r.remove_time IS NULL) AND (r.id = srr.role_id))))
     JOIN sys_user_role sur ON (((sur.remove_time IS NULL) AND (sur.role_id = r.id))))
     JOIN sys_user usr ON (((usr.remove_time IS NULL) AND (usr.id = sur.user_id))));


ALTER TABLE vw_auth_resource OWNER TO mempb;

--
-- Name: vw_estate; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_estate AS
 SELECT oe.id,
    oe.estate_no,
    oe.estate_sn,
    oe.estate_name,
    oe.estate_type_id,
    etype.name AS estate_type_name,
    oe.estate_status_id,
    oes.status_name AS estate_status_name,
    oe.logical_id,
    oe.parent_id,
    oe.estate_path,
    oe.station_id,
    os.station_no,
    os.station_sn,
    oe.category,
    oe.project_id,
    concat(os.station_no, os.station_name) AS station_name,
    oe.supplier_id,
    osup.supplier_name,
    oe.bike_frame_no,
    oe.create_time,
    oe.create_by,
    oe.last_update_time,
    oe.last_update_by,
    oe.remove_time,
    oc.corp_name,
    csu.user_name AS creat_by_user_name,
    lsu.user_name AS last_update_by_user_name,
    oe.bicycle_stake_bar_code
   FROM (((((((obj_estate oe
     LEFT JOIN obj_estate_status oes ON (((oes.remove_time IS NULL) AND (oes.id = oe.estate_status_id))))
     LEFT JOIN obj_estate_type etype ON ((etype.id = oe.estate_type_id)))
     LEFT JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = oe.station_id))))
     LEFT JOIN obj_estate_supplier osup ON (((osup.remove_time IS NULL) AND (osup.id = oe.supplier_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = oe.project_id))))
     LEFT JOIN sys_user csu ON ((csu.id = oe.create_by)))
     LEFT JOIN sys_user lsu ON ((lsu.id = oe.last_update_by)));


ALTER TABLE vw_estate OWNER TO mempb;

--
-- Name: vw_estate_module_type; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_estate_module_type AS
 SELECT aemt.id,
    aemt.estate_type_id,
    oet.name AS estate_name,
    aemt.module_type_id,
    omt.name AS module_name,
    omt.parts_type,
    omt.workpoints,
    aemt.create_time,
    aemt.create_by,
    aemt.last_update_time,
    aemt.last_update_by,
    aemt.remove_time
   FROM ((asso_estate_module_type aemt
     JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (aemt.estate_type_id = oet.id))))
     JOIN obj_estate_type omt ON (((omt.remove_time IS NULL) AND (aemt.module_type_id = omt.id))));


ALTER TABLE vw_estate_module_type OWNER TO mempb;

--
-- Name: vw_inventory_check_record; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_inventory_check_record AS
 SELECT oicr.id,
    oicr.corp_id,
    oicr.count,
    oicr.check_time,
    oicr.check_user_id,
    oicr.check_remark,
    oicr.create_time,
    oicr.create_by,
    oicr.last_update_time,
    oicr.last_update_by,
    oicr.remove_time,
    oicr.station_id,
    oicr.param_version,
    os.station_name,
    os.station_no,
    concat(os.station_no, os.station_name) AS station_no_name,
    oc.corp_name,
    rr.user_name AS check_user_name
   FROM (((obj_inventory_check_record oicr
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = oicr.corp_id))))
     LEFT JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = oicr.station_id))))
     LEFT JOIN sys_user rr ON (((rr.remove_time IS NULL) AND (rr.id = oicr.check_user_id))));


ALTER TABLE vw_inventory_check_record OWNER TO mempb;

--
-- Name: vw_inventory_check_record_detail; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_inventory_check_record_detail AS
 SELECT oicrd.id,
    oicrd.inventory_check_record_id,
    oicrd.estate_sn,
    oicr.count,
    oicr.corp_id,
    oicr.check_time,
    oicr.check_user_id,
    oicr.check_remark,
    oicr.param_version,
    oe.estate_name,
    oe.station_id,
    os.station_name,
    os.station_no,
    concat(os.station_no, os.station_name) AS station_no_name,
    oe.category,
    oe.estate_status_id,
    oe.supplier_id,
    oe.estate_batch,
    oe.parent_id,
    oe.logical_id,
    oe.project_id,
    oe.estate_path,
    oe.estate_no,
    oicrd.create_time,
    oicrd.create_by,
    oicrd.last_update_time,
    oicrd.last_update_by,
    oicrd.remove_time
   FROM (((obj_inventory_check_record_detail oicrd
     JOIN obj_inventory_check_record oicr ON (((oicr.remove_time IS NULL) AND (oicr.id = oicrd.inventory_check_record_id))))
     JOIN obj_estate oe ON (((oe.remove_time IS NULL) AND ((oe.bike_frame_no)::text = (oicrd.estate_sn)::text))))
     JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = oicr.station_id))));


ALTER TABLE vw_inventory_check_record_detail OWNER TO mempb;

--
-- Name: vw_inventory_record_detail; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_inventory_record_detail AS
 SELECT oird.id,
    oird.inventory_record_id,
    oird.estate_id,
    oir.corp_id,
    oir.estate_type_id,
    oir.operation_type,
    oir.operator,
    oir.stock_id,
    oe.estate_name,
    oe.station_id,
    oe.category,
    oe.estate_status_id,
    oe.estate_sn,
    oe.supplier_id,
    oe.estate_batch,
    oe.parent_id,
    oe.logical_id,
    oe.project_id,
    oe.estate_path,
    oe.estate_no,
    oird.create_time,
    oird.create_by,
    oird.last_update_time,
    oird.last_update_by,
    oird.remove_time
   FROM ((obj_inventory_record_detail oird
     JOIN obj_inventory_record oir ON (((oir.remove_time IS NULL) AND (oir.id = oird.inventory_record_id))))
     JOIN obj_estate oe ON (((oe.remove_time IS NULL) AND (oe.id = oird.estate_id))));


ALTER TABLE vw_inventory_record_detail OWNER TO mempb;

--
-- Name: vw_message_send_detail; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_message_send_detail AS
 SELECT smsd.id,
    smsd.send_user_id,
    su.user_name AS send_user_name,
    smsd.receive_user_id,
    su.user_name AS receive_user_name,
    smsd.sys_message_id,
    sm.message_title,
    sm.message_author,
    su.user_name AS author,
    sm.message_text,
    sm.status,
    smsd.create_time,
    smsd.create_by,
    smsd.last_update_time,
    smsd.last_update_by,
    smsd.remove_time,
    sm.message_file1_url,
    sm.message_file2_url,
    sm.message_file3_url
   FROM ((sys_message_send_detail smsd
     JOIN sys_message sm ON (((sm.remove_time IS NULL) AND (sm.id = smsd.sys_message_id))))
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = smsd.send_user_id))));


ALTER TABLE vw_message_send_detail OWNER TO mempb;

--
-- Name: vw_obj_station; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_obj_station AS
 SELECT DISTINCT os.id,
    os.project_id,
    oc.corp_name,
    os.station_no,
    os.station_sn,
    os.station_name,
    os.station_en,
    os.station_name_short,
    concat(os.station_no, os.station_name) AS station_no_name,
    os.longitude,
    os.latitude,
    os.remark,
    os.principal,
    os.create_time,
    os.create_by,
    os.last_update_time,
    os.last_update_by,
    os.remove_time
   FROM (obj_station os
     JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = os.project_id))));


ALTER TABLE vw_obj_station OWNER TO mempb;

--
-- Name: vw_role_resource; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_role_resource AS
 SELECT res.id,
    res.resource_code,
    res.resource_name,
    r.id AS role_id,
    res.create_time,
    res.create_by,
    res.last_update_time,
    res.last_update_by,
    res.remove_time
   FROM ((sys_resource res
     JOIN sys_role_resource srr ON (((srr.remove_time IS NULL) AND (srr.resource_id = res.id))))
     JOIN sys_role r ON (((r.remove_time IS NULL) AND (r.id = srr.role_id))));


ALTER TABLE vw_role_resource OWNER TO mempb;

--
-- Name: vw_score_month; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_score_month AS
 SELECT to_char(obj_work_order.last_update_time, 'yyyy-mm'::text) AS score_month,
    sum((obj_work_order.work_order_score + obj_work_order.work_order_score_deduct)) AS work_order_score_count,
    obj_work_order.repair_employee AS uid,
    ((obj_work_order.repair_employee)::double precision * random()) AS id
   FROM obj_work_order
  WHERE (obj_work_order.status_id = 900)
  GROUP BY obj_work_order.repair_employee, to_char(obj_work_order.last_update_time, 'yyyy-mm'::text)
  ORDER BY to_char(obj_work_order.last_update_time, 'yyyy-mm'::text);


ALTER TABLE vw_score_month OWNER TO mempb;

--
-- Name: vw_stock_record; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_record AS
 SELECT osr.id,
    osr.estate_type_id,
    osr.count,
    oet.name AS estate_type_name,
    oet.name_en,
    oet.category,
    oet.parts_type,
    osr.corp_id,
    oc.corp_name,
    osr.create_time,
    osr.create_by,
    su.user_name AS create_name,
    osr.last_update_time,
    osr.last_update_by,
    osr.remove_time
   FROM (((obj_stock_record osr
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = osr.create_by))))
     JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = osr.estate_type_id))))
     JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = osr.corp_id))));


ALTER TABLE vw_stock_record OWNER TO mempb;

--
-- Name: vw_stock_record_personal; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_record_personal AS
 SELECT osrp.id,
    osrp.estate_type_id,
    osrp.count,
    osrp.user_id,
    su.user_name,
    oet.name AS estate_type_name,
    oet.name_en,
    oet.category,
    oet.parts_type,
    osrp.create_time,
    osrp.create_by,
    osrp.last_update_time,
    osrp.last_update_by,
    osrp.remove_time
   FROM ((obj_stock_record_personal osrp
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = osrp.user_id))))
     JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = osrp.estate_type_id))));


ALTER TABLE vw_stock_record_personal OWNER TO mempb;

--
-- Name: vw_stock_record_personal_history; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_record_personal_history AS
 SELECT osrph.id,
    osrph.estate_type_id,
    osrph.count,
    osrph.operation_type,
    osrph.operation_time,
    osrph.create_time,
    osrph.create_by,
    osrph.last_update_time,
    osrph.last_update_by,
    osrph.remove_time,
    osrph.user_id,
    rr.user_name,
    oet.name AS estate_type_name,
    rswot.name_cn AS operation_type_name_cn
   FROM (((obj_stock_record_personal_history osrph
     LEFT JOIN sys_user rr ON (((rr.remove_time IS NULL) AND (rr.id = osrph.user_id))))
     JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = osrph.estate_type_id))))
     JOIN ref_stock_work_order_type rswot ON (((rswot.remove_time IS NULL) AND (rswot.id = osrph.operation_type))));


ALTER TABLE vw_stock_record_personal_history OWNER TO mempb;

--
-- Name: vw_stock_work_order; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_work_order AS
 SELECT oswo.id,
    su.corp_id,
    oc.corp_name,
    oswo.apply_user_id,
    su.user_name AS apply_user_name,
    oswo.serial_no,
    oswo.stock_work_order_type_id,
    oswo.apply_time,
    oswo.stock_work_order_status_id,
    rtos.operation_result,
    rtos.name_cn AS stock_work_order_status_name_cn,
    oswo.process_user_id,
    su.user_name AS process_user_name,
    oswo.apply_remark,
    oswo.reject_remark,
    oswo.confirm_time,
    oswo.reject_time,
    oswo.create_time,
    oswo.create_by,
    oswo.last_update_time,
    oswo.last_update_by,
    oswo.remove_time
   FROM (((obj_stock_work_order oswo
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = oswo.apply_user_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = su.corp_id))))
     LEFT JOIN ref_tock_work_order_status rtos ON (((rtos.remove_time IS NULL) AND (rtos.id = oswo.stock_work_order_status_id))));


ALTER TABLE vw_stock_work_order OWNER TO mempb;

--
-- Name: vw_stock_work_order_detail; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_work_order_detail AS
 SELECT oswod.id,
    oswod.stock_work_order_id,
    oswod.estate_type_id,
    oet.name AS estate_type_name,
    oet.parts_type,
    oet.category,
    oswod.count,
    su.corp_id,
    oc.corp_name,
    oswo.apply_user_id,
    su.user_name AS apply_user_name,
    oswo.process_user_id,
    suu.user_name AS process_user_name,
    oswo.serial_no,
    oswo.stock_work_order_type_id,
    rswot.name_cn AS stock_work_order_type_name,
    oswo.apply_time,
    oswo.stock_work_order_status_id,
    oswo.apply_remark,
    oswo.reject_remark,
    oswo.confirm_time,
    oswo.reject_time,
    oswo.create_time,
    oswo.create_by,
    oswo.last_update_time,
    oswo.last_update_by,
    oswo.remove_time
   FROM ((((((obj_stock_work_order_detail oswod
     JOIN obj_stock_work_order oswo ON (((oswo.remove_time IS NULL) AND (oswo.id = oswod.stock_work_order_id))))
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = oswo.apply_user_id))))
     JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = oswod.estate_type_id))))
     JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = su.corp_id))))
     LEFT JOIN ref_stock_work_order_type rswot ON (((rswot.remove_time IS NULL) AND (rswot.id = oswo.stock_work_order_type_id))))
     LEFT JOIN sys_user suu ON (((suu.remove_time IS NULL) AND (suu.id = oswo.process_user_id))));


ALTER TABLE vw_stock_work_order_detail OWNER TO mempb;

--
-- Name: vw_stock_work_order_history; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_work_order_history AS
 SELECT oswoh.id,
    oswoh.stock_work_order_id,
    oswoh.stock_work_order_status_id,
    rtwos.name_cn AS status_name,
    oswoh.process_user_id,
    rr.user_name AS process_user_name,
    oswoh.operation_time,
    oswoh.last_operation_time,
    oswo.serial_no,
    oswo.stock_work_order_type_id,
    rtwot.name_cn AS type_name,
    oswo.apply_user_id,
    r.user_name AS apply_user_name,
    oswo.apply_time,
    oswo.apply_remark,
    oswo.reject_remark,
    oswo.confirm_time,
    oswo.reject_time,
    oswoh.create_time,
    oswoh.create_by,
    oswoh.last_update_time,
    oswoh.last_update_by,
    oswoh.remove_time
   FROM (((((obj_stock_work_order_history oswoh
     JOIN obj_stock_work_order oswo ON (((oswo.remove_time IS NULL) AND (oswo.id = oswoh.stock_work_order_id))))
     JOIN sys_user rr ON (((rr.remove_time IS NULL) AND (rr.id = oswoh.process_user_id))))
     JOIN ref_tock_work_order_status rtwos ON (((rtwos.remove_time IS NULL) AND (rtwos.id = oswoh.stock_work_order_status_id))))
     JOIN ref_stock_work_order_type rtwot ON (((rtwot.remove_time IS NULL) AND (rtwot.id = oswo.stock_work_order_type_id))))
     JOIN sys_user r ON (((r.remove_time IS NULL) AND (r.id = oswo.apply_user_id))));


ALTER TABLE vw_stock_work_order_history OWNER TO mempb;

--
-- Name: vw_stock_work_order_resource; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_stock_work_order_resource AS
 SELECT owor.id,
    owor.stock_work_order_id,
    owor.file_id,
    owor.remark,
    owor.category,
    owor.create_time,
    owor.create_by,
    owor.last_update_time,
    owor.last_update_by,
    owor.remove_time,
    of.file_id AS file_md5,
    of.file_name,
    of.file_size,
    of.thumbnail,
    of.file_type,
    of.file_url,
    su.user_name AS create_by_user_name,
    suu.user_name AS last_update_by_user_name
   FROM (((obj_stock_work_order_resource owor
     LEFT JOIN obj_file of ON (((of.remove_time IS NULL) AND (of.id = owor.file_id))))
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = owor.create_by))))
     LEFT JOIN sys_user suu ON (((suu.remove_time IS NULL) AND (suu.id = owor.last_update_by))));


ALTER TABLE vw_stock_work_order_resource OWNER TO mempb;

--
-- Name: vw_user; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user AS
 SELECT t.role_names,
    t.role_ids,
    u.id,
    u.user_account,
    u.user_name,
    u.user_password,
    u.user_surname,
    u.user_status,
    u.user_type,
    u.corp_id,
    oc.corp_name,
    u.user_phone,
    u.whitelist,
    u.user_group,
    u.create_time,
    u.create_by,
    u.last_update_time,
    u.last_update_by,
    u.remove_time,
    u.user_email,
    u.user_qq,
    u.user_wechart,
    u.expiry_time
   FROM ((sys_user u
     LEFT JOIN ( SELECT sur.user_id AS asso_user_id,
            array_to_string(array_agg(sr.role_name), ','::text) AS role_names,
            array_to_string(array_agg(sur.role_id), ','::text) AS role_ids
           FROM (sys_user_role sur
             LEFT JOIN sys_role sr ON (((sr.remove_time IS NULL) AND (sr.id = sur.role_id))))
          WHERE ((sr.remove_time IS NULL) AND (sur.remove_time IS NULL))
          GROUP BY sur.user_id) t ON ((t.asso_user_id = u.id)))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = u.corp_id))));


ALTER TABLE vw_user OWNER TO mempb;

--
-- Name: vw_user_estate; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_estate AS
 SELECT oe.id,
    oe.estate_no,
    oe.estate_sn,
    oe.estate_name,
    oe.estate_type_id,
    etype.name AS estate_type_name,
    oe.estate_status_id,
    oes.status_name AS estate_status_name,
    oe.logical_id,
    oe.parent_id,
    oe.estate_path,
    sp.id AS project_id,
    oe.station_id,
    os.station_no,
    os.station_sn,
    oe.category,
    concat(os.station_no, os.station_name) AS station_name,
    oe.supplier_id,
    osup.supplier_name,
    oe.bike_frame_no,
    oe.create_time,
    oe.create_by,
    oe.last_update_time,
    oe.last_update_by,
    oe.remove_time,
    oc.corp_name,
    aup.user_id AS uid,
    csu.user_name AS creat_by_user_name,
    lsu.user_name AS last_update_by_user_name,
    oe.bicycle_stake_bar_code
   FROM ((((((((((obj_estate oe
     LEFT JOIN obj_estate_status oes ON (((oes.remove_time IS NULL) AND (oes.id = oe.estate_status_id))))
     LEFT JOIN obj_estate_type etype ON ((etype.id = oe.estate_type_id)))
     LEFT JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = oe.station_id))))
     LEFT JOIN obj_estate_supplier osup ON (((osup.remove_time IS NULL) AND (osup.id = oe.supplier_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = oe.project_id))))
     LEFT JOIN asso_user_project aup ON (((aup.remove_time IS NULL) AND (aup.project_id = oe.project_id))))
     LEFT JOIN sys_user usr ON ((usr.id = aup.user_id)))
     LEFT JOIN sys_project sp ON (((sp.remove_time IS NULL) AND (sp.id = oe.project_id))))
     LEFT JOIN sys_user csu ON ((csu.id = oe.create_by)))
     LEFT JOIN sys_user lsu ON ((lsu.id = oe.last_update_by)));


ALTER TABLE vw_user_estate OWNER TO mempb;

--
-- Name: vw_user_estate_bar_code; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_estate_bar_code AS
 SELECT obi.id,
    obi.bar_code_path,
    obi.bar_code_sn,
    obi.bar_code_message,
    obi.bar_code_category,
    obi.export_time,
    obi.activate_time,
    obi.create_time,
    obi.create_by,
    obi.last_update_time,
    obi.last_update_by,
    obi.remove_time,
    obi.relation,
    oe.estate_name,
    oe.estate_no,
    oe.station_id,
    oe.category,
    oe.estate_type_id,
    oe.project_id,
    oe.supplier_id,
    oe.bike_frame_no,
    os.station_name,
    oc.corp_name,
    oet.name AS estate_type_name,
    oes.supplier_name,
    aup.user_id AS uid,
    csu.user_name AS creat_by_user_name,
    lsu.user_name AS last_update_by_user_name,
    concat(os.station_no, os.station_name) AS station_no_name,
    oe.bicycle_stake_bar_code
   FROM ((((((((((obj_barcode_image obi
     LEFT JOIN obj_estate oe ON ((((oe.remove_time IS NULL) AND ((oe.estate_sn)::text = (obi.bar_code_sn)::text)) AND (obi.relation = 1))))
     LEFT JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = oe.station_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = oe.project_id))))
     LEFT JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = oe.estate_type_id))))
     LEFT JOIN obj_estate_supplier oes ON (((oes.remove_time IS NULL) AND (oes.id = oe.supplier_id))))
     LEFT JOIN asso_user_project aup ON (((aup.remove_time IS NULL) AND (aup.project_id = oe.project_id))))
     LEFT JOIN sys_user usr ON ((usr.id = aup.user_id)))
     LEFT JOIN sys_project sp ON (((sp.remove_time IS NULL) AND (sp.id = oe.project_id))))
     LEFT JOIN sys_user csu ON ((csu.id = obi.create_by)))
     LEFT JOIN sys_user lsu ON ((lsu.id = obi.last_update_by)));


ALTER TABLE vw_user_estate_bar_code OWNER TO mempb;

--
-- Name: vw_user_point_record; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_point_record AS
 SELECT upr.id,
    upr.user_id,
    upr.point_change,
    upr.point_source,
    upr.work_order_id,
    upr.current_date_str,
    upr.point_record_level,
    upr.create_time,
    upr.create_by,
    upr.last_update_time,
    upr.last_update_by,
    upr.remove_time,
    su.user_account,
    su.user_name,
    su.user_surname,
    su.corp_id,
    oc.corp_name,
    su.user_phone,
    su.user_email,
    su.user_qq,
    su.user_wechart,
    owo.serial_no,
    os.station_no,
    os.station_name,
    concat(os.station_no, os.station_name) AS station_no_name
   FROM ((((sys_user_point_record upr
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = upr.user_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = su.corp_id))))
     LEFT JOIN obj_work_order owo ON (((owo.remove_time IS NULL) AND (owo.id = upr.work_order_id))))
     LEFT JOIN obj_station os ON (((os.remove_time IS NULL) AND (os.id = owo.station_id))));


ALTER TABLE vw_user_point_record OWNER TO mempb;

--
-- Name: vw_work_order; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_work_order AS
 SELECT owo.id,
    owo.estate_id,
    owo.type_id,
    owo.report_employee,
    owo.report_time,
    owo.assign_time,
    owo.repair_employee,
    owo.repair_confirm_time,
    owo.repair_start_time,
    owo.repair_end_time,
    owo.status_id,
    owo.fault_description,
    owo.repair_remark,
    owo.assign_remark,
    owo.work_order_source,
    owo.maintain_remark,
    owo.station_id,
    owo.longitude,
    owo.latitude,
    owo.level,
    owo.reponse_over_time,
    owo.repair_over_time,
    owo.create_time,
    owo.create_by,
    owo.last_update_time,
    owo.last_update_by,
    owo.remove_time,
    owo.assign_employee,
    owo.report_way,
    owo.serial_no,
    owo.process_instance_id,
    owo.back_remark,
    ss.project_id,
    oet.name AS estate_type_name,
    oet.name_en AS estate_type_name_en,
    oe.estate_no,
    oe.estate_name,
    oe.estate_type_id,
    oe.category,
    oe.estate_sn,
    oc.corp_name,
    reu.user_name AS report_employee_user_name,
    aeu.user_name AS assign_employee_user_name,
    rreu.user_name AS repair_employee_user_name,
    owot.name_cn AS work_order_type_name_cn,
    owos.name_cn AS work_order_status_name_cn,
    (
        CASE
            WHEN (owo.level = 1) THEN 'level_1'::text
            WHEN (owo.level = 2) THEN 'level_2'::text
            WHEN (owo.level = 3) THEN 'level_3'::text
            WHEN (owo.level = 99) THEN 'level_99'::text
            ELSE ''::text
        END)::character varying(20) AS level_color,
    ss.station_name,
    oes.supplier_name,
    owo.fixed,
    owo.response_time_out_date,
    owo.repair_time_out_date,
    oe.bike_frame_no,
    owo.location,
    owo.work_order_score,
    owo.work_order_score_deduct,
    owo.score_deduct_remark,
    oe.bicycle_stake_bar_code,
    ss.station_no
   FROM ((((((((((obj_work_order owo
     LEFT JOIN obj_station ss ON (((ss.remove_time IS NULL) AND (owo.station_id = ss.id))))
     LEFT JOIN obj_estate oe ON (((oe.remove_time IS NULL) AND (oe.id = owo.estate_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (ss.project_id = oc.id))))
     LEFT JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = oe.estate_type_id))))
     LEFT JOIN ref_work_order_type owot ON (((owot.remove_time IS NULL) AND (owot.id = owo.type_id))))
     LEFT JOIN ref_work_order_status owos ON (((owos.remove_time IS NULL) AND (owos.id = owo.status_id))))
     LEFT JOIN sys_user reu ON (((reu.remove_time IS NULL) AND (reu.id = owo.report_employee))))
     LEFT JOIN sys_user aeu ON (((aeu.remove_time IS NULL) AND (aeu.id = owo.assign_employee))))
     LEFT JOIN sys_user rreu ON (((rreu.remove_time IS NULL) AND (rreu.id = owo.repair_employee))))
     LEFT JOIN obj_estate_supplier oes ON (((oes.remove_time IS NULL) AND (oe.supplier_id = oes.id))));


ALTER TABLE vw_work_order OWNER TO mempb;

--
-- Name: vw_user_position_record; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_position_record AS
 SELECT supr.id,
    supr.user_id,
    supr.longitude,
    supr.latitude,
    supr.position_source,
    supr.work_order_id,
    supr.record_time,
    supr.create_time,
    supr.create_by,
    supr.last_update_time,
    supr.last_update_by,
    supr.remove_time,
        CASE supr.position_source
            WHEN 1 THEN '工单创建'::text
            WHEN 2 THEN '工单确认'::text
            WHEN 3 THEN '维修到达'::text
            WHEN 4 THEN '工单完成'::text
            WHEN 5 THEN '工单遗留'::text
            ELSE ''::text
        END AS "case",
    su.user_account,
    su.user_name,
    su.user_surname,
    su.corp_id,
    oc.corp_name,
    su.user_phone,
    su.user_email,
    su.user_qq,
    su.user_wechart,
    vwo.estate_id,
    vwo.report_employee,
    vwo.report_time,
    vwo.assign_time,
    vwo.repair_employee,
    vwo.repair_confirm_time,
    vwo.repair_start_time,
    vwo.repair_end_time,
    vwo.status_id,
    vwo.fault_description,
    vwo.repair_remark,
    vwo.assign_remark,
    vwo.work_order_source,
    vwo.maintain_remark,
    vwo.station_id,
    vwo.level,
    vwo.reponse_over_time,
    vwo.repair_over_time,
    vwo.assign_employee,
    vwo.report_way,
    vwo.serial_no,
    vwo.back_remark,
    vwo.project_id,
    vwo.estate_type_name,
    vwo.estate_type_name_en,
    vwo.estate_no,
    vwo.estate_name,
    vwo.estate_type_id,
    vwo.category,
    vwo.estate_sn,
    vwo.report_employee_user_name,
    vwo.assign_employee_user_name,
    vwo.repair_employee_user_name,
    vwo.work_order_type_name_cn,
    vwo.work_order_status_name_cn,
    vwo.level_color,
    vwo.station_name,
    vwo.supplier_name,
    vwo.fixed,
    vwo.response_time_out_date,
    vwo.repair_time_out_date,
    vwo.bike_frame_no,
    supr.location
   FROM (((sys_user_position_record supr
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = supr.user_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (oc.id = su.corp_id))))
     LEFT JOIN vw_work_order vwo ON (((vwo.remove_time IS NULL) AND (vwo.id = supr.work_order_id))));


ALTER TABLE vw_user_position_record OWNER TO mempb;

--
-- Name: vw_user_role; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_role AS
 SELECT sur.id,
    sur.role_id,
    sr.role_name,
    sur.user_id,
    su.user_name,
    su.user_password,
    su.corp_id,
    sur.create_time,
    sur.create_by,
    sur.last_update_time,
    sur.last_update_by,
    sur.remove_time,
    sr.role_grade
   FROM ((sys_user_role sur
     JOIN sys_role sr ON (((sr.remove_time IS NULL) AND (sr.id = sur.role_id))))
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = sur.user_id))));


ALTER TABLE vw_user_role OWNER TO mempb;

--
-- Name: vw_user_role_resource; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_role_resource AS
 SELECT sur.id,
    sur.role_id,
    sr.role_name,
    sur.user_id,
    su.user_name,
    su.user_password,
    su.corp_id,
    srr.resource_id,
    sre.resource_name,
    sre.resource_type,
    sur.create_time,
    sur.create_by,
    sur.last_update_time,
    sur.last_update_by,
    sur.remove_time,
    sr.role_grade,
    sre.resource_code
   FROM ((((sys_user_role sur
     JOIN sys_role sr ON (((sr.remove_time IS NULL) AND (sr.id = sur.role_id))))
     JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = sur.user_id))))
     JOIN sys_role_resource srr ON (((srr.remove_time IS NULL) AND (srr.role_id = sur.role_id))))
     JOIN sys_resource sre ON (((sre.remove_time IS NULL) AND (sre.id = srr.resource_id))));


ALTER TABLE vw_user_role_resource OWNER TO mempb;

--
-- Name: vw_user_work_order; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_user_work_order AS
 SELECT owo.id,
    owo.estate_id,
    owo.type_id,
    owo.report_employee,
    owo.report_time,
    owo.assign_time,
    owo.repair_employee,
    owo.repair_confirm_time,
    owo.repair_start_time,
    owo.repair_end_time,
    owo.status_id,
    owo.fault_description,
    owo.repair_remark,
    owo.assign_remark,
    owo.work_order_source,
    owo.maintain_remark,
    owo.station_id,
    owo.longitude,
    owo.latitude,
    owo.level,
    owo.reponse_over_time,
    owo.repair_over_time,
    owo.create_time,
    owo.create_by,
    owo.last_update_time,
    owo.last_update_by,
    owo.remove_time,
    owo.assign_employee,
    owo.report_way,
    owo.serial_no,
    owo.process_instance_id,
    owo.back_remark,
    ss.project_id,
    oet.name AS estate_type_name,
    oet.name_en AS estate_type_name_en,
    oe.estate_no,
    oe.estate_name,
    oe.estate_type_id,
    oe.category,
    oe.estate_sn,
    oc.corp_name,
    reu.user_name AS report_employee_user_name,
    aeu.user_name AS assign_employee_user_name,
    rreu.user_name AS repair_employee_user_name,
    owot.name_cn AS work_order_type_name_cn,
    owos.name_cn AS work_order_status_name_cn,
    (
        CASE
            WHEN (owo.level = 1) THEN 'level_1'::text
            WHEN (owo.level = 2) THEN 'level_2'::text
            WHEN (owo.level = 3) THEN 'level_3'::text
            WHEN (owo.level = 99) THEN 'level_99'::text
            ELSE ''::text
        END)::character varying(20) AS level_color,
    ss.station_name,
    oes.supplier_name,
    aup.user_id AS uid,
    owo.fixed,
    owo.response_time_out_date,
    owo.repair_time_out_date,
    owos.name_cn AS status_name,
    oe.bike_frame_no,
    owo.location,
    owo.work_order_score,
    owo.work_order_score_deduct,
    owo.score_deduct_remark,
    oe.bicycle_stake_bar_code,
    ss.station_no
   FROM (((((((((((obj_work_order owo
     LEFT JOIN obj_station ss ON (((ss.remove_time IS NULL) AND (owo.station_id = ss.id))))
     LEFT JOIN obj_estate oe ON (((oe.remove_time IS NULL) AND (oe.id = owo.estate_id))))
     LEFT JOIN asso_user_project aup ON (((aup.remove_time IS NULL) AND (ss.project_id = aup.project_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (ss.project_id = oc.id))))
     LEFT JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = oe.estate_type_id))))
     LEFT JOIN ref_work_order_type owot ON (((owot.remove_time IS NULL) AND (owot.id = owo.type_id))))
     LEFT JOIN ref_work_order_status owos ON (((owos.remove_time IS NULL) AND (owos.id = owo.status_id))))
     LEFT JOIN sys_user reu ON (((reu.remove_time IS NULL) AND (reu.id = owo.report_employee))))
     LEFT JOIN sys_user aeu ON (((aeu.remove_time IS NULL) AND (aeu.id = owo.assign_employee))))
     LEFT JOIN sys_user rreu ON (((rreu.remove_time IS NULL) AND (rreu.id = owo.repair_employee))))
     LEFT JOIN obj_estate_supplier oes ON (((oes.remove_time IS NULL) AND (oe.supplier_id = oes.id))));


ALTER TABLE vw_user_work_order OWNER TO mempb;

--
-- Name: vw_work_order_bad_component; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_work_order_bad_component AS
 SELECT owobc.id,
    owobc.work_order_id,
    owobc.estate_type_id,
    owobc.create_time,
    owobc.create_by,
    owobc.last_update_time,
    owobc.last_update_by,
    owobc.remove_time,
    owobc.replace_count,
    oet.name,
    su.user_name AS create_by_user_name,
    suu.user_name AS last_update_by_user_name
   FROM (((obj_work_order_bad_component owobc
     LEFT JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = owobc.estate_type_id))))
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = owobc.create_by))))
     LEFT JOIN sys_user suu ON (((suu.remove_time IS NULL) AND (suu.id = owobc.last_update_by))));


ALTER TABLE vw_work_order_bad_component OWNER TO mempb;

--
-- Name: vw_work_order_history; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_work_order_history AS
 SELECT owod.id,
    su.user_name,
    oodt.name_cn AS txt,
    owod.work_order_id,
    owod.operation_type_id,
    owod.operation_remark,
    owod.operator_id,
    owod.create_time,
    owod.create_by,
    owod.last_update_time,
    owod.last_update_by,
    owod.remove_time,
    owod.last_operation_time,
    (age(owod.create_time, owod.last_operation_time))::text AS operation_time,
    owod.longitude,
    owod.latitude,
    owod.location,
    owod.work_order_score,
    owod.work_order_score_deduct
   FROM (((obj_work_order_operation owod
     LEFT JOIN ref_work_order_status oodt ON (((oodt.remove_time IS NULL) AND (oodt.id = owod.operation_type_id))))
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = owod.operator_id))))
     LEFT JOIN obj_work_order owo ON (((owo.remove_time IS NULL) AND (owo.id = owod.work_order_id))));


ALTER TABLE vw_work_order_history OWNER TO mempb;

--
-- Name: vw_work_order_module_bad_component_count; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_work_order_module_bad_component_count AS
 SELECT owobc.id,
    owobc.work_order_id,
    owobc.estate_type_id,
    owobc.create_time,
    owobc.create_by,
    owobc.last_update_time,
    owobc.last_update_by,
    owobc.remove_time,
    owobc.replace_count,
    oet.name,
    su.user_name AS create_by_user_name,
    suu.user_name AS last_update_by_user_name,
    owo.estate_id,
    owo.type_id,
    owo.report_employee,
    owo.report_time,
    owo.assign_time,
    owo.repair_employee,
    owo.repair_confirm_time,
    owo.repair_start_time,
    owo.repair_end_time,
    owo.status_id,
    owo.fault_description,
    owo.repair_remark,
    owo.assign_remark,
    owo.work_order_source,
    owo.maintain_remark,
    owo.station_id,
    owo.longitude,
    owo.latitude,
    owo.level,
    owo.reponse_over_time,
    owo.repair_over_time,
    owo.assign_employee,
    owo.report_way,
    owo.serial_no,
    owo.process_instance_id,
    owo.back_remark,
    ss.project_id,
    oet.name AS estate_type_name,
    oet.name_en AS estate_type_name_en,
    oe.estate_no,
    oe.estate_name,
    oet.category,
    oe.estate_sn,
    oc.corp_name,
    reu.user_name AS report_employee_user_name,
    aeu.user_name AS assign_employee_user_name,
    rreu.user_name AS repair_employee_user_name,
    owot.name_cn AS work_order_type_name_cn,
    owos.name_cn AS work_order_status_name_cn,
    (
        CASE
            WHEN (owo.level = 1) THEN 'level_1'::text
            WHEN (owo.level = 2) THEN 'level_2'::text
            WHEN (owo.level = 3) THEN 'level_3'::text
            WHEN (owo.level = 99) THEN 'level_99'::text
            ELSE ''::text
        END)::character varying(20) AS level_color,
    ss.station_name,
    oes.supplier_name,
    owo.fixed,
    owo.response_time_out_date,
    owo.repair_time_out_date,
    oe.bike_frame_no,
    owo.location,
    owo.work_order_score,
    owo.work_order_score_deduct,
    owo.score_deduct_remark,
    oe.bicycle_stake_bar_code,
    ss.station_no
   FROM (((((((((((((obj_work_order_bad_component owobc
     LEFT JOIN obj_work_order owo ON (((owo.remove_time IS NULL) AND (owo.id = owobc.work_order_id))))
     LEFT JOIN obj_estate_type oet ON (((oet.remove_time IS NULL) AND (oet.id = owobc.estate_type_id))))
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = owobc.create_by))))
     LEFT JOIN sys_user suu ON (((suu.remove_time IS NULL) AND (suu.id = owobc.last_update_by))))
     LEFT JOIN obj_station ss ON (((ss.remove_time IS NULL) AND (owo.station_id = ss.id))))
     LEFT JOIN obj_estate oe ON (((oe.remove_time IS NULL) AND (oe.id = owo.estate_id))))
     LEFT JOIN obj_corporation oc ON (((oc.remove_time IS NULL) AND (ss.project_id = oc.id))))
     LEFT JOIN ref_work_order_type owot ON (((owot.remove_time IS NULL) AND (owot.id = owo.type_id))))
     LEFT JOIN ref_work_order_status owos ON (((owos.remove_time IS NULL) AND (owos.id = owo.status_id))))
     LEFT JOIN sys_user reu ON (((reu.remove_time IS NULL) AND (reu.id = owo.report_employee))))
     LEFT JOIN sys_user aeu ON (((aeu.remove_time IS NULL) AND (aeu.id = owo.assign_employee))))
     LEFT JOIN sys_user rreu ON (((rreu.remove_time IS NULL) AND (rreu.id = owo.repair_employee))))
     LEFT JOIN obj_estate_supplier oes ON (((oes.remove_time IS NULL) AND (oe.supplier_id = oes.id))));


ALTER TABLE vw_work_order_module_bad_component_count OWNER TO mempb;

--
-- Name: vw_work_order_resource; Type: VIEW; Schema: business; Owner: mempb
--

CREATE VIEW vw_work_order_resource AS
 SELECT owor.id,
    owor.work_order_id,
    owor.file_id,
    owor.remark,
    owor.category,
    owor.create_time,
    owor.create_by,
    owor.last_update_time,
    owor.last_update_by,
    owor.remove_time,
    of.file_id AS file_md5,
    of.file_name,
    of.file_size,
    of.thumbnail,
    of.file_type,
    of.file_url,
    su.user_name AS create_by_user_name,
    suu.user_name AS last_update_by_user_name
   FROM (((obj_work_order_resource owor
     LEFT JOIN obj_file of ON (((of.remove_time IS NULL) AND (of.id = owor.file_id))))
     LEFT JOIN sys_user su ON (((su.remove_time IS NULL) AND (su.id = owor.create_by))))
     LEFT JOIN sys_user suu ON (((suu.remove_time IS NULL) AND (suu.id = owor.last_update_by))));


ALTER TABLE vw_work_order_resource OWNER TO mempb;

SET search_path = public, pg_catalog;

--
-- Name: act_evt_log; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_evt_log (
    log_nr_ integer NOT NULL,
    type_ character varying(64),
    proc_def_id_ character varying(64),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    time_stamp_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    data_ bytea,
    lock_owner_ character varying(255),
    lock_time_ timestamp without time zone,
    is_processed_ smallint DEFAULT 0
);


ALTER TABLE act_evt_log OWNER TO mempb;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE; Schema: public; Owner: mempb
--

CREATE SEQUENCE act_evt_log_log_nr__seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE act_evt_log_log_nr__seq OWNER TO mempb;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mempb
--

ALTER SEQUENCE act_evt_log_log_nr__seq OWNED BY act_evt_log.log_nr_;


--
-- Name: act_ge_bytearray; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ge_bytearray (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    deployment_id_ character varying(64),
    bytes_ bytea,
    generated_ boolean
);


ALTER TABLE act_ge_bytearray OWNER TO mempb;

--
-- Name: act_ge_property; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ge_property (
    name_ character varying(64) NOT NULL,
    value_ character varying(300),
    rev_ integer
);


ALTER TABLE act_ge_property OWNER TO mempb;

--
-- Name: act_hi_actinst; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_actinst (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    execution_id_ character varying(64) NOT NULL,
    act_id_ character varying(255) NOT NULL,
    task_id_ character varying(64),
    call_proc_inst_id_ character varying(64),
    act_name_ character varying(255),
    act_type_ character varying(255) NOT NULL,
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_hi_actinst OWNER TO mempb;

--
-- Name: act_hi_attachment; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_attachment (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(255),
    name_ character varying(255),
    description_ character varying(4000),
    type_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    url_ character varying(4000),
    content_id_ character varying(64),
    time_ timestamp without time zone
);


ALTER TABLE act_hi_attachment OWNER TO mempb;

--
-- Name: act_hi_comment; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_comment (
    id_ character varying(64) NOT NULL,
    type_ character varying(255),
    time_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    action_ character varying(255),
    message_ character varying(4000),
    full_msg_ bytea
);


ALTER TABLE act_hi_comment OWNER TO mempb;

--
-- Name: act_hi_detail; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_detail (
    id_ character varying(64) NOT NULL,
    type_ character varying(255) NOT NULL,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    act_inst_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(64),
    rev_ integer,
    time_ timestamp without time zone NOT NULL,
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE act_hi_detail OWNER TO mempb;

--
-- Name: act_hi_identitylink; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_identitylink (
    id_ character varying(64) NOT NULL,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64)
);


ALTER TABLE act_hi_identitylink OWNER TO mempb;

--
-- Name: act_hi_procinst; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_procinst (
    id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    business_key_ character varying(255),
    proc_def_id_ character varying(64) NOT NULL,
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    start_user_id_ character varying(255),
    start_act_id_ character varying(255),
    end_act_id_ character varying(255),
    super_process_instance_id_ character varying(64),
    delete_reason_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255)
);


ALTER TABLE act_hi_procinst OWNER TO mempb;

--
-- Name: act_hi_taskinst; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_taskinst (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64),
    task_def_key_ character varying(255),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    owner_ character varying(255),
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    claim_time_ timestamp without time zone,
    end_time_ timestamp without time zone,
    duration_ bigint,
    delete_reason_ character varying(4000),
    priority_ integer,
    due_date_ timestamp without time zone,
    form_key_ character varying(255),
    category_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_hi_taskinst OWNER TO mempb;

--
-- Name: act_hi_varinst; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_hi_varinst (
    id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(100),
    rev_ integer,
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000),
    create_time_ timestamp without time zone,
    last_updated_time_ timestamp without time zone
);


ALTER TABLE act_hi_varinst OWNER TO mempb;

--
-- Name: act_id_group; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_id_group (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    type_ character varying(255)
);


ALTER TABLE act_id_group OWNER TO mempb;

--
-- Name: act_id_info; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_id_info (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(64),
    type_ character varying(64),
    key_ character varying(255),
    value_ character varying(255),
    password_ bytea,
    parent_id_ character varying(255)
);


ALTER TABLE act_id_info OWNER TO mempb;

--
-- Name: act_id_membership; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_id_membership (
    user_id_ character varying(64) NOT NULL,
    group_id_ character varying(64) NOT NULL
);


ALTER TABLE act_id_membership OWNER TO mempb;

--
-- Name: act_id_user; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_id_user (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    first_ character varying(255),
    last_ character varying(255),
    email_ character varying(255),
    pwd_ character varying(255),
    picture_id_ character varying(64)
);


ALTER TABLE act_id_user OWNER TO mempb;

--
-- Name: act_re_deployment; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_re_deployment (
    id_ character varying(64) NOT NULL,
    name_ character varying(255),
    category_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    deploy_time_ timestamp without time zone
);


ALTER TABLE act_re_deployment OWNER TO mempb;

--
-- Name: act_re_model; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_re_model (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    key_ character varying(255),
    category_ character varying(255),
    create_time_ timestamp without time zone,
    last_update_time_ timestamp without time zone,
    version_ integer,
    meta_info_ character varying(4000),
    deployment_id_ character varying(64),
    editor_source_value_id_ character varying(64),
    editor_source_extra_value_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_re_model OWNER TO mempb;

--
-- Name: act_re_procdef; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_re_procdef (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    name_ character varying(255),
    key_ character varying(255) NOT NULL,
    version_ integer NOT NULL,
    deployment_id_ character varying(64),
    resource_name_ character varying(4000),
    dgrm_resource_name_ character varying(4000),
    description_ character varying(4000),
    has_start_form_key_ boolean,
    has_graphical_notation_ boolean,
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_re_procdef OWNER TO mempb;

--
-- Name: act_ru_event_subscr; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_event_subscr (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    event_type_ character varying(255) NOT NULL,
    event_name_ character varying(255),
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    activity_id_ character varying(64),
    configuration_ character varying(255),
    created_ timestamp without time zone NOT NULL,
    proc_def_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_ru_event_subscr OWNER TO mempb;

--
-- Name: act_ru_execution; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_execution (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    proc_inst_id_ character varying(64),
    business_key_ character varying(255),
    parent_id_ character varying(64),
    proc_def_id_ character varying(64),
    super_exec_ character varying(64),
    act_id_ character varying(255),
    is_active_ boolean,
    is_concurrent_ boolean,
    is_scope_ boolean,
    is_event_scope_ boolean,
    suspension_state_ integer,
    cached_ent_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255),
    lock_time_ timestamp without time zone
);


ALTER TABLE act_ru_execution OWNER TO mempb;

--
-- Name: act_ru_identitylink; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_identitylink (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64)
);


ALTER TABLE act_ru_identitylink OWNER TO mempb;

--
-- Name: act_ru_job; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(255) NOT NULL,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE act_ru_job OWNER TO mempb;

--
-- Name: act_ru_task; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_task (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    task_def_key_ character varying(255),
    owner_ character varying(255),
    assignee_ character varying(255),
    delegation_ character varying(64),
    priority_ integer,
    create_time_ timestamp without time zone,
    due_date_ timestamp without time zone,
    category_ character varying(255),
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    form_key_ character varying(255)
);


ALTER TABLE act_ru_task OWNER TO mempb;

--
-- Name: act_ru_variable; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE act_ru_variable (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(255) NOT NULL,
    name_ character varying(255) NOT NULL,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    task_id_ character varying(64),
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE act_ru_variable OWNER TO mempb;

--
-- Name: oauth_client_details; Type: TABLE; Schema: public; Owner: mempb
--

CREATE TABLE oauth_client_details (
    client_id character varying(256) NOT NULL,
    resource_ids character varying(256),
    client_secret character varying(256),
    scope character varying(256),
    authorized_grant_types character varying(256),
    web_server_redirect_uri character varying(256),
    authorities character varying(256),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(256)
);


ALTER TABLE oauth_client_details OWNER TO mempb;

SET search_path = business, pg_catalog;

--
-- Name: asso_estate_module_type id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY asso_estate_module_type ALTER COLUMN id SET DEFAULT nextval('asso_estate_module_type_id_seq'::regclass);


--
-- Name: asso_user_project id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY asso_user_project ALTER COLUMN id SET DEFAULT nextval('asso_user_project_id_seq'::regclass);


--
-- Name: obj_corporation id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_corporation ALTER COLUMN id SET DEFAULT nextval('obj_corporation_id_seq'::regclass);


--
-- Name: obj_device_push id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_device_push ALTER COLUMN id SET DEFAULT nextval('obj_device_push_id_seq'::regclass);


--
-- Name: obj_estate id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate ALTER COLUMN id SET DEFAULT nextval('obj_estate_id_seq'::regclass);


--
-- Name: obj_estate_bak id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_bak ALTER COLUMN id SET DEFAULT nextval('obj_estate_bak_id_seq'::regclass);


--
-- Name: obj_estate_excel id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_excel ALTER COLUMN id SET DEFAULT nextval('obj_estate_excel_id_seq1'::regclass);


--
-- Name: obj_estate_status id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_status ALTER COLUMN id SET DEFAULT nextval('obj_estate_status_id_seq'::regclass);


--
-- Name: obj_estate_supplier id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_supplier ALTER COLUMN id SET DEFAULT nextval('obj_estate_supplier_id_seq'::regclass);


--
-- Name: obj_estate_type id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_type ALTER COLUMN id SET DEFAULT nextval('obj_estate_type_id_seq'::regclass);


--
-- Name: obj_file id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_file ALTER COLUMN id SET DEFAULT nextval('obj_file_id_seq'::regclass);


--
-- Name: obj_inventory_check_record id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_check_record ALTER COLUMN id SET DEFAULT nextval('obj_inventory_check_record_id_seq'::regclass);


--
-- Name: obj_inventory_check_record_detail id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_check_record_detail ALTER COLUMN id SET DEFAULT nextval('obj_inventory_check_record_detail_id_seq'::regclass);


--
-- Name: obj_inventory_record id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_record ALTER COLUMN id SET DEFAULT nextval('obj_inventory_record_id_seq'::regclass);


--
-- Name: obj_inventory_record_detail id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_record_detail ALTER COLUMN id SET DEFAULT nextval('obj_inventory_record_detail_id_seq'::regclass);


--
-- Name: obj_station id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_station ALTER COLUMN id SET DEFAULT nextval('obj_station_id_seq'::regclass);


--
-- Name: obj_station_bak id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_station_bak ALTER COLUMN id SET DEFAULT nextval('obj_station_bak_id_seq'::regclass);


--
-- Name: obj_stock id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock ALTER COLUMN id SET DEFAULT nextval('obj_stock_id_seq'::regclass);


--
-- Name: obj_stock_record id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record ALTER COLUMN id SET DEFAULT nextval('obj_stock_record_id_seq'::regclass);


--
-- Name: obj_stock_record_personal id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record_personal ALTER COLUMN id SET DEFAULT nextval('obj_stock_record_personal_id_seq'::regclass);


--
-- Name: obj_stock_record_personal_history id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record_personal_history ALTER COLUMN id SET DEFAULT nextval('obj_stock_record_personal_history_id_seq'::regclass);


--
-- Name: obj_stock_work_order id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order ALTER COLUMN id SET DEFAULT nextval('obj_stock_work_order_id_seq'::regclass);


--
-- Name: obj_stock_work_order_detail id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_detail ALTER COLUMN id SET DEFAULT nextval('obj_stock_work_order_detail_id_seq'::regclass);


--
-- Name: obj_stock_work_order_history id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_history ALTER COLUMN id SET DEFAULT nextval('obj_stock_work_order_history_id_seq'::regclass);


--
-- Name: obj_stock_work_order_resource id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_resource ALTER COLUMN id SET DEFAULT nextval('obj_stock_work_order_resource_id_seq'::regclass);


--
-- Name: obj_work_order id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order ALTER COLUMN id SET DEFAULT nextval('obj_work_order_id_seq'::regclass);


--
-- Name: obj_work_order_bad_component id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_bad_component ALTER COLUMN id SET DEFAULT nextval('obj_work_order_bad_component_id_seq'::regclass);


--
-- Name: obj_work_order_operation id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_operation ALTER COLUMN id SET DEFAULT nextval('obj_workorder_operation_id_seq'::regclass);


--
-- Name: obj_work_order_resource id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_resource ALTER COLUMN id SET DEFAULT nextval('obj_work_order_resource_id_seq'::regclass);


--
-- Name: ref_stock_work_order_type id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_stock_work_order_type ALTER COLUMN id SET DEFAULT nextval('ref_stock_work_order_type_id_seq'::regclass);


--
-- Name: ref_tock_work_order_status id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_tock_work_order_status ALTER COLUMN id SET DEFAULT nextval('ref_tock_work_order_status_id_seq'::regclass);


--
-- Name: ref_work_order_status id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_work_order_status ALTER COLUMN id SET DEFAULT nextval('ref_work_order_status_id_seq'::regclass);


--
-- Name: ref_work_order_type id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_work_order_type ALTER COLUMN id SET DEFAULT nextval('ref_work_order_type_id_seq'::regclass);


--
-- Name: sys_message id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_message ALTER COLUMN id SET DEFAULT nextval('sys_message_id_seq'::regclass);


--
-- Name: sys_message_send_detail id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_message_send_detail ALTER COLUMN id SET DEFAULT nextval('sys_message_send_detail_id_seq'::regclass);


--
-- Name: sys_param id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_param ALTER COLUMN id SET DEFAULT nextval('sys_param_id_seq'::regclass);


--
-- Name: sys_project id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_project ALTER COLUMN id SET DEFAULT nextval('sys_project_id_seq'::regclass);


--
-- Name: sys_resource id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_resource ALTER COLUMN id SET DEFAULT nextval('sys_resource_id_seq'::regclass);


--
-- Name: sys_role id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_role ALTER COLUMN id SET DEFAULT nextval('sys_role_id_seq'::regclass);


--
-- Name: sys_role_resource id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_role_resource ALTER COLUMN id SET DEFAULT nextval('sys_role_resource_id_seq'::regclass);


--
-- Name: sys_user id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user ALTER COLUMN id SET DEFAULT nextval('sys_user_id_seq'::regclass);


--
-- Name: sys_user_detail id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_detail ALTER COLUMN id SET DEFAULT nextval('sys_user_detail_id_seq'::regclass);


--
-- Name: sys_user_point_record id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_point_record ALTER COLUMN id SET DEFAULT nextval('sys_user_point_record_id_seq'::regclass);


--
-- Name: sys_user_position_record id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_position_record ALTER COLUMN id SET DEFAULT nextval('sys_user_position_record_id_seq'::regclass);


--
-- Name: sys_user_role id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_role ALTER COLUMN id SET DEFAULT nextval('sys_user_role_id_seq'::regclass);


--
-- Name: sys_version_app id; Type: DEFAULT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_version_app ALTER COLUMN id SET DEFAULT nextval('sys_version_app_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- Name: act_evt_log log_nr_; Type: DEFAULT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_evt_log ALTER COLUMN log_nr_ SET DEFAULT nextval('act_evt_log_log_nr__seq'::regclass);


SET search_path = business, pg_catalog;

--
-- Name: asso_estate_module_type asso_estate_module_type_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY asso_estate_module_type
    ADD CONSTRAINT asso_estate_module_type_pkey PRIMARY KEY (id);


--
-- Name: asso_user_project asso_user_project_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY asso_user_project
    ADD CONSTRAINT asso_user_project_pkey PRIMARY KEY (id);


--
-- Name: obj_barcode_image obj_barcode_image_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_barcode_image
    ADD CONSTRAINT obj_barcode_image_pkey PRIMARY KEY (id);


--
-- Name: obj_corporation obj_corporation_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_corporation
    ADD CONSTRAINT obj_corporation_pkey PRIMARY KEY (id);


--
-- Name: obj_device_push obj_device_push_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_device_push
    ADD CONSTRAINT obj_device_push_pkey PRIMARY KEY (id);


--
-- Name: obj_estate_bak obj_estate_bak_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_bak
    ADD CONSTRAINT obj_estate_bak_pkey PRIMARY KEY (id);


--
-- Name: obj_estate obj_estate_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate
    ADD CONSTRAINT obj_estate_pkey PRIMARY KEY (id);


--
-- Name: obj_estate_status obj_estate_status_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_status
    ADD CONSTRAINT obj_estate_status_pkey PRIMARY KEY (id);


--
-- Name: obj_estate_supplier obj_estate_supplier_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_supplier
    ADD CONSTRAINT obj_estate_supplier_pkey PRIMARY KEY (id);


--
-- Name: obj_estate_type obj_estate_type_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_estate_type
    ADD CONSTRAINT obj_estate_type_pkey PRIMARY KEY (id);


--
-- Name: obj_file obj_file_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_file
    ADD CONSTRAINT obj_file_pkey PRIMARY KEY (id);


--
-- Name: obj_inventory_check_record_detail obj_inventory_check_record_detail_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_check_record_detail
    ADD CONSTRAINT obj_inventory_check_record_detail_pkey PRIMARY KEY (id);


--
-- Name: obj_inventory_check_record obj_inventory_check_record_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_check_record
    ADD CONSTRAINT obj_inventory_check_record_pkey PRIMARY KEY (id);


--
-- Name: obj_inventory_record_detail obj_inventory_record_detail_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_record_detail
    ADD CONSTRAINT obj_inventory_record_detail_pkey PRIMARY KEY (id);


--
-- Name: obj_inventory_record obj_inventory_record_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_inventory_record
    ADD CONSTRAINT obj_inventory_record_pkey PRIMARY KEY (id);


--
-- Name: obj_station_bak obj_station_bak_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_station_bak
    ADD CONSTRAINT obj_station_bak_pkey PRIMARY KEY (id);


--
-- Name: obj_station obj_station_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_station
    ADD CONSTRAINT obj_station_pkey PRIMARY KEY (id);


--
-- Name: obj_stock obj_stock_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock
    ADD CONSTRAINT obj_stock_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_record_personal_history obj_stock_record_personal_history_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record_personal_history
    ADD CONSTRAINT obj_stock_record_personal_history_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_record_personal obj_stock_record_personal_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record_personal
    ADD CONSTRAINT obj_stock_record_personal_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_record obj_stock_record_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_record
    ADD CONSTRAINT obj_stock_record_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_work_order_detail obj_stock_work_order_detail_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_detail
    ADD CONSTRAINT obj_stock_work_order_detail_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_work_order_history obj_stock_work_order_history_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_history
    ADD CONSTRAINT obj_stock_work_order_history_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_work_order obj_stock_work_order_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order
    ADD CONSTRAINT obj_stock_work_order_pkey PRIMARY KEY (id);


--
-- Name: obj_stock_work_order_resource obj_stock_work_order_resource_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_stock_work_order_resource
    ADD CONSTRAINT obj_stock_work_order_resource_pkey PRIMARY KEY (id);


--
-- Name: obj_work_order_bad_component obj_work_order_bad_component_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_bad_component
    ADD CONSTRAINT obj_work_order_bad_component_pkey PRIMARY KEY (id);


--
-- Name: obj_work_order obj_work_order_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order
    ADD CONSTRAINT obj_work_order_pkey PRIMARY KEY (id);


--
-- Name: obj_work_order_resource obj_work_order_resource_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_resource
    ADD CONSTRAINT obj_work_order_resource_pkey PRIMARY KEY (id);


--
-- Name: obj_work_order_operation obj_workorder_operation_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY obj_work_order_operation
    ADD CONSTRAINT obj_workorder_operation_pkey PRIMARY KEY (id);


--
-- Name: ref_stock_work_order_type ref_stock_work_order_type_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_stock_work_order_type
    ADD CONSTRAINT ref_stock_work_order_type_pkey PRIMARY KEY (id);


--
-- Name: ref_tock_work_order_status ref_tock_work_order_status_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_tock_work_order_status
    ADD CONSTRAINT ref_tock_work_order_status_pkey PRIMARY KEY (id);


--
-- Name: ref_work_order_status ref_work_order_status_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_work_order_status
    ADD CONSTRAINT ref_work_order_status_pkey PRIMARY KEY (id);


--
-- Name: ref_work_order_type ref_work_order_type_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY ref_work_order_type
    ADD CONSTRAINT ref_work_order_type_pkey PRIMARY KEY (id);


--
-- Name: sys_message sys_message_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_message
    ADD CONSTRAINT sys_message_pkey PRIMARY KEY (id);


--
-- Name: sys_message_send_detail sys_message_send_detail_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_message_send_detail
    ADD CONSTRAINT sys_message_send_detail_pkey PRIMARY KEY (id);


--
-- Name: sys_param sys_param_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_param
    ADD CONSTRAINT sys_param_pkey PRIMARY KEY (id);


--
-- Name: sys_project sys_project_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_project
    ADD CONSTRAINT sys_project_pkey PRIMARY KEY (id);


--
-- Name: sys_resource sys_resource_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_resource
    ADD CONSTRAINT sys_resource_pkey PRIMARY KEY (id);


--
-- Name: sys_role sys_role_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_role
    ADD CONSTRAINT sys_role_pkey PRIMARY KEY (id);


--
-- Name: sys_role_resource sys_role_resource_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_role_resource
    ADD CONSTRAINT sys_role_resource_pkey PRIMARY KEY (id);


--
-- Name: sys_user_detail sys_user_detail_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_detail
    ADD CONSTRAINT sys_user_detail_pkey PRIMARY KEY (id);


--
-- Name: sys_user sys_user_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user
    ADD CONSTRAINT sys_user_pkey PRIMARY KEY (id);


--
-- Name: sys_user_point_record sys_user_point_record_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_point_record
    ADD CONSTRAINT sys_user_point_record_pkey PRIMARY KEY (id);


--
-- Name: sys_user_position_record sys_user_position_record_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_position_record
    ADD CONSTRAINT sys_user_position_record_pkey PRIMARY KEY (id);


--
-- Name: sys_user_role sys_user_role_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_user_role
    ADD CONSTRAINT sys_user_role_pkey PRIMARY KEY (id);


--
-- Name: sys_version_app sys_version_app_pkey; Type: CONSTRAINT; Schema: business; Owner: mempb
--

ALTER TABLE ONLY sys_version_app
    ADD CONSTRAINT sys_version_app_pkey PRIMARY KEY (id);


SET search_path = public, pg_catalog;

--
-- Name: act_evt_log act_evt_log_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_evt_log
    ADD CONSTRAINT act_evt_log_pkey PRIMARY KEY (log_nr_);


--
-- Name: act_ge_bytearray act_ge_bytearray_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ge_bytearray
    ADD CONSTRAINT act_ge_bytearray_pkey PRIMARY KEY (id_);


--
-- Name: act_ge_property act_ge_property_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ge_property
    ADD CONSTRAINT act_ge_property_pkey PRIMARY KEY (name_);


--
-- Name: act_hi_actinst act_hi_actinst_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_actinst
    ADD CONSTRAINT act_hi_actinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_attachment act_hi_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_attachment
    ADD CONSTRAINT act_hi_attachment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_comment act_hi_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_comment
    ADD CONSTRAINT act_hi_comment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_detail act_hi_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_detail
    ADD CONSTRAINT act_hi_detail_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_identitylink act_hi_identitylink_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_identitylink
    ADD CONSTRAINT act_hi_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_proc_inst_id__key; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_proc_inst_id__key UNIQUE (proc_inst_id_);


--
-- Name: act_hi_taskinst act_hi_taskinst_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_taskinst
    ADD CONSTRAINT act_hi_taskinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_varinst act_hi_varinst_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_hi_varinst
    ADD CONSTRAINT act_hi_varinst_pkey PRIMARY KEY (id_);


--
-- Name: act_id_group act_id_group_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_group
    ADD CONSTRAINT act_id_group_pkey PRIMARY KEY (id_);


--
-- Name: act_id_info act_id_info_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_info
    ADD CONSTRAINT act_id_info_pkey PRIMARY KEY (id_);


--
-- Name: act_id_membership act_id_membership_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_id_membership_pkey PRIMARY KEY (user_id_, group_id_);


--
-- Name: act_id_user act_id_user_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_user
    ADD CONSTRAINT act_id_user_pkey PRIMARY KEY (id_);


--
-- Name: act_re_deployment act_re_deployment_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_deployment
    ADD CONSTRAINT act_re_deployment_pkey PRIMARY KEY (id_);


--
-- Name: act_re_model act_re_model_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_model
    ADD CONSTRAINT act_re_model_pkey PRIMARY KEY (id_);


--
-- Name: act_re_procdef act_re_procdef_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_procdef
    ADD CONSTRAINT act_re_procdef_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_event_subscr act_ru_event_subscr_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_event_subscr
    ADD CONSTRAINT act_ru_event_subscr_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_execution act_ru_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_execution
    ADD CONSTRAINT act_ru_execution_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_identitylink act_ru_identitylink_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_identitylink
    ADD CONSTRAINT act_ru_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_job act_ru_job_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_job
    ADD CONSTRAINT act_ru_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_task act_ru_task_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_task
    ADD CONSTRAINT act_ru_task_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_variable act_ru_variable_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_variable
    ADD CONSTRAINT act_ru_variable_pkey PRIMARY KEY (id_);


--
-- Name: act_re_procdef act_uniq_procdef; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_procdef
    ADD CONSTRAINT act_uniq_procdef UNIQUE (key_, version_, tenant_id_);


--
-- Name: oauth_client_details oauth_client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);


--
-- Name: act_idx_athrz_procedef; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_athrz_procedef ON act_ru_identitylink USING btree (proc_def_id_);


--
-- Name: act_idx_bytear_depl; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_bytear_depl ON act_ge_bytearray USING btree (deployment_id_);


--
-- Name: act_idx_event_subscr; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_event_subscr ON act_ru_event_subscr USING btree (execution_id_);


--
-- Name: act_idx_event_subscr_config_; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_event_subscr_config_ ON act_ru_event_subscr USING btree (configuration_);


--
-- Name: act_idx_exe_parent; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_exe_parent ON act_ru_execution USING btree (parent_id_);


--
-- Name: act_idx_exe_procdef; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_exe_procdef ON act_ru_execution USING btree (proc_def_id_);


--
-- Name: act_idx_exe_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_exe_procinst ON act_ru_execution USING btree (proc_inst_id_);


--
-- Name: act_idx_exe_super; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_exe_super ON act_ru_execution USING btree (super_exec_);


--
-- Name: act_idx_exec_buskey; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_exec_buskey ON act_ru_execution USING btree (business_key_);


--
-- Name: act_idx_hi_act_inst_end; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_act_inst_end ON act_hi_actinst USING btree (end_time_);


--
-- Name: act_idx_hi_act_inst_exec; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_act_inst_exec ON act_hi_actinst USING btree (execution_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_act_inst_procinst ON act_hi_actinst USING btree (proc_inst_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_start; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_act_inst_start ON act_hi_actinst USING btree (start_time_);


--
-- Name: act_idx_hi_detail_act_inst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_detail_act_inst ON act_hi_detail USING btree (act_inst_id_);


--
-- Name: act_idx_hi_detail_name; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_detail_name ON act_hi_detail USING btree (name_);


--
-- Name: act_idx_hi_detail_proc_inst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_detail_proc_inst ON act_hi_detail USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_detail_task_id; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_detail_task_id ON act_hi_detail USING btree (task_id_);


--
-- Name: act_idx_hi_detail_time; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_detail_time ON act_hi_detail USING btree (time_);


--
-- Name: act_idx_hi_ident_lnk_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_ident_lnk_procinst ON act_hi_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_ident_lnk_task; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_ident_lnk_task ON act_hi_identitylink USING btree (task_id_);


--
-- Name: act_idx_hi_ident_lnk_user; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_ident_lnk_user ON act_hi_identitylink USING btree (user_id_);


--
-- Name: act_idx_hi_pro_i_buskey; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_pro_i_buskey ON act_hi_procinst USING btree (business_key_);


--
-- Name: act_idx_hi_pro_inst_end; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_pro_inst_end ON act_hi_procinst USING btree (end_time_);


--
-- Name: act_idx_hi_procvar_name_type; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_procvar_name_type ON act_hi_varinst USING btree (name_, var_type_);


--
-- Name: act_idx_hi_procvar_proc_inst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_procvar_proc_inst ON act_hi_varinst USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_procvar_task_id; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_hi_procvar_task_id ON act_hi_varinst USING btree (task_id_);


--
-- Name: act_idx_ident_lnk_group; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_ident_lnk_group ON act_ru_identitylink USING btree (group_id_);


--
-- Name: act_idx_ident_lnk_user; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_ident_lnk_user ON act_ru_identitylink USING btree (user_id_);


--
-- Name: act_idx_idl_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_idl_procinst ON act_ru_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_job_exception; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_job_exception ON act_ru_job USING btree (exception_stack_id_);


--
-- Name: act_idx_memb_group; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_memb_group ON act_id_membership USING btree (group_id_);


--
-- Name: act_idx_memb_user; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_memb_user ON act_id_membership USING btree (user_id_);


--
-- Name: act_idx_model_deployment; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_model_deployment ON act_re_model USING btree (deployment_id_);


--
-- Name: act_idx_model_source; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_model_source ON act_re_model USING btree (editor_source_value_id_);


--
-- Name: act_idx_model_source_extra; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_model_source_extra ON act_re_model USING btree (editor_source_extra_value_id_);


--
-- Name: act_idx_task_create; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_task_create ON act_ru_task USING btree (create_time_);


--
-- Name: act_idx_task_exec; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_task_exec ON act_ru_task USING btree (execution_id_);


--
-- Name: act_idx_task_procdef; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_task_procdef ON act_ru_task USING btree (proc_def_id_);


--
-- Name: act_idx_task_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_task_procinst ON act_ru_task USING btree (proc_inst_id_);


--
-- Name: act_idx_tskass_task; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_tskass_task ON act_ru_identitylink USING btree (task_id_);


--
-- Name: act_idx_var_bytearray; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_var_bytearray ON act_ru_variable USING btree (bytearray_id_);


--
-- Name: act_idx_var_exe; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_var_exe ON act_ru_variable USING btree (execution_id_);


--
-- Name: act_idx_var_procinst; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_var_procinst ON act_ru_variable USING btree (proc_inst_id_);


--
-- Name: act_idx_variable_task_id; Type: INDEX; Schema: public; Owner: mempb
--

CREATE INDEX act_idx_variable_task_id ON act_ru_variable USING btree (task_id_);


--
-- Name: act_ru_identitylink act_fk_athrz_procedef; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_identitylink
    ADD CONSTRAINT act_fk_athrz_procedef FOREIGN KEY (proc_def_id_) REFERENCES act_re_procdef(id_);


--
-- Name: act_ge_bytearray act_fk_bytearr_depl; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ge_bytearray
    ADD CONSTRAINT act_fk_bytearr_depl FOREIGN KEY (deployment_id_) REFERENCES act_re_deployment(id_);


--
-- Name: act_ru_event_subscr act_fk_event_exec; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_event_subscr
    ADD CONSTRAINT act_fk_event_exec FOREIGN KEY (execution_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_parent; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_execution
    ADD CONSTRAINT act_fk_exe_parent FOREIGN KEY (parent_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_procdef; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_execution
    ADD CONSTRAINT act_fk_exe_procdef FOREIGN KEY (proc_def_id_) REFERENCES act_re_procdef(id_);


--
-- Name: act_ru_execution act_fk_exe_procinst; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_execution
    ADD CONSTRAINT act_fk_exe_procinst FOREIGN KEY (proc_inst_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_super; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_execution
    ADD CONSTRAINT act_fk_exe_super FOREIGN KEY (super_exec_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_identitylink act_fk_idl_procinst; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_identitylink
    ADD CONSTRAINT act_fk_idl_procinst FOREIGN KEY (proc_inst_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_job act_fk_job_exception; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_job
    ADD CONSTRAINT act_fk_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES act_ge_bytearray(id_);


--
-- Name: act_id_membership act_fk_memb_group; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_fk_memb_group FOREIGN KEY (group_id_) REFERENCES act_id_group(id_);


--
-- Name: act_id_membership act_fk_memb_user; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_fk_memb_user FOREIGN KEY (user_id_) REFERENCES act_id_user(id_);


--
-- Name: act_re_model act_fk_model_deployment; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_model
    ADD CONSTRAINT act_fk_model_deployment FOREIGN KEY (deployment_id_) REFERENCES act_re_deployment(id_);


--
-- Name: act_re_model act_fk_model_source; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_model
    ADD CONSTRAINT act_fk_model_source FOREIGN KEY (editor_source_value_id_) REFERENCES act_ge_bytearray(id_);


--
-- Name: act_re_model act_fk_model_source_extra; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_re_model
    ADD CONSTRAINT act_fk_model_source_extra FOREIGN KEY (editor_source_extra_value_id_) REFERENCES act_ge_bytearray(id_);


--
-- Name: act_ru_task act_fk_task_exe; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_task
    ADD CONSTRAINT act_fk_task_exe FOREIGN KEY (execution_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_task act_fk_task_procdef; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_task
    ADD CONSTRAINT act_fk_task_procdef FOREIGN KEY (proc_def_id_) REFERENCES act_re_procdef(id_);


--
-- Name: act_ru_task act_fk_task_procinst; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_task
    ADD CONSTRAINT act_fk_task_procinst FOREIGN KEY (proc_inst_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_identitylink act_fk_tskass_task; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_identitylink
    ADD CONSTRAINT act_fk_tskass_task FOREIGN KEY (task_id_) REFERENCES act_ru_task(id_);


--
-- Name: act_ru_variable act_fk_var_bytearray; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_variable
    ADD CONSTRAINT act_fk_var_bytearray FOREIGN KEY (bytearray_id_) REFERENCES act_ge_bytearray(id_);


--
-- Name: act_ru_variable act_fk_var_exe; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_variable
    ADD CONSTRAINT act_fk_var_exe FOREIGN KEY (execution_id_) REFERENCES act_ru_execution(id_);


--
-- Name: act_ru_variable act_fk_var_procinst; Type: FK CONSTRAINT; Schema: public; Owner: mempb
--

ALTER TABLE ONLY act_ru_variable
    ADD CONSTRAINT act_fk_var_procinst FOREIGN KEY (proc_inst_id_) REFERENCES act_ru_execution(id_);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

