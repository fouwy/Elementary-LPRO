package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

    public class MyTable extends JPanel {

        public MyTable()
        {
            //the form
            setBounds(100,0,300,1000);
            this.setLayout(null);
            //ADD SCROLLPANE
            JScrollPane scroll=new JScrollPane();
            scroll.setBounds(380,0,200,1000);
            scroll.setVisible(true);
            scroll.setFocusable(true);
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


            scroll.setBounds(380,0,200,407);
            scroll.setBounds(380,0,200,407);
            this.add(scroll);
        }



    }


