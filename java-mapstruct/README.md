# Sobre a biblioteca java-mapstruct
Finalmente uma biblioteca de conversão simples e efetiva.

Li a documentação bem rapidamente e fiz uns testes..

### Possui CDI?
Se o seu ambiente possuir injeção de dependência (Spring, J2EE), é recomendado não utilizar o método utilizado aqui com a criação de uma instância do Mapper (abaixo).
```
MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);
```