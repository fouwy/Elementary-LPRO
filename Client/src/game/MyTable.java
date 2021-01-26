package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

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
        this.setLayout(new BorderLayout());
        //THE TABLE
        final JTable table=new JTable();
//        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
//
//        for (int column = 0; column < table.getColumnCount(); column++) {
//            TableColumn tableColumn = table.getColumnModel().getColumn(column);
//            int preferredWidth = tableColumn.getMinWidth();
//            int maxWidth = tableColumn.getMaxWidth();
//
//            for (int row = 0; row < table.getRowCount(); row++)
//            {
//                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
//                Component c = table.prepareRenderer(cellRenderer, row, column);
//                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
//                preferredWidth = Math.max(preferredWidth, width);
//
//                //  We've exceeded the maximum width, no need to check other rows
//
//                if (preferredWidth >= maxWidth)
//                {
//                    preferredWidth = maxWidth;
//                    break;
//                }
//            }
//            tableColumn.setPreferredWidth( preferredWidth );
//        }

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
        for(int i=0;i<22;i++) {
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
        model.setValueAt("Weapons:", 7, 0);
        model.setValueAt("Weapon 1", 8, 0);
        model.setValueAt("Weapon 2", 9, 0);
        model.setValueAt("Weapon 3", 10, 0);
        model.setValueAt("Weapon 4", 11, 0);
        model.setValueAt("Weapon 5", 12, 0);
        model.setValueAt("Places:", 13, 0);
        model.setValueAt("Hospital", 14, 0);
        model.setValueAt("Morgue", 15, 0);
        model.setValueAt("Palace", 16, 0);
        model.setValueAt("Pool", 17, 0);
        model.setValueAt("H.O.U.N.D. Labs", 18, 0);
        model.setValueAt("Prison", 19, 0);
        model.setValueAt("Museum", 20, 0);
        model.setValueAt("Magnussen", 21, 0);

        this.add(table);
    }
}



