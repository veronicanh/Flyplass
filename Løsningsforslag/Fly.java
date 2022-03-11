////
// Tilsvarer klassen Node, dobbelt-lenket
//

public class Fly implements Comparable<Fly> {
    private String flyNr;
    private Flyplass fra, til;
    private int avgangstid;

    private Fly foran, etter;

    public Fly(int avgang, String flyNummer, Flyplass fra, Flyplass til) {
        avgangstid = avgang;
        flyNr = flyNummer;
        this.fra = fra;
        this.til = til;
    }

    @Override
    public String toString() {
        String str = "⏱ " + formaterTid(avgangstid) + ":##";
        str += "  ✈️ " + flyNr;
        str += "  📍" + fra.hentSted() + " - " + til.hentSted();
        return str;
    }

    // Sorterer flyene fra foerste til siste avgangstid
    @Override
    public int compareTo(Fly annen) {
        if (avgangstid < annen.avgangstid) {
            return 1;
        } else if (avgangstid > annen.avgangstid) {
            return -1;
        }
        return 0;
    }
    
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // Reglene for compareTo:
    // < 0 (negativt) hvis vaart objekt er "mindre" enn det andre
    // > 0 (positivt) hvis vaart objekt er "stoerre" enn det andre
    // = 0            hvis objektene er "like" store
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


    ////
    // Hentere og settere:

    public Fly hentForan() {
        return foran;
    }

    public Fly hentEtter() {
        return etter;
    }

    public void settForan(Fly foran) {
        this.foran = foran;
    }

    public void settEtter(Fly etter) {
        this.etter = etter;
    }

    public Flyplass hentFra() {
        return fra;
    }

    public String hentTilSted() {
        return til.hentSted();
    }

    // Returnerer en formatert versjon av timen som har to siffer
    // Kun kosmetisk, absolutt ikke noedvendig
    private String formaterTid(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return "" + time;
    }
}