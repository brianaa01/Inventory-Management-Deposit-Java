public interface Stocabil {
    void adaugaStoc(int cantitate);
    void extrageStoc(int cantitate) throws StocInsuficientException;
    int getStocCurent();
}
