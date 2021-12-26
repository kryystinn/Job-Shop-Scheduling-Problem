package logic.instances;

import java.io.Serializable;

/**
 * Clase Machine que representa una máquina o recurso.
 *
 * @author Cristina Ruiz de Bucesta Crespo
 *
 */
public class Machine implements Serializable {

    private int machineNumber;
    private boolean isBusy;


    /**
     * Constructor de la clase {@link Machine}.
     * Al principio de la planificación, todas las máquinas tienen la propiedad {@link #isBusy} en false.
     *
     * @param id número identificador de la máquina o recurso
     */
    public Machine(int id){
        this.machineNumber = id;
        isBusy = false;
    }

    /**
     * Devuelve el número identificador de la máquina.
     *
     * @return {@link #machineNumber}
     */
    public int getMachineNumber(){
        return this.machineNumber;
    }

    /**
     * Devuelve si la máquina está ocupada o no: es decir, si la máquina está procesando alguna tarea o se encuentra
     * libre en el momento del tiempo en el que se haga la llamada.
     *
     * @return {@link #isBusy}
     */
    public boolean isBusy(){
        return this.isBusy;
    }
}
