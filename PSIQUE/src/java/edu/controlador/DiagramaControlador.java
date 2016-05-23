package edu.controlador;

import edu.entidad.Aprendiz;
import edu.entidad.Familiar;
import edu.fachada.AprendizFacade;
import edu.fachada.FamiliarFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "diagramaControlador")
@SessionScoped
public class DiagramaControlador implements Serializable {

    @Inject
    private FamiliarFacade familiarFacade;
    
    @Inject
    private AprendizFacade aprendizFacade;
    
    private int cantidadFamiliaresTemp;
    private int conteo;
    private List<Familiar> todosFamiliares;
    private List<Familiar> familiares;
    
    @PostConstruct
    public void init(){
        familiares = new ArrayList();
        todosFamiliares = familiarFacade.findAll();
        cantidadFamiliaresTemp = 0;
        conteo = 0;
    }
    
    public void llenarFamiliares(Aprendiz a){
        for (int i = 0; i < todosFamiliares.size(); i++) {
            if(todosFamiliares.get(i).getIdAprendiz().equals(a)){
                familiares.add(todosFamiliares.get(i));
            }
        }
         cantidadFamiliaresTemp = familiares.size();
    }

    public List<Familiar> getTodosFamiliares() {
        return todosFamiliares;
    }

    public void setTodosFamiliares(List<Familiar> todosFamiliares) {
        this.todosFamiliares = todosFamiliares;
    }

    public List<Familiar> getFamiliares() {
        return familiares;
    }

    public void setFamiliares(List<Familiar> familiares) {
        this.familiares = familiares;
    }

    public int getCantidadFamiliaresTemp() {
        return cantidadFamiliaresTemp;
    }

    public void setCantidadFamiliaresTemp(int cantidadFamiliaresTemp) {
        this.cantidadFamiliaresTemp = cantidadFamiliaresTemp;
    }
    
    
    
    
}
