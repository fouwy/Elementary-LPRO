package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

    public class MyTable extends JPanel {

        public MyTable()
        {
            //the form
            setBounds(70,80,600,400);
            this.setLayout(null);
            //ADD SCROLLPANE
            JScrollPane scroll=new JScrollPane();
            scroll.setBounds(70,80,600,400);
            this.add(scroll);

            //THE TABLE
            final JTable table=new JTable();
            scroll.setViewportView(table);

            //THE MODEL OF OUR TABLE
            DefaultTableModel model=new DefaultTableModel()
            {
                public Class<?> getColumnClass(int column)
                {
                    switch(column)
                    {
                        case 0:
                            return String.class;
                        case 1:
                            return Boolean.class;
                        default:
                            return String.class;
                    }
                }
            };
            //ASSIGN THE MODEL TO TABLE
            table.setModel(model);

            model.addColumn("Suspects");
            model.addColumn("Eliminated");

            //THE ROW
            for(int i=0;i<=12;i++)
            {
                model.addRow(new Object[0]);
                model.setValueAt(false,i,1);

            }
            model.setValueAt("People:", 0, 0);
            model.setValueAt("Person A", 1, 0);
            model.setValueAt("Person B", 2, 0);
            model.setValueAt("Person C", 3, 0);
            model.setValueAt(" ", 4, 0);
            model.setValueAt("Weapons:", 5, 0);
            model.setValueAt("Weapon A", 6, 0);
            model.setValueAt("Weapon B", 7, 0);
            model.setValueAt(" ", 8, 0);
            model.setValueAt("Places:", 9, 0);
            model.setValueAt("Place A", 10, 0);
            model.setValueAt("Place B", 11, 0);
        }

    }


