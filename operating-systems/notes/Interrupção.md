**Interrupção**

Uma interrupção é um sinal gerado por hardware ou software que informa
ao processador que algo precisa de atenção imediata. Isso força o
processador a pausar a execução atual para tratar deste novo evento. A
interrupção pode ser via hardware ou software. Para a execução de uma
interrupção os seguintes passos são seguidos:

1.  O processador está executando um processo qualquer

2.  Uma interrupção é emitida via hardware ou software

3.  O SO salva o contexto do processo atual, e as próximas instruções a
    serem executadas nesse processo

4.  O processador recebe o número identificador da interrupção, checa a
    **IVT (Interrupt Vector Table)** um vetor que possui todas
    interrupções e o ponteiro para a **ISR** responsável por lidar com
    dado tipo. O **IVT** costuma ser armazenado em endereços iniciais
    fixos de memória, em sistemas modernos esse vetor possui um
    registrador próprio.

5.  **A ISR** **(Rotina de Tratamento de Interrupção)** é um componente
    do SO responsável por lidar interrupções. Ela ira identificar qual a
    origem e motivo da interrupção, e executar ações específicas de
    acordo com o motivo.

6.  Ao lidar com a interrupção, a **ISR** manda um sinal de volta ao
    dispositivo e ao SO informando que a interrupção foi tratada

7.  Após isso, o SO volta para onde a execução tinha sido interrompida
