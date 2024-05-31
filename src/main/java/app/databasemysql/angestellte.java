package app.databasemysql;

import java.sql.Date;

public class angestellte {
    private final int angNummer;
    private final String vorname;
    private final String nachname;
    private final Date birth;
    private final String geschlecht;
    private final Date hiredate;

    public angestellte(int angNummer, String vorname,String nachname, Date birth, String geschlecht, Date hiredate) {
        this.angNummer = angNummer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.birth = birth;
        this.geschlecht = geschlecht;
        this.hiredate = hiredate;
    }

    public String getVorname() {
        return vorname;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public String getNachname() {
        return nachname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public Date getBirth() {
        return birth;
    }

    public int getAngNummer() {
        return angNummer;
    }
}
