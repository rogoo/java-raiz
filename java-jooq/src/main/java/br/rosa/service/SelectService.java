package br.rosa.service;

import br.rosa.generated.tables.records.NivelRecord;
import br.rosa.vo.AuthorVO;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.List;

import static br.rosa.generated.Tables.NIVEL;

public class SelectService {

    public static void selectTextao(DSLContext context) {
        Result<org.jooq.Record> result = context.resultQuery("select id, nome from nivel")
                .fetch();

        List<AuthorVO> lista = result.stream().map(au -> {
            AuthorVO vo = new AuthorVO();
            vo.setId(au.get(NIVEL.ID));
            vo.setFirstName(au.get(NIVEL.NOME));
            return vo;
        }).toList();

        for (AuthorVO vo : lista) {
            System.out.println(vo);
        }
    }

    public static void select(DSLContext context) {
        Result<Record2<Integer, String>> result = context.select(NIVEL.ID, NIVEL.NOME)
                .from(NIVEL).fetch();

        for (Record2<Integer, String> res : result) {
            System.out.println(res.get(NIVEL.ID) + " - " + res.get(NIVEL.NOME));
        }
    }

    public static void selectTableRecord(DSLContext context) {
        Result<NivelRecord> result = context.selectFrom(NIVEL).fetch();

        for (var res : result) {
            System.out.println(res.getId() + " - " + res.getId() + " - " + res.getNome());
        }
    }
}
