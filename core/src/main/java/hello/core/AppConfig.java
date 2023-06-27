package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 각각의 역할에 대한 구현이 어떤 것인지 잘 드러나도록 Config 파일을 리팩토링 한 것. new MemoryMemberRepository() 중복되던 부분 제거
@Configuration  //spring 방식. 이렇게 하면 AppConfig 객체 직접 생성하지 않아도 처음에 스프링 뜰 때 spring 컨테이너에 생성되고 관리됨.
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(),discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();        // 이제 정액 할인에서 정률 할인으로 변경하고 싶으면 클라이언트쪽은
                                                 // 건드리지 않고 AppConfig 의 여기 부분만 바꿔주면 해결 가능(OCP,DIP 모두 준수)
        return new RateDiscountPolicy();
    }
}
