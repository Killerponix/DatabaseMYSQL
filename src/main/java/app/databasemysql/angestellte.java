package app.databasemysql;

import java.sql.Date;

public class angestellte {
    private final int nummer;
    private final String vorname;
    private final String name;
    private final Date birth;
    private final String geschlecht;
    private final Date hiredate;

    public angestellte(int nummer, String vorname,String name, Date birth, String geschlecht, Date hiredate) {
        this.nummer = nummer;
        this.vorname = vorname;
        this.name = name;
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

    public Date getBirth() {
        return birth;
    }

    public int getNummer() {
        return nummer;
    }
}
