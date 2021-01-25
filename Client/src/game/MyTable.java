package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * A table in a JPanel where the user
 * can select the people, places and weapons
 * they suspect were involved in the crime.
 */
public class MyTable extends JPanel {

    /**
     * Creates a scrollable table with
     * "Suspects" and "Eliminated" columns,
     * and rows for every person, weapon and
     * place in the game.
     */
    public MyTable()
    {
        //THE TABLE
        final JTable table=new JTable();

        //THE MODEL OF OUR TABLE
        DefaultTableModel model=new DefaultTableModel()
        {
            public Class<?> getColumnClass(int column)
            {
                if (column == 0) {
                    return String.class;
                }
                return Boolean.class;
            }
        };
        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Suspects");
        model.addColumn("Player1");
        model.addColumn("Player2");
        model.addColumn("Player3");
        model.addColumn("Player4");
        model.addColumn("Player5");
        model.addColumn("Player6");

        //THE ROW
        for(int i=0;i<24;i++) {
            model.addRow(new Object[0]);
            model.setValueAt(false,i,1);
            model.setValueAt(false,i,2);
            model.setValueAt(false,i,3);
            model.setValueAt(false,i,4);
            model.setValueAt(false,i,5);
            model.setValueAt(false,i,6);
        }

        model.setValueAt("People:", 0, 0);
        model.setValueAt("Sherlock Holmes", 1, 0);
        model.setValueAt("Moriarty", 2, 0);
        model.setValueAt("Mrs Hudson", 3, 0);
        model.setValueAt("Irene Adler", 4, 0);
        model.setValueAt("Enola Holmes", 5, 0);
        model.setValueAt("Mycroft Holmes", 6, 0);
        model.setValueAt(" ", 7, 0);
        model.setValueAt("Weapons:", 8, 0);
        model.setValueAt("Weapon 1", 9, 0);
        model.setValueAt("Weapon 2", 10, 0);
        model.setValueAt("Weapon 3", 11, 0);
        model.setValueAt("Weapon 4", 12, 0);
        model.setValueAt("Weapon 5", 13, 0);
        model.setValueAt(" ", 14, 0);
        model.setValueAt("Places:", 15, 0);
        model.setValueAt("Hospital", 16, 0);
        model.setValueAt("Morgue", 17, 0);
        model.setValueAt("Palace", 18, 0);
        model.setValueAt("Pool", 19, 0);
        model.setValueAt("H.O.U.N.D. Labs", 20, 0);
        model.setValueAt("Prison", 21, 0);
        model.setValueAt("Museum", 22, 0);
        model.setValueAt("Magnussen", 23, 0);

        this.add(table);
    }
}


