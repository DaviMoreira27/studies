**Process Scheduling**

O escalonador de processos é um componente do SO que possui como
objetivo a decisão e troca de processos a serem executados pela **CPU.
Existe 3 tipos de escalonadores:**

-   **Short-term Scheduling ou CPU Scheduler -** Escolhe qual processo
    **(ou thread)** vai usar a CPU imediatamente. Atua entre processos
    prontos para executar. Esse scheduler é executado o tempo todo, ele
    deve decidir qual processo tera o tempo da CPU entre todos aqueles
    processos que estão prontos, para tomar essa decisão ele utiliza os
    parâmetros definidos pelo algoritmo no qual foi implementado (Round
    Robin, SJF, FIFO, etc).

-   **Medium-term Scheduling ou Swapping -** Suspende processos que
    estão a muito tempo esperando por recursos, como **I/O ou CPU**.
    Coloca esses processos em uma memória secundária (disco), liberando
    **memória RAM,** para processos ativos . Esse processo é chamado de
    **swapping,** ocorre de tempos em tempos, principalmente quando o
    sistema está com pouco memória.

-   **Long-term Scheduling -** Lida com os processos na **Job Queue**,
    movendo-os para a **Ready Queue**, controla o número de processos em
    estado de prontidão, controla o número de processos ativos no
    sistemas **(multiprogramação).** Por exemplo, casos múltiplos
    programas sejam abertos ao mesmo tempo, o escalonador de longo prazo
    pode segurar a abertura de algum programa para evitar sobrecarga na
    CPU.

Como citado anteriormente, há múltiplas filas de processo em um sistema
operacional. Há 3 principais.

-   **Job Queue -** É lidada pelo **Long-term Scheduling**, armazena
    todos os processos que estão esperando para serem executados. Um
    processo vem para essa fila assim que é criado. Os processos nessa
    fila estão armazenados em disco.

-   **Ready Queue -** O **Short-term Scheduling** controla essa fila.
    Enfileira todos os processos que estão prontos para serem
    executados, os processos nessa fila já estão na memória principal e
    estão esperando para serem incluídos na CPU

-   **Block/Device Queue -** Processos que estão bloqueados devido a
    indisponibilidade de dispositivos de **I/O**. Todo dispositivo
    possui a sua própria fila de processos em espera. Armazenado na
    memória principal.

O escalonador possui como papel principal a mudança de processos a serem
executados pela CPU, a troca entre esses processos é chamada de
**Context Switch**.

A **mudança de contexto** ocorre quando o SO **interrompe** a execução
de um processo e passa a CPU para outro processo. O contexto de um
processo é formado por:

-   Registradores da CPU

-   Stack Pointer

-   Tabela de arquivos abertos

-   Espaço de memória

-   Estado do processo

-   Informações de segurança, prioridade, etc.

Para realizar o **context switch**, o **process scheduler** deve salvar
as informações do processo (registradores, estado, memória, etc.) que
está saindo da CPU em seu **[[BCP (Bloco de controle de
Processos)]{.underline}](https://www.baeldung.com/linux/pcb),** e então
carrega o dados do **BCP** do processo processo a ser executado.

**Algoritmos de escalonamento**

-   **Algoritmos Preemptivos** - Levam em consideração a prioridade de
    um processo, podendo interromper outros caso essa prioridade seja
    maior

-   **Algoritmo Garantido** - Garantias são dadas aos processos dos
    usuários, para cada processo é garantido um tempo mínimo de CPU,
    utilizado em sistemas interativos.

-   **Algoritmo Por Loteria** - Cada processo recebe *tickets* que lhe
    dão o direito a execução, a cada troca de processo, um *ticket*
    aleatório recebe o direito de ocupar a CPU. A prioridade de um
    processo pode ser atribuída a partir do número de *tickets* que um
    processo recebe. Utilizado em sistemas interativos.

-   **Algoritmo Fair-Share** - Cada usuário do sistema recebe um fração
    da CPU, e processos são escalonados visando garantir essa fração. Se
    um usuário possui mais processos que outro, então este usuário com
    maior carga de trabalha receberá uma fatia maior do tempo da CPU.
    Utilizado em sistemas interativos.

-   **Round Robin -** A CPU é alocada para cada processo por um tempo
    fixo (**quantum de tempo)**, quando o **quantum** expira, o processo
    é interrompido e colocado de volta na fila de processos prontos, e o
    próximo processo é executado.

-   **Shortest Remaining Time First (SRTF) -** Variante do **Shortest
    Job First**, o processo com o menor tempo restante de execução é
    selecionado para ser executado, se um novo processo chega com um
    tempo de execução menor, o processo em execução é interrompido.

-   **Multi-Level Feedback Queue (MLFQ) -** Processo são organizados em
    múltiplas filas, as filas com maior prioridade são executadas
    primeiro. Os processos podem mover-se entre as filas com base no seu
    tempo de execução e comportamento. Dentro dessas filas, pode haver
    algoritmos próprios, preemptivos ou não-preemptivos.

-   **Shortest Job First (SJF) -** O processo com menor tempo de
    execução estimado é selecionado. Não permite preempção.

-   **Priority Scheduling -** Cada processo recebe uma prioridade, o
    processo com **maior prioridade** é executado primeiro, uma vez que
    um processo começa a execução, ele não pode ser interrompido, mesmo
    que um processo com maior prioridade chegue.

Atualmente o Linux utiliza o **[[Earliest Eligible Virtual Deadline
First
Scheduling]{.underline}](https://en.wikipedia.org/wiki/Earliest_eligible_virtual_deadline_first_scheduling),**
tendo substituído o **Completely Fair Scheduler** em 2023.
