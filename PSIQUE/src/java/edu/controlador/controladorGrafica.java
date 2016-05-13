/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.Aprendiz;
import edu.entidad.Cita;
import edu.entidad.Psicologo;
import edu.entidad.Usuario;
import edu.fachada.AprendizFacade;
import edu.fachada.CitaFacade;
import edu.fachada.PsicologoFacade;
import edu.fachada.UsuarioFacade;
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
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "controladorGrafica")
@SessionScoped
public class controladorGrafica implements Serializable {

    @EJB
    private CitaFacade citaFacade;
    private List<Cita> listaCita;
    private List<Cita> listaCitaAprendiz;//Aprendices
    private List<Cita> listaCitaSeguimiento;//

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private AprendizFacade aprendizFacade;
    private Aprendiz aprendiz;
    private List<Aprendiz> listaAprendiz;
    private int num1 = 80;

    @EJB
    private PsicologoFacade psicologoFacade;
    private Psicologo psicologo;
    private List<Psicologo> listaPsicologo;
    private String nombrePsicologo;
    private String nombreAprendiz;
    int excelente;
    int buena;
    int aceptable;
    int podriaMejorar;
    int mala;

    private String CANCELADA;
    private String CUMPLIDA;
    private String INCUMPLIDA;
    int tempCancelada;
    int tempIncumplida;
    int tempCumplida;

    private int año, mes, dia, hora, minuto, segundo, mesAnterior;
    private String fechaActual, horaActual;
    private Calendar fecha;

    Date fecha2 = new Date();

    //Grafica Seguimiento aprendiz
    String CanceladaEnero;
    String CumplidaEnero;
    String IncumplidaEnero;

    String CanceladaFebrero;
    String CumplidaFebrero;
    String IncumplidaFebrero;

    String CanceladaMarzo;
    String CumplidaMarzo;
    String IncumplidaMarzo;

    String CanceladaAbril;
    String CumplidaAbril;
    String IncumplidaAbril;

    String CanceladaMayo;
    String CumplidaMayo;
    String IncumplidaMayo;

    String CanceladaJunio;
    String CumplidaJunio;
    String IncumplidaJunio;

    String CanceladaJulio;
    String CumplidaJulio;
    String IncumplidaJulio;

    String CanceladaAgosto;
    String CumplidaAgosto;
    String IncumplidaAgosto;

    String CanceladaSeptiembre;
    String CumplidaSeptiembre;
    String IncumplidaSeptiembre;

    String CanceladaOctubre;
    String CumplidaOctubre;
    String IncumplidaOctubre;

    String CanceladaNoviembre;
    String CumplidaNoviembre;
    String IncumplidaNoviembre;

    String CanceladaDiciembre;
    String CumplidaDiciembre;
    String IncumplidaDiciembre;

    public controladorGrafica() {
        listaAprendiz = new ArrayList<>();
        listaPsicologo = new ArrayList<>();
        listaCita = new ArrayList<>();
        listaCitaAprendiz = new ArrayList<>();
        listaCitaSeguimiento = new ArrayList<>();
        aprendiz = new Aprendiz();
        psicologo = new Psicologo();
        nombrePsicologo = "";
        nombreAprendiz = "";
        excelente = 0;
        buena = 0;
        aceptable = 0;
        podriaMejorar = 0;
        mala = 0;
        CANCELADA = "";
        CUMPLIDA = "";
        INCUMPLIDA = "";
        tempCancelada = 0;
        tempIncumplida = 0;
        tempCumplida = 0;

        fecha = GregorianCalendar.getInstance();
        año = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH) + 1;
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        hora = fecha.get(Calendar.HOUR_OF_DAY);
        minuto = fecha.get(Calendar.MINUTE);
        segundo = fecha.get(Calendar.SECOND);
        fechaActual = (dia + "/" + mes);
        horaActual = (+hora + " : " + minuto);

        CanceladaEnero = "";
        CumplidaEnero = "";
        IncumplidaEnero = "";

        CanceladaFebrero = "";
        CumplidaFebrero = "";
        IncumplidaFebrero = "";

        CanceladaMarzo = "";
        CumplidaMarzo = "";
        IncumplidaMarzo = "";

        CanceladaAbril = "";
        CumplidaAbril = "";
        IncumplidaAbril = "";

        CanceladaMayo = "";
        CumplidaMayo = "";
        IncumplidaMayo = "";

        CanceladaJunio = "";
        CumplidaJunio = "";
        IncumplidaJunio = "";

        CanceladaJulio = "";
        CumplidaJulio = "";
        IncumplidaJulio = "";

        CanceladaAgosto = "";
        CumplidaAgosto = "";
        IncumplidaAgosto = "";

        CanceladaSeptiembre = "";
        CumplidaSeptiembre = "";
        IncumplidaSeptiembre = "";

        CanceladaOctubre = "";
        CumplidaOctubre = "";
        IncumplidaOctubre = "";

        CanceladaNoviembre = "";
        CumplidaNoviembre = "";
        IncumplidaNoviembre = "";

        CanceladaDiciembre = "";
        CumplidaDiciembre = "";
        IncumplidaDiciembre = "";
    }

    public List<Psicologo> traerPsicologos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();

        String pi = (String) params.get("psicologo");
//        if (pi == null) {
//            pi = "1000";
//        }
        listaPsicologo = psicologoFacade.findAll();
        List<Psicologo> listapsi = new ArrayList();

        try {
            long idpsicologo = Long.parseLong(pi);
            nombrePsicologo = psicologoFacade.find(idpsicologo).getUsuario().getNombres() + " " + psicologoFacade.find(idpsicologo).getUsuario().getPrimerApellido();
            for (int i = 0; i < listaPsicologo.size(); i++) {
                if (listaPsicologo.get(i).getIdPsicologo().equals(idpsicologo)) {
                    listapsi.add(listaPsicologo.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPsicologo;
    }

    public void valoracion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        try {
            String pi = (String) params.get("obj");
            long idpsicologo = Long.parseLong(pi);
            nombrePsicologo = psicologoFacade.find(idpsicologo).getUsuario().getNombres() + " " + psicologoFacade.find(idpsicologo).getUsuario().getPrimerApellido();
            List<Cita> lista = new ArrayList();
            listaCita = citaFacade.findAll();

            excelente = 0;
            buena = 0;
            aceptable = 0;
            podriaMejorar = 0;
            mala = 0;

            for (int i = 0; i < listaCita.size(); i++) {
                if (listaCita.get(i).getIdPsicologo().getIdPsicologo().equals(idpsicologo)) {
                    switch (listaCita.get(i).getValoracion()) {
                        case 5:
                            excelente = excelente + listaCita.get(i).getValoracion() / listaCita.get(i).getValoracion();
                            break;
                        case 4:
                            buena = buena + listaCita.get(i).getValoracion() / listaCita.get(i).getValoracion();
                            break;
                        case 3:
                            aceptable = aceptable + listaCita.get(i).getValoracion() / listaCita.get(i).getValoracion();
                            break;
                        case 2:
                            podriaMejorar = podriaMejorar + listaCita.get(i).getValoracion() / listaCita.get(i).getValoracion();
                            break;
                        case 1:
                            mala = mala + listaCita.get(i).getValoracion() / listaCita.get(i).getValoracion();
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Aprendiz> traerAprendices() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();

        String pi = (String) params.get("psicologo");

        listaAprendiz = aprendizFacade.findAll();
        List<Aprendiz> listaApre = new ArrayList();

        try {
            long idAprendiz = Long.parseLong(pi);
            nombreAprendiz = aprendizFacade.find(idAprendiz).getUsuario().getNombres() + " " + psicologoFacade.find(idAprendiz).getUsuario().getPrimerApellido();
            for (int i = 0; i < listaAprendiz.size(); i++) {
                if (listaAprendiz.get(i).getIdAprendiz().equals(idAprendiz)) {
                    listaApre.add(listaAprendiz.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAprendiz;
    }

    public void citas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        try {
            String apr = (String) params.get("objApre");
            long idaprendiz = Long.parseLong(apr);
            nombreAprendiz = aprendizFacade.find(idaprendiz).getUsuario().getNombres() + " " + aprendizFacade.find(idaprendiz).getUsuario().getPrimerApellido();
            listaCitaAprendiz = citaFacade.findAll();

            tempCancelada = 0;
            tempIncumplida = 0;
            tempCumplida = 0;
            for (int i = 0; i < listaCitaAprendiz.size(); i++) {
                if (listaCitaAprendiz.get(i).getIdAprendiz().getIdAprendiz().equals(idaprendiz)) {
                    switch (listaCitaAprendiz.get(i).getEstado()) {
                        case "CANCELADA"://"CUMPLIDA":
                            tempCancelada++;
                            CANCELADA = Integer.toString(tempCancelada);
                            break;
                        case "INCUMPLIDA":
                            tempIncumplida++;
                            INCUMPLIDA = Integer.toString(tempIncumplida);
                            break;
                        case "CUMPLIDA":
                            tempCumplida++;
                            CUMPLIDA = Integer.toString(tempCumplida);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cogerCitasMensuales() {
        try {
            List<Cita> lista = new ArrayList();
            listaCita = citaFacade.findAll();
            SimpleDateFormat fechaComparar = new SimpleDateFormat("yyyy");
            for (int i = 0; i < listaCita.size(); i++) {
                int e = Integer.parseInt(fechaComparar.format(listaCita.get(i).getFecha()));
                if (e == año) {
                    switch (listaCita.get(i).getFecha().getMonth()) {
                        case 0:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Enero++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaAprendiz.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaEnero = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaEnero = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaEnero = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 1:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Febrero++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaFebrero = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaFebrero = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaFebrero = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 2:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Marzo++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaMarzo = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaMarzo = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaMarzo = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 3:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            // Abril++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaAbril = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaAbril = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaAbril = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 4:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Mayo++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaMayo = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaMayo = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaMayo = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 5:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Junio++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaJunio = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaJunio = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaJunio = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 6:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Julio++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaJulio = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaJulio = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaJulio = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 7:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Agosto++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaAgosto = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaAgosto = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaAgosto = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 8:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Septiember++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaSeptiembre = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaSeptiembre = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaSeptiembre = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 9:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Octubre++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaOctubre = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaOctubre = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaOctubre = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 10:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Noviembre++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaNoviembre = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaNoviembre = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaNoviembre = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                        case 11:
                            tempCancelada = 0;
                            tempIncumplida = 0;
                            tempCumplida = 0;
                            listaCitaSeguimiento = citaFacade.findAll();
                            //Diciembre++;
                            for (int j = 0; j < listaCitaSeguimiento.size(); j++) {
                                switch (listaCitaSeguimiento.get(i).getEstado()) {
                                    case "CANCELADA"://"CUMPLIDA":
                                        //tempCancelada++;
                                        tempCancelada++;
                                        CanceladaDiciembre = Integer.toString(tempCancelada);
                                        break;
                                    case "INCUMPLIDA":
                                        tempIncumplida++;
                                        IncumplidaDiciembre = Integer.toString(tempIncumplida);
                                        break;
                                    case "CUMPLIDA":
                                        tempCumplida++;
                                        CumplidaDiciembre = Integer.toString(tempCumplida);
                                        break;
                                }
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Aprendiz getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public List<Aprendiz> getListaAprendiz() {
        return listaAprendiz = aprendizFacade.findAll();
    }

    public void setListaAprendiz(List<Aprendiz> listaAprendiz) {
        this.listaAprendiz = listaAprendiz;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getExcelente() {
        return excelente;
    }

    public void setExcelente(int excelente) {
        this.excelente = excelente;
    }

    public int getBuena() {
        return buena;
    }

    public void setBuena(int buena) {
        this.buena = buena;
    }

    public int getAceptable() {
        return aceptable;
    }

    public void setAceptable(int aceptable) {
        this.aceptable = aceptable;
    }

    public int getPodriaMejorar() {
        return podriaMejorar;
    }

    public void setPodriaMejorar(int podriaMejorar) {
        this.podriaMejorar = podriaMejorar;
    }

    public int getMala() {
        return mala;
    }

    public void setMala(int mala) {
        this.mala = mala;
    }

    public String getNombrePsicologo() {
        return nombrePsicologo;
    }

    public void setNombrePsicologo(String nombrePsicologo) {
        this.nombrePsicologo = nombrePsicologo;
    }

    public String getNombreAprendiz() {
        return nombreAprendiz;
    }

    public void setNombreAprendiz(String nombreAprendiz) {
        this.nombreAprendiz = nombreAprendiz;
    }

    public String getCANCELADA() {
        return CANCELADA;
    }

    public void setCANCELADA(String CANCELADA) {
        this.CANCELADA = CANCELADA;
    }

    public String getCUMPLIDA() {
        return CUMPLIDA;
    }

    public void setCUMPLIDA(String CUMPLIDA) {
        this.CUMPLIDA = CUMPLIDA;
    }

    public String getINCUMPLIDA() {
        return INCUMPLIDA;
    }

    public void setINCUMPLIDA(String INCUMPLIDA) {
        this.INCUMPLIDA = INCUMPLIDA;
    }

    public int getTempCancelada() {
        return tempCancelada;
    }

    public void setTempCancelada(int tempCancelada) {
        this.tempCancelada = tempCancelada;
    }

    public int getTempIncumplida() {
        return tempIncumplida;
    }

    public void setTempIncumplida(int tempIncumplida) {
        this.tempIncumplida = tempIncumplida;
    }

    public int getTempCumplida() {
        return tempCumplida;
    }

    public void setTempCumplida(int tempCumplida) {
        this.tempCumplida = tempCumplida;
    }

    public String getCanceladaEnero() {
        return CanceladaEnero;
    }

    public void setCanceladaEnero(String CanceladaEnero) {
        this.CanceladaEnero = CanceladaEnero;
    }

    public String getCumplidaEnero() {
        return CumplidaEnero;
    }

    public void setCumplidaEnero(String CumplidaEnero) {
        this.CumplidaEnero = CumplidaEnero;
    }

    public String getIncumplidaEnero() {
        return IncumplidaEnero;
    }

    public void setIncumplidaEnero(String IncumplidaEnero) {
        this.IncumplidaEnero = IncumplidaEnero;
    }

    public String getCanceladaFebrero() {
        return CanceladaFebrero;
    }

    public void setCanceladaFebrero(String CanceladaFebrero) {
        this.CanceladaFebrero = CanceladaFebrero;
    }

    public String getCumplidaFebrero() {
        return CumplidaFebrero;
    }

    public void setCumplidaFebrero(String CumplidaFebrero) {
        this.CumplidaFebrero = CumplidaFebrero;
    }

    public String getIncumplidaFebrero() {
        return IncumplidaFebrero;
    }

    public void setIncumplidaFebrero(String IncumplidaFebrero) {
        this.IncumplidaFebrero = IncumplidaFebrero;
    }

    public String getCanceladaMarzo() {
        return CanceladaMarzo;
    }

    public void setCanceladaMarzo(String CanceladaMarzo) {
        this.CanceladaMarzo = CanceladaMarzo;
    }

    public String getCumplidaMarzo() {
        return CumplidaMarzo;
    }

    public void setCumplidaMarzo(String CumplidaMarzo) {
        this.CumplidaMarzo = CumplidaMarzo;
    }

    public String getIncumplidaMarzo() {
        return IncumplidaMarzo;
    }

    public void setIncumplidaMarzo(String IncumplidaMarzo) {
        this.IncumplidaMarzo = IncumplidaMarzo;
    }

    public String getCanceladaAbril() {
        return CanceladaAbril;
    }

    public void setCanceladaAbril(String CanceladaAbril) {
        this.CanceladaAbril = CanceladaAbril;
    }

    public String getCumplidaAbril() {
        return CumplidaAbril;
    }

    public void setCumplidaAbril(String CumplidaAbril) {
        this.CumplidaAbril = CumplidaAbril;
    }

    public String getIncumplidaAbril() {
        return IncumplidaAbril;
    }

    public void setIncumplidaAbril(String IncumplidaAbril) {
        this.IncumplidaAbril = IncumplidaAbril;
    }

    public String getCanceladaMayo() {
        return CanceladaMayo;
    }

    public void setCanceladaMayo(String CanceladaMayo) {
        this.CanceladaMayo = CanceladaMayo;
    }

    public String getCumplidaMayo() {
        return CumplidaMayo;
    }

    public void setCumplidaMayo(String CumplidaMayo) {
        this.CumplidaMayo = CumplidaMayo;
    }

    public String getIncumplidaMayo() {
        return IncumplidaMayo;
    }

    public void setIncumplidaMayo(String IncumplidaMayo) {
        this.IncumplidaMayo = IncumplidaMayo;
    }

    public String getCanceladaJunio() {
        return CanceladaJunio;
    }

    public void setCanceladaJunio(String CanceladaJunio) {
        this.CanceladaJunio = CanceladaJunio;
    }

    public String getCumplidaJunio() {
        return CumplidaJunio;
    }

    public void setCumplidaJunio(String CumplidaJunio) {
        this.CumplidaJunio = CumplidaJunio;
    }

    public String getIncumplidaJunio() {
        return IncumplidaJunio;
    }

    public void setIncumplidaJunio(String IncumplidaJunio) {
        this.IncumplidaJunio = IncumplidaJunio;
    }

    public String getCanceladaJulio() {
        return CanceladaJulio;
    }

    public void setCanceladaJulio(String CanceladaJulio) {
        this.CanceladaJulio = CanceladaJulio;
    }

    public String getCumplidaJulio() {
        return CumplidaJulio;
    }

    public void setCumplidaJulio(String CumplidaJulio) {
        this.CumplidaJulio = CumplidaJulio;
    }

    public String getIncumplidaJulio() {
        return IncumplidaJulio;
    }

    public void setIncumplidaJulio(String IncumplidaJulio) {
        this.IncumplidaJulio = IncumplidaJulio;
    }

    public String getCanceladaAgosto() {
        return CanceladaAgosto;
    }

    public void setCanceladaAgosto(String CanceladaAgosto) {
        this.CanceladaAgosto = CanceladaAgosto;
    }

    public String getCumplidaAgosto() {
        return CumplidaAgosto;
    }

    public void setCumplidaAgosto(String CumplidaAgosto) {
        this.CumplidaAgosto = CumplidaAgosto;
    }

    public String getIncumplidaAgosto() {
        return IncumplidaAgosto;
    }

    public void setIncumplidaAgosto(String IncumplidaAgosto) {
        this.IncumplidaAgosto = IncumplidaAgosto;
    }

    public String getCanceladaSeptiembre() {
        return CanceladaSeptiembre;
    }

    public void setCanceladaSeptiembre(String CanceladaSeptiembre) {
        this.CanceladaSeptiembre = CanceladaSeptiembre;
    }

    public String getCumplidaSeptiembre() {
        return CumplidaSeptiembre;
    }

    public void setCumplidaSeptiembre(String CumplidaSeptiembre) {
        this.CumplidaSeptiembre = CumplidaSeptiembre;
    }

    public String getIncumplidaSeptiembre() {
        return IncumplidaSeptiembre;
    }

    public void setIncumplidaSeptiembre(String IncumplidaSeptiembre) {
        this.IncumplidaSeptiembre = IncumplidaSeptiembre;
    }

    public String getCanceladaOctubre() {
        return CanceladaOctubre;
    }

    public void setCanceladaOctubre(String CanceladaOctubre) {
        this.CanceladaOctubre = CanceladaOctubre;
    }

    public String getCumplidaOctubre() {
        return CumplidaOctubre;
    }

    public void setCumplidaOctubre(String CumplidaOctubre) {
        this.CumplidaOctubre = CumplidaOctubre;
    }

    public String getIncumplidaOctubre() {
        return IncumplidaOctubre;
    }

    public void setIncumplidaOctubre(String IncumplidaOctubre) {
        this.IncumplidaOctubre = IncumplidaOctubre;
    }

    public String getCanceladaNoviembre() {
        return CanceladaNoviembre;
    }

    public void setCanceladaNoviembre(String CanceladaNoviembre) {
        this.CanceladaNoviembre = CanceladaNoviembre;
    }

    public String getCumplidaNoviembre() {
        return CumplidaNoviembre;
    }

    public void setCumplidaNoviembre(String CumplidaNoviembre) {
        this.CumplidaNoviembre = CumplidaNoviembre;
    }

    public String getIncumplidaNoviembre() {
        return IncumplidaNoviembre;
    }

    public void setIncumplidaNoviembre(String IncumplidaNoviembre) {
        this.IncumplidaNoviembre = IncumplidaNoviembre;
    }

    public String getCanceladaDiciembre() {
        return CanceladaDiciembre;
    }

    public void setCanceladaDiciembre(String CanceladaDiciembre) {
        this.CanceladaDiciembre = CanceladaDiciembre;
    }

    public String getCumplidaDiciembre() {
        return CumplidaDiciembre;
    }

    public void setCumplidaDiciembre(String CumplidaDiciembre) {
        this.CumplidaDiciembre = CumplidaDiciembre;
    }

    public String getIncumplidaDiciembre() {
        return IncumplidaDiciembre;
    }

    public void setIncumplidaDiciembre(String IncumplidaDiciembre) {
        this.IncumplidaDiciembre = IncumplidaDiciembre;
    }

}
