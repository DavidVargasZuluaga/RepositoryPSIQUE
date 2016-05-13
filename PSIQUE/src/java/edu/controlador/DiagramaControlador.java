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
    
    private List<Familiar> todosFamiliares;
    private List<Familiar> familiares;
    
    @PostConstruct
    public void init(){
        familiares = new ArrayList();
        todosFamiliares = familiarFacade.findAll();
    }
    
    public void llenarFamiliares(Aprendiz a){
        for (int i = 0; i < todosFamiliares.size(); i++) {
            if(todosFamiliares.get(i).getIdAprendiz().equals(a)){
                familiares.add(todosFamiliares.get(i));
            }
        }
    }
    
    
}
