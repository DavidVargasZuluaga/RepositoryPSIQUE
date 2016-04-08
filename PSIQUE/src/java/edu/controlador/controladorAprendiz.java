/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
//import edu.entidades.Mailer;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author ancam
 */
@Named(value = "controladorAprendiz")
@SessionScoped
public class controladorAprendiz implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private List<Usuario> listaUsurio;

    //    atributos de tipo usuario
    private Long idUsuario;
    private String tipoDocumento;
    private long noDocumento;
    private String correo;
    private String clave;
    private String estado;
    private Date fechaNacimiento;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String telefono;

//  atributos de tipo ficha
    private String noFicha;

    @EJB
    private AprendizFacade aprendizFacade;
    private Aprendiz aprendiz;
    private List<Aprendiz> listaAprendiz;

    //atributos de tipo aprendiz
    private Long idAprendiz;
    private String ubicacion;
    private String sexo;
    private String estadoCivil;
    private String raza;
    private String religion;
    private String tendenciaPolitica;
    private String orientacionSexual;
    private String discapacidad;
    private String pasaTiempo;

    private int ver;

    public controladorAprendiz() {
        ver = 0;
    }

    @PostConstruct
    public void init() {
        aprendiz = new Aprendiz();
        listaAprendiz = new ArrayList<>();
        listaUsurio = new ArrayList<>();
    }

    public void registrarAprendiz() {

        try {

            Rol objRol = new Rol();
            objRol.setIdRol(4);

            Ficha objFicha = new Ficha();
            objFicha.setNoFicha(noFicha);

            Usuario objUsuario = new Usuario();
            objUsuario.setIdRol(objRol);
            objUsuario.setIdUsuario(idUsuario);
            objUsuario.setTipoDocumento(tipoDocumento);
            objUsuario.setNoDocumento(noDocumento);
            objUsuario.setCorreo(correo);
            objUsuario.setClave(clave);
            objUsuario.setEstado("Activo");
            objUsuario.setFechaNacimiento(fechaNacimiento);
            objUsuario.setNombres(nombres);
            objUsuario.setPrimerApellido(primerApellido);
            objUsuario.setSegundoApellido(segundoApellido);
            objUsuario.setTelefono(telefono);
            usuarioFacade.create(objUsuario);
            listaUsurio.add(objUsuario);

            for (int i = 0; i < listaUsurio.size(); i++) {
                if (objUsuario.getIdUsuario().equals(idUsuario)) {

                    Aprendiz aprendiz = new Aprendiz();
                    aprendiz.setIdAprendiz(idUsuario);
                    aprendiz.setFicha(objFicha);
                    aprendiz.setUbicacion(ubicacion);
                    aprendiz.setSexo(sexo);
                    aprendiz.setEstadoCivil(estadoCivil);
                    aprendiz.setRaza(raza);
                    aprendiz.setReligion(religion);
                    aprendiz.setTendenciaPolitica(tendenciaPolitica);
                    aprendiz.setOrientacionSexual(orientacionSexual);
                    aprendiz.setDiscapacidad(discapacidad);
                    aprendiz.setPasaTiempo(pasaTiempo);
                    aprendizFacade.create(aprendiz);
                }
            }

//            Mailer.send(usuario.getCorreo(), "Bienvenida", "El sistema HELIREP se alegra de que usted este con nosotros");
            System.out.println("Aprendiz registrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarEnTabla(Aprendiz aprendiz) {
        try {
            aprendizFacade.remove(aprendiz);
            System.out.println("Aprendiz eliminado");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se elimino correctamente el usuario"));
        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
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

    public String getNoFicha() {
        return noFicha;
    }

    public void setNoFicha(String noFicha) {
        this.noFicha = noFicha;
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

    public Long getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Long idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getTendenciaPolitica() {
        return tendenciaPolitica;
    }

    public void setTendenciaPolitica(String tendenciaPolitica) {
        this.tendenciaPolitica = tendenciaPolitica;
    }

    public String getOrientacionSexual() {
        return orientacionSexual;
    }

    public void setOrientacionSexual(String orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getPasaTiempo() {
        return pasaTiempo;
    }

    public void setPasaTiempo(String pasaTiempo) {
        this.pasaTiempo = pasaTiempo;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    
}
