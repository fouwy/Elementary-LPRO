

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;


public class NotepadGUI extends JPanel {
    private boolean DEBUG = false;
    private JPanel MainPanel;
    private JTable table1;
    private JScrollPane scrollPane;
    private JButton button;


    public NotepadGUI() {


        final JTable table1 = new JTable(new TableModel());
        table1.setPreferredScrollableViewportSize(new Dimension(500, 150));
        table1.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table1);
        add(scrollPane);
    }



    class TableModel extends AbstractTableModel {
        private String[] columns = {"Suspects", "Eliminated"};
        private Object[][] data = {{"Person A", new Boolean(false)},
                {"Person B", new Boolean(false)},
                {"Weapon A", new Boolean(false)},
                {"Weapon B", new Boolean(false)},
                {"Place A", new Boolean(false)},
        };


        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if((rowIndex< data.length)&&(columnIndex<columns.length) ) return data[rowIndex][columnIndex];
            else return 0;
        }



        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 0) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            if((row< data.length)&&(col<columns.length) ) {
                if (DEBUG) {
                    System.out.println("Setting value at " + row + "," + col
                            + " to " + value
                            + " (an instance of "
                            + value.getClass() + ")");
                }

                data[row][col] = value;
                fireTableCellUpdated(row, col);


                if (DEBUG) {
                    System.out.println("New value of data:");
                    printDebugData();
                }
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        NotepadGUI newContentPane = new NotepadGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
