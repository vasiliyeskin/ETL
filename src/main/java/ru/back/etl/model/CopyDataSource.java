package ru.back.etl.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "copy_aenaflight_2017_01")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CopyDataSource {

    @NonNull
    @Id
    protected Long id;
    @Size(max = 64)
    protected String act_arr_date_time_lt;
    protected String aircraft_name_scheduled;
    @Size(max = 128)
    protected String arr_apt_name_es;
    @Size(max = 8)
    protected String arr_apt_code_iata;
    @Size(max = 128)
    protected String baggage_info;
    @Size(max = 128)
    protected String carrier_airline_name_en;
    @Size(max = 8)
    protected String carrier_icao_code;
    @Size(max = 8)
    protected String carrier_number;
    @Size(max = 64)
    protected String counter;
    @Size(max = 128)
    protected String dep_apt_name_es;
    @Size(max = 8)
    protected String dep_apt_code_iata;
    @Size(max = 64)
    protected String est_arr_date_time_lt;
    @Size(max = 64)
    protected String est_dep_date_time_lt;
    @Size(max = 128)
    protected String flight_airline_name_en;
    @Size(max = 128)
    protected String flight_airline_name;
    @Size(max = 8)
    protected String flight_icao_code;
    @Size(max = 8)
    @Column(name = "flight_number")
    protected String flightnumber;
    @Size(max = 8)
    protected String flt_leg_seq_no;
    @Size(max = 128)
    protected String gate_info;
    @Size(max = 128)
    protected String lounge_info;
    @Size(max = 32)
    protected String schd_arr_only_date_lt;
    @Size(max = 32)
    protected String schd_arr_only_time_lt;
    protected String source_data;
    @Size(max = 128)
    protected String status_info;
    @Size(max = 128)
    protected String terminal_info;
    @Size(max = 128)
    protected String arr_terminal_info;
    protected Long created_at;
    @Size(max = 64)
    protected String act_dep_date_time_lt;
    @Size(max = 32)
    protected String schd_dep_only_date_lt;
    @Size(max = 32)
    protected String schd_dep_only_time_lt;

    public CopyDataSource(DataSource buf) {
        this.id = buf.getId();
        this.act_arr_date_time_lt = buf.getAct_arr_date_time_lt();
        this.aircraft_name_scheduled = buf.getAircraft_name_scheduled();
        this.arr_apt_name_es = buf.getArr_apt_name_es();
        this.arr_apt_code_iata = buf.getArr_apt_code_iata();
        this.baggage_info = buf.getBaggage_info();
        this.carrier_airline_name_en = buf.getCarrier_airline_name_en();
        this.carrier_icao_code = buf.getCarrier_icao_code();
        this.carrier_number = buf.getCarrier_number();
        this.counter = buf.getCounter();
        this.dep_apt_name_es = buf.getDep_apt_name_es();
        this.dep_apt_code_iata = buf.getDep_apt_code_iata();
        this.est_arr_date_time_lt = buf.getEst_arr_date_time_lt();
        this.est_dep_date_time_lt = buf.getEst_dep_date_time_lt();
        this.flight_airline_name_en = buf.getFlight_airline_name_en();
        this.flight_airline_name = buf.getFlight_airline_name();
        this.flight_icao_code = buf.getFlight_icao_code();
        this.flightnumber = buf.getFlightnumber();
        this.flt_leg_seq_no = buf.getFlt_leg_seq_no();
        this.gate_info = buf.getGate_info();
        this.lounge_info = buf.getLounge_info();
        this.schd_arr_only_date_lt = buf.getSchd_arr_only_date_lt();
        this.schd_arr_only_time_lt = buf.getSchd_arr_only_time_lt();
        this.source_data = buf.getSource_data();
        this.status_info = buf.getStatus_info();
        this.terminal_info = buf.getTerminal_info();
        this.arr_terminal_info = buf.getArr_terminal_info();
        this.created_at = buf.getCreated_at();
        this.act_dep_date_time_lt = buf.getAct_dep_date_time_lt();
        this.schd_dep_only_date_lt = buf.getSchd_dep_only_date_lt();
        this.schd_dep_only_time_lt = buf.getSchd_dep_only_time_lt();
    }

    public boolean isNew() {
        return id == null;
    }
}
