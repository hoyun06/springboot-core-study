package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {  // 어노테이션 기반으로 한 필터 테스트 위해 새로운 어노테이션 생성. @Component 에서 복붙
}
