////
// Klasse for meny-loekke
//

import java.util.ArrayList;

public class Flygeleder {
    private ArrayList<Flyplass> fraFlyplasser = new ArrayList<Flyplass>();
    private Input in = new Input();

    // Starter kjoeringen med noe data
    public void utfoerFlyAnkomster(String[][] ekteFlyData) {
        for (String[] flyData : ekteFlyData) {
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
        }
    }

    public void menyLoekke() {
        printMeny();
        int svar = in.intInput(0, 4, "⤛");

        // Loekke som fortsetter saa lenge brukeren ikke
        // skriver inn '0' eller trykker enter
        while (svar > 0) {
            if (svar == 1) {
                utfoerFlyAnkomst();
            } else if (svar == 2) {
                utfoerFlyAvgang();
            } else if (svar == 3) {
                skrivInfoFlyplass();
            } else if (svar == 4) {
                skrivInfoAlleFlyplasser();
            }

            System.out.println();
            printMeny();
            svar = in.intInput(0, 4, "⤛");
        }

        System.out.println(" Takk for naa!");
        System.out.println();
        in.close();
    }

    // Printer menyen med alternativer som brukeren kan velge mellom
    public void printMeny() {
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│ 1. Nytt fly ankommer flyplass         │");
        System.out.println("│ 2. Et fly har avgang fra flyplass     │");
        System.out.println("│ 3. Skriv ut info om en flyplass       │");
        System.out.println("│ 4. Skriv ut info om alle flyplassene  │");
        System.out.println("│ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ │");
        System.out.println("│ 0. Avslutt                            │");
        System.out.println("└┬──────────────────────────────────────┘");
    }

    // Et nytt fly ankommer en flyplass
    private void utfoerFlyAnkomst() {
        int avgang = in.intInput(0, 23, " Avgangstid (time): ");
        String flyNr = in.stringInput("FlyNr (eks DY9509):").toUpperCase();
        String fraSted = in.stringInput("Ankommer flyplass: ");
        String tilSted = in.stringInput("Skal til flyplass: ");
        System.out.print(" └─");

        Flyplass fra = Hovedprogram.finnFlyplass(in.storForbokstav(fraSted));
        Flyplass til = Hovedprogram.finnFlyplass(in.storForbokstav(tilSted));

        // Fly ankommer flyplassen
        Fly nyttFly = new Fly(avgang, flyNr, fra, til);
        fra.flyAnkomst(nyttFly);
        printFlyAnkomst(fra, nyttFly);
    }

    // Et fly har avgang fra en flyplass
    private void utfoerFlyAvgang() {
        String sted = in.stringInput("Flyplass:");
        System.out.print(" └─");
        Flyplass fra = Hovedprogram.finnFlyplass(in.storForbokstav(sted));
        Fly tarAv = fra.flyAvgang();
        printFlyAvgang(fra, tarAv);
    }

    // Skriv ut info om en flyplass
    private void skrivInfoFlyplass() {
        String sted = in.stringInput("Flyplass:");
        System.out.println(" ▽");
        Flyplass flyplass = Hovedprogram.finnFlyplass(in.storForbokstav(sted));
        System.out.println(flyplass);
    }

    // Skriv ut alle flyplasser
    private void skrivInfoAlleFlyplasser() {
        System.out.println(" ├ Alle flyplasser");
        System.out.println(" ▽");

        for (Flyplass flyplass : fraFlyplasser) {
            System.out.println(flyplass);
        }
    }

    // For aa printe informasjon om flyet som har ankommet
    public void printFlyAnkomst(Flyplass flyplass, Fly fly) {
        System.out.println(" 🛬 Ankomst til " + flyplass.hentSted() + " " + fly.toString());
    }

    // For aa printe informasjon om flyet som akkurat har tatt av
    public void printFlyAvgang(Flyplass flyplass, Fly fly) {
        // Ingen fly som har avgang
        if (fly == null) {
            System.out.println(" 🛫 Alle fly fra " + flyplass.hentSted() + " har tatt av");
        } else {
            System.out.println(" 🛫 Avgang fra " + flyplass.hentSted() + " " + fly.toString());
        }
    }
}