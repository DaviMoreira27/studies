**Interleaving -** Técnica para reduzir tempo de acesso. Entre um setor
**A** e um setor **J** há **N** setores, logicamente eu terei **N**
setores entre esses dois que não necessariamente são sequenciais, **A,
C, Y, I, J, B, I, L** entrelaçamento 3. 3 setores entre A e J, K e K + 1

  -----------------------------------------------------------------------
  Trilha      12          85          40          100         75
  ----------- ----------- ----------- ----------- ----------- -----------
  Tempo       60          80          110         120         175
  Chegada                                                     

  -----------------------------------------------------------------------

Exercicio\
\
FIFO: 12, 85, 40, 100, 75

SSF: 12, 40, 85, 100, 75

ELEVATOR: 12, 40, 85, 100, 75
