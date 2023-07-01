package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy; // 추상화가 아닌 구현에도 의존할시 할인 정책 변경하면
                                                 // 클라이언트 코드도 변경(OCP 위반)
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member findMember = memberRepository.findById(memberId);
        int discountAmount = discountPolicy.discount(findMember, itemPrice);
        return new Order(memberId,itemName,itemPrice,discountAmount);
    }
    
    // 싱글톤 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
