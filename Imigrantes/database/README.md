# Banco de dados

O projeto usa o banco MySQL `Imigrantes` e JDBC. O arquivo `schema.sql` cria as tabelas da aplicacao sem remover as tabelas legadas.

- Banco novo: execute `schema.sql`.
- Banco criado pela versao anterior do projeto: execute primeiro `schema.sql` e depois `upgrade-existing.sql`. A atualizacao adiciona colunas e chaves; execute-a uma unica vez.

Os campos de Sala, Horario, Presenca e Anuncio foram definidos como campos operacionais minimos, pois o diagrama nao especifica seus atributos. `app_pergunta` e `app_aula_concluida` complementam, respectivamente, as operacoes de adicionar perguntas e registrar conclusao de aula.
