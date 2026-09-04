package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // 읽기전용 트랜잭션 -> 읽기용 모드로 되어 DB 최적화에 좋음 (기본메서드)
@RequiredArgsConstructor  // 자동으로 생성자 주입
public class MemberService {  // Data가 변경이 되는것이 있으면 @Transactional 을 추가해야함

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);  // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원검증
     */
    private void validateDuplicateMember(Member member) {  // 실무에서는 동시성 제어, 멀티스레드도 확인
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");  // 이름 비교보다는 카운트로 세는게 최적화 방면에서는 좋음
        }
    }

    /**
     * 회원 전체조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
