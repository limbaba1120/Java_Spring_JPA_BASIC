package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("kim");
            member.setAge(0);
            member.setType(MemberType.ADMIN);
            em.persist(member);



            em.flush();
            em.clear();

            List<Object[]> result = em.createQuery("select m.username, true, 'Hello' from Member m where m.type = jpql.MemberType.ADMIN")
                    .getResultList();
            for (Object[] objects : result) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
