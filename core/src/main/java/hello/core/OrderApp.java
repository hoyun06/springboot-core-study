package hello.core;

import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    // OrderService Test
    public static void main(String[] args) {
        /* MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();       AppConfig 사용 이전 */

        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();
        //OrderService orderService = appConfig.orderService();   // AppConfig 사용

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);

        Member member1 = new Member(1L, "member1", Grade.VIP);
        Member member2 = new Member(2L, "member2", Grade.BASIC);
        memberService.join(member1);
        memberService.join(member2);

        Order apple = orderService.createOrder(member1.getId(), "apple", 10000);
        Order banana = orderService.createOrder(member2.getId(), "banana", 15000);

        System.out.println("apple = " + (apple.calculatePrice() == 9000));
        System.out.println("banana = " + (banana.calculatePrice() == 15000));

    }
}
