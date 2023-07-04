package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //private final MyLogger myLogger;                      이렇게 Autowired 로 받아버리게 되면 의존관계가 스프링 컨테이너 생성 시점에 주입되어야 하는데
    //                                                      MyLogger 같은 경우 request scope 이므로 클라이언트 요청이 와야만 생성될 수 있으므로 오류가 발생함.
    //                                                      따라서 이전에 배운 Provider 사용하여 DL 활용.

    //private final ObjectProvider<MyLogger> loggerProvider; 여기서 더 간편하게 할 수 있음. @Scope 의 속성 중에 proxyMode 사용하면
    //                                                       위에 코드처럼 다시 바꿀 수 있음

    private final MyLogger myLogger;

    @GetMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        //MyLogger myLogger = loggerProvider.getObject();         // getObject 를 하는 시점에 컨테이너로부터 MyLogger 인스턴스가 생성됨.
                                                                  // proxyMode 속성 이용하면 provider 불필요.
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
