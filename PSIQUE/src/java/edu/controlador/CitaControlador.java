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
@Named(value = "citaControlador")
@SessionScoped
public class CitaControlador implements Serializable {

    @Inject
    private CitaFacade citaFacade;

    @Inject
    private PsicologoFacade psicologoFacade;

    @Inject
    private ObservacionFacade observacionFacade;

    private int año, mes, dia, hora, minuto, segundo, modalVacio, modalCita;
    private String fechaActual, horaActual;

    private Calendar fecha;
    private Cita citaTemp, citaAnterior;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mmaa");

    @PostConstruct
    private void init() {
        modalCita = 0;
        modalVacio = 0;
        fecha = GregorianCalendar.getInstance();
        año = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH) + 1;
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        hora = fecha.get(Calendar.HOUR_OF_DAY);
        minuto = fecha.get(Calendar.MINUTE);
        segundo = fecha.get(Calendar.SECOND);
        fechaActual = (dia + "/" + mes);
        horaActual = (+hora + " : " + minuto);

        citaTemp = new Cita();
    }

    public void cerrarModal() {
        modalCita = 0;
    }

    public void cancelarCitas() {
        List<Cita> citas = citaFacade.findAll();
        SimpleDateFormat fechaComparar = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getEstado().equals("SOLICITADA") || citas.get(i).getEstado().equals("PENDIENTE")) {
                if (Integer.parseInt(fechaComparar.format(citas.get(i).getFecha())) < Integer.parseInt(fechaComparar.format(fecha.getTime()))) {
                    citas.get(i).setEstado("INCUMPLIDA");
                    citaFacade.edit(citas.get(i));
                }
            }
        }
    }

    public List<Cita> citasAprendiz(Aprendiz a) {
        List<Cita> todasCitas = citaFacade.findAll();
        List<Cita> citas = new ArrayList();
        try {
            for (int i = 0; i < todasCitas.size(); i++) {
                if (todasCitas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                    citas.add(todasCitas.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<Observacion> obsevacionesAprendiz(Aprendiz a) {
        List<Observacion> todasObservaciones = observacionFacade.findAll();
        List<Observacion> observaciones = new ArrayList();
        try {
            for (int i = 0; i < todasObservaciones.size(); i++) {
                if (todasObservaciones.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                    observaciones.add(todasObservaciones.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return observaciones;
    }

    public List<Cita> citasParaHoy(Psicologo p) {
        List<Cita> resul = new ArrayList();
        List<Cita> citas = citaFacade.findAll();
        try {
            SimpleDateFormat fechaComparar = new SimpleDateFormat("yyyyMMdd");
            for (int i = 0; i < citas.size(); i++) {
                if (citas.get(i).getIdPsicologo().getIdPsicologo().equals(p.getIdPsicologo())) {
                    if (citas.get(i).getEstado().equals("PENDIENTE")) {
                        if (fechaComparar.format(fecha.getTime()).equals(fechaComparar.format(citas.get(i).getFecha()))) {
                            resul.add(citas.get(i));
                        }
                    }
                }
            }
            if (resul.isEmpty()) {
                modalVacio = 1;
            } else {
                modalVacio = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resul;
    }

    public Calendar convertirFecha(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        return c;
    }

    public List<Cita> listarCitasAprendizLog(Aprendiz a) {
        List<Cita> resul = new ArrayList();
        List<Cita> citas = citaFacade.findAll();
        try {
            for (int i = 0; i < citas.size(); i++) {
                if (citas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                    resul.add(citas.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resul;
    }

    public String valorarCita(Cita cita) {
        Cita c = cita;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        c.setValoracion(Integer.parseInt((String) params.get("valor")));
        citaFacade.edit(c);
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public String solicitarCita(Aprendiz a) {
        citaTemp = new Cita();
        boolean existe = false;
        Date fechaComparar = fecha.getTime();
        fechaComparar.setHours(fechaComparar.getHours() + 12);
        List<Cita> listaCitas = citaFacade.findAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            citaTemp.setIdAprendiz(a);
            citaTemp.setIdPsicologo(psicologoFacade.find(Long.parseLong((String) params.get("psicologo"))));
            String hora3 = "";
            switch (citaTemp.getIdPsicologo().getJornada()) {
                case "Mañana":
                    hora3 = (params.get("fecha2") + " " + params.get("hora2"));
                    break;
                case "Tarde":
                    hora3 = (params.get("fecha") + " " + params.get("hora"));
                    break;
                case "Noche":
                    break;
            }
            citaTemp.setFecha((Date) format.parse(hora3));
            if (!citaTemp.getFecha().before(fechaComparar)) {
                citaTemp.setValoracion(0);
                citaTemp.setEstado("SOLICITADA");
                for (int i = 0; i < listaCitas.size(); i++) {
                    if (listaCitas.get(i).getFecha().equals(citaTemp.getFecha())) {
                        existe = true;
                        break;
                    }
                    if (listaCitas.get(i).getIdAprendiz().equals(a)) {
                        if (listaCitas.get(i).getEstado().equals("SOLICITADA") || listaCitas.get(i).getEstado().equals("PENDIENTE")) {
                            existe = true;
                            break;
                        }
                    }
                }
                if (!existe) {
                    modalCita = 0;
                    citaFacade.create(citaTemp);
                } else {
                    modalCita = 1;
                    citaTemp = new Cita();
                }
            } else {
                modalCita = 3;
            }
        } catch (Exception e) {
            modalCita = 2;
            e.printStackTrace();
        }
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public String cancelarCitaAprendiz(Cita c) {
        citaTemp = citaFacade.find(c.getIdCita());
        citaTemp.setEstado("CANCELADA");
        citaFacade.edit(citaTemp);
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public int confirmacionSolicitud(Aprendiz a) {
        int res = 0;
        List<Cita> listaCitas = citaFacade.findAll();
        List<Cita> citas = new ArrayList();
        try {
            for (int i = 0; i < listaCitas.size(); i++) {
                if (listaCitas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                    citas.add(listaCitas.get(i));
                }
            }
            for (int i = 0; i < citas.size(); i++) {
                switch (citas.get(i).getEstado()) {
                    case "PENDIENTE":
                        res = 1;
                        break;
                    case "SOLICITADA":
                        res = 1;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Cita ultimaCitaAprendiz(Aprendiz a) {
        citaTemp = new Cita();
        List<Cita> listaCitas = citaFacade.findAll();
        for (int i = 0; i < listaCitas.size(); i++) {
            if (listaCitas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz()) && listaCitas.get(i).getEstado().equals("CUMPLIDA")) {
                citaTemp = listaCitas.get(i);
            }
        }
        if (citaTemp.getIdCita() == null) {
            citaTemp = null;
        }
        return citaTemp;
    }

    public Cita getCitaTemp() {
        return citaTemp;
    }

    public void setCitaTemp(Cita citaTemp) {
        this.citaTemp = citaTemp;
    }

    public Cita getCitaAnterior() {
        return citaAnterior;
    }

    public void setCitaAnterior(Cita citaAnterior) {
        this.citaAnterior = citaAnterior;
    }

    public int getModalCita() {
        return modalCita;
    }

    public void setModalCita(int modalCita) {
        this.modalCita = modalCita;
    }

    public int getModalVacio() {
        return modalVacio;
    }

    public void setModalVacio(int modalVacio) {
        this.modalVacio = modalVacio;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(String horaActual) {
        this.horaActual = horaActual;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

}
