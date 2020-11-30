public class Move {

package usecases1;
import java.util.Random;
import java.util.Scanner;


    public class Main {

        public static void main(String[] args) {
            char f;
            int xdelta=0;
            int ydelta=0;
            int x=0;
            int y=0;
            int linha=0;
            Scanner s=new Scanner(System.in);
            Object n = null;
            String a;
            String[][] notepad =   {{"Personagem A" , "?"},
                    {"Personagem B" , "?"},
                    {"Personagem C" , "?"},
                    {"Personagem D" , "?"}};


            do {
                //read input
                System.out.println("Qual a funcao a testar? d - lançar o dado; m - mover; c - caderno; q - sair");
                System.out.print("Funçao: ");
                f = s.next().charAt(0);


                switch(f) {

                    case('d'):
                        n=rollTheDice();
                        System.out.print("Número do dado:");
                        System.out.println(n);
                        break;

                    case('m'):
                        n=rollTheDice();
                        System.out.print("Estás na casa [");
                        System.out.print(x);
                        System.out.print("] [");
                        System.out.print(y);
                        System.out.println("]");
                        System.out.print("É possível mover ");
                        System.out.print(n);
                        System.out.println(" casas. Como queres mover em xy?");
                        do {
                            System.out.print("x: ");
                            a=s.next();
                            xdelta = Integer.parseInt(a);
                            System.out.print("y: ");
                            a=s.next();
                            ydelta = Integer.parseInt(a);
                            if((Math.abs(xdelta)+Math.abs(ydelta)>(int)n)||(Math.abs(xdelta)+Math.abs(ydelta)<(int)n)||(Math.abs(xdelta)<1)||(Math.abs(xdelta)>6)||(Math.abs(ydelta)<1)||(Math.abs(ydelta)>6))
                                System.out.println("Valores inválidos, tenta de novo");
                            else break;
                        }while(true);
                        System.out.print("Estás agora na posição [");
                        System.out.print(x=x+xdelta);
                        System.out.print("] [");
                        System.out.print(y=y+ydelta);
                        System.out.println("]");
                        break;

                    case('c'):
                        System.out.println("Caderno");
                        printNotepad(notepad);
                        System.out.println("Qual linha riscar?");
                        System.out.print("Linha: ");
                        a=s.next();
                        linha = Integer.parseInt(a);
                        notepad[linha-1][1] = "X";
                        printNotepad(notepad);



                }
            }while(f != 'q');
            s.close();
        }

        private static void printNotepad(String [][] notepad) {

            // Loop through all rows
            for (int i = 0; i < notepad.length; i++) {
                System.out.println();
                // Loop through all elements of current row
                for (int j = 0; j < notepad[i].length; j++)
                    System.out.print(notepad[i][j] + " ");
            }
            System.out.println();
        }

        private static Object rollTheDice() {
            Random rand = new Random();
            return rand.nextInt(6) + 1;
        }

    }
//((e+d>6)||(e<1)||(e>6)||(d<1)||(d>6))

}
