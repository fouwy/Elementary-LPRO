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
                if (column == 1) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);

        model.addColumn("Suspects");
        model.addColumn("Eliminated");

        //THE ROW
        for(int i=0;i<24;i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(false,i,1);

        }
        model.setValueAt("People:", 0, 0);
        model.setValueAt("Person 1", 1, 0);
        model.setValueAt("Person 2", 2, 0);
        model.setValueAt("Person 3", 3, 0);
        model.setValueAt("Person 4", 4, 0);
        model.setValueAt("Person 5", 5, 0);
        model.setValueAt("Person 6", 6, 0);
        model.setValueAt(" ", 7, 0);
        model.setValueAt("Weapons:", 8, 0);
        model.setValueAt("Weapon 1", 9, 0);
        model.setValueAt("Weapon 2", 10, 0);
        model.setValueAt("Weapon 3", 11, 0);
        model.setValueAt("Weapon 4", 12, 0);
        model.setValueAt("Weapon 5", 13, 0);
        model.setValueAt(" ", 14, 0);
        model.setValueAt("Places:", 15, 0);
        model.setValueAt("Place 1", 16, 0);
        model.setValueAt("Place 2", 17, 0);
        model.setValueAt("Place 3", 18, 0);
        model.setValueAt("Place 4", 19, 0);
        model.setValueAt("Place 5", 20, 0);
        model.setValueAt("Place 6", 21, 0);
        model.setValueAt("Place 7", 22, 0);
        model.setValueAt("Place 8", 23, 0);

        this.add(table);
    }
}


