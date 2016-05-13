/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DavidBrootal
 */
@Embeddable
public class FamiliarrelacionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idFamiliarParentezco")
    private long idFamiliarParentezco;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFamiliarRelacioncol")
    private long idFamiliarRelacioncol;

    public FamiliarrelacionPK() {
    }

    public FamiliarrelacionPK(long idFamiliarParentezco, long idFamiliarRelacioncol) {
        this.idFamiliarParentezco = idFamiliarParentezco;
        this.idFamiliarRelacioncol = idFamiliarRelacioncol;
    }

    public long getIdFamiliarParentezco() {
        return idFamiliarParentezco;
    }

    public void setIdFamiliarParentezco(long idFamiliarParentezco) {
        this.idFamiliarParentezco = idFamiliarParentezco;
    }

    public long getIdFamiliarRelacioncol() {
        return idFamiliarRelacioncol;
    }

    public void setIdFamiliarRelacioncol(long idFamiliarRelacioncol) {
        this.idFamiliarRelacioncol = idFamiliarRelacioncol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFamiliarParentezco;
        hash += (int) idFamiliarRelacioncol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FamiliarrelacionPK)) {
            return false;
        }
        FamiliarrelacionPK other = (FamiliarrelacionPK) object;
        if (this.idFamiliarParentezco != other.idFamiliarParentezco) {
            return false;
        }
        if (this.idFamiliarRelacioncol != other.idFamiliarRelacioncol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.entidad.FamiliarrelacionPK[ idFamiliarParentezco=" + idFamiliarParentezco + ", idFamiliarRelacioncol=" + idFamiliarRelacioncol + " ]";
    }
    
}
