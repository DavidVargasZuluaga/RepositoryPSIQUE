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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ancam
 */
@Named(value = "controladorFicha")
@SessionScoped
public class FichaControlador implements Serializable {

    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private FichaFacade fichaFacade;

    @Inject
    private ProgramaformacionFacade programaformacionFacade;
    private int idPrograma;

    private Ficha ficha;
    private List<Ficha> listaFichas;

    private int modalCreacion;

    @PostConstruct
    public void init() {
        ficha = new Ficha();
        listaFichas = fichaFacade.findAll();
        modalCreacion = 0;
    }

    public List<Programaformacion> programas() {
        return programaformacionFacade.findAll();
    }

    public void cerrarModal() {
        modalCreacion = 0;
    }

    public String crearFicha() {
        boolean siExiste = false;
        Ficha ficha = new Ficha();
        List<Ficha> fichas = fichaFacade.findAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            ficha.setNoFicha((String) params.get("noFicha"));
            ficha.setIdPrograma(programaformacionFacade.find(Integer.parseInt(
                    (String) params.get("idPrograma")))
            );
            ficha.setEstado("ACTIVO");
            for (int i = 0; i < fichas.size(); i++) {
                if (fichas.get(i).getNoFicha().equals(ficha.getNoFicha())) {
                    modalCreacion = 2;
                    siExiste = true;
                    break;
                }
            }
            if (siExiste) {
                fichaFacade.create(ficha);
                modalCreacion = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admonProgramas-Fichas.xhtml";
    }

    public String cambiarEstado(Ficha f) {
        List<Aprendiz> aprendices = new ArrayList();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String estado = (String) params.get("estadoPrograma");
            f.setEstado(estado);
            aprendices = f.getAprendizList();
            for (int i = 0; i < aprendices.size(); i++) {
                if (!aprendices.get(i).getUsuario().getEstado().equals("DESERTADO")) {
                    aprendices.get(i).getUsuario().setEstado(estado);
                    usuarioFacade.edit(aprendices.get(i).getUsuario());
                }
            }
            modalCreacion = 3;
            fichaFacade.edit(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

//    public void registrarFicha() {
//
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        Map params = externalContext.getRequestParameterMap();
//        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
//        boolean existe = true;
//
//        try {
//            Programaformacion programaformacion = new Programaformacion();
//            programaformacion.setIdPrograma(idPrograma);
//
//            ficha.setNoFicha((String) params.get("ficha"));
//            ficha.setIdPrograma(programaformacion);
//            ficha.setEstado("ACTIVO");
//
//            for (int i = 0; i < fichaFacade.findAll().size(); i++) {
//                if (fichaFacade.findAll().get(i).getNoFicha() == ficha.getNoFicha()) {
//                    existe = false;
//                    System.out.println("Ficha ya existente");
//                    break;
//                }
//            }
//
//            if (existe) {
//
//                fichaFacade.create(ficha);
//
//                System.out.println("ficha creada");
////                modalCreacion = 1;
//            } else {
//                System.out.println("usuario no creado");
////                modalCreacion = 2;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public void eliminarEnTabla(Ficha ficha) {
//        try {
//            fichaFacade.remove(ficha);
//            System.out.println("ficha eliminada");
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente la bodega"));
//        } catch (Exception e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
//        }
//    }
//
//    public void recogerDato(Ficha ficha) {
//        this.ficha = ficha;
//    }
//
//    public void actualizar() {
//        try {
//            fichaFacade.edit(ficha);
//            System.out.println("ficha editada");
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La bodega ha sido editada correctamente"));
//        } catch (Exception e) {
////            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
//        }
//    }
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

    public List<Ficha> getListaFichas() {
        return listaFichas;
    }

    public void setListaFichas(List<Ficha> listaFichas) {
        this.listaFichas = listaFichas;
    }

    public int getModalCreacion() {
        return modalCreacion;
    }

    public void setModalCreacion(int modalCreacion) {
        this.modalCreacion = modalCreacion;
    }

}
