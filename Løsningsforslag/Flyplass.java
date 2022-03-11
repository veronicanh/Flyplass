////
// Tilsvarer klassen Lenkeliste, prioritetskoe av dobbeltlenkede Fly
//

public class Flyplass {
    private String sted;
    private Fly nesteAvgang = null;
    private Fly sisteAvgang = null;

    public Flyplass(String sted) {
        this.sted = sted;
    }

    public String hentSted() {
        return sted;
    }

    // Returnerer antall fly i koeen
    public int antallFly() {
        int antall = 0;
        Fly midl = nesteAvgang;
        while (midl != null) {
            antall += 1;
            midl = midl.hentEtter();
        }
        return antall;
    }

    // Fly ankommer flyplassen, plasseres paa rett sted i koen
    public void flyAnkomst(Fly nyttFly) {
        // Foerste fly som ankommer
        if (antallFly() == 0) {
            nesteAvgang = nyttFly;
            sisteAvgang = nyttFly;
        }
        // Det nye flyet er nesteAvgang
        else if (skalForan(nyttFly, nesteAvgang)) {
            plasserFremst(nyttFly);
        }
        // Alle andre tilfeller
        else {
            boolean plassert = plasserFly(nyttFly);

            // Det nye flyet er sisteAvgang
            if (! plassert) {
                plasserBakerst(nyttFly);
            }
        }
    }

    // Sjekker om et fly skal fremst i koen
    private boolean skalForan(Fly nyttFly, Fly neste) {
        return (0 < nyttFly.compareTo(neste));
    }

    // Setter et fly fremst i koen
    private void plasserFremst(Fly nyttFly) {
        nesteAvgang.settForan(nyttFly);
        nyttFly.settEtter(nesteAvgang);
        nesteAvgang = nyttFly;
    }

    // Setter et fly bakerst i koen
    private void plasserBakerst(Fly nyttFly) {
        sisteAvgang.settEtter(nyttFly);
        nyttFly.settForan(sisteAvgang);
        sisteAvgang = nyttFly;
    }

    // Plasserer et fly paa rett sted i koen
    private boolean plasserFly(Fly nyttFly) {
        boolean plassert = false;
        Fly midl = nesteAvgang;

        // Bruker 'midl != null' da det siste flyet ogsaa maa sjekkes
        while ((! plassert) && (midl != null)) {
            if (skalForan(nyttFly, midl)) {
                nyttFly.settEtter(midl);
                nyttFly.settForan(midl.hentForan());

                midl.hentForan().settEtter(nyttFly);
                midl.settForan(nyttFly);

                plassert = true;
            }
            midl = midl.hentEtter();
        }
        return plassert;
    }

    // Lar flyet som er nesteAvgang "ta av" fra flyplassen
    public Fly flyAvgang() {
        // Ingen fly igjen
        if (nesteAvgang == null) {
            return null;
        }

        Fly tarAv = nesteAvgang;
        // Kun ett fly igjen
        if (antallFly() == 1) {
            nesteAvgang = null;
            sisteAvgang = null;
        }
        // Alle andre tilfeller
        else {
            nesteAvgang = tarAv.hentEtter();
            nesteAvgang.settForan(null);
        }

        tarAv.settForan(null);
        tarAv.settEtter(null);
        return tarAv;
    }

    @Override
    public String toString() {
        String str = "";
        str += pakkInnLinje("┌─   --== 🛬 \\\\ " + sted.toUpperCase() + " FLYPLASS // 🛫 ==--", "─┐");
        str += pakkInnLinje("│                " + antallFly() + " fly igjen");
        
        // Lister opp alle flyene
        if (antallFly() > 0) {
            str += pakkInnLinje("│");

            Fly midl = nesteAvgang;
            while (midl != null) {
                str += pakkInnLinje("│ " + midl.toString());
                midl = midl.hentEtter();
            }
        }

        str += fyllLinje("└", "─", "┘");
        return str;
    }

    // Brukt for testing av at sisteAvgang og foran-pekerne fungerer
    public Fly[] motsattRekkefoelge() {
        Fly[] alleFly = new Fly[antallFly()];

        int i = 0;
        Fly midl = sisteAvgang;
        while (midl != null) {
            alleFly[i] = midl;
            i += 1;
            midl = midl.hentForan();
        }
        return alleFly;
    }


    ////
    // Metoder for pen utskrift
    // Kun kosmetisk, absolutt ikke noedvendig

    private int linjeLengde = 49;

    // "Pakker inn" linjen med en │ paa hoyre side av linjen
    private String pakkInnLinje(String linje) {
        int lengde = linjeLengde;
        if (linje.contains("✈️")) {
            lengde += 1;
        }
        while (linje.length() < lengde) {
            linje += " ";
        }
        return linje + "│\n";
    }
    
    // "Pakker inn" linjen med et vilkaarlig symbol paa hoyre side av linjen
    private String pakkInnLinje(String linje, String symbol) {
        while ((linje.length() + 1) < linjeLengde) {
            linje += " ";
        }
        return linje + symbol + "\n";
    }

    // Fyller linjen med fyll (─), avslutter med symbol (┘)
    private String fyllLinje(String linje, String fyll, String symbol) {
        while (linje.length() < linjeLengde) {
            linje += fyll;
        }
        return linje + symbol;
    }
}