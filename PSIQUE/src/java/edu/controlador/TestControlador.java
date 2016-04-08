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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "testCortnolador")
@SessionScoped
public class TestControlador implements Serializable {

    @Inject
    TestFacade testFacade;

    @Inject
    RespuestaFacade respuestaFacade;
    
    @Inject
    PreguntaFacade preguntaFacade;

    Test testLog;
    Respuesta respuestaTem ;
    List<Pregunta> listaPreguntas;

    @PostConstruct
    public void init() {
        testLog = new Test();
        respuestaTem = new Respuesta();
        listaPreguntas = preguntaFacade.findAll();
    }
    
    public List<Respuesta> listaRespuestasAprendiz(Pregunta p){
        return p.getRespuestaList();
    }

    public String respuestraTestAp() {
        int suma = 0;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            for (int i = 0; i < testLog.getPreguntaList().size(); i++) {
                String resp = ""+params.get("pregunta"+""+testLog.getPreguntaList().get(i).getIdPregunta());
                Integer idRespuesta = Integer.parseInt(resp);
                Respuesta respu = respuestaFacade.find(idRespuesta);
                suma =+ respu.getValor();
            }
            testLog.setResultado(""+suma);
            testFacade.edit(testLog);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR !!!");
        }
        return "notificacion.xhtml";
    }

    public List<Test> mostrarTestsAprendiz(Usuario u) {
        List<Test> listaTestA = new ArrayList();
        List<Test> listaTestT = testFacade.findAll();
        try {
            for (int i = 0; i < listaTestT.size(); i++) {
                if (listaTestT.get(i).getIdAprendiz().getUsuario().equals(u)) {
                    if (listaTestT.get(i).getEstado().equals("ASIGNADO")) {
                        listaTestA.add(listaTestT.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTestA;
    }

    public Test getTestLog() {
        return testLog;
    }

    public void setTestLog(Test testLog) {
        this.testLog = testLog;
    }

}
