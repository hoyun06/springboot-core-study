package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        // AutoAppConfig 를 통해 컴포넌트 스캔, DiscountService 를 통해 DiscountService 객체가 bean 으로 등록되고
        // Autowired 에 의해 DiscountPolicy 의 구현체들이 각각 Map 과 List 에 저장됨.

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L,"memberA", Grade.VIP);

        int rateDiscountPrice = discountService.getDiscountPrice(member, "rateDiscountPolicy", 10000);
        int fixDiscountPrice = discountService.getDiscountPrice(member, "fixDiscountPolicy", 20000);

        Assertions.assertThat(fixDiscountPrice).isSameAs(1000);
        Assertions.assertThat(rateDiscountPrice).isSameAs(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int getDiscountPrice(Member member, String discountName, int price) {
            DiscountPolicy discountPolicy = policyMap.get(discountName);

            return discountPolicy.discount(member, price);
        }
    }
}
