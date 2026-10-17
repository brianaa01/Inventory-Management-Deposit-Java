import java.util.ArrayList;
import java.util.List;

public class Depozit {
    private List<Produs> produse;
    private DataBaseManager db;

    public Depozit(){
        produse = new ArrayList<>();
        db = new DataBaseManager();
    }

    public void incarcaProduse(){
        this.produse = db.incarcaProduse();
    }

    public void setProduse(List<Produs> produse){
        this.produse = produse;
    }

    public void adaugaProdus(Produs produs){
        db.salveazaProdusNou(produs);
        incarcaProduse();
    }

    public void eliminaProdus(Produs produs) {
        if(produs!=null){
            eliminaProdusDupaID(produs.getID());
        }
    }

    public void eliminaProdusDupaID(int id){
        Produs p = cautaDupaID(id);
        if(p!=null){
            produse.remove(p);

            db.stergeProdus(id);
        }else {
            System.out.println("Produsul cu ID:" + id + " nu a fost gasit in lista produselor");
        }
    }

    public List<Produs> cautaDupaNume(String nume){
        List<Produs> rezultat = new ArrayList<>();
        for(Produs prod : produse){
            if(prod.acelasiNume(nume)){
                rezultat.add(prod);
            }
        }

        return rezultat;
    }

    public Produs cautaDupaID(int id){
        for(Produs prod : produse){
            if(prod.acelasiID(id))
                return prod;
        }

        return null;
    }

    public List<Produs> getProduseAlerta() {
        List<Produs> rezultat = new ArrayList<>();
        for(Produs prod : produse){
            if(prod.verificaAlertaStoc()){
                rezultat.add(prod);
            }
        }
        return rezultat;
    }

    public List<Produs> getProduse(){
        return new ArrayList<>(produse);
    }

    public void vindeProdus(int id,int cantitate) throws StocInsuficientException{
        Produs p = cautaDupaID(id);
        if(p!=null){
            if(p.getStocCurent()<cantitate){
                throw new StocInsuficientException("Stoc insuficient! Disponibil:" + p.getStocCurent() + " buc");
            }

            db.inregistreazaTranzactie(id,cantitate,"OUT");
            p.extrageStoc(cantitate);
        }
    }

    public void aprovizionare(int id, int cantitate){
        Produs p = cautaDupaID(id);
        if(p!=null){
            p.adaugaStoc(cantitate);

            db.inregistreazaTranzactie(id,cantitate,"IN");

            System.out.println("Aprovizionare efectuata: produs: " + p.getNume() + " +" + cantitate + " buc");
        }
    }


}
