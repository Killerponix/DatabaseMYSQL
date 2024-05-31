package app.databasemysql;

import java.sql.Date;

public class gehalt {
    private final int nummer;
    private final int gehalt;
    private final Date from_date;
    private final Date to_date;

    public gehalt(int nummer, int gehalt, Date from_date, Date to_date) {
        this.nummer = nummer;
        this.gehalt = gehalt;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getNummer() {
        return nummer;
    }

    public int getGehalt() {
        return gehalt;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public Date getTo_date() {
        return to_date;
    }
}
