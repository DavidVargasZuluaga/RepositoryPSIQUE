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
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "testCortnolador")
@SessionScoped
public class TestCortnolador implements Serializable {

    @Inject
    TestFacade testFacade;

    Test testLog;

    @PostConstruct
    public void init() {
        testLog = new Test();
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

}
