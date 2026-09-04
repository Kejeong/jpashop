package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")  // 실무에서 작업할 때는 Entity에 NotEmpty를 사용하기보다는 form에서 화면에서 검증해야함(필요한 데이터만 넘기도록)
    private String name;

    private String city;

    private String street;

    private String zipcode;
}
