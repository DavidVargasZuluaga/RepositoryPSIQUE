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
@Named(value = "testCortnolador")
@SessionScoped
public class TestControlador implements Serializable {

    @Inject
    UsuarioFacade usuarioFacade;
    
    @Inject
    TestFacade testFacade;

    @Inject
    RespuestaFacade respuestaFacade;

    @Inject
    PreguntaFacade preguntaFacade;

    @Inject
    AprendizFacade aprendizFacade;

    int modalTest, modalTestAsignado;
    Calendar fecha;
    Pregunta preguntaLog;
    Test testLog, testRegistro;
    Respuesta respuestaTem;
    List<Pregunta> listaPreguntas;

    @PostConstruct
    public void init() {
        modalTest = 0;
        modalTestAsignado = 0;
        fecha = GregorianCalendar.getInstance();
        testLog = new Test();
        testRegistro = testFacade.find(1);
        respuestaTem = new Respuesta();
        preguntaLog = new Pregunta();
        listaPreguntas = preguntaFacade.findAll();
    }

    public String crearTest() {
        String res = "crearPregunta.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Test test = new Test();
        try {
            test.setNombre((String) params.get("titulo"));
            test.setDescripcion((String) params.get("descripcion"));
            test.setEstado("PLANTILLA");
            testFacade.create(test);
            List<Test> listaTest = testFacade.findAll();
            modalTest = 3;
            int i = listaTest.size() - 1;
            testLog = listaTest.get(i);
        } catch (Exception e) {
            e.printStackTrace();
            modalTest = 4;
            res = " ";
        }
        return res;
    }

    public void eliminarPregunta(Pregunta pregunta) {
        testLog.getPreguntaList().remove(pregunta);
        preguntaFacade.remove(pregunta);
    }

    public void crearRespuesta() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Respuesta respuesta = new Respuesta();
        try {
            int valor = Integer.parseInt((String) params.get("valor"));
            respuesta.setIdPregunta(preguntaLog);
            respuesta.setTexto((String) params.get("texto"));
            respuesta.setValor(valor);
            respuestaFacade.create(respuesta);
            preguntaLog.getRespuestaList().add(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String terminarTest() {
        testLog = new Test();
        preguntaLog = new Pregunta();
        return "indexPsicologo.xhtml";
    }

    public String crearPregunta() {
        String res = "crearRespuesta.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Pregunta pregunta = new Pregunta();
        try {
            pregunta.setIdTest(testLog);
            pregunta.setTexto((String) params.get("pregunta"));
            preguntaFacade.create(pregunta);
            testLog.getPreguntaList().add(pregunta);
            preguntaLog = pregunta;
        } catch (Exception e) {
            e.printStackTrace();
            res = " ";
        }
        return res;
    }

    public String asignarTest(Test t) {
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
            testTemp.setIdPlantilla(testTemp.getIdTest());
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

    public String asignarTestApren(Aprendiz a) {
        String res = "perfilAprendiz.xhtml";
        Test testTemp = new Test();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String test = (String) params.get("testImp");
            int testt = Integer.parseInt(test);
            testTemp = testFacade.find(testt);
            testTemp.setIdPlantilla(testTemp.getIdTest());
            testTemp.setIdTest(null);
            testTemp.setIdAprendiz(a);
            testTemp.setEstado("ASIGNADO");
            testFacade.create(testTemp);
            modalTestAsignado = 1;
        } catch (Exception e) {
            e.printStackTrace();
            modalTestAsignado = 2;
        }
        return res;
    }

    public String eliminarTest(Test t) {
        testFacade.remove(t);
        return "mostrarTest.xhtml";
    }

    public List<Pregunta> listarPreguntas(Test t) {
        List<Pregunta> preguntas = new ArrayList();
        try {
            preguntas = t.getPreguntaList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    public List<Respuesta> listarRespuestas(Pregunta p) {
        List<Respuesta> respuestas = new ArrayList();
        try {
            respuestas = p.getRespuestaList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuestas;
    }

    public List<Test> listarTestPlantilla() {
        List<Test> tests = testFacade.findAll();
        List<Test> resTets = new ArrayList();
        try {
            for (int i = 0; i < tests.size(); i++) {
                if (tests.get(i).getEstado().equals("PLANTILLA")) {
                    resTets.add(tests.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resTets;
    }

    public List<Respuesta> listaRespuestasAprendiz(Pregunta p) {
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
                String resp = "" + params.get("pregunta" + "" + testLog.getPreguntaList().get(i).getIdPregunta());
                Integer idRespuesta = Integer.parseInt(resp);
                Respuesta respu = respuestaFacade.find(idRespuesta);
                suma = +respu.getValor();
            }
            testLog.setResultado("" + suma);
            testLog.setFecha(fecha.getTime());
            testLog.setEstado("RESUELTO");
            modalTest = 1;
            testFacade.edit(testLog);
        } catch (Exception e) {
            modalTest = 2;
            e.printStackTrace();
        }
        return "notificacion.xhtml";
    }

    public String respuesRegistro(Usuario u, Aprendiz a) {
        int suma = 0;
        boolean existe = false;
        Test t = new Test();
        List<Usuario> usuarios = usuarioFacade.findAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            for (int i = 0; i < testRegistro.getPreguntaList().size(); i++) {
                String resp = "" + params.get("pregunta" + "" + testRegistro.getPreguntaList().get(i).getIdPregunta());
                Integer idRespuesta = Integer.parseInt(resp);
                Respuesta respu = respuestaFacade.find(idRespuesta);
                suma = respu.getValor()+ suma;
            }
            for (int i = 0; i < usuarios.size(); i++) {
                if(usuarios.get(i).getNoDocumento() == u.getNoDocumento()){
                    existe = true;
                }
            }
            if(!existe){
            usuarioFacade.create(u);
            a.setIdAprendiz(u.getIdUsuario());
            aprendizFacade.create(a);
            t.setIdAprendiz(a);
            t = testRegistro;
            t.setIdTest(null);
            t.setResultado(""+suma);
            t.setFecha(fecha.getTime());
            t.setEstado("RESUELTO");
            testFacade.create(t);
            modalTest = 4;
            }
        } catch (Exception e) {
            modalTest = 5;
            e.printStackTrace();
        }
        return "/index.xhtml";
    }

    public List<Test> mostrarTestsAprendiz(Usuario u) {
        List<Test> listaTestA = new ArrayList();
        List<Test> listaTestT = testFacade.findAll();
        try {
            for (int i = 0; i < listaTestT.size(); i++) {
                if (listaTestT.get(i).getEstado().equals("ASIGNADO")) {
                    if (listaTestT.get(i).getIdAprendiz().getUsuario().getIdUsuario().equals(u.getIdUsuario())) {
                        listaTestA.add(listaTestT.get(i));
                    }
                }
            }
        } catch (Exception e) {
        }
        return listaTestA;
    }

    public List<Test> listarTestAprendiz(Aprendiz a) {
        List<Test> listaTestA = new ArrayList();
        List<Test> listaTestT = testFacade.findAll();
        try {
            for (int i = 0; i < listaTestT.size(); i++) {
                if (listaTestT.get(i).getEstado().equals("RESUELTO")) {
                    if (listaTestT.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                        listaTestA.add(listaTestT.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTestA;
    }

    public List<Pregunta> listarPreguntasPlantilla(Test t) {
        List<Pregunta> preguntas = new ArrayList();
        List<Pregunta> preguntasT = preguntaFacade.findAll();
        try {
            for (int i = 0; i < preguntasT.size(); i++) {
                if (t.getIdPlantilla().equals(preguntasT.get(i).getIdTest().getIdTest())) {
                    preguntas.add(preguntasT.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return preguntas;
    }

    public void eliminarRespuesta(Respuesta respuesta) {
        preguntaLog.getRespuestaList().remove(respuesta);
        respuestaFacade.remove(respuesta);
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

    public Pregunta getPreguntaLog() {
        return preguntaLog;
    }

    public void setPreguntaLog(Pregunta preguntaLog) {
        this.preguntaLog = preguntaLog;
    }

    public Test getTestRegistro() {
        return testRegistro;
    }

    public void setTestRegistro(Test testRegistro) {
        this.testRegistro = testRegistro;
    }

}
