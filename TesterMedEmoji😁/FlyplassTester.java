////
// Integrasjonstest for Flyplass-klassen
//
// Versjon uten emojier:
// https://github.com/veronicanh/Flyplass/tree/main/tester%20uten%20emojier
//

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class FlyplassTester {
    private ArrayList<Flyplass> fraFlyplasser = new ArrayList<Flyplass>();
    private Flyplass OSL = Hovedprogram.finnFlyplass("Oslo");
    private Flyplass ARN = Hovedprogram.finnFlyplass("Stockholm");

    private ArrayList<Fly> osloFly = new ArrayList<Fly>();
    
    // Lar flyene i ekteDlyData lande ved flyplasser
    public void utfoerFlyAnkomster(String[][] ekteFlyData) {
        for (String[] flyData : ekteFlyData) {
            laFlyLande(flyData);
        }
    }

    // Leser inn Fly fra fil og legger dem til paa Flyplassen
    public void lesFlyFraFil(String filnavn) throws FileNotFoundException {
        System.out.println();
        Scanner fil = new Scanner(new File(filnavn));

        System.out.println("Ankomster fra filen '" + filnavn + "'");
        while (fil.hasNextLine()) {
            String[] linje = fil.nextLine().split(",");
            laFlyLande(linje);
        }
        fil.close();
    }

    // Hjelpemetode som haandterer fly-akomster for baade
    // utfoerFlyAnkomster og lesFlyFraFil
    private void laFlyLande(String[] flyData) {
        int avgang = Integer.parseInt(flyData[0]);
        String flyNr = flyData[1];
        Flyplass fra = Hovedprogram.finnFlyplass(flyData[2]);
        Flyplass til = Hovedprogram.finnFlyplass(flyData[3]);

        // Legger til flyplassen i fra-listen dersom den ikke er der fra foer
        if (! (fraFlyplasser.contains(fra))) {
            fraFlyplasser.add(fra);
        }

        Fly nyttFly = new Fly(avgang, flyNr, fra, til);
        fra.flyAnkomst(nyttFly);
        printFlyAnkomst(fra, nyttFly);

        // Legger til flyet i osloFly dersom det drar fra Oslo
        if (fra.hentSted().equals(OSL.hentSted())) {
            osloFly.add(nyttFly);
        }
    }

    // For aa printe informasjon om flyet som har ankommet
    public void printFlyAnkomst(Flyplass flyplass, Fly fly) {
        System.out.println(" 🛬 Ankomst til " + flyplass.hentSted() + " " + fly.toString());
    }

    // Sjekk om Fly-ankomsten gikk bra ved aa printe Flyplassene
    public void skrivInfoFlyplasser() {
        System.out.println("Skiver ut info om alle flyplasser");
        for (Flyplass flyplass : fraFlyplasser) {
            System.out.println(flyplass + "\n");
        }
    }

    // Lar alle flyene ta av
    public void utfoerAvganger() {
        System.out.println("Avganger");
        System.out.println("Lar 2 fly ta av fra Oslo");
        for (int i = 0; i < 2; i++) {
            Fly fly = OSL.flyAvgang();
            printFlyAvgang(OSL, fly);
        }

        System.out.println("Lar alle fly ta av fra Stockholm");
        for (int i = 0; i < ARN.antallFly(); i++) {
            Fly fly = ARN.flyAvgang();
            printFlyAvgang(ARN, fly);
        }
        System.out.println();

        // Sjekker pekerne midt i avgangene
        utfoerPekerTest();

        System.out.println("Flere avganger");
        Fly fly = OSL.flyAvgang();
        printFlyAvgang(OSL, fly);

        while (fly != null) {
            fly = OSL.flyAvgang();
            printFlyAvgang(OSL, fly);
        }
        System.out.println("\n");
    }

    // For aa printe informasjon om flyet som akkurat har tatt av
    public void printFlyAvgang(Flyplass flyplass, Fly fly) {
        if (fly == null) {
            System.out.println(" 🛫 Alle fly fra " + flyplass.hentSted() + " har tatt av");
        } else {
            System.out.println(" 🛫 Avgang fra " + flyplass.hentSted() + " " + fly.toString());
        }
    }

    // Sjekker at sisteAvgang- og foran-pekerne i Fly og Flyplass er riktige
    private void utfoerPekerTest() {
        System.out.println("Test av sisteAvgang og foran-pekere");
        System.out.println(" ~ Sjekker at foran-pekerne er riktige ved aa");
        System.out.println(" ~ iterere gjennom koen i motsatt rekkefoelge");
        System.out.println(" ~ ");
        Fly[] baklengs = OSL.motsattRekkefoelge();
        for (int i = 0; i < baklengs.length; i++) {
            System.out.println(" ~ i:" + (baklengs.length - i - 1) + "  " + baklengs[i]);
        }
        System.out.println();
    }

    ////
    // Oppgave 2: Datastrukturtegning
    // Tegn datastrukturen slik den ser ut etter at denne metoden har kjoert
    public void tegneOppgave() {
        System.out.println("Tegne-oppgave");
        System.out.println(" Tegn strukturen til Oslo Flyplass slik den ");
        System.out.println(" ser ut etter at koden under har kjoert\n");

        // Lar tre Fly ankomme den tomme OSL-flyplassen
        // ArrayListen osloFly ser slik ut:
        // [Test, Kirkenes, Tromsoe, Kobenhavn, Verona, Stockholm, London]
        
        for (int i = 1; i < 4; i++) {
            Fly fly = osloFly.get(i);
            OSL.flyAnkomst(fly);
        }
        System.out.println(OSL);
        System.out.println("\n");
    }
}
