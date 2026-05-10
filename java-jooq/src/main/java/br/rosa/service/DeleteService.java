package br.rosa.service;

import org.jooq.DSLContext;

public class DeleteService {

    public static void deleteNivel(DSLContext context, Integer idNivel) {
        // context.delete(NIVEL).where(NIVEL.ID.eq(idNivel)).execute();
    }
}
