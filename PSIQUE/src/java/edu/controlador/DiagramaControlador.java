package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "diagramaControlador")
@SessionScoped
public class DiagramaControlador implements Serializable {

    @Inject
    FamiliarFacade familiarFacade;

    @Inject
    FamiliarrelacionFacade familiarrelacionFacade;

    List<Familiar> padres;
    List<Familiar> hijos;
    List<Familiar> parejas;
    List<Familiar> todosFamiliars;

    private int modalModificado;

    @PostConstruct
    public void init() {
        modalModificado = 0;
        padres = new ArrayList();
        hijos = new ArrayList();
        parejas = new ArrayList();
        todosFamiliars = familiarFacade.findAll();
    }

    public String cargarFamiliaresCercanos(Aprendiz a) {
        init();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        List<Familiar> todosFamiliares = familiarFacade.findAll();
        try {
            for (int i = 0; i < todosFamiliares.size(); i++) {
                if (todosFamiliares.get(i).getIdAprendiz().equals(a)) {
                    if (!todosFamiliares.get(i).getParentezco().isEmpty()) {
                        switch (todosFamiliares.get(i).getParentezco()) {
                            case "PADRE":
                                padres.add(todosFamiliares.get(i));
                                break;
                            case "PAREJA":
                                parejas.add(todosFamiliares.get(i));
                                break;
                            case "HIJO":
                                hijos.add(todosFamiliares.get(i));
                                break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "perfilAprendiz.xhtml";

    }

    public List<Familiar> cargarRelaciones(Familiar f) {
        List<Familiarrelacion> todasRelaciones = familiarrelacionFacade.findAll();
        List<Familiar> todosFamiliares = familiarFacade.findAll();
        List<Familiar> familiares = new ArrayList();
        try {
            for (int i = 0; i < todasRelaciones.size(); i++) {
                if (todasRelaciones.get(i).getIdFamiliarParentezco().equals(f)) {
                    familiares.add(todasRelaciones.get(i).getIdFamiliarRelacion());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return familiares;
    }

    public String editarFamiliar(Familiar f) {
        String res = "perfilAprendiz.xhtml";
        try {
            familiarFacade.edit(f);
            modalModificado = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void cerrarModal() {
        modalModificado = 0;
    }

    public List<Familiar> getPadres() {
        return padres;
    }

    public void setPadres(List<Familiar> padres) {
        this.padres = padres;
    }

    public List<Familiar> getHijos() {
        return hijos;
    }

    public void setHijos(List<Familiar> hijos) {
        this.hijos = hijos;
    }

    public List<Familiar> getParejas() {
        return parejas;
    }

    public void setParejas(List<Familiar> parejas) {
        this.parejas = parejas;
    }

    public List<Familiar> getTodosFamiliars() {
        return todosFamiliars;
    }

    public void setTodosFamiliars(List<Familiar> todosFamiliars) {
        this.todosFamiliars = todosFamiliars;
    }

    public int getModalModificado() {
        return modalModificado;
    }

    public void setModalModificado(int modalModificado) {
        this.modalModificado = modalModificado;
    }

}
