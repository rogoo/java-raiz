package br.rosa.mapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.rosa.dto.MesaDTO;
import br.rosa.model.MesaEntity;

public class MesaMapperTest {

	@Test
	void converteMesaEntityParaMesaDTO() {
		MesaEntity entity = new MesaEntity(7L, "mesa 77", new Date(), Arrays.asList(11, 22, 23));

		MesaDTO dto = MesaMapper.INSTANCE.entityParaDTO(entity);

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(entity.getNome(), dto.getNomeMesa());
		Assertions.assertEquals(entity.getId(), dto.getCodigo());
		Assertions.assertEquals(entity.getDataCriacao(), dto.getDataCriacao());
		Assertions.assertIterableEquals(entity.getListaNumeros(), dto.getLista());
	}

	@Test
	void converteMesaEntityParaMesaDTO_EntityNula() {
		MesaEntity entity = null;

		MesaDTO dto = MesaMapper.INSTANCE.entityParaDTO(entity);

		Assertions.assertNull(dto);
	}

	@Test
	void converteMesaDTOParaMesaEntity() {
		MesaDTO dto = new MesaDTO(99L, "Mesa Teste", new Date(), Arrays.asList(344, 1));

		MesaEntity entity = MesaMapper.INSTANCE.dtoParaEntity(dto);

		Assertions.assertNotNull(entity);
		Assertions.assertEquals(dto.getNomeMesa(), entity.getNome());
		Assertions.assertEquals(dto.getCodigo(), entity.getId());
		Assertions.assertEquals(dto.getDataCriacao(), entity.getDataCriacao());
		Assertions.assertIterableEquals(dto.getLista(), entity.getListaNumeros());
	}

	@Test
	void converteMesaDTOParaMesaEntity_DTONulo() {
		MesaDTO dto = null;

		MesaEntity entity = MesaMapper.INSTANCE.dtoParaEntity(dto);

		Assertions.assertNull(entity);
	}

	@Test
	void convertListaMesaEntityParaListaMesaDTO() {
		List<MesaEntity> listaEntity = Arrays.asList(new MesaEntity(2L, "mesa bra 2"),
				new MesaEntity(66L, "mesa bra bli"));

		List<MesaDTO> listaDTO = MesaMapper.INSTANCE.entityParaDTOs(listaEntity);

		Assertions.assertNotNull(listaDTO);
		Assertions.assertEquals(2, listaDTO.size());

		Assertions.assertAll("Validando primeiro elemento...",
				() -> Assertions.assertEquals(listaEntity.get(0).getId(), listaDTO.get(0).getCodigo()),
				() -> Assertions.assertEquals(listaEntity.get(0).getNome(), listaDTO.get(0).getNomeMesa()),
				() -> Assertions.assertNull(listaDTO.get(0).getDataCriacao()),
				() -> Assertions.assertNull(listaDTO.get(0).getLista()));

		Assertions.assertAll("Validando segundo elemento...",
				() -> Assertions.assertEquals(listaEntity.get(1).getId(), listaDTO.get(1).getCodigo()),
				() -> Assertions.assertEquals(listaEntity.get(1).getNome(), listaDTO.get(1).getNomeMesa()),
				() -> Assertions.assertNull(listaDTO.get(1).getDataCriacao()),
				() -> Assertions.assertNull(listaDTO.get(1).getLista()));
	}
}
