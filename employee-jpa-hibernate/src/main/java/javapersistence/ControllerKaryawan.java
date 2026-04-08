package javapersistence;

import java.util.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class ControllerKaryawan {
    private EntityManagerFactory emf;

    public ControllerKaryawan() {
        this.emf = Persistence.createEntityManagerFactory("JPUKaryawan");
    }

    private EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE/INSERT
    public boolean insert(Karyawan obj) {
        EntityManager em = createEntityManager();
        boolean r = false;
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            r = true;
            System.out.println("✅ INSERT SUCCESS: " + obj.getNip());
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            System.out.println("❌ INSERT ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return r;
    }

    // READ - GET ALL
    @SuppressWarnings("unchecked")
    public Collection<Karyawan> getAllKaryawan() {
        EntityManager em = createEntityManager();
        Collection<Karyawan> result = null;
        try {
            Query qry = em.createNamedQuery("Karyawan.findAll");
            result = (Collection<Karyawan>) qry.getResultList();
            System.out.println("✅ GET ALL SUCCESS: " + result.size() + " records");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("❌ GET ALL ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return result;
    }

    // READ - GET BY NIP
    public Karyawan getKaryawan(String nip) {
        EntityManager em = createEntityManager();
        Karyawan result = null;
        try {
            result = em.find(Karyawan.class, nip);
            if (result != null) {
                System.out.println("✅ GET BY NIP SUCCESS: " + nip);
            } else {
                System.out.println("⚠️ GET BY NIP: Not found - " + nip);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("❌ GET BY NIP ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return result;
    }

    // READ - GET FILTERED (JPQL)
    @SuppressWarnings("unchecked")
    public Collection<Karyawan> getFilteredKaryawan(String sql) {
        EntityManager em = createEntityManager();
        Collection<Karyawan> result = null;
        try {
            Query qry = em.createQuery(sql);
            result = (Collection<Karyawan>) qry.getResultList();
            System.out.println("✅ FILTER SUCCESS: " + result.size() + " records");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("❌ FILTER ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return result;
    }

    // UPDATE
    public boolean update(Karyawan obj) {
        EntityManager em = createEntityManager();
        boolean r = false;
        try {
            em.getTransaction().begin();
            Karyawan managed = em.find(Karyawan.class, obj.getNip());
            if (managed != null) {
                managed.setNmKar(obj.getNmKar());
                managed.setTemLhr(obj.getTemLhr());
                managed.setTglLhr(obj.getTglLhr());
                managed.setJabatan(obj.getJabatan());
                em.merge(managed);
                em.getTransaction().commit();
                r = true;
                System.out.println("✅ UPDATE SUCCESS: " + obj.getNip());
            } else {
                em.getTransaction().rollback();
                System.out.println("❌ UPDATE FAILED: Data tidak ditemukan - " + obj.getNip());
            }
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            System.out.println("❌ UPDATE ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return r;
    }

    // DELETE
    public boolean delete(String nip) {
        EntityManager em = createEntityManager();
        boolean r = false;
        try {
            em.getTransaction().begin();
            Karyawan kar = em.find(Karyawan.class, nip);
            if (kar != null) {
                em.remove(kar);
                em.getTransaction().commit();
                r = true;
                System.out.println("✅ DELETE SUCCESS: " + nip);
            } else {
                em.getTransaction().rollback();
                System.out.println("❌ DELETE FAILED: Data tidak ditemukan - " + nip);
            }
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            System.out.println("❌ DELETE ERROR: " + ex.getMessage());
        } finally {
            em.close();
        }
        return r;
    }
}