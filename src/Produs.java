public abstract class Produs implements Stocabil, Cautabil{
    private int id;
    private String nume;
    private int stoc;
    private int idCategorie;
    private int stocMinim;

    public Produs(int id, String nume, int stoc, int stocMinim,int id_categorie) {
        this.id = id;
        this.nume = nume;
        this.stoc = stoc;
        this.stocMinim = stocMinim;
        this.idCategorie = id_categorie;
    }

    @Override
    public void adaugaStoc(int cantitate) {
        if(cantitate>0)
            stoc += cantitate;
    }

    @Override
    public void extrageStoc(int cantitate) throws StocInsuficientException {
        if(cantitate>stoc)
            throw new StocInsuficientException("Stoc insuficient pentru: " + nume + "Disponibil doar: " + stoc);
        stoc-=cantitate;
    }

    @Override
    public int getStocCurent() {
        return stoc;
    }

    public int getStocMinim() {
        return stocMinim;
    }


    @Override
    public boolean acelasiNume(String nume) {
        if(nume==null) return false;
        return this.nume.toLowerCase().contains(nume.toLowerCase());
    }

    @Override
    public boolean acelasiID(int id) {
        if(id==this.id)return true;
        return false;
    }

    public boolean verificaAlertaStoc(){
        if(stoc<stocMinim)
            return true;
        return false;
    }

    public String getNume() {
        return nume;
    }

    public int getID()
    {
        return id;
    }

    public int getIDCatgorie()
    {
        return idCategorie;
    }

    public abstract String getDetaliiProdus();

}
