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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ancam
 */
@Named(value = "controladorAprendiz")
@SessionScoped
public class controladorAprendiz implements Serializable {

    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private AprendizFacade aprendizFacade;

    @Inject
    private RolFacade rolFacade;
    
    @Inject
    private FichaFacade fichaFacade;

    private Usuario usuario;
    private Aprendiz aprendiz;
    private List<Aprendiz> listaAprendiz;

    private int ver;
    private int modalAprendiz;

    @PostConstruct
    public void init() {
        aprendiz = new Aprendiz();
        listaAprendiz = new ArrayList<>();
        ver = 0;
        modalAprendiz = 0;
        usuario = new Usuario();
    }
    
    public void cerrarModal(){
        modalAprendiz = 0;
    }

    public String guardarAprendizUsuario() {
        aprendiz = new Aprendiz();
        usuario = new Usuario();
        String res = "";
        boolean existe = false;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Usuario> usuarios = usuarioFacade.findAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String clave2 = (String) params.get("clave2");
            usuario.setClave((String) params.get("clave1"));
            usuario.setNoDocumento(Long.parseLong((String) params.get("noDocumento")));
            usuario.setCorreo((String) params.get("correo"));
            if (!usuario.getClave().equals(clave2)) {
                existe = true;
                modalAprendiz = 1;
            }
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getNoDocumento() == usuario.getNoDocumento() || usuarios.get(i).getCorreo().equals(usuario.getCorreo())) {
                    existe = true;
                    modalAprendiz = 2;
                    break;
                }
            }
            if (!existe) {
                usuario.setIdRol(rolFacade.find(4));
                usuario.setTipoDocumento((String) params.get("tipoDocumento"));
                usuario.setEstado("ACTIVO");
                usuario.setFechaNacimiento((Date) format.parse((String) params.get("fecha")));
                usuario.setNombres((String) params.get("nombres"));
                usuario.setPrimerApellido((String) params.get("primerApellido"));
                usuario.setTelefono((String) params.get("telefono"));
                usuario.setSegundoApellido((String) params.get("segundoApellido"));
                aprendiz.setFicha(fichaFacade.find((String) params.get("ficha")));
                aprendiz.setUbicacion((String) params.get("ubicacion"));
                aprendiz.setSexo((String) params.get("sexo"));
                aprendiz.setEstadoCivil((String) params.get("estadoCivil"));
                aprendiz.setRaza((String) params.get("raza"));
                aprendiz.setReligion((String) params.get("religion"));
                aprendiz.setTendenciaPolitica((String) params.get("tendenciaPolitica"));
                aprendiz.setOrientacionSexual((String) params.get("orientacionSexual"));
                aprendiz.setDiscapacidad((String) params.get("discapacidad"));
                aprendiz.setPasaTiempo((String) params.get("pasaTiempo"));
                res = "testRegistro.xhtml";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void recogerDato(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public void verDatos() {
        ver = 1;
    }

    public void cerrarDatos() {
        ver = 0;
    }

    public Aprendiz getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public List<Aprendiz> getListaAprendiz() {
        listaAprendiz = aprendizFacade.findAll();
        return listaAprendiz;
    }

    public void setListaAprendiz(List<Aprendiz> listaAprendiz) {
        this.listaAprendiz = listaAprendiz;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getModalAprendiz() {
        return modalAprendiz;
    }

    public void setModalAprendiz(int modalAprendiz) {
        this.modalAprendiz = modalAprendiz;
    }

}
