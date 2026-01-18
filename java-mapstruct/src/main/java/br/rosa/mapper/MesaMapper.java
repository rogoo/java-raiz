package br.rosa.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.rosa.dto.MesaDTO;
import br.rosa.model.MesaEntity;

@Mapper
public interface MesaMapper {

	MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);

	@Mapping(target = MesaDTO.CAMPO_CODIGO, source = MesaEntity.CAMPO_ID)
	@Mapping(target = MesaDTO.CAMPO_NOME_MESA, source = MesaEntity.CAMPO_NOME)
	@Mapping(target = MesaDTO.CAMPO_LISTA, source = MesaEntity.CAMPO_LISTA_NUMEROS)
	MesaDTO entityParaDTO(MesaEntity mesaEntity);

	@InheritInverseConfiguration
	MesaEntity dtoParaEntity(MesaDTO mesaDTO);

	List<MesaDTO> entityParaDTOs(List<MesaEntity> listaMesa);
}
