////
// Hovedprogram som kjoerer Enhetstester, Integrasjonsstest (dvs. FlyplassTester)
// og til slutt startes meny-loekken i Flygeleder.
//
// Flyplassen ligger i en HashMap der stedsnavnet er noekkelen
// Metoden finnFlyplass(String sted) brukes for aa finne en Flyplass
//
// Versjon uten emojier:
// https://github.com/veronicanh/Flyplass/tree/main/tester%20uten%20emojier
//

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Hovedprogram {
    public static Input in = new Input();

    public static HashMap<String, Flyplass> alleFlyplasser = new HashMap<>();

    public static String[][] ekteFlyData = new String[][] {
        { "10", "T35T01", "Oslo",      "Test" },
        { "17", "SK4478", "Oslo",      "Kirkenes" },
        { "21", "DY3820", "Oslo",      "Tromsoe" },
        { "18", "DY9509", "Oslo",      "Kobenhavn" },
        { "15", "SK4675", "Oslo",      "Verona" },
        { "08", "DY8204", "Oslo",      "Stockholm" },
        { "17", "SK4478", "Oslo",      "London" },
        { "13", "SK4675", "Stockholm", "Alicante" },
    };

    public static void main(String[] args) {
        // Enhetstester
        boolean allePassert = utfoerEnhetstester();

        // Dere kommer ikke videre foer alle tester er passert
        if (! allePassert) {
            return;
        }

        // Integrasjonsstest
        tilbakestillFlyplasser();
        utfoerIntegrasjonstest();

        // Menyloekke
        tilbakestillFlyplasser();
        utfoerMenyloekke();

        in.close();
    }

    private static boolean utfoerEnhetstester() {
        Enhetstester e = new Enhetstester();

        e.lesInnFly(ekteFlyData);
        e.utfoerTester();
        e.printResultat();

        return e.passerteAlleTester();
    }

    // Tester Flyplass-klassen med "ekte" data
    private static void utfoerIntegrasjonstest() {        
        System.out.println(" Trykk enter for aa kjoere integrasjonstesten");
        System.out.println(" Oppgi et filnavn dersom du vil lese inn Fly fra fil");
        System.out.print("  └⤛ ");
        String filnavn = in.nextLine();
        System.out.println("\n");

        FlyplassTester tester = new FlyplassTester();
        tester.utfoerFlyAnkomster(ekteFlyData);
        
        // Dersom brukeren skrev inn noe annet enn enter
        if (! (filnavn.equals(""))) {
            try {
                tester.lesFlyFraFil(filnavn);
            } catch (FileNotFoundException e) {
                System.out.println("ERROR! Fant ikke filen '" + filnavn + "'");
                System.out.println("Fortsetter kjoeringen med kun eksempel-data");
            }
        }
        System.out.println();

        tester.skrivInfoFlyplasser();
        tester.utfoerAvganger();
        tester.tegneOppgave();
    }

    private static void utfoerMenyloekke() {
        System.out.println("Vil du starte menyLoekken fra Flygeleder-klassen? (ja/nei)");
        System.out.print("  └⤛ ");
        String svar = in.nextLine();
        

        if (svar.toLowerCase().equals("ja") || svar.toLowerCase().equals("j")) {
            System.out.println("\nGjoer ferdig oppgave 6 foerst!");
            System.out.println("Om du har gjor den saa kan du kommentere vekk linjen under\n");
            
            // Flygeleder fl = new Flygeleder();
            // fl.utfoerFlyAnkomster(ekteFlyData);
            // fl.menyLoekke();
        }
    }

    // Returnerer Flyplassen paa et gitt sted
    // Dersom stedet ikke finnes i noeklene til HashMap'en fra foer av,
    // saa lages det en ny Flypass, som legges inn og returneres
    public static Flyplass finnFlyplass(String sted) {
        if (!(alleFlyplasser.containsKey(sted))) {
            Flyplass ny = new Flyplass(sted);
            alleFlyplasser.put(sted, ny);
        }
        return alleFlyplasser.get(sted);
    }

    // Fjerner/overskriver alle flyplasser, for aa fjerne alle Fly som har landet
    public static void tilbakestillFlyplasser() {
        for (String sted : alleFlyplasser.keySet()) {
            Flyplass ny = new Flyplass(sted);
            alleFlyplasser.put(sted, ny);
        }
    }
}
