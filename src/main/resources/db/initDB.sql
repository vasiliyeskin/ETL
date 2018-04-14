CREATE SEQUENCE if not exists global_seq  START 100000;
CREATE SEQUENCE if not exists global_seq2 START 200000;

CREATE TABLE IF NOT EXISTS aenaflight_2017_01
(
  id                      BIGSERIAL NOT NULL,
  act_arr_date_time_lt    CHARACTER VARYING(64),
  aircraft_name_scheduled TEXT,
  arr_apt_name_es         CHARACTER VARYING(128),
  arr_apt_code_iata       CHARACTER VARYING(8),
  baggage_info            CHARACTER VARYING(128),
  carrier_airline_name_en CHARACTER VARYING(128),
  carrier_icao_code       CHARACTER VARYING(8),
  carrier_number          CHARACTER VARYING(8),
  counter                 CHARACTER VARYING(64),
  dep_apt_name_es         CHARACTER VARYING(128),
  dep_apt_code_iata       CHARACTER VARYING(8),
  est_arr_date_time_lt    CHARACTER VARYING(64),
  est_dep_date_time_lt    CHARACTER VARYING(64),
  flight_airline_name_en  CHARACTER VARYING(128),
  flight_airline_name     CHARACTER VARYING(128),
  flight_icao_code        CHARACTER VARYING(8),
  flight_number           CHARACTER VARYING(8),
  flt_leg_seq_no          CHARACTER VARYING(8),
  gate_info               CHARACTER VARYING(128),
  lounge_info             CHARACTER VARYING(128),
  schd_arr_only_date_lt   CHARACTER VARYING(32),
  schd_arr_only_time_lt   CHARACTER VARYING(32),
  source_data             TEXT,
  status_info             CHARACTER VARYING(128),
  terminal_info           CHARACTER VARYING(128),
  arr_terminal_info       CHARACTER VARYING(128),
  created_at              BIGINT,
  act_dep_date_time_lt    CHARACTER VARYING(64),
  schd_dep_only_date_lt   CHARACTER VARYING(32),
  schd_dep_only_time_lt   CHARACTER VARYING(32),
  CONSTRAINT pk_aenaflight_2017_01 PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS copy_aenaflight_2017_01
(
  id                      BIGSERIAL NOT NULL,
  act_arr_date_time_lt    CHARACTER VARYING(64),
  aircraft_name_scheduled TEXT,
  arr_apt_name_es         CHARACTER VARYING(128),
  arr_apt_code_iata       CHARACTER VARYING(8),
  baggage_info            CHARACTER VARYING(128),
  carrier_airline_name_en CHARACTER VARYING(128),
  carrier_icao_code       CHARACTER VARYING(8),
  carrier_number          CHARACTER VARYING(8),
  counter                 CHARACTER VARYING(64),
  dep_apt_name_es         CHARACTER VARYING(128),
  dep_apt_code_iata       CHARACTER VARYING(8),
  est_arr_date_time_lt    CHARACTER VARYING(64),
  est_dep_date_time_lt    CHARACTER VARYING(64),
  flight_airline_name_en  CHARACTER VARYING(128),
  flight_airline_name     CHARACTER VARYING(128),
  flight_icao_code        CHARACTER VARYING(8),
  flight_number           CHARACTER VARYING(8),
  flt_leg_seq_no          CHARACTER VARYING(8),
  gate_info               CHARACTER VARYING(128),
  lounge_info             CHARACTER VARYING(128),
  schd_arr_only_date_lt   CHARACTER VARYING(32),
  schd_arr_only_time_lt   CHARACTER VARYING(32),
  source_data             TEXT,
  status_info             CHARACTER VARYING(128),
  terminal_info           CHARACTER VARYING(128),
  arr_terminal_info       CHARACTER VARYING(128),
  created_at              BIGINT,
  act_dep_date_time_lt    CHARACTER VARYING(64),
  schd_dep_only_date_lt   CHARACTER VARYING(32),
  schd_dep_only_time_lt   CHARACTER VARYING(32),
  CONSTRAINT pk_copyaenaflight_2017_01 PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS aenaflight_source
(
  id                      BIGSERIAL                   NOT NULL PRIMARY KEY,
  adep                    CHARACTER VARYING(8)        NOT NULL,
  ades                    CHARACTER VARYING(8)        NOT NULL,
  flight_code             CHARACTER VARYING(8)        NOT NULL,
  flight_number           CHARACTER VARYING(8)        NOT NULL,
  carrier_code            CHARACTER VARYING(8),
  carrier_number          CHARACTER VARYING(8),
  status_info             CHARACTER VARYING(256)      NOT NULL,
  schd_dep_lt             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  schd_arr_lt             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  est_dep_lt              TIMESTAMP WITHOUT TIME ZONE,
  est_arr_lt              TIMESTAMP WITHOUT TIME ZONE,
  act_dep_lt              TIMESTAMP WITHOUT TIME ZONE,
  act_arr_lt              TIMESTAMP WITHOUT TIME ZONE,
  flt_leg_seq_no          INTEGER                     NOT NULL,
  aircraft_name_scheduled TEXT,
  baggage_info            CHARACTER VARYING(128),
  counter                 CHARACTER VARYING(128),
  gate_info               CHARACTER VARYING(128),
  lounge_info             CHARACTER VARYING(128),
  terminal_info           CHARACTER VARYING(128),
  arr_terminal_info       CHARACTER VARYING(128),
  source_data             TEXT,
  created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL
);


CREATE TABLE IF NOT EXISTS bufferedTable
(
  id                      BIGSERIAL                   NOT NULL PRIMARY KEY,
  adep                    CHARACTER VARYING(8)        NOT NULL,
  ades                    CHARACTER VARYING(8)        NOT NULL,
  flight_code             CHARACTER VARYING(8)        NOT NULL,
  flight_number           CHARACTER VARYING(8)        NOT NULL,
  status_info             CHARACTER VARYING(256)      NOT NULL,
  schd_dep_lt             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  schd_arr_lt             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  flt_leg_seq_no          INTEGER                     NOT NULL,
  created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL
);
