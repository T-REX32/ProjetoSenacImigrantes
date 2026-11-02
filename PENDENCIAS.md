# Pendências em relação ao banco de dados

O schema em `database/banco.sql` é a fonte da verdade. Os requisitos abaixo não podem ser implementados integralmente sem tabelas ou colunas que não existem no schema; nenhuma alteração de banco foi feita.

- **RF64 e RF65:** `USUARIO` não possui `cpf` nem `sobrenome`. Sugestão: revisar esses requisitos para operar somente com os dados existentes em `USUARIO`.
- **RF67 e Parte 1B:** não há tabela de log de auditoria. Sugestão: definir com o grupo uma alternativa de auditoria antes de implementar a parte correspondente.
- **RF49:** não há tabela para armazenar token de recuperação de senha. Sugestão: definir outro fluxo de recuperação ou solicitar ao grupo uma decisão sobre persistência.
- **RF52, RF51 e RF55:** não há tabela de notificações nem coluna de preferência de notificações. Sugestão: não oferecer notificações persistentes até definir como armazená-las.
- **RF58, RF10 e RF59:** não há tabela de mensagens nem de avisos. Sugestão: revisar esses fluxos considerando a ausência de persistência.
- **RF61 e RF63:** não há tabela de idiomas nem traduções; existe somente `IMIGRANTE.idioma_preferido`, como texto. Sugestão: limitar o idioma ao texto já disponível.
- **RF06 e RF07:** `FORMULARIO` e `RESPOSTA` não armazenam perguntas, opções nem pesos. Sugestão: não calcular recomendação de trilha nem nível de aptidão a partir desses dados.
- **RF25:** `AULA` não possui data, vagas nem nível de dificuldade. Sugestão: limitar as operações de aula às colunas que existem.
- **RF29, RF33 e RF60:** não há tabela de reservas. Sugestão: não implementar reserva de salas até haver uma decisão sobre esse fluxo.
- **RF09:** a inscrição é em curso (`INSCRICAO.curso_id`), não em aula; também não há tabela de inscrição em aula. Sugestão: adaptar o fluxo de inscrição para cursos.
- **RNF03:** não há coluna de status de aprovação em trilha, curso ou aula. Sugestão: não implementar aprovação de conteúdo com o schema atual.
- **RF19, RF28, RF57 e RF30:** não há tabelas de feedback, material de apoio nem declaração de voluntário. Sugestão: não persistir esses recursos sem definir uma estrutura compatível.
- **RF34 a RF43:** não há tabela de instituição; há somente o texto `REPRESENTANTE.instituicao`. Sugestão: tratar instituição apenas como o texto associado ao representante.
