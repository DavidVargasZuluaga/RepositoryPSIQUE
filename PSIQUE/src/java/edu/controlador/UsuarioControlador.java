package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "usuarioControlador")
@SessionScoped
public class UsuarioControlador implements Serializable {

    @Inject
    private EntradaFacade entradaFacade;

    @Inject
    private RolFacade rolFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private AprendizFacade aprendizFacade;

    @Inject
    private PsicologoFacade psicologoFacade;

    @Inject
    private CitaFacade citaFacade;

    @Inject
    private FichaFacade fichaFacade;

    private int ver, modalCreacion, modalRecuperarContraseña, modalIngreso,
            modalModificarAprendiz, año, mes, dia, hora, minuto, segundo,
            modalCambios;
    private String version, fechaActual, horaActual;

    private Entrada objEntrada;
    private Calendar fecha2;
    private Usuario usuarioLog, usuarioTemp, correo;
    ;
    private Aprendiz aprendizLog, aprendizTemp;
    private Psicologo psicologoLog;
    private Date fecha1 = new Date();

    private List<Usuario> listaUsuarios;
    private List<Psicologo> listaPsicologos;
    private List<Entrada> listaEntrada;
    private List<Aprendiz> listaAprendices;
    private List<Aprendiz> listaAprendicesCoor;

    @PostConstruct
    public void init() {
        version = "PSIQUE 3.9.4";
        modalIngreso = 0;
        modalCreacion = 0;
        modalRecuperarContraseña = 0;
        modalModificarAprendiz = 0;
        modalCambios = 0;
        ver = 0;
        fecha2 = GregorianCalendar.getInstance();
        año = fecha2.get(Calendar.YEAR);
        mes = fecha2.get(Calendar.MONTH) + 1;
        dia = fecha2.get(Calendar.DAY_OF_MONTH);
        hora = fecha2.get(Calendar.HOUR_OF_DAY);
        minuto = fecha2.get(Calendar.MINUTE);
        segundo = fecha2.get(Calendar.SECOND);
        fechaActual = (dia + "/" + mes + "/" + año);
        horaActual = (+hora + " : " + minuto);

        correo = new Usuario();
        usuarioLog = new Usuario();
        usuarioTemp = new Usuario();
        aprendizLog = new Aprendiz();
        aprendizTemp = new Aprendiz();
        psicologoLog = new Psicologo();
        objEntrada = new Entrada();

        listaUsuarios = usuarioFacade.findAll();
        listaPsicologos = psicologoFacade.findAll();
        listaAprendices = aprendizFacade.findAll();

    }

    public List<Aprendiz> listarAprendicesActivos() {
        List<Aprendiz> resList = new ArrayList();
        for (int i = 0; i < listaAprendices.size(); i++) {
            if (listaAprendices.get(i).getUsuario().getEstado().equals(
                    "ACTIVO")) {
                resList.add(listaAprendices.get(i));
            }
        }
        return resList;
    }

//    public Usuario buscarPorNombre(String nombre) {
//        List<Usuario> listaU = usuarioFacade.findAll();
//        Usuario us = new Usuario();
//        for (int i = 0; i < listaU.size(); i++) {
//            if (listaU.get(i).getNombres().equals(nombre) || 
    //listaU.get(i).getPrimerApellido().equals(nombre)) {
//                us = listaU.get(i);
//                break;
//            }
//        }
//        return us;
//    }
    public String editarPerfilAprendiz() {
        try {
            aprendizFacade.edit(aprendizTemp);
            modalModificarAprendiz = 1;
        } catch (Exception e) {
            modalModificarAprendiz = 2;
            e.printStackTrace();
        }
        return "perfilAprendiz.xhtml";
    }

    public String editarDatosAprendiz() {
        try {
            aprendizFacade.edit(aprendizTemp);
            modalModificarAprendiz = 1;
        } catch (Exception e) {
            modalModificarAprendiz = 2;
            e.printStackTrace();
        }
        return "perfilAprendiz.xhtml";
    }

    public String autenticar() {
        String res = "/PSIQUE/";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String doc2 = (String) params.get("documento");
            long doc = Long.parseLong(doc2);
            String clave = (String) params.get("clave");
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getNoDocumento() == doc
                        && listaUsuarios.get(i).getClave().equals(clave)) {
                    usuarioLog = listaUsuarios.get(i);
                    if (usuarioLog.getEstado().equals("ACTIVO")) {
                        httpServletRequest.getSession().setAttribute(
                                "UsuarioLog", listaUsuarios.get(i));
                        switch (usuarioLog.getIdRol().getIdRol()) {
                            case 1:
                                res = "/modAdmon/principalAdmon.xhtml";
                                registrarIngresoAlSistema();
                                break;
                            case 2:
                                res = "/modCoordinador/principalCoordinador.xhtml";
                                registrarIngresoAlSistema();
                                break;
                            case 3:
                                res = "/modPsicologo/indexPsicologo.xhtml";
                                registrarIngresoAlSistema();
                                break;
                            case 4:
                                res = "/modAprendiz/principalAprendiz.xhtml";
                                registrarIngresoAlSistema();
                                break;
                        }
                        break;
                    } else {
                        modalIngreso = 2;
                        break;
                    }

                } else {
                    modalIngreso = 1;
                }
            }

        } catch (Exception e) {
        }
        return res;
    }

    public void cerrarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            facesContext.getExternalContext().redirect("/PSIQUE/");
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarSesionAdmon() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null && usuarioLog.getIdRol().getIdRol() == 1) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE/403.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarSesionCoordinador() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null && usuarioLog.getIdRol().getIdRol() == 2) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE/403.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarSesionPsicologo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null && usuarioLog.getIdRol().getIdRol() == 3) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE/403.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validarSesionAprendiz() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null && usuarioLog.getIdRol().getIdRol() == 4) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE/403.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String editarContraseña() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String contraseña = (String) params.get("nuevaContraseña");
            String repetriContraseña = (String) params.get("repetriContraseña");
            if (!usuarioLog.getClave().equals(contraseña)) {
                if (contraseña.equals(repetriContraseña)) {
                    usuarioLog.setClave(repetriContraseña);
                    usuarioFacade.edit(usuarioLog);
                    modalCambios = 4;
                } else {
                    modalCambios = 3;
                }
            } else {
                modalCambios = 2;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public String editarDatosPersonales() {
        String res = "/PSIQUE";
        usuarioFacade.edit(usuarioLog);
        modalCambios = 1;
        return "";
    }

    public String actualizarEstado(Usuario u) {
        String res = "/modAdmon/todosUsuarios.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String es = (String) params.get("estadoDes");
            u.setEstado(es);
            usuarioFacade.edit(u);
        } catch (Exception e) {
        }
        return res;
    }

    public String recuperarContraseña() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String c = (String) params.get("correo");
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getCorreo().equals(c)) {
                    correo = listaUsuarios.get(i);
                    Mailer.send(correo.getCorreo(), "Recuperar contraseña sistema PSIQUE", "Coordial saludo señor@:" + correo.getNombres() + correo.getPrimerApellido() + " Su clave es: " + correo.getClave() + " Su numero de documento es: " + correo.getNoDocumento());
                    modalRecuperarContraseña = 1;
                    break;
                } else {
                    System.out.println("Ese usuario no se encuentra registrado en el sistema");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            modalRecuperarContraseña = 2;
        }
        return "/index.xhtml";
    }

    public void registrarIngresoAlSistema() {
        List<Entrada> entradas = entradaFacade.findAll();
        objEntrada.setAccion("Ingreso al sistema");
        objEntrada.setFecha(fecha1);
        objEntrada.setUsuarioidUsuario(usuarioLog);
        entradaFacade.create(objEntrada);
        if(entradaFacade.count() > 100){
            for (int i = 0; i < entradas.size(); i++) {
                entradaFacade.remove(entradas.get(i));
                break;
            }
        }
    }

    public void cerrarModal() {
        modalRecuperarContraseña = 0;
        modalCreacion = 0;
        modalModificarAprendiz = 0;
        modalIngreso = 0;
        modalCambios = 0;
    }

    //    Parte Andres parte del controlador Usuario
    public void verDatos() {
        ver = 1;
    }

    public void cerrarDatos() {
        ver = 0;
    }

    public List<Usuario> traerListaAprendiz() {
        List<Usuario> lista = new ArrayList();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 4 && listaUsuarios.get(i).getEstado().endsWith("ACTIVO")) {
                lista.add(listaUsuarios.get(i));
            }
        }
        return lista;
    }

    public List<Usuario> traerListaCoordinadores() {
        List<Usuario> listaCo = new ArrayList();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 2 && listaUsuarios.get(i).getEstado().endsWith("ACTIVO")) {
                listaCo.add(listaUsuarios.get(i));
            }
        }
        return listaCo;
    }

    public List<Usuario> traerListaPsicologos() {
        List<Usuario> listaPs = new ArrayList();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 3 && listaUsuarios.get(i).getEstado().endsWith("ACTIVO")) {
                listaPs.add(listaUsuarios.get(i));
            }
        }
        return listaPs;
    }

    public String actualizarAdmon() {
        try {
            usuarioFacade.edit(usuarioLog);
            modalCambios = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
     public List<Aprendiz> listaAprendicesCoordinador() {
        List<Aprendiz> resListApre = new ArrayList();
        for (int i = 0; i < listaAprendices.size(); i++) {
            if (listaAprendices.get(i).getUsuario().getEstado().equals("ACTIVO") && listaAprendices.get(i).getFicha().getIdPrograma().getIdPrograma().equals(usuarioLog.getCoordinador().getIdPrograma().getIdPrograma())) {
                resListApre.add(listaAprendices.get(i));
            }
        }
        return resListApre;
    }

    public List<Psicologo> listaPsicologoCoordinador() {
        List<Psicologo> resListPsi = new ArrayList();
        for (int i = 0; i < listaPsicologos.size(); i++) {
            if (listaPsicologos.get(i).getUsuario().getEstado().equals("ACTIVO")) {
                resListPsi.add(listaPsicologos.get(i));
            }
        }
        return resListPsi;
    }


//    public String actualizar(Usuario usuarioM) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        Map params = externalContext.getRequestParameterMap();
//        try {
//            //     rol.setIdRol((Integer.parseInt((String) params.get("rol"))));
//            usuarioM.setNombres((String) params.get("nombres"));
//            usuarioM.setPrimerApellido((String) params.get("primerApellido"));
//            usuarioM.setSegundoApellido((String) params.get("segundoApellido"));
////            aprendiz.setTipoDocumento((String) params.get("tipoDocumento"));
//            usuarioM.setNoDocumento(Long.parseLong((String) params.get("noDocumento")));
//            usuarioM.setCorreo((String) params.get("correo"));
//            usuarioM.setClave((String) params.get("clave"));
//            usuarioM.setTelefono((String) params.get("telefono"));
//            usuarioM.setEstado((String) params.get("estado"));
//            System.out.println("a");
//            usuarioFacade.edit(usuarioM);
//            System.out.println("Usuario modificado");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "/modAdmon/principalAdmon.xhtml";
//    }
    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    public String getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(String horaActual) {
        this.horaActual = horaActual;
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public int getModalCreacion() {
        return modalCreacion;
    }

    public void setModalCreacion(int modalCreacion) {
        this.modalCreacion = modalCreacion;
    }

    public Usuario getUsuarioTemp() {
        return usuarioTemp;
    }

    public void setUsuarioTemp(Usuario usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

    public Aprendiz getAprendizTemp() {
        return aprendizTemp;
    }

    public void setAprendizTemp(Aprendiz aprendizTemp) {
        this.aprendizTemp = aprendizTemp;
    }

    public Psicologo getPsicologoLog() {
        return psicologoLog;
    }

    public void setPsicologoLog(Psicologo psicologoLog) {
        this.psicologoLog = psicologoLog;
    }

    public List<Psicologo> getListaPsicologos() {
        return listaPsicologos;
    }

    public void setListaPsicologos(List<Psicologo> listaPsicologos) {
        this.listaPsicologos = listaPsicologos;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Aprendiz getAprendizLog() {
        return aprendizLog;
    }

    public void setAprendizLog(Aprendiz aprendizLog) {
        this.aprendizLog = aprendizLog;
    }

    public Calendar getFecha2() {
        return fecha2;
    }

    public void setFecha2(Calendar fecha2) {
        this.fecha2 = fecha2;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

//    public Correo getCorreo() {
//        return correo;
//    }
//
//    public void setCorreo(Correo correo) {
//        this.correo = correo;
//    }
    public int getModalRecuperarContraseña() {
        return modalRecuperarContraseña;
    }

    public void setModalRecuperarContraseña(int modalRecuperarContraseña) {
        this.modalRecuperarContraseña = modalRecuperarContraseña;
    }

    public List<Entrada> getListaEntrada() {
        return listaEntrada = entradaFacade.findAll();
    }

    public void setListaEntrada(List<Entrada> listaEntrada) {
        this.listaEntrada = listaEntrada;
    }

    public int getModalModificarAprendiz() {
        return modalModificarAprendiz;
    }

    public void setModalModificarAprendiz(int modalModificarAprendiz) {
        this.modalModificarAprendiz = modalModificarAprendiz;
    }
//
//    public Correo getCorreo() {
//        return correo;
//    }
//
//    public void setCorreo(Correo correo) {
//        this.correo = correo;
//    }

    public List<Aprendiz> getListaAprendices() {
        return listaAprendices;
    }

    public void setListaAprendices(List<Aprendiz> listaAprendices) {
        this.listaAprendices = listaAprendices;
    }

    public Entrada getObjEntrada() {
        return objEntrada;
    }

    public void setObjEntrada(Entrada objEntrada) {
        this.objEntrada = objEntrada;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public int getModalIngreso() {
        return modalIngreso;
    }

    public void setModalIngreso(int modalIngreso) {
        this.modalIngreso = modalIngreso;
    }

    public int getModalCambios() {
        return modalCambios;
    }

    public void setModalCambios(int modalCambios) {
        this.modalCambios = modalCambios;
    }

}
