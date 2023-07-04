package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {

    @Test
    void prototypeTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find p1");
        PrototypeBean p1 = ac.getBean(PrototypeBean.class);     // 이때 인스턴스가 새로 생성되고 init 호출
        System.out.println("find p2");
        PrototypeBean p2 = ac.getBean(PrototypeBean.class);     // 이때 또 인스턴스가 새로 생성되고 init 호출

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);

        assertThat(p1).isNotSameAs(p2);

        ac.close();     // 여기서는 ac.close() 하더라도 @PreDestroy 가 붙은 메소드 실행되지 않음.
                        // 프로토타입 빈이므로 스프링 컨테이너가 관리하는 객체가 아니기 때문.
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close");
        }
    }
}
