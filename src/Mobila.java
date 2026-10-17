public class Mobila extends Produs{
    private String material;
    private String dimensiuni;

    public Mobila(String material, String dimensiuni, String nume, int id, int idCategorie, int stoc, int stocMinim) {
        super(id,nume,stoc,stocMinim,idCategorie);
        this.material = material;
        this.dimensiuni = dimensiuni;
    }

    public String getMaterial() {
        return material;
    }

    public String getDimensiuni() {
        return dimensiuni;
    }

    @Override
    public String getDetaliiProdus() {
        return "Material: " + material + ", Dimensiuni: " + dimensiuni;
    }
}
