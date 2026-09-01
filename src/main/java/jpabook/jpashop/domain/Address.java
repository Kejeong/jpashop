package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable  // 이 클래스는 다른 엔티티에 포함해서 쓸 값이다
@Getter
public class Address {
    private String street;
    private String city;
    private String zipcode;

    protected Address() {  // @Setter는 제거하고, 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스 생성
        // JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를 public or protected로 설정하는데 후자가 더 안전함
    }

    public Address(String street, String city, String zipcode) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}
