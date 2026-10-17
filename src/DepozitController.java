import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepozitController {
    private Depozit model;
    private DepozitView view;

    public DepozitController(Depozit model, DepozitView view) {
        this.model = model;
        this.view = view;

        view.actualizeazaTabel(model.getProduse());

        view.getBtnDelete().addActionListener(new StergeListener());

        view.getTxtCautare().getDocument().addDocumentListener(new CautareListener());

        view.getBtnAdd().addActionListener(new AdaugaListener());

        view.getBtnVinde().addActionListener(new VindeListener());

        view.getBtnAprovizionare().addActionListener(new AprovListener());

        view.getBtnAlerta().addActionListener(new AlertaListener());
    }

    class StergeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int viewRow = view.getTable().getSelectedRow();

            if(viewRow != -1) {
                int modelRow = view.getTable().convertRowIndexToModel(viewRow);
                int id = (int) view.getTableModel().getValueAt(modelRow, 0);
                String numeProdus = (String) view.getTableModel().getValueAt(modelRow, 1);

                int confirm = JOptionPane.showConfirmDialog(view, "Sigur vrei sa stergi produsul: " + numeProdus + " ?");

                if(confirm == JOptionPane.YES_OPTION) {
                    model.eliminaProdusDupaID(id);
                    view.actualizeazaTabel(model.getProduse());
                }
            }else{
                view.showError("Trebuie sa selectezi un produs din tabel!");
            }
        }
    }

    class CautareListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            aplicaFiltru();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            aplicaFiltru();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            aplicaFiltru();
        }

        public  void aplicaFiltru(){
            String text = view.getTxtCautare().getText().trim();

            if(text.isEmpty()){
                view.getSorter().setRowFilter(null);
            }

            try{
                Integer.parseInt(text);

                view.getSorter().setRowFilter(RowFilter.regexFilter("^" + text,0));
            }catch(NumberFormatException e){
                view.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + text,1));

            }
        }
    }

    class AdaugaListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] date = view.afiseazaDialogAdaugare();

            if(date!=null){
                try{
                    String nume = date[0];
                    int stoc = Integer.parseInt(date[1]);
                    int stocMinim = Integer.parseInt(date[2]);
                    int idCat = Integer.parseInt(date[3]);
                    String tip = date[4];

                    Produs p = null;

                    if(tip.equals("Electronic")){
                        int garantie = Integer.parseInt(date[5]);
                        int tensiune = Integer.parseInt(date[6]);
                        p = new Electronic(0,nume,stoc,stocMinim,idCat,garantie,tensiune);
                    }else if(tip.equals("Mobila")){
                        String material = date[7];
                        String dimensiuni = date[8];

                        p = new Mobila(material,dimensiuni,nume,0,idCat,stoc,stocMinim);
                    }else if(tip.equals("Consumabil")){
                        p = new Consumabil(0,nume,stoc,stocMinim,idCat);
                    }

                    if(p!=null){
                        model.adaugaProdus(p);
                        view.actualizeazaTabel(model.getProduse());
                    }
                }catch(NumberFormatException ex){
                    view.showError("Eroare: campurile pt stoc, id categorie, tensiune si garantie trebuie sa fie numere!");
                }


            }
        }
    }

    class VindeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int viewRow = view.getTable().getSelectedRow();

            if(viewRow != -1) {
                int modelRow = view.getTable().convertRowIndexToModel(viewRow);

                int id = (int) view.getTableModel().getValueAt(modelRow,0);
                String nume = (String) view.getTableModel().getValueAt(modelRow,1);

                String raspuns = JOptionPane.showInputDialog(view, "Cate buc?", JOptionPane.QUESTION_MESSAGE);

                if(raspuns!=null && !raspuns.isEmpty()){
                    try{
                        int cantitate = Integer.parseInt(raspuns);

                        if(cantitate<=0){
                            view.showError("Introduceti o cantitate > 0!");
                            return;
                        }

                        model.vindeProdus(id,cantitate);
                        model.incarcaProduse();
                        view.actualizeazaTabel(model.getProduse());

                        JOptionPane.showMessageDialog(view,"Vanzare efectuata cu succes!");
                    }catch(NumberFormatException ex){
                        view.showError("Introduceti un numar intreg valid!");
                    }catch(StocInsuficientException exceptie){
                        view.showError(exceptie.getMessage());
                    }
                }
            }
            else{
                view.showError("Selecteaza un produs pentru a-l vinde!");
            }
        }
    }

    class AprovListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int viewRow = view.getTable().getSelectedRow();

            if(viewRow != -1) {
                int modelRow = view.getTable().convertRowIndexToModel(viewRow);

                int id = (int) view.getTableModel().getValueAt(modelRow,0);
                String nume = (String) view.getTableModel().getValueAt(modelRow,1);

                String raspuns = JOptionPane.showInputDialog(view, "Cate buc?", JOptionPane.QUESTION_MESSAGE);

                if(raspuns!=null && !raspuns.isEmpty()){
                    try{
                        int cantitate = Integer.parseInt(raspuns);

                        if(cantitate<=0){
                            view.showError("Introduceti o cantitate > 0!");
                            return;
                        }

                        model.aprovizionare(id,cantitate);
                        model.incarcaProduse();
                        view.actualizeazaTabel(model.getProduse());

                        JOptionPane.showMessageDialog(view,"Aprovizionare efectuata cu succes!");


                    }catch(NumberFormatException ex){
                        view.showError("Introduceti un numar intreg valid!");
                    }
                }
            }
            else{
                view.showError("Selectati un produs pentru aprovizionare");
            }
        }
    }

    class AlertaListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.toggleAlerta();

            JButton sursa = (JButton)e.getSource();
            if(sursa.getText().equals("Alerta Stoc")){
                sursa.setText("Reseteaza inapoi");
            }else{
                sursa.setText("Alerta Stoc");
            }
        }
    }

}
