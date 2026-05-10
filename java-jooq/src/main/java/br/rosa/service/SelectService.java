package br.rosa.service;

import br.rosa.generated.tables.Author;
import br.rosa.generated.tables.Post;
import br.rosa.vo.AuthorVO;
import br.rosa.vo.PostVO;
import org.jooq.DSLContext;

import java.util.List;

import static org.jooq.impl.DSL.multiset;

public class SelectService {

    public static void selectTest(DSLContext context) {
        List<AuthorVO> lista = context.select(Author.AUTHOR.ID, Author.AUTHOR.FIRST_NAME,
                        Post.POST.ID.as("post.id"), Post.POST.TITLE.as("post.title"))
                .from(Author.AUTHOR).join(Post.POST)
                .on(Post.POST.ID_AUTHOR.eq(Author.AUTHOR.ID)).fetch().into(AuthorVO.class);
        lista.forEach(au -> System.out.println(au));
    }

    public static void selectTestAux(DSLContext context) {
        List<AuthorVO> lista = context.dsl().select(Author.AUTHOR.ID,
                        Author.AUTHOR.FIRST_NAME.as("firstName"),
                        multiset(context.dsl().select(Post.POST.ID, Post.POST.TITLE).from(Post.POST)
                                .where(Post.POST.ID_AUTHOR.eq(Author.AUTHOR.ID))).as("post")
                                .convertFrom(r -> r.map(postRecord -> {
                                    PostVO postVO = new PostVO();
                                    postVO.setId(postRecord.get(Post.POST.ID));
                                    postVO.setTitle(postRecord.get(Post.POST.TITLE));
                                    return postVO;
                                })))
                .from(Author.AUTHOR)
                .fetchInto(AuthorVO.class);
        lista.forEach(au -> System.out.println(au));
    }

    // public static void selectUsandoString(DSLContext context) {
    //     Result<org.jooq.Record> result = context.resultQuery("select id, nome from nivel")
    //             .fetch();
    //
    //     List<AuthorVO> lista = result.stream().map(au -> {
    //         AuthorVO vo = new AuthorVO();
    //         vo.setId(au.get(NIVEL.ID));
    //         vo.setFirstName(au.get(NIVEL.NOME));
    //         return vo;
    //     }).toList();
    //
    //     for (AuthorVO vo : lista) {
    //         System.out.println(vo);
    //     }
    // }

    // public static void selectTopicoNomeIdDisciplina(DSLContext context) {
    //     List<TopicoVO> result = context.select(TOPICO.NOME, TOPICO.ID_DISCIPLINA).from
    //     (TOPICO)
    //             .fetchInto(TopicoVO.class);
    //     for (TopicoVO vo : result) {
    //         System.out.println(vo);
    //     }
    // }
    //
    // public static void select(DSLContext context) {
    //     Result<Record2<Integer, String>> result = context.select(NIVEL.ID, NIVEL.NOME)
    //             .from(NIVEL).fetch();
    //
    //     for (Record2<Integer, String> res : result) {
    //         System.out.println(res.get(NIVEL.ID) + " - " + res.get(NIVEL.NOME));
    //     }
    // }
    //
    // public static void selectTableRecord(DSLContext context) {
    //     Result<NivelRecord> result = context.selectFrom(NIVEL).fetch();
    //
    //     for (var res : result) {
    //         System.out.println(res.getId() + " - " + res.getId() + " - " + res.getNome());
    //     }
    // }
}
