package sarasa.wantedinternship.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sarasa.wantedinternship.domain.entity.Article;
import sarasa.wantedinternship.domain.entity.Member;
import sarasa.wantedinternship.repository.ArticleRepository;
import sarasa.wantedinternship.service.MemberService;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class LoadDatabase implements CommandLineRunner {

    private final MemberService memberService;
    private final ArticleRepository articleRepository;

    @Override
    public void run(String... args) throws Exception {
        Member member1 = new Member("test1@test.com", "test1234");
        Member member2 = new Member("test2@test.com", "test1234");
        Member member3 = new Member("test3@test.com", "test1234");
        Member member4 = new Member("test4@test.com", "test1234");
        Member member5 = new Member("test5@test.com", "test1234");

        List<Member> members = List.of(
                member1, member2, member3, member4, member5);

        members.forEach(memberService::signUp);

        Article article1 = new Article("test_title1", "test_content1", member1);
        Article article2 = new Article("test_title2", "test_content2", member2);
        Article article3 = new Article("test_title3", "test_content3", member3);
        Article article4 = new Article("test_title4", "test_content4", member4);
        Article article5 = new Article("test_title5", "test_content5", member5);

        List<Article> articles = List.of(
                article1, article2, article3, article4, article5);

        articleRepository.saveAll(articles);
    }

}
