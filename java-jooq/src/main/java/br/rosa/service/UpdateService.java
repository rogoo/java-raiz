package br.rosa.service;

import br.rosa.vo.NivelVO;
import org.jooq.DSLContext;

public class UpdateService {

    public static void udateNivel(DSLContext context, NivelVO nivel) {
        // context.update(NIVEL).set(NIVEL.NOME, nivel.getNome())
        //         .where(NIVEL.ID.eq(nivel.getId())).execute();
    }
}
