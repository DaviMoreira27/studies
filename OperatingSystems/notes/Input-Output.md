## Visão de Camadas do Tanenbaum

[Software de E/S do Usuário ]  ^ Serviços
[ Software de E/S ]            |
[ Drivers ]                    |
[Manipuladores de Interrupção] |
[ Hardware ]                   |

### Princípios de Hardware

Podem ser dividos em duas categorias

- **Baseados em bloco**: Informação é armazenada em blocos de tamanho fixo, cada um com um endereço próprio

  - Tamanho entre 512 a 32.768 bytes
    - Leitura e escrita
    - Ex: discos rígidos, SSDs

- **Baseados em caracter**: Aceita uma sequência de caracteres, sem se importar com a estrutura de blocos, informação não é endereçavel e não pode ser buscada
  - Ex: impressoras, interfaces de rede (placas de rede); placas de som;

**Clocks** e outros dispositivos não se encaixam em nenhum das categorias.

Dispositivos de I/O possuem dois componentes:

- **Mecânico** -> o dispositivo em si
- **Eletrônico** -> controladores ou adaptadores (placas)

O dispositivo e a controladora se comunicam por meio de uma interfaces

- **Serial** ou **paralela**
- Barramentos: IDE, ISA, SCSI, AGP, USB, PCI

A **interface** define o padrão físico e lógico de conexão entre dispositivos, incluindo pinos, protocolos de sinalização e camadas de comunicação.
  - **Interface PCI Express (PCIe)**: usa pares seriais ponto a ponto, define camadas de enlace (framing, CRC) e de transação (endereçamento, filas de requisição).

O **barramento** são as linhas fisicas ou lógicas (bus) que transportam dados e sinais de controle entre componentes do sistema.

A **controladora** é um circuito ou chip responsável por gerenciar a comunicação e o funcionamento de um dispositivo de I/O. Ela converte comandos do processador em sinais que o dispositivo compreende e vice-versa.
  - **Exemplo real**: o controlador SATA no chipset de uma placa-mãe gerencia discos rígidos e SSDs. Quando o sistema operacional pede para ler um arquivo, a controladora SATA transforma esse pedido em sinais elétricos na interface SATA do disco.
  - Cada controladora possui um conjunto de **registradores de controle**, que são utilizados na comunicação com a CPU
  - Alguns dispositivos possuem **buffers**, que são carregados antes de serem despejados na controladora, que então passa para a CPU.
  - Para a comunicação entre a **CPU** e os **registradores de controle** utiliza-se **Portas, Memory-mapped ou híbrido**
    - **PORTA** -> Cada registrador possui um número de porta de I/O, de 8 ou 16 bits, a CPU utiliza um espaço de endereçamento diferente para a memória e os dispotivos. Utilizado em SOs modernos. Utiliza Assembly
    - **MEMORY-MAPPED** -> Mapeia os registradores em espaços de memória, cada registrador possui um único endereço de memória, eles são colocados no topo da memória e protegidos por endereços restritos aos processos. Utiliza linguagem de alto nível, principalmente usado para dispositivos de vídeo
    - **ESTRATÉGIA HÍBRIDA** -> Registradores são associados a portas, enquantos os buffers são mapeados na memória.

**Como funciona a comunicação da CPU com os dispositivos?**
  - Quando a CPU deseja ler uma palavra, ela coloca o endereço que ela está desejando no barramento de endereço e manda um comando READ no barramento de controle;
  - Essa comunicação pode ser controlada pela própria CPU ou pela DMA;

O sistema operacional utiliza **drivers** para gerenciar os dispositivos de I/O, escrevendo e lendo nos registradores/buffers. Eles agem como interface entre o hardware e o software, são escritos pelo fabricante ou pela comunidade.
  - Comunicação em baixo nível
  - Traduz o estado do dispositivo

### Formas de realizar E/S

**Como esses acessos são controlados e gerenciados (operações de I/O)?**

Há 3 formas de se fazer impressoras

**I/O programada** -> Dados são trocados entre o processador e o módulo de I/O, um programa próprio para esse controle (software). Em Processadores sem interrupção, é necessario aguardar um dispositivo mais lento responder e processar, gera gargalo. Para processadores com interrupção,

**I/O dirigida por interrupção** -> A transferência de dados é iniciada pelo próprio dispositivo de I/O, que sinaliza à CPU por meio de uma interrupção quando está pronto para comunicar. Esse mecanismo permite que a CPU não fique constantemente verificando o estado dos dispositivos, como acontece na I/O por polling.

**DMA (Direct Memory Access)** -> Os dados trocados entre a memória e os dispositivos não precisam passar pela CPU, há um módulo dedicado à DMA, substituindo a CPU nas operações de I/O com DMA. O dispositvo manda um sinal informando ao DMA que realizará uma operação na memória (escrita ou leitura), o DMA controla os barramento de controle, endereço e dados e caso não seja possivel gravar os dados diretamente na memória ainda, o dispositivo carregará tudo em seu cache (buffer) ou no buffer de sua controladora, e quando possível, todo o conteúdo é despejado na memória. Possui varios registradores que podem ser lidos e escritos pela CPU, R de endereço de memória, contador de bytes e de controle.
  - **Registradores de controle** -> Armazenam a **porta** em uso, tipo (R ou W), unidade de transferência (byte ou palavra) e o número de bytes a ser transferido.

### Principíos de Software

- Organizar o software como uma série de camadas facilita a independência dos dispositivos

-> Camadas mais baixas apresentam detalhes de hardware
  - Drivers e manipuladores de interrupção
-> Camadas mais altas apresentam interface para o usuário
  - Aplicações de Usuário
  - Chamadas de Sistemas
  - Software independente de I/O ou Subsistema de Kernel de I/O

#### Camadas

**Drivers**

- Gerenciados pelo kernel do SOs
- Dependentes do dispositivo, são feitos pela comunidade ou pelos próprios fabricantes
- São incorporados ao Kernel em tempo de execução (carregados na memória e executados), apesar disso a certos drivers (sata, framebuffer e usb básico) podem ser embutidos no próprio kernel e carregados assim que o sistema inicializa, não permitindo o seu descarregamento
- Módulos são drivers não embutidos que não foram carregados ao kernel ainda.

**Software Independente de I/O**

- Realiza funções comuns a qualquer tipo de dispositivo (leitura, escrita) atrás de uma interface com um conjunto comum de funções
```c
int fd = open("/dev/sda", O_RDONLY); // /dev/sda pode ser um SATA, NVME ou virtual, porém a
read(fd, buffer, 512); // interface trata de forma independente do hardware
```
- Realiza escalonamento de I/O, decidindo qual operação sera executada primeiro
- Prove identificadores para os dispositivos e vincula os drivers corretos a cada
- Usa buffers intermediarios para compensar a diferença de velocidade entre a CPU/memória e o dispositivo, Exemplo: Ao ler de um pendrive, o sistema armazena os dados temporariamente em um buffer na RAM, evitando que o programa tenha que esperar a leitura byte por byte.
- Faz cache de dados na memória RAM
- Faz relatórios de erros e proteção dos dispositivos contra acessos indevidos
- Permite o envio de comandos específicos denominados **ioctl** usando essa infraestrutura comum de I/O. Exemplo: Recebe chamadas de **SMART** para monitoramento de dispotivos de armazenamento, encaminha para o driver correto e o driver converte isso para um comando real. `sudo smartctl -a /dev/sda`
- Gerencia a alocação, uso e liberação dos dispositivos dedicados, que possuem acesso concorrentes
- Define um tamanho do bloco independente do dispositivo. Exemplo: dois dispositivos de armazenamento, um com blocos de 512 bytes e outro com blocos de 4 kb, essa interface cria salva os dados em um buffer intermediario para lidar com essas disparidades
- Lida com a transferência de dados
  - **Síncrona**: Requer bloqueio até que os dados estejam prontos para a transferência
  - **Assíncrona**: Transferências acionadas por interrupções
- Lida com diferentes tipos de dispositivos
  - **Compartilháveis**: podem ser utilizados por vários usuários ao mesmo tempo (dísco rígido)
  - **Dedicados**: são utilizados por apenas um usuário de cada vez (impressora)

**Software de I/O no nível usuário**

- Bibliotecas de I/O são utilizadas pelos programas de usuários
  - Chamadas ao sistema (Sys calls)

### Dispositivos de I/Os


#### Discos Rígidos

Um disco rígido é formado por 4 componentes principais

- O disco em si, feito de material magnético, possuindo **2 superfícies** (meio obvio isso)
- Cada superfície possui diversas **trilhas**, são aneis magnetizados ao redor do centro do disco
- Cada trilha é formada por **setores** ou **blocos**, como se fossem quadrados dentro dessas faixas magnétizadas. Cada bloco/setor possui de **512 bytes a 32Kib**.
- Um conjunto de trilhas de mesma distância do centro do disco é chamado de **cilindro**,
para facilitar a visualização, pegue uma trilha e todas as trilhas correspondentes logo abaixo deixa, veja que é formado um cilindro
- **Cada superfície** do disco possui uma **cabeça**, elas são as que realizam a leitura do disco em si.

O tamanho do disco é definido pelo **(número de cabeças * número de cilindros * número de setores * o tamanho de cada setor)**

**O número de cílindros é igual ao número de trilhas por superfície**

O tempo de acesso de um disco, ou **seek**, é definido pleo tempo de deslocamento do cabeçote até o cilindro correspondente à trilha a ser acessada.

Uma forma de reduzir o tempo de **seeking** é chamada de **interleaving**, essa técnica evita perda de tempo entre a leitura de setores consecutivos:
  - É aplicado pela controladora do disco, para o SO, os setores continuam organizados de forma lógica (1, 2, 3, 4...)
  - O objetivo é alinhar a velocidade da rotação do disco com a velocidade de leitura da controladora/processador, isso era comum quando o hardware não era rapido o suficiente para acompanhar a rotação do disco, sistemas modernos não utilizam mais.
  - Setores são numerados com um espaço de **N** setores entre eles
  - Por exemplo, em um computador com interleaving de 0 e 12 blocos, os setores são armazenados sequencialmente; 1, 2, 3, 4, 5...Em um computador com interleaving de 3, entre os setores 1 e 2, serão colocados logicamente 3 outros setores, por exemplo; 1, 4, 7, 10, 2,

A estrutura moderna de armazenamento em bloco não requere mais a técnica de **interleaving**, os dados são comumentes armazenados como **clusters**(grupos de setores), e buffer de dados é grande o suficiente para permitir que todos os setores em um bloco sejam lidos sem qualquer tipo de delay entre os setores.

Fatores que influenciam tempo para leitura/escrita no disco:
  - Velocidade de acesso (seek) → tempo para o movimento do braço até o cilindro;
  - Delay de rotação (latência) → tempo para posicionar o setor na cabeça do disco;
  - Tempo da transferência dos dados;

O tempo de acesso é definido por:
  - **Tempo de seek + Tempo de latência + Tempo de transferência**

Um disco pode receber múltiplos endereços para acessar (leitura ou escrita), por isso é necessário um algoritimo de escalonamento.
  - **FCFS (FIFO) First Come First Served** -> O primeiro endereço a vir, sera o primeiro endereço a ser acessado
  - **SSF Shortest Seek First** -> O primeiro endereço a ser acessado, será aquele com o menor tempo de acesso, ou seja, se o cabeço do disco está na posição 20, o primeiro elemento for 50, e o segundo 10, ele ira primeiro no endereço 10 e depois no endereço 50. Pode causar **starvation**.
  - **Elevator ou SCAN** -> São definidas duas direções para o disco, **up ou down**, o disco ele deve atender todas as requisições em uma única direção, até começar a atendar as da outra direção, ou seja, o disco está na posição 30 com o estado de up, em seguida chega na seguinte ordem os endereços; 50, 10, 5, 90, 100, 2. O disco ira atender na seguinte ordem, 50, 90, 100, 10, 5, 2

O driver mantém uma lista encadeada com as requisições para cada cilindro.

O endereçamento de discos antigamente era feito pelo esquema **Cylinder-Head-Sector**, uma seção do arquivo deveria ser encontrada passando esses 3 dados para o SO, atualmente o endereçamento é feito pelo esquema **LBA (Logical Block Addressing)**. Os setores são indexados de maneira sequencial (LBA 0, LBA 1, LBA 2), a tradução é feita pela controladora do disco. O LBA 0 corresponde ao primeiro setor do primeiro cilindro, da primeira superificie do primeiro disco, o LBA 1 corresponde ao segundo setor do primeiro cilindro, da primeira superificie do primeiro disco e assim por diante.

### Tipos de Clocks

#### Clock de Hardware

- Localizado na CPU ou na placa-mãe
- Gera pulsos síncronos
- Sinal utilizado para a execução de instruções
- A frequência de clock é definida por quantas vezes esse sinal se repete por segundo (Hz)
- Gera interrupções em intervalos definidos conhecidos como **clock ticks**

#### Clock de Software

- A cada X clock ticks, o clock do software executa alguma tarefa (atualizar a hora, interromper processos monitoração, etc)
- A data e hora de um sistema pode ser encontrada de quatro formas:
  - O número de clock ticks entre o momento atual e a data default do UNIX (01/01/1970 24:00:00) e Windows (01/01/1980)
  - Checa a **CMOS (placa-mãe)**, uso de baterias para não perder as informações
  - Pergunta ao usuário
  - Checa um servidor remoto
