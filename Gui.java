import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Gui extends JFrame{

    // *******************************************************************************
    // *******************************************************************************
    // Variables
    private static int N = 16;
    private static int GENS_LIMIT = 1;
    private static int millis = 10;
    private static int gen_counter = 1;


    public static JButton[][] tiles;
    private static JButton[] down;
    private static JButton start;
    private static JButton[][] tiles2;
    public static int[][] values;
    public static int[][] values2;
    private static boolean[][] initialization;





    // *******************************************************************************
    // *******************************************************************************
    // Constructor
    public Gui(){
        super("Conway's Game Of Life");
        setLayout(new GridLayout(N+1, N));
        tiles = new JButton[N][N];
        tiles2 = new JButton[N][N];
        down = new JButton[N];
        values = new int[N][N];
        values2 = new int[N][N];
        initialization = new boolean[N][N];

        // 3 Main Variables
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                values[i][j] = 0;
                // JButton b = new JButton("(" + i + ", " + j + ")");
                JButton b = new JButton();
                tiles[i][j] = b;
                initialization[i][j] = true;
                add(b);
                b.setBackground(Color.BLACK);
            }
        }

        // 1 Down Variable
        for(int i=0; i<N; i++){
            JButton b = new JButton("Proceed");
            down[i] = b;
            add(b);
            if(i != 0){
                b.setVisible(false);
                b.setEnabled(false);
            }
            else{
                b.setVisible(true);
                b.setEnabled(true);
                start = b;
            }

        }
    }






    // *******************************************************************************
    // *******************************************************************************
    // Initialize the states by clicking the buttons
    public static void initialize(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                JButton b = tiles[i][j];
                boolean init = initialization[i][j];
                b.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                if(b.isEnabled()){
                                    b.setBackground(Color.RED);
                                    // b.setEnabled(false);                    // Maybe wrong
                                    // values[i][j] = 1;
                                    // b.removeActionListener();
                                }
                            }
                        }
                );
            }
        }

        start.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if(start.isEnabled()){
                            start.setBackground(Color.GREEN);
                            // start.setEnabled(false);
                            // System.out.println("Starting....");
                            updateValues();
                            defineNextGenValues();
                            // String message = "Generation " + gen_counter;
                            // JOptionPane.showMessageDialog(null, message);
                            gen_counter++;
                        }
                    }
                }
        );

    }





    // *******************************************************************************
    // *******************************************************************************
    public static void updateValues(){
        //System.out.println("***********************************");
        //System.out.println("Generation 0");
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(tiles[i][j].getBackground() == Color.RED){
                    values[i][j] = 1;
                }
                else{
                    values[i][j] = 0;
                }
            }
        }
        // showTable();
        delay();
        // System.out.println("Leaving initialize function");
    }




    public static void updateTiles(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(values[i][j] == 1){
                    tiles[i][j].setBackground(Color.RED);
                }
                else{
                    tiles[i][j].setBackground(Color.BLACK);
                }
            }
        }
        showTable();
        delay();
    }







    // *******************************************************************************
    // *******************************************************************************
    public static void showTable(){
        for(int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%d ", values[i][j]);
            }
            System.out.println();
        }
    }


    public static int sum(ArrayList<Integer> list){
        int SUM = 0;
        for(int i=0; i<list.size(); i++){
            SUM += list.get(i);
        }
        return SUM;
    }


    public static void delay(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                JButton b = tiles[i][j];
                if(values[i][j] == 1){
                    b.setBackground(Color.RED);
                }
                else{
                    b.setBackground(Color.BLACK);
                }
            }
        }
        // int millis = 500;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }










    // *******************************************************************************
    // *******************************************************************************
    public static int decide(int state, ArrayList<Integer> statesNear){
        int SUM = sum(statesNear);
        int nextState = -1000;
        if(state == 0){
            // It's a dead cell and the only chance to live is to have 3 alive neighbors
            if(SUM == 3){
                nextState = 1;
            }
            else{
                nextState = 0;
            }
        }

        else{
            // It's an alive cell. The only chances to continue living is to have 2 or 3 neighbors
            if(SUM == 2 || SUM == 3){
                nextState = 1;
            }
            else{
                nextState = 0;
            }
        }
        return nextState;

    }










    // *******************************************************************************
    // *******************************************************************************
    public static void defineNextGenValues(){
        // System.out.println("Entering next gen");
        System.out.println("***********************************");
        System.out.println("Generation " + gen_counter);
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){

                int state = values[i][j];
                int nextState = -1000;

                // Case 1.a - UP and LEFT
                if(i == 0 && j == 0){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[0][1]);
                    statesNear.add(values[1][0]);
                    statesNear.add(values[1][1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 1.b - UP and RIGHT
                else if (i == 0 && j == N-1){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[0][N-2]);
                    statesNear.add(values[1][N-1]);
                    statesNear.add(values[1][N-2]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 1.c - UP and MIDDLE
                else if (i == 0 && (j != 0 || j != N-1)){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[0][j-1]);
                    statesNear.add(values[0][j+1]);
                    statesNear.add(values[1][j-1]);
                    statesNear.add(values[1][j]);
                    statesNear.add(values[1][j+1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 2.a - DOWN and LEFT
                else if (i == N-1 && j == 0){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[N-1][1]);
                    statesNear.add(values[N-2][0]);
                    statesNear.add(values[N-2][1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 2.b - DOWN and RIGHT
                else if (i == N-1 && j == N-1){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[N-1][N-2]);
                    statesNear.add(values[N-2][N-1]);
                    statesNear.add(values[N-2][N-2]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 2.c - DOWN and MIDDLE
                else if (i == N-1 && (j != 0 || j != N-1)){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[N-1][j-1]);
                    statesNear.add(values[N-1][j+1]);
                    statesNear.add(values[N-2][j-1]);
                    statesNear.add(values[N-2][j]);
                    statesNear.add(values[N-2][j+1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 3 - LEFT
                else if (j == 0 && (i != 0 || i != N-1)){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[i-1][0]);
                    statesNear.add(values[i+1][0]);
                    statesNear.add(values[i-1][1]);
                    statesNear.add(values[i][1]);
                    statesNear.add(values[i+1][1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 4 - RIGHT
                else if (j == N-1 && (i != 0 || i != N-1)){
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[i-1][N-1]);
                    statesNear.add(values[i+1][N-1]);
                    statesNear.add(values[i-1][N-2]);
                    statesNear.add(values[i][N-2]);
                    statesNear.add(values[i+1][N-2]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

                // Case 5 MIDDLE
                else{
                    ArrayList<Integer> statesNear = new ArrayList<Integer>();
                    statesNear.add(values[i-1][j-1]);
                    statesNear.add(values[i-1][j]);
                    statesNear.add(values[i-1][j+1]);
                    statesNear.add(values[i][j-1]);
                    statesNear.add(values[i][j+1]);
                    statesNear.add(values[i+1][j-1]);
                    statesNear.add(values[i+1][j]);
                    statesNear.add(values[i+1][j+1]);
                    nextState = decide(state, statesNear);
                    values2[i][j] = nextState;
                }

            } // END OF FOR2

        }   // END OF FOR1
        // Now, I have defined my next generation.
        // The data are in 'values2' and not in 'values', so I have to copy them

        values = Arrays.stream(values2).map(int[]::clone).toArray(int[][]::new);
        updateTiles();
        System.out.println("Generation " + gen_counter);
        System.out.println("***********************************");
    }








    // *******************************************************************************
    // *******************************************************************************
    // Main Function
    public static void main(String[] args){
        Gui gui = new Gui();
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setSize(800, 800);
        gui.setVisible(true);
        initialize();
        // Until the previous line, we have predetermine the initial stage (generation 0)
        // Both tiles and values have now the desired info
        // From now and then, I will work with values array to determine next generations
        showTable();
        delay();

    }
}