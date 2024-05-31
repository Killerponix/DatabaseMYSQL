package app.databasemysql;

import java.sql.Date;

public class titel {
    private final int nummer;
    private final String Titel;
    private final Date from_date;
    private final Date to_date;

    public titel(int nummer, String titel, Date from_date, Date to_date) {
        this.nummer = nummer;
        Titel = titel;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public int getNummer() {
        return nummer;
    }

    public Date getTo_date() {
        return to_date;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public String getTitel() {
        return Titel;
    }
}
