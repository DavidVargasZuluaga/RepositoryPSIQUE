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
@Named(value = "controladorUsuario")
@SessionScoped
public class controladorUsuario implements Serializable {

    @EJB
    private RolFacade rolFacade;
    private Rol rol;

    @EJB
    private AprendizFacade aprendizFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    private Usuario usuario;
    private List<Usuario> listaAprendiz;
    private List<Usuario> listaCoordinadores;
    private List<Usuario> listaPsicologos;
    private List<Usuario> listaUsuarios;

//    atributos de tipo usuario
    private Usuario usuarioLog;
    private Usuario usuarioTemp;
    private Usuario usuarioTemp2;

    public controladorUsuario() {
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuarioLog = new Usuario();
        usuarioTemp = new Usuario();
        usuarioTemp2 = new Usuario();
        listaAprendiz = usuarioFacade.findAll();
        listaCoordinadores = usuarioFacade.findAll();
        listaPsicologos = usuarioFacade.findAll();
        listaUsuarios = new ArrayList();
        rol = new Rol();
    }

    public String autenticar() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String res = "index.xhtml";

        try {
            Long doc = Long.parseLong((String) params.get("documento"));
            String clave = (String) params.get("clave");
            this.listaUsuarios = usuarioFacade.findAll();
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getNoDocumento() == doc && listaUsuarios.get(i).getClave().equals(clave)) {
                    this.usuarioLog = listaUsuarios.get(i);
                    httpServletRequest.getSession().setAttribute("UsuarioLog", listaUsuarios.get(i));
                    break;
                }
            }
            switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "modAdmon/principalAdmon.xhtml";
                    break;

                case 2:
                    res = "/modCoordinador/principalCoordinador.xhtml";
                    break;

                case 3:
                    res = "/modPsicologo/indexPsicologo.xhtml";
                    break;

                case 4:
                    res = "/modUsuario/miPerfilAd.xhtml";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String cerrarSesion() {
        try {
            init();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }

    public void validarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> traerListaAprendiz() {
        List<Usuario> lista = new ArrayList();
        for (int i = 0; i < listaAprendiz.size(); i++) {
            if (listaAprendiz.get(i).getIdRol().getIdRol() == 4) {
                lista.add(listaAprendiz.get(i));
            }
        }
        return lista;
    }

    public List<Usuario> traerListaCoordinadores() {
        List<Usuario> listaCo = new ArrayList();
        for (int i = 0; i < listaCoordinadores.size(); i++) {
            if (listaCoordinadores.get(i).getIdRol().getIdRol() == 2) {
                listaCo.add(listaCoordinadores.get(i));
            }
        }
        return listaCo;
    }
    
    public List<Usuario> traerListaPsicologos() {
        List<Usuario> listaPs = new ArrayList();
        for (int i = 0; i < listaPsicologos.size(); i++) {
            if (listaPsicologos.get(i).getIdRol().getIdRol() == 3) {
                listaPs.add(listaPsicologos.get(i));
            }
        }
        return listaPs;
    }

    public void actualizar(Usuario usuarioM) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        try {
            //     rol.setIdRol((Integer.parseInt((String) params.get("rol"))));
            usuarioM.setNombres((String) params.get("nombres"));
            usuarioM.setPrimerApellido((String) params.get("primerApellido"));
            usuarioM.setSegundoApellido((String) params.get("segundoApellido"));
//            aprendiz.setTipoDocumento((String) params.get("tipoDocumento"));
            usuarioM.setNoDocumento(Long.parseLong((String) params.get("noDocumento")));
            usuarioM.setCorreo((String) params.get("correo"));
            usuarioM.setClave((String) params.get("clave"));
            usuarioM.setTelefono((String) params.get("telefono"));
            usuarioM.setEstado((String) params.get("estado"));
            //          aprendiz.setIdRol(rol);
            System.out.println("a");
            usuarioFacade.edit(usuarioM);
            System.out.println("Usuario modificado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios = usuarioFacade.findAll();
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaAprendiz() {
        return listaAprendiz;
    }

    public void setListaAprendiz(List<Usuario> listaAprendiz) {
        this.listaAprendiz = listaAprendiz;
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public Usuario getUsuarioTemp() {
        return usuarioTemp;
    }

    public void setUsuarioTemp(Usuario usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

}
