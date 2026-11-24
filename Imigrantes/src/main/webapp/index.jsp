<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Aprendizagem, acolhimento e oportunidades para pessoas imigrantes construírem novos caminhos.">
    <meta name="theme-color" content="#faf8ff">
    <title>Imigrantes | Aprender, pertencer, avançar</title>
    <link rel="preload" as="image" href="${pageContext.request.contextPath}/assets/images/comunidade-aprendizado.jpg">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=20261124-1">
</head>
<body class="landing-body">
    <a class="skip-link" href="#conteudo">Pular para o conteúdo</a>
    <header class="landing-header">
        <div class="landing-header-inner">
            <a class="brand landing-brand" href="${pageContext.request.contextPath}/" aria-label="Imigrantes, início">
                <span>Imigrantes</span>
            </a>
            <nav class="landing-nav" aria-label="Navegação principal">
                <a href="#sobre">O projeto</a>
                <a href="#como-funciona">Como funciona</a>
                <a href="#perfis">Participe</a>
            </nav>
            <div class="landing-actions">
                <a class="landing-login" href="${pageContext.request.contextPath}/login">Entrar</a>
                <a class="landing-button landing-button-primary" href="${pageContext.request.contextPath}/cadastro">Criar conta <span aria-hidden="true">↗</span></a>
            </div>
        </div>
    </header>

    <main id="conteudo">
        <section class="landing-hero">
            <div class="landing-hero-inner">
                <div class="landing-hero-copy">
                    <p class="landing-kicker"><span class="kicker-dot" aria-hidden="true"></span> Educação que acolhe e transforma</p>
                    <h1>Um novo lugar.<br>Novos caminhos.<br><em>Seu próximo passo.</em></h1>
                    <p class="landing-lead">Uma comunidade que conecta pessoas imigrantes a aprendizagem, educadores e oportunidades para construir um futuro com mais possibilidades.</p>
                    <div class="landing-hero-actions">
                        <a class="landing-button landing-button-primary landing-button-large" href="${pageContext.request.contextPath}/cadastro">
                            Comece sua jornada <span aria-hidden="true">→</span>
                        </a>
                        <a class="landing-text-link" href="#como-funciona">Conheça o projeto <span aria-hidden="true">↓</span></a>
                    </div>
                    <div class="landing-reassurance">
                        <span class="reassurance-icon" aria-hidden="true">
                            <svg viewBox="0 0 24 24"><path d="M12 3.2 19 6v5.1c0 4.3-2.8 7.8-7 9.7-4.2-1.9-7-5.4-7-9.7V6l7-2.8Z"/><path d="m9 12 2 2 4-4"/></svg>
                        </span>
                        <span>Um espaço seguro para aprender, compartilhar e seguir em frente.</span>
                    </div>
                </div>
                <div class="landing-hero-visual">
                    <div class="hero-photo-frame">
                        <img src="${pageContext.request.contextPath}/assets/images/comunidade-aprendizado.jpg"
                             alt="Pessoas reunidas em torno de computadores, colaborando em uma atividade de aprendizagem."
                             width="1600" height="1350" fetchpriority="high">
                    </div>
                    <div class="hero-photo-accent" aria-hidden="true"></div>
                    <div class="hero-note-card">
                        <span class="hero-note-icon" aria-hidden="true">
                            <svg viewBox="0 0 24 24"><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2Z"/><path d="M8 7h8M8 11h6"/></svg>
                        </span>
                        <span><strong>Aprender em comunidade</strong><small>Conhecimento abre portas.</small></span>
                        <span class="note-arrow" aria-hidden="true">↗</span>
                    </div>
                    <span class="hero-image-caption">Cada história merece espaço para florescer.</span>
                </div>
            </div>
        </section>

        <section class="landing-manifesto" id="sobre">
            <div class="manifesto-inner">
                <p class="landing-kicker">Sobre o projeto</p>
                <h2>Chegar a um novo país é o começo de uma história.<br><span>Aprender ajuda a escrever o próximo capítulo.</span></h2>
                <p class="manifesto-copy">A plataforma aproxima pessoas imigrantes, professores, voluntários e organizações. Juntos, criamos caminhos de aprendizagem com acolhimento, orientação e oportunidades reais.</p>
            </div>
            <div class="manifesto-mark" aria-hidden="true">“</div>
        </section>

        <section class="landing-how" id="como-funciona">
            <div class="landing-section-heading">
                <p class="landing-kicker">Um passo de cada vez</p>
                <h2>Seu caminho começa com uma conversa.</h2>
                <p>Uma experiência simples para entender seus objetivos e aproximar você das oportunidades certas.</p>
            </div>
            <div class="journey-grid">
                <article class="journey-step">
                    <span class="journey-number">01</span>
                    <span class="journey-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="3.5"/><path d="M5 20c.7-3.1 3.3-5 7-5s6.3 1.9 7 5"/></svg>
                    </span>
                    <h3>Conte sua história</h3>
                    <p>Crie sua conta e compartilhe seus interesses, experiências e o que deseja aprender.</p>
                </article>
                <article class="journey-step">
                    <span class="journey-number">02</span>
                    <span class="journey-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><path d="M4 5.5A2.5 2.5 0 0 1 6.5 3H20v17H6.5A2.5 2.5 0 0 1 4 17.5v-12Z"/><path d="M4 17.5A2.5 2.5 0 0 1 6.5 15H20M8 7h7M8 10.5h5"/></svg>
                    </span>
                    <h3>Descubra possibilidades</h3>
                    <p>Explore trilhas de aprendizagem e aulas oferecidas por educadores e parceiros.</p>
                </article>
                <article class="journey-step">
                    <span class="journey-number">03</span>
                    <span class="journey-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><path d="M4 18.5 9 13l3 2 7-8"/><path d="M14 7h5v5"/><path d="M4 4v16h16"/></svg>
                    </span>
                    <h3>Avance no seu ritmo</h3>
                    <p>Acompanhe sua jornada, desenvolva novas habilidades e celebre cada conquista.</p>
                </article>
            </div>
        </section>

        <section class="landing-community">
            <div class="community-photo">
                <img src="${pageContext.request.contextPath}/assets/images/comunidade-pessoas.jpg"
                     alt="Grupo diverso de pessoas sentado lado a lado, com os braços nos ombros uns dos outros, diante de uma paisagem."
                     width="1100" height="800" loading="lazy">
                <span class="community-photo-label">Pertencer também é encontrar apoio.</span>
            </div>
            <div class="community-copy">
                <p class="landing-kicker">Mais do que uma plataforma</p>
                <h2>Conhecimento cresce quando é compartilhado.</h2>
                <p>Uma aula pode abrir uma porta. Uma pessoa que acredita em você pode mudar o caminho inteiro. Aqui, estudantes, educadores e organizações fazem parte da mesma rede.</p>
                <a class="landing-text-link" href="#perfis">Encontre seu lugar na comunidade <span aria-hidden="true">→</span></a>
                <div class="community-values">
                    <span><span aria-hidden="true">✓</span> Acolhimento e respeito</span>
                    <span><span aria-hidden="true">✓</span> Aprendizagem para a vida</span>
                    <span><span aria-hidden="true">✓</span> Oportunidades em comunidade</span>
                </div>
            </div>
        </section>

        <section class="landing-participate" id="perfis">
            <div class="landing-section-heading">
                <p class="landing-kicker">Há um lugar para você</p>
                <h2>Faça parte dessa rede.</h2>
                <p>Escolha como quer contribuir. Cada perfil tem uma forma própria de transformar possibilidades em caminhos.</p>
            </div>
            <div class="participate-grid">
                <article class="participate-card participate-card-featured">
                    <span class="participate-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="3.5"/><path d="M5 20c.7-3.1 3.3-5 7-5s6.3 1.9 7 5"/></svg>
                    </span>
                    <p class="participate-label">Quero aprender</p>
                    <h3>Sou imigrante</h3>
                    <p>Encontre trilhas, aulas e apoio para avançar em seus objetivos.</p>
                    <a href="${pageContext.request.contextPath}/cadastro">Começar como imigrante <span aria-hidden="true">→</span></a>
                </article>
                <article class="participate-card">
                    <span class="participate-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><path d="M3 19.5A2.5 2.5 0 0 1 5.5 17H21"/><path d="M5.5 3H21v18H5.5A2.5 2.5 0 0 1 3 18.5v-13A2.5 2.5 0 0 1 5.5 3Z"/><path d="M8 7h7M8 11h5"/></svg>
                    </span>
                    <p class="participate-label">Quero ensinar</p>
                    <h3>Sou educador</h3>
                    <p>Compartilhe sua experiência e ajude a construir novas oportunidades.</p>
                    <a href="${pageContext.request.contextPath}/cadastro">Participar como educador <span aria-hidden="true">→</span></a>
                </article>
                <article class="participate-card">
                    <span class="participate-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24"><path d="M3 21h18M5 21V7l7-4 7 4v14M9 10h.01M15 10h.01M9 14h.01M15 14h.01M10 21v-4h4v4"/></svg>
                    </span>
                    <p class="participate-label">Quero apoiar</p>
                    <h3>Sou uma organização</h3>
                    <p>Abra as portas da sua instituição e conecte pessoas a espaços de aprendizagem.</p>
                    <a href="${pageContext.request.contextPath}/cadastro">Participar como representante <span aria-hidden="true">→</span></a>
                </article>
            </div>
        </section>

        <section class="landing-cta">
            <div class="cta-orbit cta-orbit-one" aria-hidden="true"></div>
            <div class="cta-orbit cta-orbit-two" aria-hidden="true"></div>
            <div class="cta-content">
                <p class="landing-kicker">Seu próximo capítulo começa aqui</p>
                <h2>O futuro se constrói<br>em comunidade.</h2>
                <p>Chegue como você é. Há espaço para aprender, ensinar e caminhar junto.</p>
                <a class="landing-button landing-button-light landing-button-large" href="${pageContext.request.contextPath}/cadastro">
                    Criar minha conta <span aria-hidden="true">→</span>
                </a>
            </div>
        </section>
    </main>

    <footer class="landing-footer">
        <div class="landing-footer-main">
            <div class="footer-brand-block">
                <a class="brand landing-brand footer-brand" href="${pageContext.request.contextPath}/">
                    <span>Imigrantes</span>
                </a>
                <p>Aprendizagem, acolhimento e oportunidades para novos caminhos.</p>
            </div>
            <div class="footer-nav-group">
                <h2>Explore</h2>
                <a href="#sobre">O projeto</a>
                <a href="#como-funciona">Como funciona</a>
                <a href="#perfis">Participe</a>
            </div>
            <div class="footer-nav-group">
                <h2>Acesse</h2>
                <a href="${pageContext.request.contextPath}/login">Entrar na plataforma</a>
                <a href="${pageContext.request.contextPath}/cadastro">Criar uma conta</a>
            </div>
        </div>
        <div class="landing-footer-bottom">
            <span>© <%= java.time.Year.now().getValue() %> Imigrantes. Educação que aproxima.</span>
            <a href="#conteudo">Voltar ao início ↑</a>
        </div>
    </footer>

    <nav class="mobile-action-bar" aria-label="Acesso rápido">
        <a class="mobile-login" href="${pageContext.request.contextPath}/login">Entrar</a>
        <a class="mobile-signup" href="${pageContext.request.contextPath}/cadastro">Criar conta <span aria-hidden="true">→</span></a>
    </nav>
</body>
</html>
