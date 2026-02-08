package br.rosa.service;

import org.jooq.DSLContext;

import static br.rosa.generated.Tables.NIVEL;

public class DeleteService {

    public static void deleteNivel(DSLContext context, Integer idNivel) {
        context.delete(NIVEL).where(NIVEL.ID.eq(idNivel)).execute();
    }
}
