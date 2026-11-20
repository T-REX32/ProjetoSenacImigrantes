# Banco de dados

O projeto usa o banco MySQL `Imigrantes` e JDBC. O arquivo `schema.sql` cria as tabelas da aplicacao sem remover as tabelas legadas.

- Banco novo: execute `schema.sql`.
- Banco legado anterior à migração JDBC: faça backup, execute `schema.sql` e depois `upgrade-existing.sql`, uma única vez.
- Banco da versão JDBC já existente: faça backup e execute somente `upgrade-accounts.sql`, uma única vez. Não execute também `upgrade-existing.sql`, pois ela adiciona as colunas-base dessa migração.

Cadastros de imigrante ficam ativos após a criação. Professores formados, voluntários e representantes começam como `PENDENTE`; um administrador precisa aprová-los alterando o status para `ATIVO` no módulo **Usuários**. O formulário público não oferece o perfil de administrador.

Para testes locais, é possível aprovar automaticamente a primeira conta de cada perfil de professor (formado ou voluntário) e de representante, desde que ainda não exista nenhuma conta desse perfil no banco. Antes de iniciar o Tomcat no PowerShell, defina:

```powershell
$env:IMIGRANTES_AUTO_APPROVE_FIRST_PROFILES = "true"
```

O padrão é desativado. Remova a variável ou defina-a como `"false"` para voltar ao fluxo normal. Não habilite essa opção em produção. Se já houver uma conta pendente do perfil no banco, ela continua pendente e não será substituída por outra aprovação automática.

Para criar o primeiro administrador em uma instalação local, crie uma conta de imigrante e, com acesso administrativo ao MySQL, promova somente o e-mail escolhido:

```sql
UPDATE app_usuario
SET perfil = 'ADMINISTRADOR', status = 'ATIVO'
WHERE email = 'email-da-conta@exemplo.com';
```

Depois, saia e entre novamente para carregar as permissões administrativas. Não exponha credenciais do MySQL nem execute essa promoção em uma conta sem verificar o endereço.

O cadastro valida formato do e-mail, unicidade de e-mail/CPF e dígitos verificadores do CPF. A confirmação de que o endereço de e-mail realmente recebe mensagens ainda depende de configurar e integrar um provedor de envio; não é simulada pelo formulário.

Os campos de Sala, Horario, Presenca e Anuncio foram definidos como campos operacionais minimos, pois o diagrama nao especifica seus atributos. `app_pergunta` e `app_aula_concluida` complementam, respectivamente, as operacoes de adicionar perguntas e registrar conclusao de aula.
