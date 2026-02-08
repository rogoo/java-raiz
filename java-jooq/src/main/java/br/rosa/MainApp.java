package br.rosa;

import br.rosa.service.DeleteService;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderImplicitJoinType;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.jooq.impl.DSL.using;

public class MainApp {

    static DSLContext context = null;

    public static void main(String[] asdf) {
        String user = "admin6";
        String pass = "admin6";
        String url = "jdbc:mysql://localhost:3306/mquest";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            context = using(conn, SQLDialect.MYSQL);
            context.configuration().settings()
                    .withRenderImplicitJoinToManyType(RenderImplicitJoinType.LEFT_JOIN);

            //SelectService.selectTableRecord(context);
            //InsertService.insertNivel(context, new NivelVO("rod"));
            //UpdateService.udateNivel(context, new NivelVO(1, "rodrigo"));
            DeleteService.deleteNivel(context, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
