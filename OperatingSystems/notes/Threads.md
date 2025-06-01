**Threads**

Thread é a unidade de trabalho mais básica no qual um processo é
constituído. Todo processo tem pelo menos uma thread, chamada de
**thread de controle**, porém, sistemas atuais suportam múltiplas
threads em um único processo, isso é chamado de **multithreading**,
apesar disso, ainda há programas que não fazem uso de **multithreading**
como **NodeJs**

**Threads** em um processo compartilham o espaço de memória, porém cada
thread possui seu próprio contexto, pilha, estado e contador de
programa.

Um processo terá o espaço de memória, variáveis globais, arquivos
abertos, processos filhos e permissões.

Por compartilhar o mesmo espaço de endereçamento, o **context switch**
entre threads é mais rápido, já que a troca da memória do programa é a
parte mais pesada deste processo. Porém, por compartilharem o mesmo
espaço, uma thread pode afetar o funcionamento de outra. O navegador web
Chrome, ao invés de colocar cada nova aba em um mesmo processo, cria um
processo para cada nova aba, e dentro dessas há múltiplas threads que
lidam com a renderização da interface e ações de **I/O**. Já o Firefox,
ao menos antigamente, era arquitetura de forma **single-process**,
**multi-threading**, tendo apenas um processo, com múltiplas threads
lidando com abas, extensões, etc.

Atualmente, tanto o Chrome quanto o Firefox utilizam o conceito de
**multi-process, multi-threading**, vários processos, com um processo
principal lidando com a interface, e vários outros lidando com abas e
extensões, dentro desses processos há diversas threads responsáveis por
renderização, processamento, network, **I/O**, etc.

A thread é parte que realmente executa as instruções na CPU. O **process
scheduler** não escalona processos inteiros para serem executados, eles
estão na memória, porém o que é executado no ciclo de execução são as
instruções presentes dentro de uma thread de um processador.

Há duas formas de implementar **threads**:

-   **Modo Usuário -** São gerenciadas pela aplicação, não pelo SO, o
    Kernel vê apenas um processo, mesmo que ele tenha múltiplas threads.

    -   Elas são mais leves de gerenciar e não requerem syscalls, então
        são mais rápidas,

    -   Porém, se um thread bloquear, o processo inteiro é bloqueado,
        não aproveitando múltiplos núcleos da CPU.

-   **Modo Kernel** **-** Gerenciadas pelo SO, cada thread é visível
    pelo kernel e pode ser agendada separadamente

    -   Suporte nativo a paralelismo.

    -   Porém, são mais pesadas e a mudança de contexto envolve o
        kernel, o gerenciamento é mais lento.

Elas não são implementadas em um único espaço, devido a questões de
privilégio, devido a isso as threads podem ser implementadas de 3 jeitos

-   **M:N (Híbrido) -** Várias threads em modo usuário mapeadas em
    várias threads em modo kernel, combinando desempenho e paralelismo,
    porém é mais complexo de se implementar.

-   **1:1 (Linux e Windows) -** Cada thread em modo usuário corresponde
    a uma thread no kernel. Essa implementação é realiza com
    **pthreads**

-   **N:1 -** Todas as threads em modo usuário, compartilham uma única
    thread em modo kernel, é mais leve, porém não há paralelismo
