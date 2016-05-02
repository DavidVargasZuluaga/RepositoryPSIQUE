/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t"),
    @NamedQuery(name = "Test.findByIdTest", query = "SELECT t FROM Test t WHERE t.idTest = :idTest"),
    @NamedQuery(name = "Test.findByNombre", query = "SELECT t FROM Test t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Test.findByResultado", query = "SELECT t FROM Test t WHERE t.resultado = :resultado"),
    @NamedQuery(name = "Test.findByFecha", query = "SELECT t FROM Test t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Test.findByEstado", query = "SELECT t FROM Test t WHERE t.estado = :estado"),
    @NamedQuery(name = "Test.findByIdPlantilla", query = "SELECT t FROM Test t WHERE t.idPlantilla = :idPlantilla")})
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTest")
    private Integer idTest;
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 40)
    @Column(name = "resultado")
    private String resultado;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 15)
    @Column(name = "estado")
    private String estado;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "idPlantilla")
    private Integer idPlantilla;
    @JoinColumn(name = "idAprendiz", referencedColumnName = "idAprendiz")
    @ManyToOne
    private Aprendiz idAprendiz;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "test1")
    private Test test;
    @JoinColumn(name = "idTest", referencedColumnName = "idPlantilla", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Test test1;
    @OneToMany(mappedBy = "idTest")
    private List<Pregunta> preguntaList;

    public Test() {
    }

    public Test(Integer idTest) {
        this.idTest = idTest;
    }

    public Integer getIdTest() {
        return idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Aprendiz getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Aprendiz idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Test getTest1() {
        return test1;
    }

    public void setTest1(Test test1) {
        this.test1 = test1;
    }

    @XmlTransient
    public List<Pregunta> getPreguntaList() {
        return preguntaList;
    }

    public void setPreguntaList(List<Pregunta> preguntaList) {
        this.preguntaList = preguntaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTest != null ? idTest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.idTest == null && other.idTest != null) || (this.idTest != null && !this.idTest.equals(other.idTest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Test[ idTest=" + idTest + " ]";
    }
    
}
