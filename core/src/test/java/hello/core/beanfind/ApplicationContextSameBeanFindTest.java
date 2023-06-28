package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회할 때 같은 타입인 객체가 여러 개일 경우")
    void findBeanByTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("같은 타입이 두 개 이상이면 빈 이름으로 조회하면 된다")
    void findBeanTypeDuplicateByName() {
        MemberRepository memberRepository1 = ac.getBean("memberRepository1", MemberRepository.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository2", MemberRepository.class);
        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
    }

    @Test
    @DisplayName("같은 타입인 객체 모두 조회")
    void findAllBeanByType() {
        Map<String, MemberRepository> beans = ac.getBeansOfType(MemberRepository.class);
        for (String s : beans.keySet()) {
            MemberRepository memberRepository = beans.get(s);
            System.out.println("key = " + s + " value = " + memberRepository);
        }
    }

    @Configuration // 스프링 빈으로 동일 클래스를 가지는 객체 여러개 등록하기 위해 작성한 test 용 config 클래스
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
