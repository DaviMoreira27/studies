## Visão de Camadas do Tanenbaum

[Software de E/S do Usuário ] ^ Serviços
[ Software de E/S ] |
[ Drivers ] |
[Manipuladores de Interrupção] |
[ Hardware ] |

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
