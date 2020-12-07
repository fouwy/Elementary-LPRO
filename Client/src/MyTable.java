
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.lang.model.type.NullType;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

    public class MyTable extends JFrame{

        //MAIN METHOD
        public static void main(String[] args)
        {

            EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    //INITIALIZE JFRAME FORM
                    MyTable form=new MyTable();
                    form.setVisible(true);;
                }
            });

        }

        //CONSTRUCTOR
        public MyTable()
        {
            //the form
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(200,200,800,400);
            setTitle("Notepad");
            getContentPane().setLayout(null);

            //ADD SCROLLPANE
            JScrollPane scroll=new JScrollPane();
            scroll.setBounds(70,80,600,200);
            getContentPane().add(scroll);

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


