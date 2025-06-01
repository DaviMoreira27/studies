**Deadlock**

É uma situação onde um ou mais processos ficam bloqueados
indefinidamente, cada um esperando um recurso que está sendo segurado
por outro. Para que um **deadlock** ocorra, 4 condições são necessárias
**ao mesmo tempo**:

-   **Exclusão Mútua -** cada recurso pode estar somente em uma de duas
    situações: ou associado a um único processo ou disponível

-   **Posse e espera -** Um processo que já detém um recurso está
    esperando a liberação de outro recurso por outro processo

-   **Não Preempção** **-** Recursos não podem ser retirar a força de um
    processo

-   **Espera Circular -** Existe uma cadeia de processos, onde cada
    processo espera um recurso que outro processo está ocupando.

Para lidar com deadlocks pode se optar por 4 opções:

-   **Ignorar o problema -** Quando a probabilidade/frequência de um
    deadlock é baixa, compensa mais reiniciar o sistema e prosseguir com
    a tarefa desejada.

-   **Detectar e recuperar o problema -** Permite que deadlocks ocorram,
    porém os detecta e trata-os automaticamente, sem intervenção manual
    através de algoritmos.

    -   **Detecção com um recurso de cada tipo -** Ao montar um grafo,
        se houver um ciclo onde todos os recursos estão ocupados por
        outros, então ira ocorrer um deadlock.

    -   **Detecção com vários recursos de cada tipo -** Montamos dois
        vetores, um com o número de recursos existentes **(E)**, outro
        com o número de recursos (**A)** disponíveis. Após isso montamos
        duas matrizes, uma **matriz de alocação corrente**, indicando
        quantas instâncias de um recurso **j** estão alocadas para o
        processo **i (Cij)**. E uma **matriz de requisições**, onde cada
        entrada **Rij** indica quantas instâncias do recurso **j** o
        processo **i** ainda precisa para terminar. Verificamos se algum
        processo pode ter sua requisição **R\[i\]** satisfeita com os
        recursos disponíveis em **A**, se sim, liberamos os recursos que
        ele estava usando **C\[i\]** e os adicionamos de volta a **A**.
        Repetimos até todos os processos conseguirem terminar **(sem
        deadlock)**, ou nenhum processo puder mais continuar
        **(deadlock).**

    -   **Recuperação por meio de preempção -** Possiblidade de retirar
        temporariamente um recurso de seu atual dono e entrega-lo a
        outro processo.

    -   **Recuperação por meio de rollback -** Recursos alocados a um
        processo são armazenados em arquivos de verificação, quando
        ocorre um deadlock, os processos voltam ao estado no qual
        estavam antes do deadlock **(solução cara)**.

    -   **Recuperação por meio de eliminação de processos -** Processos
        que estão no ciclo do deadlock são retirados do ciclo, **melhor
        solução para processos que não possuem drawbacks** **para o
        sistema**.

-   **Evitar dinamicamente o problema -** Alocação individual de
    recursos à medida que os processos necessitam, porém a cada nova
    solicitação o sistema deve verificar se o sistema entrará em um
    estado seguro ou não.

    -   **Estado Seguro -** Quando há uma ordem onde todos os processos
        podem obter os recursos requisitados e retorna-los sem que haja
        risco de um **deadlock**.

    -   **Estado Inseguro -** Não necessariamente significa que um
        deadlock **vai** acontecer, porém não é possível dizer que ele
        **não ira acontecer**, nesse estado significa que a qualquer
        pedido de recursos o sistema pode ou não entrar em deadlock.

    -   Uma forma de alcançar isso é através do **algoritmo do
        banqueiro**, o sistema so emprestará recursos a um processo se
        tiver certeza que o cliente poderá devolve-lo futuramente.

        -   Cada processo deve informar previamente o máximo de recursos
            que vai precisar

        -   O sistema mantém: **o que já foi alocado, o que ainda está
            disponível, o que cada processo ainda pode pedir**

        -   Quando um processo fizer uma nova solicitação, o sistema
            deverá simular a alocação, verificar se essa alocação de
            recurso faria com que o estado se tornasse inseguro, se sim,
            nega ou adia a solicitação, se não, prossiga.

        -   Utiliza um **vetor de recursos disponíveis, uma matriz de
            alocação atual, uma matriz de necessidade restante e um
            vetor de recursos máximos por processo.**

-   **Prevenir Deadlock -** Impedir que uma das 4 condições ocorra

    -   **Exclusão Mútua -** É possível alocar recurso apenas quando for
        absolutamente necessário, não é muito viável pois pode causar
        **Race Conditions**.

    -   **Uso e espera -** Pode-se requisitar todos os recursos
        inicialmente, porém o processo pode não saber o que vai
        precisar. Ou então, quando um processo quiser um novo recurso,
        deve liberar todos os que possui e requisitar todos novamente.

    -   **Não-preempção** - A retirada abrupta de um recurso pode
        ocasionar problemas de desempenho ou integridade da informação,
        **não recomendado.**

    -   **Espera Circular -** Pode-se ordenar numericamente os recursos
        e requisita-los apenas em ordem crescente, é ineficiente, porém
        a mais atrativa de ser praticada.
