package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  // Spring에서 Spring Bean으로 등록해줌
@RequiredArgsConstructor  // Repository도 생성자 주입이 가능함
public class MemberRepository {
    private final EntityManager em;  // spring이 Entity Manager를 만들어서 주입을 해줌

    public void save(Member member) {
        em.persist(member);  //jpa가 해당 멤버를 저장하는 로직을 해줌
    }

    public Member findOne(Long id) {  // 회원 단건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {  // 모든 회원 조회
       return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {  // 회원의 이름 조회
        return em.createQuery("select m from Member m Where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
