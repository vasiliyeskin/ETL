package ru.back.etl.Utils;

import ru.back.etl.model.CopyDataSource;
import ru.back.etl.model.DataDestination;
import ru.back.etl.model.DataSource;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSourceUtil {

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    public static DataSource transformToOne(List<DataSource> sourceList) {

        DataSource aloneSource = new DataSource();
        int startIndex = 0;
        for (DataSource ds: sourceList) {
            if(ds.getSchd_arr_only_date_lt().length() > 1 && ds.getSchd_dep_only_date_lt().length() > 1)
            {
                aloneSource = ds;
                startIndex++;
                break;
            }
        }
        if(aloneSource.getId() == null) return aloneSource;

        StringBuilder baggage_info = new StringBuilder(aloneSource.getBaggage_info());
        StringBuilder counter = new StringBuilder(aloneSource.getCounter());
        StringBuilder gate_info = new StringBuilder(aloneSource.getGate_info());
        StringBuilder lounge_info = new StringBuilder(aloneSource.getLounge_info());
        StringBuilder terminal_info = new StringBuilder(aloneSource.getTerminal_info());
        StringBuilder arr_terminal_info = new StringBuilder(aloneSource.getArr_terminal_info());

        String buf = aloneSource.getBaggage_info();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getBaggage_info())) {
                buf = sourceList.get(i).getBaggage_info();
                if (baggage_info.length() + 1 + buf.length() < 128)
                    baggage_info.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setBaggage_info(baggage_info.toString());

        buf = aloneSource.getCounter();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getCounter())) {
                buf = sourceList.get(i).getCounter();
                if (counter.length() + 1 + buf.length() < 64)
                    counter.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setCounter(counter.toString());

        buf = aloneSource.getGate_info();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getGate_info())) {
                buf = sourceList.get(i).getGate_info();
                if (gate_info.length() + 1 + buf.length() < 128)
                    gate_info.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setGate_info(gate_info.toString());

        buf = aloneSource.getLounge_info();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getLounge_info())) {
                buf = sourceList.get(i).getLounge_info();
                if (lounge_info.length() + 1 + buf.length() < 128)
                    lounge_info.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setLounge_info(lounge_info.toString());

        buf = aloneSource.getTerminal_info();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getTerminal_info())) {
                buf = sourceList.get(i).getTerminal_info();
                if (terminal_info.length() + 1 + buf.length() < 128)
                    terminal_info.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setTerminal_info(terminal_info.toString());

        buf = aloneSource.getArr_terminal_info();
        for (int i = startIndex; i < sourceList.size(); i++) {
            if (!buf.equals(sourceList.get(i).getArr_terminal_info())) {
                buf = sourceList.get(i).getArr_terminal_info();
                if (arr_terminal_info.length() + 1 + buf.length() < 128)
                    arr_terminal_info.insert(0, ',').insert(0, buf);
                else
                    break;
            }
        }
        aloneSource.setArr_terminal_info(arr_terminal_info.toString());

        return aloneSource;
    }

    public static List<DataDestination> convertCopyDataSourcesToDataDestinations(List<CopyDataSource> sourceList)
    {
        List<DataDestination> dataDestinations = new ArrayList<>();
        for (CopyDataSource cds: sourceList) {
            dataDestinations.add(new DataDestination(cds));
        }

        return dataDestinations;
    }

    public static List<DataSource> converCopyDataSourcesToDataSources(List<CopyDataSource> copyDataSources) {
        List<DataSource> sources = new ArrayList<>();
        for (CopyDataSource cds: copyDataSources) {
            sources.add(new DataSource(cds));
        }
        return sources;
    }

    /*private static List<Date> getEstArrDates(List<DataSource> sourceList) {
        List<Date> dateList = new ArrayList<>();
        Date date = new Date();

        for (DataSource dt : sourceList) {
            try {
                date = dateFormat.parse(dt.getEst_arr_date_time_lt());
                dateList.add(date);
            } catch (ParseException e) {
                dateList.add(null);
            }
        }

        return dateList;
    }

    private static int searchLatestValue(List<DataSource> sourceList) {
        int indexLatest = 0;
        Date date = new Date();

        for (DataSource dt : sourceList) {
            if (dt.getEst_arr_date_time_lt().length() > 5) {

            }
        }

        return indexLatest;
    }

    private static void copyActDTtoEstDT(List<DataSource> sourceList) {
        for (DataSource dt : sourceList) {
            if (dt.getEst_arr_date_time_lt().length() < 5) {
                dt.setEst_arr_date_time_lt(dt.getAct_arr_date_time_lt());
            }
        }
    }*/

}
