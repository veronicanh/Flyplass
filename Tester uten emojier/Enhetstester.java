////
// Enhetstester for Flyplass-klassen
//

public class Enhetstester {
    private int antTester = 0;
    private int antOk = 0;
    private int antFeil = 0;

    private String printTestTittel = "\nTest nr. ";
    private String passertTekst    = " > Ok";
    private String feiletTekst     = " > ERROR!";

    private String[] fasitTil = new String[] {
        "Stockholm",
        "Test",
        "Verona",
        "Kirkenes",
        "London",
        "Kobenhavn",
        "Tromsoe",
    };

    private Flyplass OSL = Hovedprogram.finnFlyplass("Oslo");
    private Flyplass ARN = Hovedprogram.finnFlyplass("Stockholm");

    private Fly[] osloFly = new Fly[fasitTil.length];

    // Leser inn alle fly som har Oslo som fra-Flyplass
    public void lesInnFly(String[][] ekteFlyData) {
        for (int i = 0; i < ekteFlyData.length; i++) {
            String[] flyData = ekteFlyData[i];

            int avgang = Integer.parseInt(flyData[0]);
            String flyNr = flyData[1];
            Flyplass fra = Hovedprogram.finnFlyplass(flyData[2]);
            Flyplass til = Hovedprogram.finnFlyplass(flyData[3]);

            if (fra.hentSted().equals(OSL.hentSted())) {
                osloFly[i] = new Fly(avgang, flyNr, fra, til);
            }
        }
    }

    private void utfoerFlyAnkomsterOslo() {
        for (Fly fly : osloFly) {
            OSL.flyAnkomst(fly);
        }
    }

    public void utfoerTester() {
        // Test nr. 1
        printTestTittel(1, "Sjekker Flyplass.hentSted()");
        // Oslo
        if (! ("Oslo".equals(OSL.hentSted()))) {
            printTestFeilet("OSL.hentSted() ga feil resultat");
            forventetFaktisk("Oslo", OSL.hentSted());
        } else {
            printTestPassert("Oslo");
        }
        // Stockholm
        if (! ("Stockholm".equals(ARN.hentSted()))) {
            printTestFeilet("ARN.hentSted() ga feil resultat");
            forventetFaktisk("Stockholm", ARN.hentSted());
        } else {
            printTestPassert("Stockholm");
        }

        // Test nr. 2
        printTestTittel(2, "Sjekker ankomst til Flyplass");
        OSL.flyAnkomst(osloFly[0]);
        if (OSL.antallFly() != 1) {
            printTestFeilet("Feil antall fly i koen etter ankomst");
            forventetFaktisk("1", (OSL.antallFly() + ""));
        } else {
            printTestPassert("Riktig antall fly");
        }

        // Test nr. 3
        printTestTittel(3, "Sjekker avgang fra Flyplass");
        Fly tattAv = OSL.flyAvgang();
        if (osloFly[0] != tattAv) {
            printTestFeilet("Feil fly returnert etter avgang");
            forventetFaktisk(osloFly[0].toString(), tattAv.toString());
        } else {
            printTestPassert("Riktig returverdi");
        }
        if (OSL.antallFly() != 0) {
            printTestFeilet("Feil antall fly i koen etter avgang");
            forventetFaktisk(osloFly[0].toString(), tattAv.toString());
        } else {
            printTestPassert("Riktig antall fly");
        }

        // Test nr. 4
        printTestTittel(4, "Sjekker avgang fra tom Flyplass");
        Fly nullVerdi = OSL.flyAvgang();
        if (nullVerdi != null) {
            printTestFeilet("Returnerte feil verdi ved avgang fra tom Flyplass");
            forventetFaktisk(null, nullVerdi.toString());
        } else {
            printTestPassert("Riktig returverdi");
        }

        // Test nr. 5
        printTestTittel(5, "Sjekker utskrift av Flyplassen");
        utfoerFlyAnkomsterOslo();
        System.out.println(OSL);
        printTestPassert("Det ser ut som det gikk bra :D");

        // Test nr. 6
        printTestTittel(6, "Sjekker antall Fly i koen etter alle Fly-ankomstene");
        if (OSL.antallFly() != fasitTil.length) {
            printTestFeilet("Feil antall fly i koen etter ankomst");
            forventetFaktisk((fasitTil.length + ""), (OSL.antallFly() + ""));
        } else {
            printTestPassert("Riktig antall fly");
        }

        // Test nr. 7
        printTestTittel(7, "Sjekker rekkefoelgen til Flyene");
        for (int i = 0; i < fasitTil.length; i++) {
            Fly fly = OSL.flyAvgang();
            if (! (fasitTil[i].equals(fly.hentTilSted()))) {
                printTestFeilet("Fant et annet fly enn forventet paa i = " + i);
                forventetFaktisk(fasitTil[i], fly.hentTilSted());
            } else {
                printTestPassert("Fant riktig fly paa i = " + i);
            }
        }

        // Test nr. 8
        printTestTittel(8, "Sjekker antall Fly i koen etter nye Fly-ankomster");
        utfoerFlyAnkomsterOslo();
        if (OSL.antallFly() != fasitTil.length) {
            printTestFeilet("Feil antall fly i koen etter ankomster");
            forventetFaktisk((fasitTil.length + ""), (OSL.antallFly() + ""));
        } else {
            printTestPassert("Riktig antall fly");
        }

        // Test nr. 9
        printTestTittel(9,
            "Sjekker foran-pekerne til Fly ved aa iterere over flyene i\n" +
            "prioritetskoen i motsatt rekkefoelge. Starter med sisteAvgang-\n" +
            "Flyet og foelger foran-pekerne helt til nesteAvgang"
        );
        Fly[] baklengs = OSL.motsattRekkefoelge();
        // Sjekker at flyene ligger i riktig rekkefoelge
        for (int i = 0; i < 3; i++) {
            String forventet = fasitTil[fasitTil.length - i - 1];
            String faktisk = baklengs[i].hentTilSted();
            if (! (forventet.equals(faktisk))) {
                printTestFeilet("Fant et annet fly enn forventet paa i = " + (fasitTil.length - i - 1));
                forventetFaktisk(forventet, faktisk);
            } else {
                printTestPassert("Fant riktig fly paa i = " + (fasitTil.length - i - 1));
            }
        }
        printTestPassert("Det ser ut som det gikk bra :D");
    }

    private void printTestTittel(int nr, String hva) {
        System.out.println(printTestTittel + nr);
        System.out.println(hva);
    }

    private void printTestPassert(String melding) {
        System.out.println(passertTekst + " - " + melding);
        antOk++;
        antTester++;
    }

    private void printTestFeilet(String melding) {
        System.out.println(feiletTekst + " " + melding);
        antFeil++;
        antTester++;
    }

    private void forventetFaktisk(String forventet, String faktisk) {
        System.out.println("   Forventet verdi: '" + forventet + "'");
        System.out.println("   Faktisk verdi:   '" + faktisk + "'");
    }

    public void printResultat() {
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(" RESULTAT");
        System.out.println(String.format("  %2d av %2d tester passerte", antOk, antTester));

        if (antFeil > 0) {
            System.out.println(String.format("  %2d feil", antFeil));
            System.out.println();
            System.out.println("  Proev aa rett opp feilene");
            System.out.println("  og kjoer testene paa nytt");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        } else {
            System.out.println("  Ingen feil!");
            System.out.println("  Bra jobba :D");
            System.out.println(" * * * * * * * * * * * * * *\n");
        }
        System.out.println();
    }

    public boolean passerteAlleTester() {
        return (antFeil == 0);
    }
}