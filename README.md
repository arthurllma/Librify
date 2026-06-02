# Librify - Biblioteca Virtual Acadêmica (UNIFOR)

O **Librify** é um aplicativo mobile nativo desenvolvido como um projeto de extensão universitária para a **UNIFOR**. O objetivo principal da plataforma é facilitar o compartilhamento, a circulação e o acesso a obras acadêmicas e literárias entre a comunidade universitária, promovendo a democratização do conhecimento.

## 🚀 Arquitetura e Tecnologias

O projeto foi construído seguindo as melhores práticas de desenvolvimento Android moderno:

*   **Linguagem:** [Kotlin](https://kotlinlang.org/) (100% nativo).
*   **Interface UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Arquitetura declarativa).
*   **Design System:** [Material Design 3](https://m3.material.io/) com identidade visual personalizada (Azul UNIFOR #1A73E8).
*   **Padrão de Arquitetura:** **MVVM** (Model-View-ViewModel) para separação clara de responsabilidades.
*   **Gerenciamento de Estado:** `StateFlow` e `MutableStateFlow` garantindo o *Unidirectional Data Flow* (UDF).
*   **Padrão de Dados:** **Repository Pattern** (implementado via Singleton `ActivityRepository`) para compartilhamento de estado global e reatividade em tempo real entre telas.
*   **Navegação:** `Navigation Compose` com suporte a rotas parametrizadas e gerenciamento de pilha (backstack).

## ✨ Funcionalidades Implementadas

### 🔐 Autenticação e Segurança
*   **Fluxo Completo:** Telas de Login, Cadastro e Recuperação de Senha.
*   **Segurança de Navegação:** Lógica de `popUpTo` e `inclusive` no Logout para limpar o histórico e proteger o acesso pós-sessão.

### 🏠 Home e Descoberta
*   **Destaque Mensal:** Banner proeminente com o "Livro do Mês" e acesso rápido a detalhes.
*   **Seções Inteligentes:** Carrosséis horizontais de "Livros em destaque" e "Recomendados para você".

### 📚 Catálogo e Detalhes
*   **Busca Reativa:** Filtragem em tempo real por título ou autor através da `CatalogViewModel`.
*   **Filtros por Categoria:** Uso de `FilterChips` para navegação rápida entre gêneros acadêmicos.
*   **Detalhes do Livro:** Tela rica com sinopse, especificações técnicas e sistema de avaliações (estrelas e comentários).
*   **Ações de Usuário:** Funcionalidades de Reserva de Livro e Adição à Lista Pessoal.

### ✍️ Publicação e Gestão (Fluxo Central)
*   **Envio de Obras:** Formulário vertical completo com simulação de upload de arquivo PDF.
*   **Acompanhamento de Status:** Badges coloridos (Em análise, Aprovado, Rejeitado) que atualizam reativamente conforme a moderação.

### 📜 Histórico e Atividades
*   **Divisão por Abas:** Uso de `TabRow` para separar "Atividades Atuais" (Empréstimos/Reservas) de "Histórico Passado".

### 👤 Perfil e Administração
*   **Central do Usuário:** Gestão de conta e acesso a notificações.
*   **Assistente Virtual:** Chat interativo com respostas automáticas simuladas via `ChatViewModel`.
*   **Painel Administrativo:** Interface exclusiva para moderadores aprovarem ou rejeitarem publicações, com impacto imediato no repositório global.

## 📁 Estrutura de Diretórios

```text
br.unifor.librify/
├── data/
│   └── repository/
│       └── ActivityRepository.kt       # Singleton (In-memory Database)
├── domain/
│   └── model/
│       ├── Book.kt                    # Modelo de Livros
│       ├── Loan.kt                    # Modelo de Empréstimos/Reservas
│       ├── Publication.kt             # Modelo de Submissões
│       └── Review.kt                  # Modelo de Avaliações
├── ui/
│   ├── components/                    # Componentes Core (LibrifyButton, BookCard)
│   ├── features/                      # Módulos por funcionalidade
│   │   ├── admin/                     # Painel de Aprovação
│   │   ├── auth/                      # Login, Register, ForgotPassword
│   │   ├── bookdetail/                # Tela de Detalhes
│   │   ├── catalog/                   # Busca e Filtros
│   │   ├── chat/                      # Assistente Virtual
│   │   ├── history/                   # Atividades e Prazos
│   │   ├── home/                      # Dashboard Inicial
│   │   ├── profile/                   # Perfil do Usuário
│   │   └── publish/                   # Formulário de Publicação
│   ├── navigation/
│   │   ├── LibrifyNavGraph.kt         # Grafo de Navegação Central
│   │   └── MainScreen.kt              # Container da Barra Inferior
│   └── theme/                         # Design System (Color.kt, Theme.kt)
└── MainActivity.kt                    # Ponto de Entrada do App
```

## 🛠️ Como Executar o Projeto

1.  **Pré-requisitos:** Certifique-se de ter o [Android Studio (Ladybug ou superior)](https://developer.android.com/studio) instalado.
2.  **Clonar/Abrir:** Abra o projeto no Android Studio selecionando a pasta raiz `Librify`.
3.  **Sincronização:** Aguarde o término do **Gradle Sync** para baixar as dependências (Compose, Navigation, Icons Extended).
4.  **Emulador:** No *Device Manager*, inicie um emulador (recomendado API 33+).
5.  **Rodar:** Clique no botão verde **Run 'app'** (ou `Shift + F10`).
6.  **Testar:** Na tela de login, clique em **"Entrar"** para acessar a experiência completa com dados simulados reativos.

---
*Desenvolvido como projeto de extensão para a UNIFOR.*
