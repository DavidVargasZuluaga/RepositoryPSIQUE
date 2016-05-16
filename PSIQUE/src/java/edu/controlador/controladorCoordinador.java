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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "controladorCoordinador")
@SessionScoped
public class controladorCoordinador implements Serializable {

    @EJB
    private RolFacade rolFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private List<Usuario> listaUsurio;

    //    atributos de tipo usuario
    private Usuario usuarioTemp;

    @EJB
    private CoordinadorFacade coordinadorFacade;
    private Coordinador coordinador;
    private List<Coordinador> listaCoordinador;

    private int ver;
    private int idPrograma;
    private int modalCreacion;


     private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void init() {
        coordinador = new Coordinador();
        listaUsurio = new ArrayList<>();
        listaCoordinador = new ArrayList<>();
        ver = 0;
        modalCreacion = 0;
    }

    public void cerrarModal(){
        modalCreacion = 0;
    }
    
    public void crearUsuario() {
        usuarioTemp = new Usuario();
        boolean existe = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            usuarioTemp.setIdRol(rolFacade.find(2));
            usuarioTemp.setTipoDocumento((String) params.get("tipoDocumento"));
            usuarioTemp.setNoDocumento(Long.parseLong((String) params.get("noDocumento")));
            usuarioTemp.setClave((String) params.get("clave"));
            String clave2 = (String) params.get("calve2");
            usuarioTemp.setCorreo((String) params.get("correo"));
            usuarioTemp.setEstado("ACTIVO");
            String fecha = (String)params.get("fecha");
            usuarioTemp.setFechaNacimiento((Date) format.parse(fecha));
            usuarioTemp.setNombres((String) params.get("nombres"));
            usuarioTemp.setPrimerApellido((String) params.get("primerApellido"));
            usuarioTemp.setSegundoApellido((String) params.get("segundoApellido"));
            usuarioTemp.setTelefono((String) params.get("telefono"));
            for (int i = 0; i < usuarioFacade.findAll().size(); i++) {
                if (usuarioFacade.findAll().get(i).getNoDocumento() == usuarioTemp.getNoDocumento() || usuarioFacade.findAll().get(i).getCorreo().equals(usuarioTemp.getCorreo())) {
                    existe = false;
                    modalCreacion = 2;
                    break;
                }
            }
            if(!usuarioTemp.getClave().equals(clave2)){
                existe=false;
                modalCreacion = 3;
            }
            if (existe) {
                usuarioFacade.create(usuarioTemp);
                Programaformacion programaformacion = new Programaformacion();
                programaformacion.setIdPrograma(idPrograma);
                coordinador.setIdCoordinador(usuarioTemp.getIdUsuario());
                coordinador.setIdPrograma(programaformacion);
                coordinadorFacade.create(coordinador);
                listaCoordinador.add(coordinador);
                modalCreacion = 1;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String crearCoordinador() {
        crearUsuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try {
            if (usuarioTemp != null) {
                Programaformacion programaformacion = new Programaformacion();
                programaformacion.setIdPrograma(idPrograma);

                coordinador.setUsuario(usuarioTemp);
                coordinador.setIdPrograma(programaformacion);
                coordinadorFacade.create(coordinador);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    public void eliminarEnTabla(Coordinador coordinador) {
        try {
            coordinadorFacade.remove(coordinador);
            System.out.println("Aprendiz eliminado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente el usuario"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void recogerDato(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public void actualizar() {
        try {
            coordinadorFacade.edit(coordinador);
            System.out.println("Coordinador editado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "El usuario ha sido editado correctamente"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void verDatos() {
        ver = 1;
    }

    public void cerrarDatos() {
        ver = 0;
    }

    public List<Coordinador> getListaCoordinador() {
        return listaCoordinador = coordinadorFacade.findAll();
    }

    public void setListaCoordinador(List<Coordinador> listaCoordinador) {
        this.listaCoordinador = listaCoordinador;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioTemp() {
        return usuarioTemp;
    }

    public void setUsuarioTemp(Usuario usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

    public int getModalCreacion() {
        return modalCreacion;
    }

    public void setModalCreacion(int modalCreacion) {
        this.modalCreacion = modalCreacion;
    }

}
