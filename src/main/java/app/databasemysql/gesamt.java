package app.databasemysql;

import java.sql.Date;

public class gesamt {

    private final int angNummer;
    private final String vorname;
    private final String nachname;
    private final Date birth;
    private final String geschlecht;
    private final Date hiredate;
    private final String titel;
    private final Date tfrom_date;
    private final Date tto_date;
    private final int gehalt;
    private final Date gfrom_date;
    private final Date gto_date;



    public gesamt(int angNummer, String vorname, String nachname, Date birth, String geschlecht, Date hiredate, String titel
            , Date tfrom_date, Date tto_date, int gehalt, Date gfrom_date, Date gto_date) {
        this.angNummer = angNummer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.birth = birth;
        this.geschlecht = geschlecht;
        this.hiredate = hiredate;
        this.titel = titel;
        this.tfrom_date = tfrom_date;
        this.tto_date = tto_date;
        this.gehalt = gehalt;
        this.gfrom_date = gfrom_date;
        this.gto_date = gto_date;
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

    public String getTitel() {
        return titel;
    }

    public Date getTfrom_date() {
        return tfrom_date;
    }

    public Date getTto_date() {
        return tto_date;
    }

    public int getGehalt() {
        return gehalt;
    }

    public Date getGfrom_date() {
        return gfrom_date;
    }

    public Date getGto_date() {
        return gto_date;
    }

}

