# Projeto Imigrantes

Aplicacao web Java 8 com Servlet, JSP, JDBC e MySQL. O projeto nao usa Hibernate nem frameworks adicionais.

## Preparar o banco

Execute `database/schema.sql` no MySQL. Se estiver atualizando o banco usado pela versao anterior, execute tambem `database/upgrade-existing.sql` uma unica vez, depois do schema. Os detalhes estao em [database/README.md](./database/README.md).

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

O painel inclui cadastros para os perfis, formularios, trilhas, aulas, inscricoes, avaliacoes, salas, horarios, presencas e anuncios. O progresso e o certificado sao atualizados pelo sistema quando as aulas disponiveis de uma inscricao sao concluidas.
