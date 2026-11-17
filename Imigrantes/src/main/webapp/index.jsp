<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Uma plataforma de ensino e acolhimento para pessoas imigrantes.">
    <title>Início | Imigrantes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <header class="topbar public-nav">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <nav class="public-nav-links" aria-label="Navegação principal">
            <a href="#sobre">Sobre o projeto</a>
            <a class="button secondary small" href="${pageContext.request.contextPath}/login">Entrar</a>
            <a class="button small" href="${pageContext.request.contextPath}/cadastro">Criar conta</a>
        </nav>
    </header>

    <main>
        <section class="hero">
            <div class="hero-content">
                <span class="eyebrow">Aprendizado que aproxima</span>
                <h1>Novos caminhos começam com oportunidades.</h1>
                <p>Um espaço de ensino, desenvolvimento e acolhimento para pessoas imigrantes construírem seus próximos passos.</p>
                <div class="hero-actions">
                    <a class="button" href="${pageContext.request.contextPath}/cadastro">Quero participar</a>
                    <a class="button secondary" href="#sobre">Conhecer o projeto</a>
                </div>
            </div>
            <div class="hero-note" aria-label="Acolhimento e aprendizado">
                <span class="hero-note-mark" aria-hidden="true">+</span>
                <p>Conhecimento abre portas. Acolhimento ajuda a atravessá-las.</p>
            </div>
        </section>

        <section class="public-section" id="sobre">
            <div class="section-heading">
                <span class="eyebrow">Uma comunidade para crescer</span>
                <h2>Aprender, compartilhar e seguir em frente.</h2>
                <p>Reunimos estudantes, educadores voluntários, professores e representantes para apoiar jornadas de aprendizagem.</p>
            </div>
            <div class="grid">
                <article class="card feature-card">
                    <span class="feature-number">01</span>
                    <h3>Formação acessível</h3>
                    <p>Trilhas de ensino e aulas organizadas para apoiar o desenvolvimento de novos conhecimentos.</p>
                </article>
                <article class="card feature-card">
                    <span class="feature-number">02</span>
                    <h3>Acompanhamento</h3>
                    <p>Inscricoes e progresso ajudam cada participante a acompanhar sua trajetoria de aprendizagem.</p>
                </article>
                <article class="card feature-card">
                    <span class="feature-number">03</span>
                    <h3>Comunidade acolhedora</h3>
                    <p>Educadores e parceiros compartilham experiencias, oportunidades e apoio.</p>
                </article>
            </div>
        </section>

        <section class="join-banner">
            <div>
                <span class="eyebrow">Seu próximo passo</span>
                <h2>Vamos aprender juntos?</h2>
                <p>Crie sua conta para conhecer a plataforma e acompanhar suas atividades.</p>
            </div>
            <a class="button light-button" href="${pageContext.request.contextPath}/cadastro">Comecar agora</a>
        </section>
    </main>

    <footer class="site-footer">
        <a class="brand" href="${pageContext.request.contextPath}/">Imigrantes</a>
        <span>Ensino e acolhimento para novos caminhos.</span>
    </footer>
</body>
</html>
