Na execução de múltiplos processos em um SO, pode ser necessário acessar
recursos que outro processo já esteja utilizando, ou então comunicar-se
entre si ou executa-los em uma sequência correta.

-   **Race Conditions**: Processos tentando acessar recursos disputados
    e utilizados concorrentemente por outros processos.

-   **Regiões Críticas** - São recursos compartilhados, que podem/estão
    sofrendo Race Conditions.

-   **Exclusão Mútua** - Significa garantir que um processo não terá
    acesso à uma região crítica quando outro processo pode estar
    utilizando essa região. É a propriedade de um programa que garante
    que somente um processo tem acesso a determinada variável. Há 4
    condições para uma boa solução de exclusão mútua.

    -   **Dois processos não podem estar simultaneamente na mesma região
        crítica;**

    -   **Nenhuma restrição deve ser feita com relação à CPU**

    -   **Processos que não estão em regiões críticas não podem bloquear
        outros processos que desejam utilizar regiões críticas;**

    -   **Processos não podem esperar para sempre para acessarem regiões
        críticas;**

Há 5 implementações que visam garantir a exclusão mútua

-   **Espera Ocupada (Busy Waiting) -** Sincronização dos processos
    através de flags. Todos os processos ficam em estado de espera
    (looping), até que possam utilizar a região crítica. Há 4 formas de
    implementar esse modo de sincronização:

    -   Debilitar todas as interrupções ao entrar na região crítica, so
        habilitando ao sair, sem as interrupções, não há troca entre
        processos. Pode causar problemas devido a regra 2 e regra 4.

    -   É possível também utilizar variáveis de *lock,* se essa variável
        estiver no valor 0, significa que nenhum processo está na região
        crítica, e vice-versa. Viola condição 1.

    -   A alternância estrita, utiliza uma variável compartilhada que
        indica de quem é a vez de acessar a região crítica. Um dos
        problemas é a rigidez, um processo urgente pode ficar bloqueado.
        VIola a condição 3.

    -   A solução de Peterson e TSL (Test and Set Lock) implementa uma
        variável para bloquear a entrada de um processo na região
        crítica quando outro processo já está lá. Essa variável é
        compartilhada pelos processos que concorrem pelo uso da **região
        crítica.** Por peterson há duas variáveis, uma para indicar quem
        possui preferência para acessar a **RC** e outra para indicar se
        um processo quer entrar lá ou não. A principal diferença com a
        TLS, é que a TLS, além de não ter uma variável indicando quem
        possui a preferência, ela implementa via hardware as
        verificações.

-   **Primitivas Sleep/Wakeup -** Um par de primitivas pode ser
    utilizada para bloquear e desbloquear processos que desejam utilizar
    a **RC**. A primitiva Sleep é uma chamada de sistema que bloqueia o
    processo que a chamou até que algum outro processo a acorde. A
    chamada Wakeup acorda o processo suspenso. Ambas possuem dois
    parâmetros, o processo sendo manipulado e um endereço de memória
    para realizar a correspondência entre uma primitiva Sleep com sua
    correspondente Wakeup.

    -   Um problema comum solucionado por este método é a questão do
        **Produtor/Consumidor,** (bounded buffer ou buffer limitado).
        Dois processos compartilham um buffer de tamanho fixo. O
        processo produtor coloca dados, o consumidor retira. Os
        principais problemas enfrentados durante esse procedimento são;
        produtor quer inserir mais dados, porém o buffer já está cheio,
        o consumidor deseja retirar dados, porém o buffer está vazio.
        Isso é solucionado colocando um dos processos para dormir, e
        quando a problemática não existir mais, lança a chamada de
        **Wakeup**.

    -   Um problema que pode acontecer com essa implementação é o de
        *lost wakeup*, quando um processo recebe um sinal de Wakeup
        antes de ser suspenso, causando um **deadlock**.

    -   A solução para isso seria implementar um bit de controle, no
        qual recebe um valor true quando um sinal é enviado para um
        processo que não está dormindo. Porém, caso haja vários pares de
        processos, vários bits devem ser criados, sobrecarregando o
        sistema.

-   **Semáforos** - Outra solução para exclusão mútua são os semáforos,
    implementado por uma variável inteira que armazena o número de
    sinais wakeup enviados. Ele pode ter valor 0 quando não há sinal, ou
    o número de sinais. Conta o número de recursos disponíveis no SO, se
    a variável for 0, não há recurso disponível. Adiciona também duas
    novas primitivas, **up** e **down.** Há dois tipos de semáforos.

    -   **Binário ou Mutex**: Utiliza apenas 0 ou 1. Apenas um processo
        por vez na **RC**. Quando um processo deseja entrar na **RC** o
        mutex trava, caso o mutex já esteja travado, o processo que
        deseja entrar espera até que seja liberado. Quando o outro
        processo finaliza, ele destrava o mutex, permitindo que o outro
        processo entre. O processo que está esperando pode ser avisado
        de duas formas, **Busy Waiting** ou **Sleep/Wakeup**.

    -   **Contador (solução geral)**: Verifica se o valor do semáforo é
        maior que 0, se for, o semáforo é decrementado. Se for 0, o
        processo é posto para dormir até completar a operação down. **Se
        o valor do semáforo for 0**, nenhum recurso está disponível, e
        **processos podem estar bloqueados esperando**, caso ele seja
        maior que zero é um indicativo que pode não haver nenhum
        processo esperando.

        -   **Down -** Executada sempre que um recurso deseja usar uma
            RC, caso o semáforo seja maior que seja, decrementa o valor,
            indicando que irá utilizar a RC, caso seja igual a zero,
            põem o processo que chamou a primitiva em espera, até que a
            RC esteja disponível.

        -   **Up** - Executado sempre que um processo liberar o recurso,
            incrementa o valor do semáforo. Caso haja processos
            dormindo, o Up escolhe um e libera o down, nessa caso, o
            valor do semáforo permanece o mesmo.

    -   Ambas soluções descritas acima são **atômicas,** enquanto o SO
        testa o semáforo, todas as interrupções são desabilitadas no
        sistema, para garantir que nenhum processo tente mexer nessa
        variável enquanto isso. Se houver múltiplas CPUs, cada variável
        é protegida por uma variável lock, como a instrução **TSL.**

    -   Ao utilizar 3 semáforos resolvemos o problema do
        produtor/consumidor.

        -   **Full:** Conta o número de slots no buffer que estão
            ocupados, iniciando com 0.

        -   **Empty:** Conta o número de slots no buffer que estão
            vazios, inciado com o número total de slots no buffer.

        -   **Mutex:** Garante que os processos produtor e consumidor,
            não acessem o buffer ao mesmo tempo. Esse semáforo permite a
            exclusão mútua.

-   **Monitores -** Uma estrutura de sincronização de alto nível, usada
    para controlar o acesso concorrente a recursos compartilhados entre
    processos ou threads**.** Um conjunto de procedimentos, variáveis e
    estrutura de dados, agrupadas em único módulo. **Somente um processo
    pode estar ativo dentro do monitor em um mesmo instante;** outros
    processos ficam bloqueados até que possam estar ativos no monitor.

    -   Processos que tentarem entrar em um monitor ocupado são
        bloqueados automaticamente.

    -   O acesso às variáveis compartilhadas só pode ser feito via
        procedimentos do monitor.

    -   Os monitores utilizam variáveis de condição para lidar com
        espera ativa (esperar por uma certa condição dentro do monitor).

        -   **Wait(cond) -** Suspende a execução até que a condição seja
            sinalizada

        -   **Signal(cond)** - Acorda um processo esperando a condição.

        -   **Broadcast(cond)** - Mais raro, acorda todos os processos
            que esperam uma dada condição.

-   **Passagem de Mensagem -** Provê troca de mensagens entre processos
    rodando em maquinas diferentes. Utiliza duas primitivas, **send** e
    **receive.**

    -   **Send -** Envia uma mensagem para um determinado destino.

    -   **Receive -** Recebe essa mensagem em uma determinada fonte. Se
        nenhuma mensagem estiver disponível, o procedimento **receive**
        é bloqueado até que uma mensagem chegue. Esse procedimento envia
        uma mensagem **acknowledgement** para o procedimento **send**,
        caso essa mensagem não seja recebida, o send reenvia a mensagem.

    -   Utiliza uma rede (internet, Ethernet, etc) para comunicação
        entre SOs.

    -   Um problema que pode acontecer é a mensagem de confirmação ser
        perdida. Para evitar isso, o send possui uma identificação para
        cada mensagem, o receive irá receber essa mensagem duplicada,
        verá que é igual a uma que já foi recebida pelo sistema, e irá
        descarta-la.

-   A outras formas de comunicação entre processos:

    -   **RPC (Remote Procedure Call)** - Rotinas que permitem a
        comunicação de processos em diferentes máquinas

    -   **MPI (Message Passing Interface) -** Usada em sistemas
        paralelos.

    -   **RMI Java (Remote Method Invocation) -** Permite que um objeto
        ativo em uma máquina virtual Java possa interagir com objetos de
        outras máquinas virtuais Java, independentemente da localização
        dessas máquinas virtuais;

    -   **Web Services: -** Permite que serviços sejam compartilhados
        através da Web

    -   **gRPC (Google Remote Procedure Call) -** Um framework de
        comunicação remota que permite que aplicações em diferentes
        linguagens troquem dados estruturados via chamadas de função,
        usando Protocol Buffers e HTTP/2.
