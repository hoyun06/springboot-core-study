package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
// InitializingBean 인터페이스의 afterPropertiesSet 메소드는 객체 생성 후 의존관계 주입이 끝난 뒤에 호출됨.
// DisposableBean 인터페이스의 destroy 메소드는 빈 소멸 전에 호출됨.
public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
//        connect();
//        call("초기화 연결 메세지");   아직 url 정보가 세팅되기 전이므로 의미없는 작업.
    }
    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출되어야 함.
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call = " + url + "   message = " + message);
    }

    // 서비스 종료시 호출되어야 함.
    public void disconnect() {
        System.out.println("close = " + url);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }
    @Override
    public void destroy() throws Exception {
        disconnect();
    }
}
