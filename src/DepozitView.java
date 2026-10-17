import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DepozitView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnAdd;
    private JButton btnVinde;
    private JButton btnDelete;
    private JButton btnAlerta;
    private JButton btnAprovizionare;
    private JTextField txtCautare;
    private boolean modAlerta = false;

    public DepozitView() {
        setTitle("Depozit View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        Color albastruInchis = new Color(41, 128, 185);
        Color albastruDeschis = new Color(214, 234, 248);
        Color griDeschisHeader = new Color(242, 242, 242);
        Color culoareButon = new Color(174, 214, 241);

        JPanel baraCautare = new JPanel();
        baraCautare.setBackground(albastruInchis);
        txtCautare = new JTextField(20);
        baraCautare.add(new JLabel("Cautare:"));
        baraCautare.add(txtCautare);
        add(baraCautare, BorderLayout.NORTH);

        String[] coloane = {"ID","Nume","Stoc","Stoc minim","Tip","Detalii"};
        tableModel = new DefaultTableModel(coloane, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel) {
            @Override
            public String getToolTipText(java.awt.event.MouseEvent e) {

                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                if (row > -1 && col > -1) {
                    Object value = getValueAt(row, col);
                    return (value == null) ? null : value.toString();
                }
                return super.getToolTipText(e);
            }
        };

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.setBackground(albastruDeschis);
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(133, 193, 233));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(griDeschisHeader);

        table.setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int modelRow = table.convertRowIndexToModel(row);
                int stoc = (int) table.getModel().getValueAt(modelRow,2);
                int min = (int) table.getModel().getValueAt(modelRow,3);


                if (modAlerta && stoc < min) {
                    c.setForeground(Color.RED);
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(Color.BLACK);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }

                if(isSelected){
                    c.setBackground(new Color(133, 193, 233));
                }
                else{
                    c.setBackground(albastruDeschis);
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel butoane = new JPanel();
        //btnAdd = new JButton("Adauga");
        btnAdd = new JButton("Adauga produs");
        btnVinde = new JButton("Vinde");
        btnDelete = new JButton("Sterge produs");
        btnAlerta = new JButton("Alerta Stoc");
        btnAprovizionare = new JButton("Aprovizionare");

        butoane.add(btnAdd);
        butoane.add(btnDelete);
        butoane.add(btnVinde);
        butoane.add(btnAprovizionare);
        butoane.add(btnAlerta);

        add(butoane, BorderLayout.SOUTH);



    }

    public void actualizeazaTabel(List<Produs> produse){
        tableModel.setRowCount(0);
        for(Produs p : produse){
            Object[] row = {p.getID(),p.getNume(),p.getStocCurent(),p.getStocMinim(),p.getClass().getSimpleName(),p.getDetaliiProdus()};
            tableModel.addRow(row);
        }
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnVinde() {
        return btnVinde;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnAlerta() {
        return btnAlerta;
    }

    public JButton getBtnAprovizionare() {
        return btnAprovizionare;
    }

    public JTextField getTxtCautare() {
        return txtCautare;
    }

    public JTable getTable() {
        return table;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void showError(String mesaj) {
        JOptionPane.showMessageDialog(this, mesaj);
    }

    public String[] afiseazaDialogAdaugare(){
        JTextField numeField = new JTextField(20);
        JTextField stocField = new JTextField(5);
        JTextField stocMinimField = new JTextField(5);
        JTextField idCatField = new JTextField(5);
        JComboBox<String> tip = new JComboBox<>(new String[]{"Electronic","Mobila","Consumabil"});

        JTextField garantieField = new JTextField(5);
        JTextField tensiuneField = new JTextField(5);
        JTextField materialField = new JTextField(10);
        JTextField dimensiuniField = new JTextField(15);

        JPanel panelE =  new JPanel(new GridLayout(2,2));
        panelE.add(new JLabel("Garantie(luni):"));
        panelE.add(garantieField);
        panelE.add(new JLabel("Tensiune:"));
        panelE.add(tensiuneField);

        JPanel panelM = new JPanel(new GridLayout(2,2));
        panelM.add(new JLabel("Material:"));
        panelM.add(materialField);
        panelM.add(new JLabel("Dimensiuni:"));
        panelM.add(dimensiuniField);

        tip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String s = (String) tip.getSelectedItem();

                if(s.equals("Electronic")){
                    idCatField.setText("1");
                    panelE.setVisible(true);
                    panelM.setVisible(false);
                }else if(s.equals("Mobila")){
                    idCatField.setText("2");
                    panelE.setVisible(false);
                    panelM.setVisible(false);
                }else if(s.equals("Consumabil")){
                    idCatField.setText("3");
                    panelE.setVisible(false);
                    panelM.setVisible(false);
                }


                SwingUtilities.getWindowAncestor(tip).pack();
            }
        });

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        main.add(new JLabel("Nume:"));
        main.add(numeField);
        main.add(new JLabel("Stoc curent:"));
        main.add(stocField);
        main.add(new JLabel("Stoc minim:"));
        main.add(stocMinimField);
        main.add(new JLabel("ID categorie:"));
        main.add(idCatField);
        main.add(new JLabel("Tip:"));
        main.add(tip);
        main.add(panelE);
        main.add(panelM);

        int result = JOptionPane.showConfirmDialog(this,main,"Adaugare produs",JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            return new String[]{numeField.getText(),stocField.getText(),stocMinimField.getText(),idCatField.getText(),
                    (String) tip.getSelectedItem(),garantieField.getText(),tensiuneField.getText(),materialField.getText(),dimensiuniField.getText()};
        }

        return null;



    }

    public void toggleAlerta(){
        this.modAlerta = !this.modAlerta;
        table.repaint();
    }

}
