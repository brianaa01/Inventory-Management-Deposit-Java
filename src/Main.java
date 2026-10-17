import java.util.List;

public class Main {
    public static void main(String[] args) {

        Depozit depozit = new Depozit();

        depozit.incarcaProduse();

        //System.out.println("--- STOC INITIAL ---");
        //afiseazaProduse(depozit.getProduse());

        //System.out.println("\n--- EFECTUARE APROVIZIONARE ---");
        //depozit.aprovizionare(1, 5);

        //System.out.println("\n--- ADAUGARE PRODUS NOU ---");
        //Electronic tabletaNoua = new Electronic(0, "iPad Air 6", 15, 3, 1, 24, 220);
        //depozit.adaugaProdus(tabletaNoua);

        //System.out.println("--- STERGERE PRODUS ---");
        //depozit.eliminaProdusDupaID(27);

        //System.out.println("\n--- STOC FINAL (Dupa operatiuni) ---");
        //afiseazaProduse(depozit.getProduse());

        DepozitView view = new DepozitView();

        new DepozitController(depozit, view);

       //view.actualizeazaTabel(depozit.getProduse());
        view.setVisible(true);
    }

    /*
    public static void afiseazaProduse(List<Produs> lista) {
        if(lista.isEmpty()) {
            System.out.println("Nu exista produse.");
        } else {
            for(Produs p : lista) {
                System.out.printf("ID: %-3d | %-25s | Stoc: %-3d | Tip: %s%n",
                        p.getID(), p.getNume(), p.getStocCurent(), p.getClass().getSimpleName());
            }
        }
    }
    */
}