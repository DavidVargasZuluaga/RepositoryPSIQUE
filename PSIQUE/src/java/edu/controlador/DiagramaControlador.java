package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    private Aprendiz aprendizTemp;
    private Familiar familiarTemp;

    private List<Familiar> padres;
    private List<Familiar> hijos;
    private List<Familiar> parejas;
    private List<Familiar> todosFamiliars;

    private int modalModificado;

    @PostConstruct
    public void init() {
        aprendizTemp = new Aprendiz();
        familiarTemp = new Familiar();
        modalModificado = 0;
        padres = new ArrayList();
        hijos = new ArrayList();
        parejas = new ArrayList();
        todosFamiliars = familiarFacade.findAll();
    }

    public String cargarFamiliaresCercanos(Aprendiz a) {
        init();
        this.aprendizTemp = a;
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
        String res = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String t = (String) params.get("fecha");
            if(!t.equals("")){
                f.setFechaNacimiento((Date) format.parse((String) params.get("fecha")));
            }
            t = (String) params.get("fecha2");
            if(!t.equals("")){
                f.setFechaDdefuncion((Date) format.parse((String) params.get("fecha2")));
            }
            familiarFacade.edit(f);
            cargarFamiliaresCercanos(this.aprendizTemp);
            modalModificado = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String eliminarFamiliar(Familiar f) {
        String res = "";
        List<Familiar> familiares = familiarFacade.findAll();
        List<Familiarrelacion> todasRelaciones = familiarrelacionFacade.findAll();
        try {
            for (int i = 0; i < todasRelaciones.size(); i++) {
                if (todasRelaciones.get(i).getIdFamiliarParentezco().equals(f)) {
                    // familiarFacade.remove(familiarFacade.find(todasRelaciones.get(i).getIdFamiliarParentezco()));
                    if (!cargarRelaciones(todasRelaciones.get(i).getIdFamiliarParentezco()).isEmpty()) {
                        eliminarFamiliar(todasRelaciones.get(i).getIdFamiliarRelacion());
                    }
                }
            }
            familiarFacade.remove(f);
            cargarFamiliaresCercanos(this.aprendizTemp);
            modalModificado = 2;
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

    public Familiar getFamiliarTemp() {
        return familiarTemp;
    }

    public void setFamiliarTemp(Familiar familiarTemp) {
        this.familiarTemp = familiarTemp;
    }

}
