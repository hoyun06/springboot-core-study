package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercentage = 10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) return price / discountPercentage;
        else return 0;
    }
}
