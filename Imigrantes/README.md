# Projeto Imigrantes

Aplicacao web Java 8 com Servlet, JSP, JDBC e MySQL. O projeto nao usa Hibernate nem frameworks adicionais.

## Preparar o banco

Execute `database/schema.sql` no MySQL para uma instalação nova. Em uma instalação existente, siga a migração adequada em [database/README.md](./database/README.md) e faça backup antes de alterar o banco.

Por padrao, a conexao usa `localhost:3306`, banco `Imigrantes`, usuario `root` e senha `root`, preservando a configuracao local anterior. Para trocar esses valores, defina `IMIGRANTES_DB_URL`, `IMIGRANTES_DB_USER` e `IMIGRANTES_DB_PASSWORD`.

## Compilar e executar

Na raiz do repositorio, gere o WAR:

```powershell
mvn -f .\Imigrantes\pom.xml package
```

Com o Tomcat 9 instalado no local usado neste ambiente, publique e inicie com:

```powershell
$tomcat = "$env:USERPROFILE\.tomcat\apache-tomcat-9.0.124"
Copy-Item .\Imigrantes\target\Imigrantes.war "$tomcat\webapps\Imigrantes.war" -Force
& "$tomcat\bin\catalina.bat" run
```

Mantenha esse terminal aberto e acesse `http://localhost:8080/Imigrantes/`. A raiz do site abre a pagina inicial publica; de la, a pessoa pode criar uma conta ou entrar. O formulario de curso so deve aparecer ao abrir explicitamente `/Imigrantes/curso/novo`.

Se o Tomcat ja estiver rodando, pare-o com `Ctrl+C` antes de iniciar novamente. Se a pagina antiga continuar aparecendo, confirme que o WAR acima foi copiado e que esta acessando a URL raiz terminada em `/Imigrantes/`, nao uma rota de formulario salva nos favoritos.

O site inicial é público. O cadastro permite escolher um único perfil: imigrante, professor formado, voluntário ou representante de instituição/ONG. Perfis não-imigrantes aguardam aprovação; administrador só pode ser atribuído por alguém com acesso ao banco ou por um administrador existente. Veja o procedimento inicial em [database/README.md](./database/README.md).

Para testar os primeiros perfis de professor/voluntário e representante sem aprovação manual, habilite `IMIGRANTES_AUTO_APPROVE_FIRST_PROFILES=true` apenas no terminal que inicia o Tomcat; a opção está desativada por padrão e não deve ser ligada em produção.

Após entrar, cada perfil vê apenas os módulos autorizados. Imigrantes respondem ao formulário inicial antes de acessar as trilhas e aulas publicadas. O perfil permite alterar nome e senha; CPF e e-mail não são editáveis, e a senha atual é exigida para trocar a senha. As alterações de nome e senha geram auditoria sem registrar valores de senha.

O painel inclui os módulos existentes de trilhas, aulas, inscrições, avaliações, salas, horários, presenças e anúncios. A autorização e o fluxo de aprovação por perfil estão implementados; as demais regras de negócio (mensagens/notificações, tradução, emissão de declaração para voluntário, etc.) não fazem parte deste conjunto de telas ainda.
