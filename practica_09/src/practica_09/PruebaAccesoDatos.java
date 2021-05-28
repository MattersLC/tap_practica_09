package practica_09;

import javax.swing.JFrame;

/**
 * @author Orlando Lucero
 */
public class PruebaAccesoDatos extends JFrame{
    
    public static void main(String[] args) {
        ManipulaDatos md = new ManipulaDatos();
        md.setVisible(true);
        md.setSize(620, 600);
        md.setDefaultCloseOperation(EXIT_ON_CLOSE);
        md.setLocationRelativeTo(null);
    }
}
