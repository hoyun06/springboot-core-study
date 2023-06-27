package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        //MemberService memberService = new MemberServiceImpl(); // AppConfig 작성 이전에 사용했던 방법
        //AppConfig appConfig  = new AppConfig();                  // AppConfig 사용
        //MemberService memberService = appConfig.memberService();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 이렇게 ApplicationContext 사용해서 AppConfig.class 정보 넘겨주면 우리가 AppConfig 에서 Bean 으로 등록한 
        // 객체들을 생성하고 관리해줌. 기본적으로 Bean 들은 AppConfig 내에서의 메소드 이름으로 저장됨.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        // '메소드 이름으로 찾겠다'라고 지정.

        Member member1 = new Member(0L,"member1", Grade.VIP);
        Member member2 = new Member(1L,"member2",Grade.BASIC);

        memberService.join(member1);
        memberService.join(member2);

        Member findMember1 = memberService.findMember(0L);
        Member findMember2 = memberService.findMember(1L);

        System.out.println(member1.getName().equals(findMember1.getName()));
        System.out.println(member2.getName().equals(findMember2.getName()));
    }
}
