package br.rosa;

import static org.jooq.impl.DSL.using;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderImplicitJoinType;

public class MainApp {

	static DSLContext context = null;

	public static void main(String[] asdf) {
		String user = "root";
		String pass = "root";
		String url = "jdbc:mysql://localhost:3306/mtest";

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			context = using(conn, SQLDialect.MYSQL);
			context.configuration().settings().withRenderImplicitJoinToManyType(
					RenderImplicitJoinType.LEFT_JOIN);

			SelectService.selectTableRecord(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
