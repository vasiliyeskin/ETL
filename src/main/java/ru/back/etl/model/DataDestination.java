package ru.back.etl.model;

import lombok.*;
import ru.back.etl.Utils.DataSourceUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.Date;

@Entity
@Data
@Table(name = "aenaflight_source")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class DataDestination {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Long id;
    @NonNull
    @Size(max = 8)
    protected String adep;
    @NonNull
    @Size(max = 8)
    protected String ades;
    @NonNull
    @Size(max = 8)
    protected String flight_code;
    @NonNull
    @Size(max = 8)
    protected String flight_number;
    @Size(max = 8)
    protected String carrier_code;
    @Size(max = 8)
    protected String carrier_number;
    @NonNull
    @Size(max = 256)
    protected String status_info;
    @NonNull
    protected Date schd_dep_lt;
    @NonNull
    protected Date schd_arr_lt;
    protected Date est_dep_lt;
    protected Date est_arr_lt;
    protected Date act_dep_lt;
    protected Date act_arr_lt;
    @NonNull
    protected Integer flt_leg_seq_no;
    protected String aircraft_name_scheduled;
    @Size(max = 128)
    protected String baggage_info;
    @Size(max = 128)
    protected String counter;
    @Size(max = 128)
    protected String gate_info;
    @Size(max = 128)
    protected String lounge_info;
    @Size(max = 128)
    protected String terminal_info;
    @Size(max = 128)
    protected String arr_terminal_info;
    protected String source_data;
    @NotNull
    protected Date created_at;


    public DataDestination(CopyDataSource cds) {
        this.adep = cds.getDep_apt_code_iata();
        this.ades = cds.getArr_apt_code_iata();
        this.flight_code = cds.getFlight_icao_code();
        this.flight_number = cds.getFlightnumber();
        this.carrier_code = cds.getCarrier_icao_code();
        this.carrier_number = cds.getCarrier_number();
        this.status_info = cds.getStatus_info();

        try {
            this.schd_dep_lt = DataSourceUtil.dateFormat.parse((cds.getSchd_dep_only_date_lt() + " " + cds.getSchd_dep_only_time_lt()));
            this.schd_arr_lt = DataSourceUtil.dateFormat.parse((cds.getSchd_arr_only_date_lt() + " " + cds.getSchd_arr_only_time_lt()));
        } catch (ParseException e) {
        }

        try {
            if (cds.getEst_dep_date_time_lt().length() > 1)
                this.est_dep_lt = DataSourceUtil.dateFormat.parse(cds.getEst_dep_date_time_lt());
            if (cds.getEst_arr_date_time_lt().length() > 1)
                this.est_arr_lt = DataSourceUtil.dateFormat.parse(cds.getEst_arr_date_time_lt());
            if (cds.getAct_dep_date_time_lt().length() > 1)
                this.act_dep_lt = DataSourceUtil.dateFormat.parse(cds.getAct_dep_date_time_lt());
            if (cds.getAct_arr_date_time_lt().length() > 1)
                this.act_arr_lt = DataSourceUtil.dateFormat.parse(cds.getAct_arr_date_time_lt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.flt_leg_seq_no = Integer.parseInt(cds.getFlt_leg_seq_no());
        this.aircraft_name_scheduled = cds.getAircraft_name_scheduled();
        this.baggage_info = cds.getBaggage_info();
        this.counter = cds.getCounter();
        this.gate_info = cds.getGate_info();
        this.lounge_info = cds.getLounge_info();
        this.terminal_info = cds.getTerminal_info();
        this.arr_terminal_info = cds.getArr_terminal_info();
        this.created_at = new Date(cds.getCreated_at());

    }
}
