package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;
    @Test
    void createOrder() {
        //given
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();

        Member member1 = new Member(1L, "member1", Grade.VIP);
        Member member2 = new Member(2L, "member2", Grade.BASIC);
        memberService.join(member1);
        memberService.join(member2);

        //when
        Order apple = orderService.createOrder(member1.getId(), "apple", 10000);
        Order banana = orderService.createOrder(member2.getId(), "banana", 15000);

        //then
        assertThat(apple.calculatePrice()).isEqualTo(9000);
        assertThat(banana.calculatePrice()).isEqualTo(15000);
    }
}
