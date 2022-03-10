public class Flyplass {

    public String hentSted() {return null;}

    // Returnerer antall fly i koeen
    public int antallFly() {return 0;}

    // Fly ankommer flyplassen, plasseres paa rett sted i koen
    public void flyAnkomst(Fly nyttFly) {}

    // Lar flyet som er nesteAvgang "ta av" fra flyplassen
    public Fly flyAvgang() {return null;}

    
    ////
    // Hjelpemetoder:

    // Sjekker om et fly skal fremst i koen
    private boolean skalForan(Fly nyttFly, Fly neste) {return false;}

    // Setter et fly fremst i koen
    private void plasserFremst(Fly nyttFly) {}

    // Setter et fly bakerst i koen
    private void plasserBakerst(Fly nyttFly) {}

    // Plasserer et fly paa rett sted i koen
    private boolean plasserFly(Fly nyttFly) {return false;}



    @Override
    public String toString() {return null;}

    // Brukt for testing av at sisteAvgang og foran-pekerne fungerer
    public Fly[] motsattRekkefoelge() {return null;}
}