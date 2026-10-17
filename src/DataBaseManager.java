import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private final String url = "jdbc:postgresql://localhost:5432/deposit";
    private final String user = "postgres";
    private final String password = "bellatara2005";

    public List<Produs> incarcaProduse(){
        List<Produs> produse = new ArrayList<>();

        String sql = "SELECT p.*, e.garantie_luni, e.tensiune, m.material, m.dimensiuni " +
                     "FROM produs p " +
                     "LEFT JOIN electronice e ON p.id_produs = e.id_produs " +
                     "LEFT JOIN mobila m ON p.id_produs = m.id_produs " +
                     "ORDER BY p.id_produs ASC";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                int id = rs.getInt("id_produs");
                String nume = rs.getString("nume");
                int idCategorie = rs.getInt("id_categorie");
                int stocMinim =  rs.getInt("stoc_minim");
                int stocCurent =  rs.getInt("stoc_curent");

                if(idCategorie == 1){
                    produse.add(new Electronic(id,nume,stocCurent,stocMinim,idCategorie,rs.getInt("garantie_luni"),rs.getInt("tensiune")));
                }
                else if(idCategorie == 2){
                    produse.add(new Mobila(rs.getString("material"),rs.getString("dimensiuni"),nume,id,idCategorie,stocCurent,stocMinim));
                }
                else{
                    produse.add(new Consumabil(id,nume,stocCurent,stocMinim,idCategorie));
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return produse;
    }

    public void inregistreazaTranzactie(int idProdus, int cantitate, String tip){
        String sql = "INSERT INTO tranzactie (id_produs,tip,cantitate) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProdus);
            pstmt.setString(2, tip.toUpperCase()); // IN sau OUT
            pstmt.setInt(3, cantitate);

            pstmt.executeUpdate();
            System.out.println("Tranzactie inregistrata: " + tip.toUpperCase());

        } catch (SQLException e) {
            System.err.println("Eroare la insert tranzactie: " + e.getMessage());
        }

    }

    public void salveazaProdusNou(Produs p){

        String sql = "INSERT INTO produs (nume,id_categorie,stoc_minim,stoc_curent) VALUES (?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url,user,password)){
            conn.setAutoCommit(false);

            try(PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                pstmt.setString(1,p.getNume());
                pstmt.setInt(2,p.getIDCatgorie());
                pstmt.setInt(3,p.getStocMinim());
                pstmt.setInt(4,p.getStocCurent());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);

                    if(p instanceof Electronic){
                        Electronic e = (Electronic) p;
                        String sqlE = "INSERT INTO electronice (id_produs,garantie_luni,tensiune) VALUES (?,?,?)";

                        try(PreparedStatement pstmtE = conn.prepareStatement(sqlE)){
                            pstmtE.setInt(1,id);
                            pstmtE.setInt(2,e.getGarantieLuni());
                            pstmtE.setInt(3,e.getTensiune());
                            pstmtE.executeUpdate();
                        }
                    }else if (p instanceof Mobila) {
                            Mobila mobila = (Mobila) p;
                            String sqlMobila = "INSERT INTO mobila (id_produs,material,dimansiuni) VALUES (?,?,?)";

                            try (PreparedStatement pstmtM = conn.prepareStatement(sqlMobila)) {
                                pstmtM.setInt(1, id);
                                pstmtM.setString(2, mobila.getMaterial());
                                pstmtM.setString(3, mobila.getDimensiuni());
                                pstmtM.executeUpdate();
                            }
                    }

                }
                conn.commit();
                System.out.println("Produsul a fost salvat cu succes in baza de date");
            }catch(SQLException ex){
                conn.rollback();
                throw ex;
            }

        }catch(SQLException e){
            System.err.println("Eroare la salvarea produsului " + e.getMessage());
        }

    }

    public void stergeProdus(int idProdus){
        String sql = "DELETE FROM produs WHERE id_produs = ?";

        try(Connection conn = DriverManager.getConnection(url,user,password);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,idProdus);
            int rowAffected = pstmt.executeUpdate();

            if(rowAffected > 0){
                System.out.println("Produsul cu ID: " + idProdus + " a fost sters");
            }

        }catch(SQLException e){
            System.err.println("Eroare la stergerea produsului " + e.getMessage());
        }
    }


}
