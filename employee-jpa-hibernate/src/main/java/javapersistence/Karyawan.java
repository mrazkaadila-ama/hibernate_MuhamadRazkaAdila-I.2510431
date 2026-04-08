package javapersistence;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "karyawan")
@NamedQueries({
    @NamedQuery(name = "Karyawan.findAll", query = "SELECT k FROM Karyawan k"),
    @NamedQuery(name = "Karyawan.findByNip", query = "SELECT k FROM Karyawan k WHERE k.nip = :nip"),
    @NamedQuery(name = "Karyawan.findByNmKar", query = "SELECT k FROM Karyawan k WHERE k.nmKar = :nmKar"),
    @NamedQuery(name = "Karyawan.findByTemLhr", query = "SELECT k FROM Karyawan k WHERE k.temLhr = :temLhr"),
    @NamedQuery(name = "Karyawan.findByTglLhr", query = "SELECT k FROM Karyawan k WHERE k.tglLhr = :tglLhr"),
    @NamedQuery(name = "Karyawan.findByJabatan", query = "SELECT k FROM Karyawan k WHERE k.jabatan = :jabatan")
})
public class Karyawan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "nip")
    private String nip;

    @Column(name = "nm_kar")
    private String nmKar;

    @Column(name = "tem_lhr")
    private String temLhr;

    @Column(name = "tgl_lhr")
    @Temporal(TemporalType.DATE)
    private Date tglLhr;

    @Column(name = "jabatan")
    private String jabatan;

    public Karyawan() {}

    public Karyawan(String nip) {
        this.nip = nip;
    }

    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }

    public String getNmKar() { return nmKar; }
    public void setNmKar(String nmKar) { this.nmKar = nmKar; }

    public String getTemLhr() { return temLhr; }
    public void setTemLhr(String temLhr) { this.temLhr = temLhr; }

    public Date getTglLhr() { return tglLhr; }
    public void setTglLhr(Date tglLhr) { this.tglLhr = tglLhr; }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String jabatan) { this.jabatan = jabatan; }

    @Override
    public String toString() {
        return "Karyawan[nip=" + nip + "]";
    }
}