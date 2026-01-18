package br.rosa;

import static br.rosa.generated.Tables.AUTHOR;
import static br.rosa.generated.Tables.POST;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;

import br.rosa.generated.tables.records.AuthorRecord;
import br.rosa.vo.AuthorVO;

public class SelectService {

	public static void selectTextao(DSLContext context) {
		Result<org.jooq.Record> result = context
				.resultQuery("select id, first_name from author").fetch();

		List<AuthorVO> lista = result.stream().map(au -> {
			AuthorVO vo = new AuthorVO();
			vo.setId(au.get(AUTHOR.ID));
			vo.setFirstName(au.get(AUTHOR.FIRST_NAME));
			return vo;
		}).toList();

		for (AuthorVO vo : lista) {
			System.out.println(vo);
		}
	}

	public static void select(DSLContext context) {
		Result<Record2<Integer, Integer>> result = context
				.select(AUTHOR.ID, AUTHOR.post().ID).from(AUTHOR).fetch();

		for (Record2<Integer, Integer> res : result) {
			System.out.println(res.get(AUTHOR.ID) + " - " + res.get(POST.ID));
		}
	}

	public static void selectTableRecord(DSLContext context) {
		Result<AuthorRecord> result = context.selectFrom(AUTHOR).fetch();

		for (var res : result) {
			System.out.println(res.getId() + " - " + res.getFirstName() + " - "
					+ res.getBirthday());
		}
	}
}
