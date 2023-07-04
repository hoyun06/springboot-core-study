package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean1.addCount();
        assertThat(protoTypeBean1.getCount()).isEqualTo(1);

        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean2.addCount();
        assertThat(protoTypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, ProtoTypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);  // 이때 스프링 컨테이너에서 조회를 해오지만 그 안에 있는 prototypeBean 에 대한 주입은
                                                                // 스프링 컨테이너 생성시점에 완료되었으므로 PrototypeBean 인스터스를 새로 생성하지 않음.
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);  // 이때 스프링 컨테이너에서 조회를 해오지만 그 안에 있는 prototypeBean 에 대한 주입은
                                                                // 스프링 컨테이너 생성시점에 완료되었으므로 PrototypeBean 인스터스를 새로 생성하지 않음.
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);               // 결과적으로 clientBean1 과 clientBean2 는 같은 PrototypeBean 인스턴스를 공유함.
                                                                // 우리가 의도한 바는 이게 아니라 매번 새로운 PrototypeBean 인스턴스를 생성해서 사용하길 원함.
    }

    @Scope("singleton")
    static class ClientBean {
        //private final ProtoTypeBean protoTypeBean;  // ClientBean 이 스프링 빈으로 등록될 때 @Autowired 로 인해 생성자에서 의존관계 주입이 일어남.
                                                      // 그 시점에 스프링 컨테이너에서 ProtoTypeBean 인스턴스가 프로토타입 스코프로 새로 생성되어 주입됨.
//        @Autowired
//        public ClientBean(ProtoTypeBean protoTypeBean) {
//            this.protoTypeBean = protoTypeBean;
//        }

        @Autowired
        private ObjectProvider<ProtoTypeBean> protoTypeBeanProvider;    // ObjectProvider 를 사용하면 getObject() 를 할 때마다 컨테이너에서
                                                                        // ProtoTypeBean 에 대한 DL 만 수행해줌. 우리가 원래 의도하던대로 동작.
        public int logic() {
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.getObject();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class ProtoTypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("ProtoTypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("ProtoTypeBean.destroy " + this);
        }
    }
}
