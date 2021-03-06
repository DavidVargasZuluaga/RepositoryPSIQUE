/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ancam
 */
@Named(value = "controladorPsicologos")
@SessionScoped
public class controladorPsicologos implements Serializable {

    @EJB
    private RolFacade rolFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private List<Usuario> listaUsurio;

    private Usuario usuarioTemp;
//
//    //Atributos de tipo usuario
//    private Long idUsuario;
//    private String tipoDocumento;
//    private long noDocumento;
//    private String correo;
//    private String clave;
//    private String estado;
//    private Date fechaNacimiento;
//    private String nombres;
//    private String primerApellido;
//    private String segundoApellido;
//    private String telefono;
//
    @EJB
    private PsicologoFacade psicologoFacade;
    private Psicologo psicologo;
    private List<Psicologo> ListaPsicologo;
//
//    //Atributos de tipoPsicologo
//    private String jornada;
    private int ver;
    private int modalCreacionPsicologo;

    public controladorPsicologos() {

    }

    @PostConstruct
    public void init() {
        psicologo = new Psicologo();
        ListaPsicologo = new ArrayList<>();
        modalCreacionPsicologo = 0;
    }

    public void cerrarModal() {
        modalCreacionPsicologo = 0;
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
            usuarioTemp.setIdRol(rolFacade.find(3));
            usuarioTemp.setTipoDocumento((String) params.get("tipoDocumento"));
            usuarioTemp.setNoDocumento(Long.parseLong((String) params.get("noDocumento")));
            usuarioTemp.setClave((String) params.get("clave"));
            String clave2 = (String) params.get("clave2");
            usuarioTemp.setCorreo((String) params.get("correo"));
            usuarioTemp.setEstado("ACTIVO");
            usuarioTemp.setFechaNacimiento((Date) format.parse((String) params.get("fecha")));
            usuarioTemp.setNombres((String) params.get("nombres"));
            usuarioTemp.setPrimerApellido((String) params.get("primerApellido"));
            usuarioTemp.setSegundoApellido((String) params.get("segundoApellido"));
            usuarioTemp.setTelefono((String) params.get("telefono"));
            for (int i = 0; i < usuarioFacade.findAll().size(); i++) {
                if (usuarioFacade.findAll().get(i).getNoDocumento() == usuarioTemp.getNoDocumento() || usuarioFacade.findAll().get(i).getCorreo().equals(usuarioTemp.getCorreo())) {
                    existe = false;
                    modalCreacionPsicologo = 2;
                    break;
                }
            }
            if (!usuarioTemp.getClave().equals(clave2)) {
                existe = false;
                modalCreacionPsicologo = 3;
            }
            if (existe) {
                usuarioFacade.create(usuarioTemp);

                psicologo.setIdPsicologo(usuarioTemp.getIdUsuario());
                psicologo.setJornada((String) params.get("jornada"));
                psicologoFacade.create(psicologo);
                System.out.println("usuario creado");
                modalCreacionPsicologo = 1;
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarEnTabla(Psicologo psicologo) {
        try {
            psicologoFacade.remove(psicologo);
            System.out.println("Aprendiz eliminado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente el usuario"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public void recogerDato(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    public void verDatos() {
        ver = 1;
    }

    public void cerrarDatos() {
        ver = 0;
    }

    public Psicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    public List<Psicologo> getListaPsicologo() {
        ListaPsicologo = psicologoFacade.findAll();
        return ListaPsicologo;
    }

    public void setListaPsicologo(List<Psicologo> ListaPsicologo) {
        this.ListaPsicologo = ListaPsicologo;
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

    public Usuario getUsuarioTemp() {
        return usuarioTemp;
    }

    public void setUsuarioTemp(Usuario usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

    public int getModalCreacionPsicologo() {
        return modalCreacionPsicologo;
    }

    public void setModalCreacionPsicologo(int modalCreacionPsicologo) {
        this.modalCreacionPsicologo = modalCreacionPsicologo;
    }

}
