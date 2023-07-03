package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("rateDiscountPolicy")    // 추가 구분자 부여. 만약 같은 타입의 빈이 2개 이상 조회되더라도 qualifier 를 통해 히나만 조회할 수 있음.
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercentage = 10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) return price / discountPercentage;
        else return 0;
    }
}
