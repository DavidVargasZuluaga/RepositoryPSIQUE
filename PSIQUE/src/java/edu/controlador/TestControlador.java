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
    
    @Inject
    AprendizFacade aprendizFacade;

    int modalTest, modalTestAsignado ;
    Test testLog;
    Respuesta respuestaTem ;
    List<Pregunta> listaPreguntas;

    @PostConstruct
    public void init() {
        modalTest = 0;
        modalTestAsignado = 0;
        testLog = new Test();
        respuestaTem = new Respuesta();
        listaPreguntas = preguntaFacade.findAll();
    }
    
    public String asignarTest(Test t){
        String res = "mostrarTest.xhtml";
        Test testTemp = new Test();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String para = (String) params.get("aprendiz");
            long para2 = Long.parseLong(para);
            testTemp = t;
            testTemp.setIdTest(null);
            testTemp.setIdAprendiz(aprendizFacade.find(para2));
            testTemp.setEstado("ASIGNADO");
            testFacade.create(testTemp);
            modalTestAsignado = 1;
        } catch (Exception e) {
            e.printStackTrace();
            modalTestAsignado = 2;
        }
        return res;
    }
    
    public List<Test> listarTestPlantilla(){
        List<Test> tests = testFacade.findAll();
        List<Test> resTets = new ArrayList();
        for (int i = 0; i < tests.size(); i++) {
            if(tests.get(i).getEstado().equals("PLANTILLA")){
                resTets.add(tests.get(i));
            }
        }
        return resTets;
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
            testLog.setEstado("RESUELTO");
            modalTest = 1;
            testFacade.edit(testLog);
        } catch (Exception e) {
            modalTest = 2;
            e.printStackTrace();
        }
        return "notificacion.xhtml";
    }

    public List<Test> mostrarTestsAprendiz(Usuario u) {
        List<Test> listaTestA = new ArrayList();
        List<Test> listaTestT = testFacade.findAll();
        try {
            for (int i = 0; i < listaTestT.size(); i++) {
                if (listaTestT.get(i).getIdAprendiz().getUsuario().getIdUsuario().equals(u.getIdUsuario())) {
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
    
    public List<Test> listarTestAprendiz(Aprendiz a){
        List<Test> listaTestA = new ArrayList();
        List<Test> listaTestT = testFacade.findAll();
        try {
            for (int i = 0; i < listaTestT.size(); i++) {
                if(listaTestT.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())){
                    listaTestA.add(listaTestT.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTestA;
    }
    
    public void cerrarModal() {
        modalTest = 0;
        modalTestAsignado = 0;
    }

    public Test getTestLog() {
        return testLog;
    }

    public void setTestLog(Test testLog) {
        this.testLog = testLog;
    }

    public int getModalTest() {
        return modalTest;
    }

    public void setModalTest(int modalTest) {
        this.modalTest = modalTest;
    }

    public Respuesta getRespuestaTem() {
        return respuestaTem;
    }

    public void setRespuestaTem(Respuesta respuestaTem) {
        this.respuestaTem = respuestaTem;
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public int getModalTestAsignado() {
        return modalTestAsignado;
    }

    public void setModalTestAsignado(int modalTestAsignado) {
        this.modalTestAsignado = modalTestAsignado;
    }

}
