public class Consumabil extends Produs{

    public Consumabil(int id, String nume, int stoc, int stocMinim, int idCategorie)
    {
        super(id,nume,stoc,stocMinim,idCategorie);
    }

    @Override
    public String getDetaliiProdus() {
        return "Atricol de consum general";
    }
}
