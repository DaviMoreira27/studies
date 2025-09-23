- Fragmentação Interna - Espaço de sobra em uma partição ocupada

- Fragmentação Externa - A soma dos espaços das partições disponíveis
  é adequada ao tamanho do processo, porém não são contínuas


# Partições Fixas

- Ocorria fragmentação interna

- Fila Única - A primeira partição em que o processo couber, ele será
  colocado

- Fila Múltipla - Cada partição possui uma fila própria

- A quantidade de informações no **BCP** diminui. Agora a CPU já sabe
  quais partições existem no sistema

# Partições Variáveis

- As partições são alocadas dinamicamente

- Ainda pode haver fragmentação interna, porém o desperdício é baixo
  demais para ser considerado

- Para não haver fragmentação externa é necessário mover partição
  livre até a próxima partição livre, tornando-as uma única partição
  contínua. Esse processo é denominado **compactação**, porém esse
  processo é muito custoso para a CPU

- **Bitmap -** Um vetor com um bit para cada partição, ele serve para
  representar se a partição está livre ou ocupada (0 livre, 1
  ocupada).

- **Lista Encadeada -** Uma lista encadeada indicando o conjunto de
  bits ocupados e desocupados próximos, se há um processo ou não
  nesses bits, onde ele começa e qual o seu tamanho. Ex:
  \[P;0;4\]-\>\[H;5;8\] = 111100000000

# Algoritmos de Alocação de Memória para Processos

- **First Fit -** Aloca o primeiro segmento disponível.

- **Best Fit -** Aloca o segmento de memória que possui menor sobra de
  espaço em relação ao processo.

- **Worst Fit -** Aloca o segmento que tem a maior sobra de memória em
  relação ao processo. Combate fragmentação externa, porém favorece a
  interna.

- **Next Fit -** É um algoritmo de alocação de memória usado em particionamento variável.
Ele é uma variação do algoritmo First Fit, com uma diferença importante: a busca pelo
espaço livre começa a partir da última alocação realizada, não do início da memória.

# Memória Paginação e Segmentação

**Memória Virtual -** Expandir a memória física do sistema,
possibilitando a execução de processos maiores. Dividir o processo em
partes menores com o **SO** sendo responsável por essa divisão. Através
de páginas, segmentação ou paginação segmentada. Paginação, tamanhos
iguais, segmentação tamanho variável.

**Paginação -** Dividir a memória em páginas, pedaços de tamanhos
iguais. No disco terá páginas também.

**Espaço de Endereçamento Virtual -** Endereços do processo, 32 bits = 4
GB, 64 bits = 16 EB.

**Tabela de Páginas -** Mapeamento de um endereço virtual com um endereço
real (espaço de memória real ou físico).

O espaço físico da memória se tornou insuficiente para sustentar
diversos processos de forma eficiente, disso veio a necessidade da
criação de um espaço de memória virtual.

Um processo aloca endereços virtuais, no momento da execução de alguma
operação o seu endereço virtual é traduzido pela MMU para um endereço
real. Há duas formas de atingir isso.

## Paginação

A técnica de paginação utiliza blocos de tamanho fixo na MV e MR
chamados de **páginas e page frames**, o SO mantém uma lista de todas os
**page frames** do sistema.

De forma resumida, a memória virtual é separada em páginas (blocos de
tamanho fixo), enquanto a memória física é separada em **page
frames**, ambos possuem o mesmo tamanho.

Enquanto o SO mantém uma lista de todas os **page frames** do sistema,
cada processo possui a sua própria **tabela de páginas**, que mapeia as
suas **páginas virtuais** com os **page frames.**

A tabela de páginas de um processo é constituída de um número da página
virtual, que vem do endereço virtual do processo, e o número do **page
frame** correspondente na memória física.

Ao acessar o dado de um processo, caso a página virtual deste dado ainda
não esteja mapeada com um **page frame**, o processo deve retornar um
**PAGE_FAULT** e alocar os page frames na memória física.

O tamanho das páginas (atualmente 4Kb) é definido pelo hardware, na
construção do computador, a CPU + MMU define um tamanho suportado para
as páginas. Dentro desse range de valores suportados, o sistema
operacional em seu kernel específico para aquela arquitetura define o
tamanho exato a ser usado. Porém, também há tamanhos alternativos
chamados de **Huge Pages/Large Pages.** Para cargas de trabalho que
manipulam grandes volumes de memória, como bancos de dados, máquinas
virtuais e aplicações científicas, os tamanhos padrão (4 KB) são
ineficientes, porque as tabelas de páginas ficam muito grandes e o tempo
gasto para traduzir endereços aumenta consideravelmente. Dessa forma, o
operador do computador pode configurar para utilizar ou não páginas
maiores (dependendo da arquitetura, pode-se chegar até 1 GB).

O principal problema da paginação é **fragmentação interna:**

- **Páginas maiores:** leitura mais eficiente, tabela menor, mas maior
  fragmentação interna.

- **Páginas menores:** leitura menos eficiente, tabela maior, mas
  menor fragmentação interna.

### Onde armazenar a tabela de páginas?

A tabela pode ser armazenada em 3 lugares diferentes:

- Em um conjunto de registradores (caso a memória seja pequena)

  - Apesar de ser rápido, em todo chaveamento de contexto será
    necessário carregar toda a tabela de páginas do processo neste
    registrador.

- Na própria memória RAM, sendo gerenciada pelo **MMU** usando dois
  registradores

  - O **registrador base da tabela de páginas** (PTBR - page table
    base register), indica o endereço físico de memória onde a
    tabela está alocada.

  - O **registrador limite da tabela de páginas** (PTLR - page table
    limit register), indica o número de entradas da tabela (número
    de páginas).

  - O problema é que é necessário dois acessos a memória sempre que
    for necessário referenciar algum dado da página, um para acessar
    a tabela, outro para acessar a devida posição.

- Em uma memória **cache na MMU**

  - Também conhecido por **TLB (Translation Lookaside Buffer)** ou
    buffer de tradução dinâmica.

  - É um hardware especial para mapear endereços virtuais para
    endereços reais sem ter que passar pela tabela de páginas na
    memória principal (possui o melhor desempenho).

  - O **TLB** armazena um conjunto recente de mapeamentos da tabela
    de páginas.

  - Cada entrada na TLB contém o número da página virtual e o número
    do page frame.

  - O programa solicita um endereço virtual, a **MMU** consulta a
    **TLB**, se encontrar **(TLB Hit)** o endereço físico é
    retornado, caso contrário **(TLB Miss)** um acesso a tabela de
    páginas na memória principal ocorre e um novo mapeamento é
    copiado para a **TLB**, após isso a **MMU** acessa o endereço
    físico correspondente.

  - Para manter a correspondência correta do mapeamento, o SO pode
    executar **TLB Flush**, limpando todo o cache, isso ocorre
    durante uma troca de páginas ou context switch, em alguns
    processadores, apenas a entrada que não é mais válida é apagada.
    Também pode-se manter um PID do processo para cada entrada na
    TLB, evitando que um processador acesse o espaço de memória de
    outro.

### Qual a estrutura de uma entrada na tabela de páginas?

Uma tabela de páginas 32 bits é formada por 6 colunas diferentes. Para
computadores 64 bits, a tabela de páginas pode ser dividida em múltiplas
tabelas diferentes:

- **Número do Page Frame**

- **Número da Página Virtual**

- **Bit de Residência (Present Bit):** Indica se a entrada é válida
  para o uso, se a página virtual está na memória principal ou não.

- **Bit de Proteção (Protection Bit):** Indica os tipos de acessos
  permitidos, leitura, escrita e/ou execução, código deve ter o bit de
  execução ativado, enquanto dados devem ter apenas os de escrita e
  leitura.

- **Bit de Modificação (Dirty Bit):** Controla o uso da página, se ela
  foi modificada desde que foi armazenada no disco. Quando uma página
  é carregada no disco, este bit é zerado, se o processo escrever algo
  nessa página, o sistema ativa este bit. Isso indica se a página
  precisa ser copiada de volta ou não para disco, para que as
  modificações não sejam perdidas. Se for necessário remover essa
  página da RAM, o **MMU** deve consultar esse bit antes.

- **Bit de Referência (Referenced Bit):** Controla o uso da página,
  auxilia o SO na escolha da página que deve deixar a memória
  principal. Se o valor é 1, a página foi referenciada (escrita ou
  leitura), se for zero, ela não foi acessada ainda.

- **Bit de Cache (Cache Disable Bit, CD):** Necessário quando os
  dispositivos de **I/O** são mapeados na memória e não em um
  endereçamento específico **I/O.** Ele define se aquela área pode ou
  não ser armazenada no cache do processador (**Ler sobre memory-mapped
  I/O**).

### Quantas páginas reais serão alocadas a um processo?

Há duas estratégias:

- **Alocação fixa ou estática:** Cada processo terá um número máximo
  de page frames, definidos quando o processo é criado. O limite pode
  ser igual para todos os processos.

  - Possui como vantagem a **simplicidade**, porém um número muito
    pequeno de page frames pode causar muita paginação, por
    consequência, muita troca de páginas.

- **Alocação variável ou dinâmica:** O número máximo de page frames
  varia durante sua execução.

  - Processos com elevada taxa de paginação podem ter seu limite de
    páginas reais ampliado, ou diminuído caso haja pouca troca. O
    problema se dá devido à necessidade de **monitoramento
    constante** para a eventual realocação.

### Quando uma página deve ser carregada para a memória?

Há 3 estratégias:

- **Paginação Simples:** Todas as páginas virtuais são carregadas na
  memória durante a criação do processo, o **bit de residência** de
  todas elas está ativo. Causa desperdício de memória.

- **Paginação por Demanda:** Apenas as páginas referenciadas são
  carregadas na memória principal.

- **Paginação Antecipada:** Carrega para a memória, além das páginas
  referenciadas, outras páginas que podem ou não ser necessárias para
  o processo. O SO, além das principais, carrega páginas próximas ou
  relacionadas, a escolha é baseada em dois princípios, **localidade
  temporal:** páginas acessadas recentemente tendem a ser acessadas de
  novo. E **localidade espacial:** se uma página foi acessada, é
  provável que páginas próximas também.

### Como trazer uma página para a memória?

Acessa a tabela de páginas, verifica se ela está no disco ou na memória.

Se estiver **em disco**, dá **page fault**, o processo é suspenso e
inserido em uma fila especial **([Page Fault Queue](https://zeux.io/2014/12/21/page-fault-queue/))**,
após isso, uma página real livre deve ser alocada. Então a tabela de
páginas é atualizada e a página que estava em disco é carregada na
memória (o processo de carregamento é realizado por um processo
denominado **Pager**), após isso, o processo é retirado da fila especial
e a suspensão é finalizada.

Outra possibilidade é se a página que o processo está tentando acessar,
está fora do seu espaço de endereçamento virtual, isso gera um erro de
segmentação (**Segmentation Fault**), o processo pode ser finalizado
pelo SO ou tratado pelo próprio processo.

### Como liberar espaço na memória?

Há algoritmos específicos para esse swap in/out da memória, so irei citar o **LRU por Aging** e **Clock**

- **NFU Not Frequently Used** -> Esse algortimo parte do princípio de cada página possuir um contador iniciado em zero e incrementado de 1 em 1 a cada referência à página, a página com o menor contador é trocada quando necessário, o problema disso é uma página nova que está sendo referenciada com frequência poderá ser trocada ao invés de uma página antiga que já não é mais referenciada. Para resolver esse problema é utilizado a variação **LFU** (será falado apenas da implementação por software (**Aging**):

  - Um contador de mais ou menos 8 bits é associada a cada página
  - A cada [**clock tick**](https://superuser.com/a/101202) os contadores de todas as páginas sofrem um shift para a direita (deslocados)
  - Se uma página foi acessada entre um **tick** e outro, o bit mais significativo do contador é setado para 1
  - Ao substituir uma página, é escolhido o que tem o menor valor do contador, o menos recentemente usado.

- **Clock** -> Os **page frames** são listados em uma lista circular, cada page frame possui um **bit R de referência**, que indica se a página foi usada recentemente.
  - Sempre que uma página é acessada por um processo, o hardware ou SO **setam o bit R para 1**
  - Por estar organizado em uma lista circular, um ponteiro percorre todas as páginas multiplas vezes, sempre que é necessário susbtituir uma página, o ponteiro retira a primeira página com bit R = 0 da memória, e põem a nova página.
  - Enquanto ele passa, toda página que possui bit R = 1, possui esse bit zerado, até ser modificado na próxima referência.

## Políticas de Substituição de Páginas

Sempre que ocorre uma page fault e a memória está cheia, é necessário realizar uma troca de página, utilizando um dos algoritmos acima, ou diversos outros. Há dois tipos de políticas de substituição de Páginas

- **Política Local** -> Um processo so pode substituir suas próprias páginas, o sistema aloca um número fixo de frames (páginas reais) para **cada processo** e quando há a necessidade de troca, o processo ira escolher apenas **entre seus frames**.
  - Vantagens: **isolamento**, um processo não afeta diretamente outros - **Previsibilidade**, fácil de controlar o uso de memória por processo
  - Desvantagens: difícil definir quantos frames cada processo ira necessitar para funcionar eficientemente.
- **Política Global** -> Qualquer processo (a nível usuário) pode ter os seus frames substituídos por de outro processo,
  - Vantagens: processos com precisam de mais memória podem usar mais quadros, menos trocas = menos latência
  - Desvantagens: processos com menor prioridade podem ser prejudicados, ficado com poucos frames e gerando muitas faltas de páginas - um processo ineficiente pode prejudicar diversos outros.

Na política global, é comum que o SO imponha travas que impeçam processos de retirarem frames dele, como por exemplo:

- **Páginas fixas**: páginas críticas do SO são fixadas na memória e não podem ser substituídas
- **Listas separadas por nível**: o SO mantém listas de páginas separadas para o **nível de usuário e kernel**, dessa forma pode-se restringir a substituição apenas entre as páginas de usuário.

Uma implementação atual comum é o hibrído entre essas duas, **substituição global com limites de frames por processo**.

**Algoritmos de substituição local (exclusivamente locais)**:
• Working Set;
• WSClock;

**Algoritmos de substituição local/global**:
• Ótimo;
• NRU;
• FIFO;
• Segunda Chance;
• LRU;
• Relógio

## Para onde a página vai ao ser descartada?

Uma página é removida da RAM quando:

- Há uma falta de página (page fault) — ou seja, o processo tenta acessar uma página que não está na RAM;

- A memória RAM está cheia e não há quadros (frames) livres;

- O sistema precisa escolher uma página para remover usando uma política de substituição (como LRU, FIFO, etc).

Ao ser substituida ele sera direcionada a área de troca (swap area) localizada em disco. Ela é gerenciada como uma lista de espaços disponíveis. O endereço na área de troca de cada processo é mantida na tabela de processos (o MMU lida com isso).

Há duas possibilidades de se lidar com isso:

1. Assim que o processo é criado, ele é copiado todo para sua área de troca no disco, sendo carregado para a memória quando necessário (**paginação simples em disco**). Neste caso, é criada uma sub areas de troca diferentes para dados, pilha e programa.

2. Nada é alocado antecipadamente, espaço é alocado em disco quando a página for enviada para lá. Assim, o processo na memória RAM não fica “amarrado” a uma área específica (**páginação por demanda em disco**;

## Tabela de páginas invertida e Multinível

Geralmente, cada processo possui uma tabela de páginas associada a ele, porém em arquiteturas modernas isso pode consumir uma grande quantidade de memórias, há duas alternativas para isso, **tabela de páginas invertida e tabela de páginas multinível**:

### Tabela de páginas invertida

O SO mantém uma **única tabela** para os page frames da memória (páginas reais). Cada entrada consiste do endereço virtual da página armazenada naquela página real, com informações sobre o processo dono da página virtual.

Quando uma referência de memória é realizada (página virtual), a tabela de páginas invertida é pesquisada para encontrar a moldura de página correspondente.

Enquanto a tabela de páginas comum é classificada por endereços virtuais, a invertida é **classificada com base em endereços físicos**.

- Vantagens:

  - Ocupa menos espaço;
  - É mais fácil de gerenciar apenas uma tabela;

- Desvantagens:
  - Aumenta o tempo de pesquisa na tabela, pois apesar de ser **classificada por endereços físicos, é pesquisada por endereços lógicos**. Pode ser resolvido implementando **TLB**

**TLB**, significa "Translation Lookaside Buffer" (Buffer de Tradução Lookaside). É um cache de hardware que armazena traduções recentes de endereços virtuais para endereços físicos, otimizando o acesso à memória.

### Tabela de páginas Multinível

Utilizado principalmente em arquiteturas 64 bits

Em vez de ter uma única tabela gigante, o sistema usa uma árvore de tabelas. A ideia central é dividir o endereço virtual em vários pedaços, cada um sendo um índice em um nível da tabela

Para mais informações, leia: https://www.baeldung.com/cs/multi-level-page-tables

## Segmentação

Diferente da paginação, onde a memória é dividida em blocos de tamanho fixo (páginas), na segmentação a memória é dividida em partes de tamanhos variáveis, de acordo com a estrutura lógica do programa.

Dessa forma, código, dados, heap e pilha são dividas separadamente podendo crescer individualmente.

Na segmentação é implementada uma tabela de segmentos por processo, mapeia endereço lógico para fisico. Cada entrada nessa tabela é composta de dois campos

- **Endereço base**: Contém o endereço fisico inicial da onde o endereço está na memórias
- **Limite**: tamanho do segmento

Um endereço virtual na segmentação é composto por dois campos:

- **Número do segmento**: usado para buscar uma entrada na **tabela de segmentos do processo**
- **Offset**: a posição dentro de um bloco de memória (página ou segmento)

A tradução funciona da seguinte forma:

1. Recebe um endereço Virtual
2. Consulta a **tabela de segmentos**
3. Verifica se o offset é menor que o limites
4. Calcula o endereço físico `base + offset`

**Possui fragmentação externa**.

## Segmentação paginada

Resolve o problema de fragmentação externa da segmentação, enquanto separa o processo em partes que crescem individualmente.

O endereço virtual é constituido de:

[ Nº do Segmento ][ Nº da Página ][ Offset ]

**Número do segmento** -> usado para cessar a tabela de segmentos do processo, cada entrada contém a tabela de páginas daquele segmentos
**Número da página** -> usaddo para acessar a entrada da tabela de páginas desse segmento, cada entrada contém o quadro físico da memória.
**Offset** -> indica a posição dentro do **page frame**.


# Sumário

### 1. Gerenciamento por Partições

*   **Partições Fixas:** A memória é dividida em blocos de tamanho predefinido.
    *   **Problemas:** Causa **fragmentação interna** (espaço desperdiçado dentro de uma partição) e **fragmentação externa** (espaços livres não contíguos que impedem a alocação de um processo).
    *   **Alocação:** Pode usar uma **fila única** para todos os processos ou **filas múltiplas** (uma por partição).

*   **Partições Variáveis:** As partições são criadas dinamicamente com o tamanho exato do processo, eliminando a fragmentação interna.
    *   **Problema:** Sofre de **fragmentação externa**. A solução é a **compactação** (juntar os espaços livres), mas é um processo custoso para a CPU.
    *   **Gerenciamento:** Utiliza **Bitmap** (um mapa de bits que indica se cada bloco está livre ou ocupado) ou **Lista Encadeada**.

### 2. Algoritmos de Alocação de Memória

São estratégias para decidir em qual espaço livre um processo será alocado:
*   **First Fit:** Aloca no primeiro espaço disponível que seja grande o suficiente.
*   **Best Fit:** Aloca no menor espaço que comporte o processo, minimizando a sobra.
*   **Worst Fit:** Aloca no maior espaço disponível, deixando a maior sobra possível.

### 3. Memória Virtual: Paginação e Segmentação

A **memória virtual** é uma técnica que permite executar processos maiores que a memória RAM, traduzindo endereços virtuais (do processo) para endereços físicos (na RAM).

*   **Paginação:**
    *   **Conceito:** A memória virtual e a física são divididas em blocos de tamanho fixo chamados **páginas** e **page frames**.
    *   **Funcionamento:** Uma **Tabela de Páginas** mapeia as páginas virtuais de um processo para os frames na memória física. Para acelerar esse processo, usa-se um cache de hardware chamado **TLB (Translation Lookaside Buffer)**.
    *   **Page Fault:** Ocorre quando um processo tenta acessar uma página que não está na RAM, fazendo com que o SO a carregue do disco.
    *   **Problema:** Fragmentação interna.
    *   **Substituição:** Quando a memória está cheia, algoritmos como **Clock** ou **LRU** decidem qual página remover. A política pode ser **local** (troca páginas do próprio processo) ou **global** (troca páginas de qualquer processo de usuário).
    *   **Otimizações:** Para sistemas modernos, usam-se **Tabelas de Páginas Multinível** (para arquiteturas 64-bit) ou **Invertidas** (uma tabela única para todo o sistema, economizando espaço).

*   **Segmentação:**
    *   **Conceito:** A memória é dividida em blocos de tamanho variável e com significado lógico (ex: código, dados, pilha).
    *   **Funcionamento:** Uma **Tabela de Segmentos** armazena o endereço base e o tamanho de cada segmento.
    *   **Problema:** Causa **fragmentação externa**.

*   **Segmentação Paginada:**
    *   **Conceito:** Um modelo híbrido onde os segmentos lógicos são, por sua vez, divididos em páginas.
    *   **Vantagem:** Combina a organização lógica da segmentação com a eficiência da paginação, eliminando a fragmentação externa.
