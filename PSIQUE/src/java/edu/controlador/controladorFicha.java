/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ancam
 */
@Named(value = "controladorFicha")
@SessionScoped
public class controladorFicha implements Serializable {

    @EJB
    private ProgramaformacionFacade programaformacionFacade;
//    atributos tipo programaFormacion
    private int idPrograma;

    @EJB
    private FichaFacade fichaFacade;
    private Ficha ficha;
    private List<Ficha> listaFicha;

    @PostConstruct
    public void init() {
        ficha = new Ficha();
        listaFicha = new ArrayList<>();
    }

    public controladorFicha() {
    }

    public List<Programaformacion> programas() {
        return programaformacionFacade.findAll();
    }

    public void registrarFicha() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        boolean existe = true;

        try {
            Programaformacion programaformacion = new Programaformacion();
            programaformacion.setIdPrograma(idPrograma);

            ficha.setNoFicha((String) params.get("ficha"));
            ficha.setIdPrograma(programaformacion);
            ficha.setEstado("ACTIVO");

            for (int i = 0; i < fichaFacade.findAll().size(); i++) {
                if (fichaFacade.findAll().get(i).getNoFicha() == ficha.getNoFicha()) {
                    existe = false;
                    System.out.println("Ficha ya existente");
                    break;
                }
            }

            if (existe) {
               
                fichaFacade.create(ficha);
                
                System.out.println("ficha creada");
//                modalCreacion = 1;
            } else {
                System.out.println("usuario no creado");
//                modalCreacion = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void eliminarEnTabla(Ficha ficha) {
        try {
            fichaFacade.remove(ficha);
            System.out.println("ficha eliminada");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente la bodega"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void recogerDato(Ficha ficha) {
        this.ficha = ficha;
    }

    public void actualizar() {
        try {
            fichaFacade.edit(ficha);
            System.out.println("ficha editada");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La bodega ha sido editada correctamente"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public List<Ficha> getListaFicha() {
        return listaFicha=fichaFacade.findAll();
    }

    public void setListaFicha(List<Ficha> listaFicha) {
        this.listaFicha = listaFicha;
    }
    
    
}
