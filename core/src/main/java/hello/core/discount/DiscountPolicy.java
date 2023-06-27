package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price); // 얼만큼 할인되는지 그 금액을 반환
}
