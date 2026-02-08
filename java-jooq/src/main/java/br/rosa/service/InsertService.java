package br.rosa.service;

import br.rosa.vo.NivelVO;
import org.jooq.DSLContext;

import static br.rosa.generated.Tables.NIVEL;

public class InsertService {

    public static void insertNivel(DSLContext context, NivelVO nivel) {
        context.insertInto(NIVEL, NIVEL.NOME).values(nivel.getNome()).execute();
    }
}
