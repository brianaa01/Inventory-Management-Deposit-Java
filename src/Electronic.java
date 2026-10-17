public class Electronic extends Produs{
    private int garantieLuni;
    private int tensiune;

    public Electronic(int id, String nume, int stoc, int stocMinim, int idCategorie, int garantieLuni, int tensiune) {
        super(id, nume, stoc, stocMinim, idCategorie);
        this.garantieLuni = garantieLuni;
        this.tensiune = tensiune;
    }

    public int getGarantieLuni() {
        return garantieLuni;
    }

    public int getTensiune() {
        return tensiune;
    }

    @Override
    public String getDetaliiProdus() {
        return "Garantie luni: " + garantieLuni + ", tensiune: " + tensiune;
    }


}
