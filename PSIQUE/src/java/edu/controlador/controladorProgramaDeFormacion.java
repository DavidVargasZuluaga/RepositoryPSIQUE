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

/**
 *
 * @author ancam
 */
@Named(value = "controladorProgramaDeFormacion")
@SessionScoped
public class controladorProgramaDeFormacion implements Serializable {

    @EJB
    private ProgramaformacionFacade programaformacionFacade;
    private Programaformacion programaformacion;
    List<Programaformacion> listaProgramaDeFormacion = new ArrayList<>();

    private String nombrePrograma;
    public controladorProgramaDeFormacion() {
    }

    @PostConstruct
    public void init() {
    }

    public String registrarPrograma() {
        try {
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            Map params = externalContext.getRequestParameterMap();

            Programaformacion objProgramaformacion = new Programaformacion();
            objProgramaformacion.setNombrePrograma("" + params.get("nombrePrograma"));
        
            
            programaformacionFacade.create(objProgramaformacion);
            System.out.println("Programa registrado");
//           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La bodega ha sido registrada correctamente"));
        } catch (Exception e) {
            System.out.println("No se registro");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
        return "programaFormacionRegistro.xhtml";
    }

    public void eliminarEnTabla(Programaformacion programaformacion) {
        try {
            programaformacionFacade.remove(programaformacion);
            System.out.println("Programa eliminado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente la bodega"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void recogerDato(Programaformacion programaformacion) {
        this.programaformacion = programaformacion;
    }

    public void actualizar() {
        try {
            programaformacionFacade.edit(programaformacion);
            System.out.println("Programa editado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La bodega ha sido editada correctamente"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public Programaformacion getProgramaformacion() {
        return programaformacion;
    }

    public void setProgramaformacion(Programaformacion programaformacion) {
        this.programaformacion = programaformacion;
    }

    public List<Programaformacion> getListaProgramaDeFormacion() {   
        return listaProgramaDeFormacion = programaformacionFacade.findAll();
    }

    public void setListaProgramaDeFormacion(List<Programaformacion> listaProgramaDeFormacion) {
        this.listaProgramaDeFormacion = listaProgramaDeFormacion;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }
    
}
