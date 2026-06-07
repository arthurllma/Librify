# Librify Mobile 📱📖

O **Librify Mobile** é um aplicativo Android nativo de biblioteca virtual acadêmica, projetado para modernizar a experiência de leitura e gestão de obras na comunidade universitária. O app permite automatizar o processo de reservas de livros, consulta de acervo em tempo real e a publicação colaborativa de obras acadêmicas por parte dos alunos.

Este software foi desenvolvido como parte de um **Projeto de Extensão da Universidade de Fortaleza (UNIFOR)**, integrando conceitos práticos de Engenharia de Software, Arquitetura de Sistemas e Desenvolvimento Mobile Nativo.

---

## Contexto Acadêmico
* **Instituição:** Universidade de Fortaleza (UNIFOR)
* **Curso:** Ciência da Computação
* **Disciplina:** Requisitos e Modelagem de Sistemas
* **Natureza:** Projeto de Extensão Universitária
* **Orientador/Professor:** Dr. Pedro Pinheiro

---

## Funcionalidades Principais (Escopo Mobile)

O sistema foi totalmente modelado via UML (Casos de Uso e Diagramas de Atividades) e implementado com base nos requisitos funcionais adaptados para a experiência mobile nativa:

*   ** Autenticação Mobile (RF01 - RF03):** Login, Cadastro e Recuperação de Senha com persistência de estado e segurança de navegação (limpeza de pilha no logout).
*   ** Home e Destaque (RF05 - RF06):** Interface intuitiva com banner dinâmico do "Livro do Mês" e carrosséis de recomendação personalizados.
*   ** Catálogo e Busca (RF07):** Pesquisa reativa por título ou autor com filtragem instantânea via Chips de categorias acadêmicas.
*   ** Detalhes e Reservas (RF08):** Tela de especificações completa com fluxos de reserva imediata e adição à lista de leitura.
*   ** Avaliação Colaborativa (RF09):** Sistema de feedback com notas de 1 a 5 estrelas e listagem de comentários de outros leitores.
*   ** Histórico e Prazos (RF10 - RF11):** Painel de controle para o usuário monitorar empréstimos ativos, reservas pendentes e histórico de devoluções.
*   ** Publicação Acadêmica (RF12 - RF13):** Formulário de envio de obras (PDF) com acompanhamento de status em tempo real (Em análise/Aprovado).
*   ** Assistente Virtual (RF14):** Chat interativo integrado para suporte ao usuário e dúvidas sobre o acervo.
*   ** Painel Administrativo:** Interface exclusiva para moderadores realizarem a aprovação ou rejeição de publicações diretamente no app.

---

## Tecnologias Utilizadas

Para garantir performance, fluidez e uma arquitetura escalável (Enterprise Level), utilizamos o stack tecnológico mais moderno da plataforma Android:

*   **Linguagem:** [Kotlin](https://kotlinlang.org/) (Corrotinas e Flow para processamento assíncrono).
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Desenvolvimento declarativo e reativo).
*   **Design System:** [Material Design 3](https://m3.material.io/) (Identidade visual customizada com as cores da UNIFOR).
*   **Arquitetura:** **MVVM** (Model-View-ViewModel) com separação rigorosa de camadas.
*   **Data Management:** **Repository Pattern** com implementação Singleton para sincronização de estado global em tempo real.
*   **Navegação:** `Navigation Compose` com suporte a rotas parametrizadas e transições suaves.

---

## Como Executar o App

1.  **Requisitos:** Android Studio (Versão Ladybug ou superior) e JDK 17+.
2.  **Clone:** Importe o projeto no Android Studio.
3.  **Sincronização:** Aguarde o Gradle baixar as dependências (`material-icons-extended`, `navigation-compose`, etc).
4.  **Emulador:** Recomenda-se um dispositivo com API 33 (Android 13) ou superior.
5.  **Execução:** Clique em **Run 'app'** e acesse com qualquer credencial (Simulação Reativa).

---
*Projeto de Extensão Universitária - Universidade de Fortaleza (UNIFOR)*
