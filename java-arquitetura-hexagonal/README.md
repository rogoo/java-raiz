# Arquitetura Hexagonal (porta/adapter)
As portas in/out definem, através de uma interface, o que o adaptador deve fazer.
Substituir o adaptor é simples e não tem grande impacto (basta trocar a classe que implementa a
interface).

Regra de negócio toda no domain.

Adicionei no adapter de entrada (in) um consumer Kafka (não existe producer neste projeto).

Tive que trocar porta pois o querido Kafka UI estava já configurado usando a 8080.

Isso ai, vamos que vamos! 🚀