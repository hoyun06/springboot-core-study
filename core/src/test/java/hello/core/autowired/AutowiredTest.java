package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    @DisplayName("Autowired 옵션 테스트")
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class); // TestBean 객체가 bean 으로 등록됨.
    }

    // Autowired 옵션 테스트 위해서 임의의 클래스 작성. TestBean 클래스가 빈으로 등록되면서 @Autowired 붙은 메소드 실행.
    // Member 클래스는 도메인 객체로서 스프링 빈으로 관리되지 않으므로 의존관계 주입을 받으려고 할 경우 오류가 발생함.
    // 그 오류를 피하는 방법을 테스트하기 위한 클래스.
    static class TestBean {

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
