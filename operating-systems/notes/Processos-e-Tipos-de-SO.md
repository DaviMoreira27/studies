**Processos**

Uma das partes principais que constituem um SO. O SO **controla todos**
os processos. São **programas em execução**.

É formado por:

-   Um espaço de endereço

-   Uma lista de alocação de memória (mínimo, máximo)

-   Um conjunto de registradores (contador de programa.

Possui 3 estados básicos:

-   Bloqueado: Um processo pode ser bloqueado atráves da chamada de
    sistema **block ou pause,** ou então se não há entradas disponíveis
    para que o processo continue a execução.

-   **Pronto**: É passado para pronto quando o tempo daquele processo na
    CPU acaba e outro processo é colocado em seu lugar. Ou então, caso o
    evento que estava bloqueando esse processo acabe.

-   **Executando**

Pode ser categorizado de duas formas:

**CPU-bound:** Utilizam muito o processador.

**IO-bound**: Realizam muitas operações de entrada e saída

Cada processo possui uma entrada na tabela de processos. Cada entrada
possui um ponteiro para o bloco de controle de processos, um local no SO
que contém todas as informações dos processos (contextos de hardware,
software, endereço de memória, etc).

**Chamadas de Sistemas**

É a interface fundamental entre uma aplicação e o Kernel. Syscalls não
são chamadas diretamente, mas sim invocadas por funções incorporadoras
presentes da **glibc (a biblioteca padrão C em Linux)**.\
\
Normalmente o trabalho dessas funções incorporadoras é básico, onde ao
invés de apenas chamar a sys call, é copiado os argumentos para os
registradores corretos e setando a variável **errno,** basicamente uma
variável responsável por indicar oq deu errado em uma chamada de
sistema.

O processador opera em **dois modos principais** (sistemas modernos
possuem múltiplos modos):

-   **Modo do Usuário (User Mode):**

    -   Usado por aplicações comuns

    -   Sem acessos direto ao hardware ou a instruções privilegiadas

    -   As instruções disponíveis são limitadas para evitar falhas ou
        ataques ao sistema.

    -   Se o programa tentar executar uma instrução privilegiada, o
        hardware bloqueia e gera uma exceção.

-   **Modo Kernel (Kernel Mode):**

    -   Usado exclusivamente pelo **Sistema Operacional.** Somente o
        código do **Kernel** roda nesse modo

    -   Acesso total a todos os recursos do sistema, incluindo hardware
        e instruções privilegiadas

Passo a passo de execução de uma syscall:

1.  O usuário utiliza alguma função wrapper ao invés de chamar a syscall
    diretamente, como exemplo será utilizado o **printf**.

2.  Esse comando tem na sua implementação a chamada de sistema
    **write**, ela recebe como primeiro parâmetro **(fd)** o **descritor
    do arquivo**, basicamente o local onde será feita a escrita da
    string, como segundo parâmetro, é passado o endereço de memória do
    que será escrito **(buff),** como último parâmetro **(count)**, é
    passado e passado o número de caracteres a serem escritos.
    ***write(1, "Teste\\n", 6).*** Leia mais sobre [[File Descriptors
    aqui.]{.underline}](https://stackoverflow.com/questions/5256599/what-are-file-descriptors-explained-in-simple-terms)

3.  A **libc** então converte a instrução para **assembly code**,
    armazenando nos registradores [[o número identificador da
    syscall]{.underline}](https://filippo.io/linux-syscall-table/), e
    cada um dos argumentos passados. Além disso, ele executa a instrução
    especial ***syscall***, essa instrução entra no modo kernel e
    entrega o controle ao SO.

4.  Em modo kernel, o processador salva o contexto do modo usuário,
    desativa ou não as interrupções (depende do SO), troca para a
    **stack do kernel** e salta para a função inicial do Kernel,
    configurada em **MSR_LSTAR.** Ele é um **registrador especial**,
    usado na arquitetura **x86_64**, ele guarda o endereço de memória da
    função inicial que o sistema deve executar ao entrar em modo kernel.
    Essa função deve salvar o contexto do usuário, verificar se a
    syscall **(write)** é válida, chamar a função correspondente
    **(sys_write)**, retornar o valor da syscall em **rax**, e voltar
    para o modo usuário, **sysret**.

5.  Voltando a chamada da **MSR_LSTAR**, o sistema, com o **rax**
    armazenado em dos registradores, procura a syscall desejada na
    tabela de syscalls **(sys_call_table)**, e executa a função com os
    argumentos que estavam nos registradores.

6.  Ao executar a syscall desejada, o kernel verifica a validade dos
    argumentos, copia os dados do espaço do usuário para o espaço do
    kernel, envia os dados para o driver do terminal ou seja lá qual
    seja o driver do **file descriptor**, e retorna a quantidade de
    bytes escritos, ou um erro.

7.  Após a execução é armazenado o valor de retorno da syscall no rax
    (-1 error, ou 0 sucesso), usa a instru ção **sysret**, para voltar
    ao modo usuário, restaura o contexto e o sistema continua a execução
    do estava em fila.

Utilizando **man syscalls** é possível ver todas as syscalls do Linux.

**Tipos de SO (Funcionamento)**

Há dois tipos principais de SO no quesito de funcionamento,
**Multiprogramming e Multitasking.**

-   **Multiprogramming:** É uma técnica que permite que vários programas
    fiquem na carregados na memória ao mesmo tempo, alternando entre
    eles para maximizar o uso de CPU. Nele, a memória armazena vários
    processos em memória, prontos para serem executados, quando um
    processo precisa esperar por **I/O**, a CPU transfere o seu tempo
    para outro processo.

    -   Para esses SOs, são utilizados **algoritmos não preemptivos,**
        ou seja, não é levado em consideração a prioridade dos
        processos, como por exemplo **First Come FIrst Served e Shortest
        Job First**.

-   **Multitasking (Time Sharing):** Já deste modo, vários processos
    compartilham a CPU, alternando rapidamente entre eles. Nele a CPU é
    dividida em fatias de tempo, cada processo recebe uma fatia. Quando
    acaba, o SO interrompe e passa para o próximo.

    -   Aqui é essencial o uso de algoritmos preemptivos e interativos,
        como **Garantido, por Loteria, etc.**

-   **Multiprocessamento:** É uma técnica onde o sistema possui dois ou
    mais processadores (ou núcleos) que trabalham em paralelo,
    permitindo que vários processos sejam executados ao mesmo tempo.
    Diferente da multitarefa, aqui realmente há execução simultânea
    física, e não apenas alternância rápida entre processos. Isso
    melhora o desempenho, confiabilidade e permite maior escalabilidade.

    -   Para esses SOs, o sistema pode usar **algoritmos preemptivos ou
        não preemptivos**, mas geralmente **algoritmos preemptivos**
        como **Round-Robin** ou **Prioridade Preemptiva** são usados
        para tirar o máximo proveito dos processadores.

-   **Multiusuário**: Nesse tipo de sistema, **vários usuários** podem
    acessar e usar o computador **simultaneamente**, com seus próprios
    processos, arquivos e configurações. O SO gerencia os recursos de
    forma que cada usuário tenha uma **experiência isolada e segura**,
    garantindo que um não interfira no outro.

    -   Para funcionar bem, esses sistemas usam **algoritmos de
        escalonamento justos**, como **Fair-Share** ou **algoritmos por
        prioridade**, para distribuir os recursos de forma equilibrada
        entre os usuários.

-   **Sistemas em Tempo Real (Real Time)** - Sistemas onde processos não
    podem ter a sua resposta atrasada. Há dois tipos principais;

    -   **Hard Real Time:** atrasos não são tolerados

    -   **Soft Real Time:** atrasos são tolerados, porém exigem uma
        baixa latência.

    -   Em *STRs* podem ocorrer eventos (entrada de dados específicos,
        comando específico, etc), esses eventos podem causar a execução
        de processos **periodicamente**, que ocorrem em intervalos
        regulares de tempo (**periódicos**) e **aperiódicos**, que
        ocorrem em intervalos irregulares. Além disso há dois tipos de
        algoritmos:

        -   **Estáticos:** decisões de escalonamento antes do sistema
            começar, onde já se tem a informação necessária.

        -   **Dinâmicas:** decisões em tempo real.

Um Sistema Operacional pode conter uma ou diversas dessas
características ao mesmo tempo, porém há características que são
conflitantes.

**Tipos de SO (Construção)**

-   **Monolítico:** Todos os módulos do sistema (gerenciamento de
    arquivos, memória, processos, drivers, etc.) são compilados
    separadamente e ligados em um único arquivo executável, esse arquivo
    é o kernel, que roda com acesso total ao hardware, por exemplo, o
    Linux pode ser compilado em um único arquivo **vmlinuz**, esse
    arquivo contém todos os módulos essenciais integrados. Os módulos
    interagem **diretamente entre si,** com interfaces.

    -   Como vantagem, possui uma estrutura simples e boa eficiência e
        desempenho em tempo de execução, porém é difícil de manter e
        escalar, e um erro em um módulo, pode comprometer todo o
        sistema.

-   **Microkernel:** Apenas as funções essenciais, como comunicação
    entre processos, gerenciamento de memória e interrupções, ficam no
    núcleo (kernel), sistemas de arquivos, driver e outros, são
    executado fora do kernel, no **modo usuário**. Os serviços se
    comunicam com o microkernel via troca de mensagens **IPC -
    Inter-Process Communication)**.

    -   Possui como vantagem, maior modularidade e segurança, porém o
        desempenho pode ser inferior devido ao custo das trocas de
        mensagens.

    -   **IPC (Inter-Process-Communication):** É um conjunto de
        mecanismos que permite que dois ou mais processos se comuniquem,
        trocando dados e sinais. Nesse tipo de sistema operacional
        (microkernel), os serviços são processados individuais, não
        compartilhando memória diretamente. Eles podem se comunicar de
        diversas formas, como filas de mensagens, pipes, sockets e
        signals.

    -   Em sistemas tradicionais, essa comunicação entre processos é
        feita via memória compartilhada, requisitando o uso de
        semáforos, mutexes, e algoritmos que lidem com deadlock e
        exclusão mútua.

-   **Layered:** O sistema é divido em **camadas hierárquicas**, onde
    cada camada fornece serviços para a camada superior e usa serviços
    da inferior,.

    -   Possui como vantagem, isolar as funções do sistema operacional,
        facilitando manutenção e depuração, porém cada nova camada
        implica uma mudança no modo de acesso.

-   **Client-Server (Cliente/Servidor):** O Kernel atua como
    **cliente**, e os serviços do sistema (arquivos, drivers, memoria,
    etc.) atuam como servidores, cada um rodando como um processo
    separado. O kernel é mínimo, assim como na arquitetura de
    microkernel. Enquanto a maior parte do sistema está implementada
    como processos de usuário. Os processos servidores não possuem
    acesso direto ao hardware, dessa forma, se algum problema ocorrer, o
    hardware não é afetado. Os serviços que controlam o **I/O,** ainda
    são implementados a nível de kernel, devido as complicações de sua
    implementação a nível de usuário.

-   **Máquina Virtual:** O sistema cria uma abstração de múltiplas
    máquinas virtuais, independentes e isolados, onde cada máquina
    fornece uma cópia virtual do hardware,incluindo modos de acesso,
    interrupções, dispositivos de E/S, etc. Cada máquina virtual pode
    ter seu próprio SO.
