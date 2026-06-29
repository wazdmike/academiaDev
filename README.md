# AcademiaDev — Plataforma de Cursos Online
 
CLI desenvolvido em Java puro, seguindo estritamente os princípios da **Clean Architecture**.
Desenvolvido para fins acadêmicos, simulando uma plataforma simples de cursos.
 
---
 
## Como executar
 
### No IntelliJ IDEA
1. Abra o projeto no IntelliJ
2. Aguarde o Maven
3. Execute a classe `Main.java`
 
### Usuários pré-cadastrados para login
| E-mail                    | Perfil  | Plano   |
|---------------------------|---------|---------|
| admin@academiadev.com     | Admin   | —       |
| miguel@email.com          | Student | BASIC   |
| andrea@email.com          | Student | PREMIUM |
| joaop@email.com           | Student | PREMIUM |
 
---

## Justificativas de Design
 
### 1. Como a Regra da Dependência foi seguida
 
A Clean Architecture determina que as dependências de código-fonte só podem apontar para dentro, ou seja, em direção às políticas de mais alto nível. Neste projeto, a hierarquia é:
 
```
infrastructure -> application -> domain -> main
```
 
**Domain:** não depende de nenhuma camada externa. Não há imports de `application` ou `infrastructure`. O domínio utiliza apenas recursos da JDK (`java.lang`, `java.time`). As regras de negócio ficam centralizadas nas entidades.

**Application:** contém os casos de uso e depende somente do Domain. As interfaces de repositório são usadas para abstração de persistência, e os Use Cases recebem essas dependências via construtor, sem instanciar implementações concretas.

**Infrastructure:** implementa os contratos definidos na camada de Application e manipula as entidades do Domain. Aqui ficam os repositórios em memória (`Map`, `List`, `ArrayDeque`), o exportador CSV e o console.

**Main:** responsável apenas por montar a aplicação. É a única camada que conhece todas as outras.
 
---
 

### 2. Como a Injeção de Dependência foi feita em Main.java

A injeção de dependência é feita manualmente via construtor.

```java
CourseRepository courseRepo = new CourseRepositoryInMemory();
UserRepository userRepo = new UserRepositoryInMemory();

EnrollStudentUseCase enrollUseCase =
    new EnrollStudentUseCase(courseRepo, enrollRepo);

ConsoleController controller =
    new ConsoleController(scanner, view, userRepo, csvExporter, enrollUseCase, cancelUseCase);
```
 
Isso garante baixo acoplamento: os Use Cases não sabem se os dados vêm de memória, arquivo ou banco de dados.

---
 
### 3. Como a camada Domain foi mantida pura
 
Três decisões garantem a independência do domínio:

a) Entidades sem frameworks: `Course`, `Student`, `Enrollment` e demais classes são POJOs simples, sem JPA, Lombok ou qualquer dependência externa.

b) Regras de negócio dentro do domínio: validações como progresso de matrícula e regras de inscrição ficam dentro das entidades. Erros de negócio são tratados via exceções do próprio domínio.

c) Persistência abstraída: o domínio não conhece implementação de armazenamento. Ele depende apenas de interfaces.

---
 
### 4. Como os detalhes foram isolados na camada Infrastructure
 
**Persistência em memória.** As quatro implementações de repositório (`CourseRepositoryInMemory`, `UserRepositoryInMemory`, `EnrollmentRepositoryInMemory`, `SupportTicketQueueInMemory`) encapsulam completamente as estruturas de dados:
 
- `Map<String, Course>` para cursos
- `Map<String, User>` para usuários
- `List<Enrollment>` para matrículas
- `ArrayDeque<SupportTicket>` para fila de atendimento
Essas estruturas ficam encapsuladas na infraestrutura e não vazam para outras camadas.
 
**Exportação CSV**: a classe `GenericCsvExporter` utiliza Reflection (`java.lang.reflect.Field`) para mapear atributos dinamicamente e gerar arquivos CSV. Esse comportamento é isolado na infraestrutura por ser um detalhe técnico.
Fluxo:
```
ConsoleController -> Use Case -> List<Entidade> -> CSV Exporter
```
 
Os UseCases nunca importam `GenericCsvExporter`. A camada `application` permanece completamente ignorante de como os dados são formatados para saída.
 
**UI de console.** `ConsoleView` apenas exibe dados. `ConsoleController` lida com entrada do usuário, delega regras para Use Cases e trata exceções de forma amigável.
 
---
 
## Ferramentas e Tecnologias
 
- **Java 22**
- **Collections Framework** — `HashMap`, `ArrayDeque`, `ArrayList`
- **Stream API**
- **Reflection API** — `GenericCsvExporter` em `infrastructure.utils`
- **Sem frameworks externos**
