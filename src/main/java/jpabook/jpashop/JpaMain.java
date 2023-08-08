package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            MemberEX memberEX = new MemberEX();
            memberEX.setName("member1");
            memberEX.setHomeAddress(new AddressEX("city1", "zip"));

            memberEX.getFavoriteFoods().add("치킨");
            memberEX.getFavoriteFoods().add("족발");
            memberEX.getFavoriteFoods().add("피자");

            memberEX.getAddressHistory().add(new AddressEX("old1", "zip"));
            memberEX.getAddressHistory().add(new AddressEX("old2", "zip"));

            em.persist(memberEX);

            em.flush();
            em.clear();

            MemberEX findMember = em.find(MemberEX.class, memberEX.getId());

            //homeCity -> newCity
            AddressEX a = findMember.getHomeAddress();
            findMember.setHomeAddress(new AddressEX("newCity", a.getZipcode()));
            //치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new AddressEX("old1", "zip"));
            findMember.getAddressHistory().add(new AddressEX("newCity1", "zip"));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
