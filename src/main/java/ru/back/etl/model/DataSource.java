package ru.back.etl.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Data
@Table(name="aenaflight_2017_01")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class DataSource {

    @NonNull @Id  protected Long id;
    @Size(max = 64) protected String act_arr_date_time_lt;
     protected String aircraft_name_scheduled;
    @Size(max = 128) protected String arr_apt_name_es;
    @Size(max = 8) protected String arr_apt_code_iata;
    @Size(max = 128) protected String baggage_info;
    @Size(max = 128) protected String carrier_airline_name_en;
    @Size(max = 8) protected String carrier_icao_code;
    @Size(max = 8) protected String carrier_number;
    @Size(max = 64) protected String counter;
    @Size(max = 128) protected String dep_apt_name_es;
    @Size(max = 8) protected String dep_apt_code_iata;
    @Size(max = 64) protected String est_arr_date_time_lt;
    @Size(max = 64) protected String est_dep_date_time_lt;
    @Size(max = 128) protected String flight_airline_name_en;
    @Size(max = 128) protected String flight_airline_name;
    @Size(max = 8) protected String flight_icao_code;
    @Size(max = 8) @Column(name = "flight_number") protected String flightnumber;
    @Size(max = 8) protected String flt_leg_seq_no;
    @Size(max = 128) protected String gate_info;
    @Size(max = 128) protected String lounge_info;
    @Size(max = 32) protected String schd_arr_only_date_lt;
    @Size(max = 32) protected String schd_arr_only_time_lt;
    protected String source_data;
    @Size(max = 128) protected String status_info;
    @Size(max = 128) protected String terminal_info;
    @Size(max = 128) protected String arr_terminal_info;
    protected Long created_at;
    @Size(max = 64) protected String act_dep_date_time_lt;
    @Size(max = 32) protected String schd_dep_only_date_lt;
    @Size(max = 32) protected String schd_dep_only_time_lt;

    public DataSource(CopyDataSource cdf) {
        this.id = cdf.getId();
        this.act_arr_date_time_lt = cdf.getAct_arr_date_time_lt();
        this.aircraft_name_scheduled = cdf.getAircraft_name_scheduled();
        this.arr_apt_name_es = cdf.getArr_apt_name_es();
        this.arr_apt_code_iata = cdf.getArr_apt_code_iata();
        this.baggage_info = cdf.getBaggage_info();
        this.carrier_airline_name_en = cdf.getCarrier_airline_name_en();
        this.carrier_icao_code = cdf.getCarrier_icao_code();
        this.carrier_number = cdf.getCarrier_number();
        this.counter = cdf.getCounter();
        this.dep_apt_name_es = cdf.getDep_apt_name_es();
        this.dep_apt_code_iata = cdf.getDep_apt_code_iata();
        this.est_arr_date_time_lt = cdf.getEst_arr_date_time_lt();
        this.est_dep_date_time_lt = cdf.getEst_dep_date_time_lt();
        this.flight_airline_name_en = cdf.getFlight_airline_name_en();
        this.flight_airline_name = cdf.getFlight_airline_name();
        this.flight_icao_code = cdf.getFlight_icao_code();
        this.flightnumber = cdf.getFlightnumber();
        this.flt_leg_seq_no = cdf.getFlt_leg_seq_no();
        this.gate_info = cdf.getGate_info();
        this.lounge_info = cdf.getLounge_info();
        this.schd_arr_only_date_lt = cdf.getSchd_arr_only_date_lt();
        this.schd_arr_only_time_lt = cdf.getSchd_arr_only_time_lt();
        this.source_data = cdf.getSource_data();
        this.status_info = cdf.getStatus_info();
        this.terminal_info = cdf.getTerminal_info();
        this.arr_terminal_info = cdf.getArr_terminal_info();
        this.created_at = cdf.getCreated_at();
        this.act_dep_date_time_lt = cdf.getAct_dep_date_time_lt();
        this.schd_dep_only_date_lt = cdf.getSchd_dep_only_date_lt();
        this.schd_dep_only_time_lt = cdf.getSchd_dep_only_time_lt();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataSource that = (DataSource) o;
        return Objects.equals(act_arr_date_time_lt, that.act_arr_date_time_lt) &&
                Objects.equals(arr_apt_name_es, that.arr_apt_name_es) &&
                Objects.equals(arr_apt_code_iata, that.arr_apt_code_iata) &&
                Objects.equals(baggage_info, that.baggage_info) &&
                Objects.equals(carrier_airline_name_en, that.carrier_airline_name_en) &&
                Objects.equals(carrier_icao_code, that.carrier_icao_code) &&
                Objects.equals(carrier_number, that.carrier_number) &&
                Objects.equals(counter, that.counter) &&
                Objects.equals(dep_apt_name_es, that.dep_apt_name_es) &&
                Objects.equals(dep_apt_code_iata, that.dep_apt_code_iata) &&
                Objects.equals(est_arr_date_time_lt, that.est_arr_date_time_lt) &&
                Objects.equals(est_dep_date_time_lt, that.est_dep_date_time_lt) &&
                Objects.equals(flight_airline_name_en, that.flight_airline_name_en) &&
                Objects.equals(flight_airline_name, that.flight_airline_name) &&
                Objects.equals(flight_icao_code, that.flight_icao_code) &&
                Objects.equals(flightnumber, that.flightnumber) &&
                Objects.equals(flt_leg_seq_no, that.flt_leg_seq_no) &&
                Objects.equals(gate_info, that.gate_info) &&
                Objects.equals(lounge_info, that.lounge_info) &&
                Objects.equals(schd_arr_only_date_lt, that.schd_arr_only_date_lt) &&
                Objects.equals(schd_arr_only_time_lt, that.schd_arr_only_time_lt) &&
                Objects.equals(status_info, that.status_info) &&
                Objects.equals(terminal_info, that.terminal_info) &&
                Objects.equals(arr_terminal_info, that.arr_terminal_info) &&
                Objects.equals(act_dep_date_time_lt, that.act_dep_date_time_lt) &&
                Objects.equals(schd_dep_only_date_lt, that.schd_dep_only_date_lt) &&
                Objects.equals(schd_dep_only_time_lt, that.schd_dep_only_time_lt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), act_arr_date_time_lt, arr_apt_name_es, arr_apt_code_iata, baggage_info, carrier_airline_name_en, carrier_icao_code, carrier_number, counter, dep_apt_name_es, dep_apt_code_iata, est_arr_date_time_lt, est_dep_date_time_lt, flight_airline_name_en, flight_airline_name, flight_icao_code, flightnumber, flt_leg_seq_no, gate_info, lounge_info, schd_arr_only_date_lt, schd_arr_only_time_lt, status_info, terminal_info, arr_terminal_info, act_dep_date_time_lt, schd_dep_only_date_lt, schd_dep_only_time_lt);
    }
}
