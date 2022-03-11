////
// Verktoey-klasse som brukes for aa hente input fra bruker
// Fungerer ganske likt som den vanlige Scanner-klassen
// Metoder for aa hente String- og int-input
//

import java.util.Scanner;
import java.lang.IndexOutOfBoundsException;
import java.lang.NumberFormatException;

public class Input {
    private Scanner sc;

    public Input() {
        sc = new Scanner(System.in);
    }

    public String nextLine() {
        return sc.nextLine();
    }

    public void close() {
        sc.close();
    }


    // Returnerer et String-input, printer en beskrivelse foerst
    public String stringInput(String hva) {
        System.out.print(" ├ " + hva + " ");
        sc = new Scanner(System.in);
        return sc.nextLine();
    }

    // Entrer teksten til aa ha stor forbokstav
    public String storForbokstav(String str) {
        try {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        } catch (IndexOutOfBoundsException e) {
            return str;
        }
    }

    // Returnerer et int-input, printer en beskrivelse foerst
    public int intInput(int fra, int til, String hva) {
        System.out.print(" ├" + hva + " ");
        return intInput(fra, til);
    }

    // Returnerer en int i spennet FraOgMed 'fra', TilOgMed 'til'
    public int intInput(int fra, int til) {
        sc = new Scanner(System.in);
        String str = sc.nextLine();

        // Avslutter loekken dersom brukeren trykker enter
        if (str.equals("")) {
            return -1;
        }

        int svar;
        try {
            // Proever aa konvertere teksten til et tall -> ...
            svar = Integer.parseInt(str);

            // Sjekker at tallet er innenfor de oppgitte grensene
            if (svar < fra || svar > til) {
                System.out.println(" ├ ERROR! Skriv inn et tall mellom " +
                        fra + " og " + til);
                svar = intInput(fra, til, "⤛");
            }
        } catch (NumberFormatException e) {
            // -> ... dersom det ikke gaar saa har brukeren skrev inn en tekst
            System.out.println(" ├ ERROR! Skriv inn et tall");
            svar = intInput(fra, til, "⤛");
        }
        return svar;
    }
}
