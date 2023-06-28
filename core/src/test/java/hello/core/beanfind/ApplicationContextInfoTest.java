package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    private AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력")
    void findAllBeans() {
        String[] beanNames = ac.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = ac.getBean(beanName);
            System.out.println("name = " + beanName + " object = " + bean);
        }
    }
    @Test // 위에서는 스프링 내부에서 필요로 하는 빈까지 모두 출력되었는데 이번엔 AppConfig 내에서 등록한 빈만 출력하고자 함.
    @DisplayName("애플리케이션 빈 출력")
    void findApplicationBeans() {
        String[] beanNames = ac.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanName);

            //ROLE_APPLICATION : AppConfig 에서 직접 등록한 빈
            //ROLE_INFRASTRUCTURE : 스프링 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanName);
                System.out.println("name = " + beanName + " object = " + bean);
            }
        }
    }
}
