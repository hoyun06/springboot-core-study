package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    private AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("하나의 인터페이스를 상속하는 두 개의 스프링 빈 조회시 exception 발생")
    void findBeanByParentDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }
    @Test
    @DisplayName("하나의 인터페이스를 상속하는 두 개의 스프링 빈 조회시 이름을 지정하면 정상 동작")
    void findBeanByParentBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        DiscountPolicy fixDiscountPolicy = ac.getBean("fixDiscountPolicy", DiscountPolicy.class);
        System.out.println("fixDiscountPolicy = " + fixDiscountPolicy);
        System.out.println("rateDiscountPolicy = " + rateDiscountPolicy);
    }

    @Test
    @DisplayName("부모 타입이 아닌 구현체의 타입으로 조회하면 중복되지 않음")
    void findBeanByChild() {
        DiscountPolicy fixDiscountPolicy = ac.getBean(FixDiscountPolicy.class);
        DiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
        System.out.println("fixDiscountPolicy = " + fixDiscountPolicy);
        System.out.println("rateDiscountPolicy = " + rateDiscountPolicy);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findAllByParentType() {
        Map<String, DiscountPolicy> beans = ac.getBeansOfType(DiscountPolicy.class);
        for (String s : beans.keySet()) {
            DiscountPolicy discountPolicy = beans.get(s);
            System.out.println(s + " = " + discountPolicy);
        }
    }

    @Test
    @DisplayName("Object 타입으로 모든 빈 조회")
    void findAllBeans() {
        Map<String, Object> beans = ac.getBeansOfType(Object.class);
        for (String s : beans.keySet()) {
            Object o = beans.get(s);
            System.out.println("key = " + s + " value = " + o);
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
