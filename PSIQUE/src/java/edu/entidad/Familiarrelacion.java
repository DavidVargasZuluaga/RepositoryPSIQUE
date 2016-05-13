/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidBrootal
 */
@Entity
@Table(name = "familiarrelacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiarrelacion.findAll", query = "SELECT f FROM Familiarrelacion f"),
    @NamedQuery(name = "Familiarrelacion.findByParentezco", query = "SELECT f FROM Familiarrelacion f WHERE f.parentezco = :parentezco"),
    @NamedQuery(name = "Familiarrelacion.findByIdFamiliarParentezco", query = "SELECT f FROM Familiarrelacion f WHERE f.familiarrelacionPK.idFamiliarParentezco = :idFamiliarParentezco"),
    @NamedQuery(name = "Familiarrelacion.findByIdFamiliarRelacioncol", query = "SELECT f FROM Familiarrelacion f WHERE f.familiarrelacionPK.idFamiliarRelacioncol = :idFamiliarRelacioncol")})
public class Familiarrelacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FamiliarrelacionPK familiarrelacionPK;
    @Size(max = 25)
    @Column(name = "parentezco")
    private String parentezco;
    @JoinColumn(name = "idFamiliarParentezco", referencedColumnName = "idFamiliar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Familiar familiar;
    @JoinColumn(name = "idFamiliarRelacioncol", referencedColumnName = "idFamiliar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Familiar familiar1;

    public Familiarrelacion() {
    }

    public Familiarrelacion(FamiliarrelacionPK familiarrelacionPK) {
        this.familiarrelacionPK = familiarrelacionPK;
    }

    public Familiarrelacion(long idFamiliarParentezco, long idFamiliarRelacioncol) {
        this.familiarrelacionPK = new FamiliarrelacionPK(idFamiliarParentezco, idFamiliarRelacioncol);
    }

    public FamiliarrelacionPK getFamiliarrelacionPK() {
        return familiarrelacionPK;
    }

    public void setFamiliarrelacionPK(FamiliarrelacionPK familiarrelacionPK) {
        this.familiarrelacionPK = familiarrelacionPK;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public Familiar getFamiliar() {
        return familiar;
    }

    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    public Familiar getFamiliar1() {
        return familiar1;
    }

    public void setFamiliar1(Familiar familiar1) {
        this.familiar1 = familiar1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familiarrelacionPK != null ? familiarrelacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Familiarrelacion)) {
            return false;
        }
        Familiarrelacion other = (Familiarrelacion) object;
        if ((this.familiarrelacionPK == null && other.familiarrelacionPK != null) || (this.familiarrelacionPK != null && !this.familiarrelacionPK.equals(other.familiarrelacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.Familiarrelacion[ familiarrelacionPK=" + familiarrelacionPK + " ]";
    }
    
}
