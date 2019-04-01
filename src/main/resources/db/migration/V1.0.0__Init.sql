
-- =================================================================================================
-- Definicion de estructuras
-- =================================================================================================

drop table if exists currency cascade;
drop table if exists ncb_exchange_rate;
drop table if exists cb_exchange_rate;


create table if not exists currency (
	id serial not null constraint currency_pk primary key
	, iso_numeric_code character varying(3) not null
	, iso_string_code character varying(3) not null
	, symbol character varying(5) not null
	, short_description character varying(15) not null
	, description character varying(30) not null
	, is_domestic boolean not null default false
	, is_active boolean not null default true
	, constraint iso_numeric_code_uq unique (iso_numeric_code)
	, constraint iso_string_code_uq unique (iso_string_code)
);

alter table currency OWNER to ${owner};

create table if not exists ncb_exchange_rate (
	id integer not null constraint ncb_exchange_rate_pk primary key
	, currency_id integer not null constraint ncb_exchange_rate_currency_fk references currency (id)
	, exchange_rate_date date not null
	, exchange_rate_amount numeric(18, 4) not null
	, created_on timestamp(3) without time zone not null default (now())
	, constraint ncb_exchange_rate_uq unique (currency_id, exchange_rate_date)
);

alter table ncb_exchange_rate OWNER to ${owner};

create table if not exists cb_exchange_rate (
	id serial not null constraint cb_exchange_rate_pk primary key
	, currency_id integer not null constraint cb_exchange_rate_currency_fk references currency (id)
	, bank character varying(15) not null
	, exchange_rate_date date not null
	, sell numeric(18, 4) not null
	, buy numeric(18, 4) not null
	, is_best_sell_price boolean not null default false
	, is_best_buy_price boolean not null default false
	, created_on timestamp(3) without time zone not null default (now())
	, constraint cb_exchange_rate_uq unique (bank, currency_id, exchange_rate_date)
);

alter table cb_exchange_rate OWNER to ${owner};

