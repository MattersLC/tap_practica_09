package practica_09;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Orlando Lucero
 */
public class ModeloTablaPersona extends AbstractTableModel {
    private List <Object []> dato;
    private String encabezado[] = new String[] {
        "No. Iden", "Nombre", "Fecha Nac.", "Sexo" 
    };
    private Class tipos[] = new Class[] {
        String.class, String.class, String.class, String.class
    };
    
    @Override
    public Class getColumnClass(int c) {
        return tipos[c];
    }
    
    @Override
    public int getRowCount() {
        return dato.size();
    }
    
    @Override
    public int getColumnCount() {
        return tipos.length;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        return dato.get(r)[c];
    }
    
    @Override
    public String getColumnName(int col) {
        return encabezado[col];
    }
    
    public void setDatos(List <Object []> d) {
        dato = d;
    }
    
}
