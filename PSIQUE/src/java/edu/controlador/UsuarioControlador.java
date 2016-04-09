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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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

    
    private Calendar fecha2;
    private Usuario usuarioLog;
    private Usuario usuarioTemp;
    private Aprendiz aprendizLog;
    private Aprendiz aprendizTemp;
    private Psicologo psicologoLog;

    private List<Usuario> listaUsuarios;
    private List<Psicologo> listaPsicologos;

    private int ver;
    private int modalCreacion;
    private int año;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;
    private String version;
    private String fechaActual;
    private String horaActual ;
    
    @PostConstruct
    public void init() {
        fecha2 = GregorianCalendar.getInstance();
        modalCreacion = 0;
        ver = 0;

        usuarioLog = new Usuario();
        usuarioTemp = new Usuario();
        aprendizLog = new Aprendiz();
        aprendizTemp = new Aprendiz();
        psicologoLog = new Psicologo();

        listaUsuarios = usuarioFacade.findAll();
        listaPsicologos = psicologoFacade.findAll();

        año = fecha2.get(Calendar.YEAR);
        mes = fecha2.get(Calendar.MONTH)+1;
        dia = fecha2.get(Calendar.DAY_OF_MONTH);
        hora = fecha2.get(Calendar.HOUR_OF_DAY);
        minuto = fecha2.get(Calendar.MINUTE);
        segundo = fecha2.get(Calendar.SECOND);
        version = "PSIQUE 3.8";
        fechaActual = (dia + "/" + mes);
        horaActual = (+ hora + " : " + minuto);

    }

    public Usuario buscarPorNombre(String nombre) {
        List<Usuario> listaU = usuarioFacade.findAll();
        Usuario us = new Usuario();
        for (int i = 0; i < listaU.size(); i++) {
            if (listaU.get(i).getNombres().equals(nombre) || listaU.get(i).getPrimerApellido().equals(nombre)) {
                us = listaU.get(i);
                break;
            }
        }
        return us;
    }

    public String redireccionarACitasSolicitadas(String direccion) {
        return "/" + direccion;
    }

    public String autenticar() {
        String res = "/PSIQUE/index.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            Long doc = Long.parseLong((String) params.get("documento"));
            String clave = (String) params.get("clave");
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getNoDocumento() == doc && listaUsuarios.get(i).getClave().equals(clave)) {
                    this.usuarioLog = listaUsuarios.get(i);
                    httpServletRequest.getSession().setAttribute("UsuarioLog", listaUsuarios.get(i));
                    break;
                }
            }
            switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "/modAdmon/principalAdmon.xhtml";
                    break;
                case 2:
                    res = "/modCoordinador/principalCoordinador.xhtml";
                    break;
                case 3:
                    res = "/indexPsicologo.xhtml";
                    break;
                case 4:
                    res = "/modAprendiz/principalAprendiz.xhtml";
                    break;
                default:
                    res = "/index.xhtml";
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
        return "/PSIQUE/index";
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

    public void crearUsuario() {
        usuarioTemp = new Usuario();
        boolean existe = true;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            usuarioTemp.setIdRol(rolFacade.find(4));
            usuarioTemp.setTipoDocumento((String) params.get("tipoDocumento"));
            usuarioTemp.setNoDocumento(Long.parseLong((String) params.get("documento")));
            usuarioTemp.setClave((String) params.get("clave"));
            usuarioTemp.setClave((String) params.get("correo"));
            usuarioTemp.setEstado("ACTIVO");
            usuarioTemp.setFechaNacimiento((Date) params.get("fechaNacimiento"));
            usuarioTemp.setNombres((String) params.get("nombres"));
            usuarioTemp.setPrimerApellido((String) params.get("primerApellido"));
            usuarioTemp.setSegundoApellido((String) params.get("segundoApellido"));
            usuarioTemp.setTelefono((String) params.get("telefono"));
            for (int i = 0; i < usuarioFacade.findAll().size(); i++) {
                if (usuarioFacade.findAll().get(i).getNoDocumento() == usuarioTemp.getNoDocumento()) {
                    existe = false;
                    usuarioTemp = new Usuario();
                    break;
                }
            }
            if (existe) {
                usuarioFacade.create(usuarioTemp);
                modalCreacion = 1;
            } else {
                modalCreacion = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String crearAprendiz() {
        crearUsuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        try {
            if (usuarioTemp != null) {
                aprendizTemp.setUsuario(usuarioTemp);
                aprendizTemp.setFicha(fichaFacade.find((Integer) params.get("Ficha")));
                aprendizTemp.setUbicacion((String) params.get("ubicacion"));
                aprendizTemp.setSexo((String) params.get("sexo"));
                aprendizTemp.setEstadoCivil((String) params.get("estadoCivil"));
                aprendizTemp.setRaza((String) params.get("raza"));
                aprendizTemp.setReligion((String) params.get("religion"));
                aprendizTemp.setTendenciaPolitica((String) params.get("politica"));
                aprendizTemp.setOrientacionSexual((String) params.get("sexual"));
                aprendizTemp.setDiscapacidad((String) params.get("discapacidad"));
                aprendizTemp.setPasaTiempo((String) params.get("pasaTiempo"));
                aprendizFacade.create(aprendizTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    public String editarDatosPersonales() {
        String res = "/PSIQUE";
        usuarioFacade.edit(usuarioLog);
        switch (usuarioLog.getIdRol().getIdRol()) {
            case 1:
                res = "/index.xhtml";
                break;
            case 2:
                res = "/modCoordinador/configuracion.xhtml";
                break;
            case 3:
                res = "/modPsicologo/configuracion.xhtml";
                break;
            case 4:
                res = "/modAprendiz/configuracion.xhtml";
                break;
            default:
                res = "/index.xhtml";
                break;
        }
        return "";
    }

//    public String recuperarContraseña() {
//        String res = "/modUsuario/recuperarContraseña.xhtml";
//        String correo = " ";
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = facesContext.getExternalContext();
//        Map params = externalContext.getRequestParameterMap();
//        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
//        try {
//            correo = ((String) params.get("correo"));
//            Mailer.send(correo, "correo", "Mensajeeeeeeeee");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
    
    
    
    // FELIPE ES UN PUERCO METIENDO CONTENIDO DEL MODULO CITAS EN ESTE CONTROLADOR
    // PENDIENTE POR MODIFICAR
    public String cancelarCita(Cita cita) {
        cita.setEstado("CANCELADA");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }

    public String aceptarCitar(Cita cita) {
        cita.setEstado("PENDIENTE");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }

    public List<Aprendiz> mostrarAprendices() {
        List<Aprendiz> aprendices = aprendizFacade.findAll();
        return aprendices;
    }

    public List<Cita> mostrarCitasPendientes() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("PENDIENTE")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public List<Cita> mostrarCitasSolicitadas() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("SOLICITADA")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public List<Cita> mostrarCitas() {
        List<Cita> Citas = citaFacade.findAll();
        return Citas;
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
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 4) {
                lista.add(listaUsuarios.get(i));
            }
        }
        return lista;
    }

    public List<Usuario> traerListaCoordinadores() {
        List<Usuario> listaCo = new ArrayList();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 2) {
                listaCo.add(listaUsuarios.get(i));
            }
        }
        return listaCo;
    }

    public List<Usuario> traerListaPsicologos() {
        List<Usuario> listaPs = new ArrayList();
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getIdRol().getIdRol() == 3) {
                listaPs.add(listaUsuarios.get(i));
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

    
}
