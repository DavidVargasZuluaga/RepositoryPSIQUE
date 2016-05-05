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
import java.util.ArrayList;
import java.util.Date;
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
    String nombrePsicologo;
    int excelente;
    int buena;
    int aceptable;
    int podriaMejorar;
    int mala;

    Date fecha = new Date();

    int Enero;
    int Febrero;
    int Marzo;
    int Abril;
    int Mayo;
    int Junio;
    int Julio;
    int Agosto;
    int Septiember;
    int Octubre;
    int Noviembre;
    int Diciembre;

    public controladorGrafica() {
        listaAprendiz = new ArrayList<>();
        listaPsicologo = new ArrayList<>();
        listaCita = new ArrayList<>();
        aprendiz = new Aprendiz();
        psicologo = new Psicologo();
        nombrePsicologo = "";
        excelente = 0;
        buena = 0;
        aceptable = 0;
        podriaMejorar = 0;
        mala = 0;

        Enero = 0;
        Febrero = 0;
        Marzo = 0;
        Abril = 0;
        Mayo = 0;
        Junio = 0;
        Julio = 0;
        Agosto = 0;
        Septiember = 0;
        Octubre = 0;
        Noviembre = 0;
        Diciembre = 0;
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

    public void cogerCitasMensuales() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();

//        String pi = (String) params.get("obj");
//        long idpsicologo = Long.parseLong(pi);
//        nombrePsicologo = psicologoFacade.find(idpsicologo).getUsuario().getNombres() + " " + psicologoFacade.find(idpsicologo).getUsuario().getPrimerApellido();
        List<Cita> lista = new ArrayList();
        listaCita = citaFacade.findAll();

        Enero = 0;
        Febrero = 0;
        Marzo = 0;
        Abril = 0;
        Mayo = 0;
        Junio = 0;
        Julio = 0;
        Agosto = 0;
        Septiember = 0;
        Octubre = 0;
        Noviembre = 0;
        Diciembre = 0;

        for (int i = 0; i < listaCita.size(); i++) {
            if (listaCita.get(i).getFecha().getYear() == fecha.getYear()) {
                switch (listaCita.get(i).getFecha().getMonth()) {
                    case 1:
                        Enero++;
                        break;
                    case 2:
                        Febrero++;
                        break;
                    case 3:
                        Marzo++;
                        break;
                    case 4:
                        Abril++;
                        break;
                    case 5:
                        Mayo++;
                        break;
                    case 6:
                        Junio++;
                        break;
                    case 7:
                        Julio++;
                        break;
                    case 8:
                        Agosto++;
                        break;
                    case 9:
                        Septiember++;
                        break;
                    case 10:
                        Octubre++;
                        break;
                    case 11:
                        Noviembre++;
                        break;
                    case 12:
                        Diciembre++;
                        break;
                }
            }
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

    public int getEnero() {
        return Enero;
    }

    public void setEnero(int Enero) {
        this.Enero = Enero;
    }

    public int getFebrero() {
        return Febrero;
    }

    public void setFebrero(int Febrero) {
        this.Febrero = Febrero;
    }

    public int getMarzo() {
        return Marzo;
    }

    public void setMarzo(int Marzo) {
        this.Marzo = Marzo;
    }

    public int getAbril() {
        return Abril;
    }

    public void setAbril(int Abril) {
        this.Abril = Abril;
    }

    public int getMayo() {
        return Mayo;
    }

    public void setMayo(int Mayo) {
        this.Mayo = Mayo;
    }

    public int getJunio() {
        return Junio;
    }

    public void setJunio(int Junio) {
        this.Junio = Junio;
    }

    public int getJulio() {
        return Julio;
    }

    public void setJulio(int Julio) {
        this.Julio = Julio;
    }

    public int getAgosto() {
        return Agosto;
    }

    public void setAgosto(int Agosto) {
        this.Agosto = Agosto;
    }

    public int getSeptiember() {
        return Septiember;
    }

    public void setSeptiember(int Septiember) {
        this.Septiember = Septiember;
    }

    public int getOctubre() {
        return Octubre;
    }

    public void setOctubre(int Octubre) {
        this.Octubre = Octubre;
    }

    public int getNoviembre() {
        return Noviembre;
    }

    public void setNoviembre(int Noviembre) {
        this.Noviembre = Noviembre;
    }

    public int getDiciembre() {
        return Diciembre;
    }

    public void setDiciembre(int Diciembre) {
        this.Diciembre = Diciembre;
    }

}
