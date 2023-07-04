package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    //private final MyLogger myLogger;      마찬가지로 MyLogger 는 클라이언트 요청이 있을 때만 생성되므로 Autowired 로 스프링 컨테이너 생성 시점에
    //                                      의존관계 주입을 받으려고 한다면 오류가 발생한다.

    //private final ObjectProvider<MyLogger> loggerProvider;    proxyMode 속성 사용하면 provider 불필요함.

    private final MyLogger myLogger;

    public void logic(String id) {
        //MyLogger myLogger = loggerProvider.getObject();     // 여기서 getObject 를 하더라도 같은 http 요청 처리 중에는 LogDemoController 내부에서
                                                            // getObject 를 해서 받은 MyLogger 인스턴스와 같은 인스턴스를 조회해옴. 그것이 request scope 의 특징
                                                            // 하지만 proxyMode 속성 사용하면 provider 불필요함.
        myLogger.log("service id = " + id);
    }

}
