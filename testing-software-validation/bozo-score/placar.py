class Placar:
    def __init__(self):
        self.POSICOES = 10
        self.placar = self.POSICOES * [0]
        self.taken = self.POSICOES * [False]
        self.nomes = [
            "Ones",
            "Twos",
            "Threes",
            "Fours",
            "Fives",
            "Sixes",
            "Full",
            "Sequence",
            "Four of a kind",
            "General",
        ]

    def __str__(self):
        s = ""
        for i in range(3):
            s += self.uma_linha(i) + "   |   "
            s += self.uma_linha(i + 6) + "   |  "
            s += self.uma_linha(i + 3) + "\n-------|----------|-------\n"
        s += "       |   " + self.uma_linha(9) + "   |"
        s += "\n       +----------+\n"
        return s

    # Metodo: uma_linha
    # Descrição: Retorna uma linha do placar, a linha 9 recebe uma formatação especial
    # Domínio de entrada: Posição válida no placar (inteiros positivos)
    # Domínio de saída: String representando a linha do placar
    # Classes Inválidas: i > 10, i < 0
    # Classes Válidas: Qualquer valor de i entre 0 e 9 que seja inteiro.
    def uma_linha(self, i):
        if i == 9:
            num = (
                "{:^4d}".format(self.placar[i])
                if self.taken[i]
                else "({:2d})".format(i + 1)
            )
        else:
            num = (
                "{:^4d}".format(self.placar[i])
                if self.taken[i]
                else "({:1d}) ".format(i + 1)
            )
        return num

    # Método: add
    # Descrição: Adiciona a pontuação dos dados obtida dos dados rolados em uma posição no placar,
    # Domínio de entrada: Posição válida no placar (inteiros positivos, maiores ou iguais a 1 e menores que 10) e dados válidos (uma lista de inteiros positivos
    # de tamanho igual a 5)
    # Domínio de saída: IndexError para posições no placar menores que 1 e maiores que 10, valueError para caso uma posição já selecionada seja passada
    # IndexError para dados com um tamanho menor que 5
    # Classes Inválidas: i > taken.length ou i < 0 casos de teste: { [1000, 6], [-5, 2], ([6, 3], [6, 2], [2, 10])} IMPORTANT: esse caso de teste
    # ([6, 3], [6, 2]) significado que foi selecionado uma posição 6 (que é uma posição válida) mas logo em seguida foi selecionado uma posição 6 novamente (o que é inválido)
    # Classes Válidas: posição de [0, 9] casos de teste: { 2, 5, 9, 3 }
    # lista de dados de tamanho 5, casos de teste: { [2, [5,2,3,4,5]], [6, [5,2,3,4,6]], [1, [4,1,5,4,5]]}
    def add(self, posicao, dados):
        if posicao < 1 or posicao > self.POSICOES:
            raise IndexError("Valor da posição no placar é ilegal")
        if self.taken[posicao - 1]:
            raise ValueError("Posição ocupada no placar")
        k = 0
        if posicao in range(1, 7):
            k = self.conta(posicao, dados) * posicao
        elif posicao == 7:
            k = 15 if self.checkFull(dados) else 0
        elif posicao == 8:
            k = 20 if self.checkSeqMaior(dados) else 0
        elif posicao == 9:
            k = 30 if self.checkQuadra(dados) else 0
        else:
            k = 40 if self.checkQuina(dados) else 0
        self.placar[posicao - 1] = k
        self.taken[posicao - 1] = True

    # Método: getScore
    # Descrição: Retorna a pontuação total do placar ou a pontuação específica de uma posição no placar
    # Domínio de entrada: uma posição k inteira positiva 0 <= k <= 9 ou um k nulo
    # Domínio de saída: Um inteiro positivo >= 0 e <= 297
    # Classes Inválidas: k < 0 ou k > 9 casos de teste: { -1, 100, -2000, -9, -10 }
    # Classes Válidas: [0, 9] casos de teste: { 1, 5, 9, 0 }
    def getScore(self, k=None):
        if k != None:
            return self.placar[k]
        t = 0
        for i in range(self.POSICOES):
            if self.taken[i]:
                t += self.placar[i]
        return t

    # Método: getTaken
    # Descrição: Retorna se uma posição no placar foi selecionada ou não
    # Domínio de entrada: uma posição k inteira positiva 0 <= k <= 9
    # Domínio de saída: Um valor booleano True ou False
    # Classes Inválidas: k < 0 ou k > 9 casos de teste: { -1, 100, -2000, -9, -10 }
    # Classes Válidas: [0, 9] casos de teste: { 1, 5, 9, 0 }
    def getTaken(self, k):
        return self.taken[k]

    # Método: getName
    # Descrição: Retorna o nome de um tipo de pontuação (quadra, quina, full...)
    # Domínio de entrada: uma posição k inteira positiva 0 <= k <= 9
    # Domínio de saída: Uma string com o nome do tipo de pontuação
    # Classes Inválidas: k < 0 ou k > 9 casos de teste: { -1, 100, -2000, -9, -10 }
    # Classes Válidas: [0, 9] casos de teste: { 1, 5, 9, 0 }
    def getName(self, k):
        return self.nomes[k]

    # Método: conta
    # Descrição: Conta quantas vezes um número aparece em um vetor
    # Domínio de entrada: um número n e um vetor de números vet
    # Domínio de saída: Um valor inteiro cont que representa a quantidade de vezes que n aparece em vet
    # Classes Inválidas: n não é um número ou vet não é um vetor de números, casos de teste: { ('a', [1, 2, 3]),
    # (2, [1, 2, 'a']), ('a', [1, 2, 3, 4, 5]), (null, [1, 2, 3, 4, 5, 6]) }
    # Classes Válidas: n é um número e vet é um vetor de números, casos de teste: { (1, [1, 2, 5]), (6, [2, 1, 5]) }
    def conta(self, n, vet):
        cont = 0
        for i in vet:
            if i == n:
                cont += 1
        return cont

    # Método: checkFull
    # Descrição: Verifica se uma combinação de 5 dados constitui um "Full House" (Trinca e Par).
    # Domínio de entrada: Um vetor/lista 'dados' contendo 5 valores inteiros de dados.
    # Domínio de saída: Um valor booleano (True se for Full House, False caso contrário).
    # Classes Inválidas: 'dados' não é um vetor/lista de 5 inteiros, ou contém valores fora do intervalo [1, 6].
    # Casos de teste: { [1, 1,, 4, 1, 2, 2], [5, 5, 3, 6, 6, 6], [1, 2, 4, 5] }
    # Classes Válidas: 'dados' é um vetor/lista de 5 inteiros. Casos de teste: { [1, 1, 1, 2, 2], [5, 5, 6, 6, 6], [1, 2, 3, 4, 5] }
    def checkFull(self, dados):
        v = sorted(dados)
        return (v[0] == v[1] and v[1] == v[2] and v[3] == v[4]) or (
            v[0] == v[1] and v[2] == v[3] and v[3] == v[4]
        )

    # Método: checkSeqMaior
    # Descrição: Verifica se uma combinação de 5 dados forma uma "Sequência Maior" (5 valores consecutivos, por exemplo: 2, 3, 4, 5, 6).
    # Domínio de entrada: Um vetor/lista 'dados' contendo 5 valores inteiros de dados (1 a 6).
    # Domínio de saída: Um valor booleano (True se for Sequência Maior, False caso contrário).
    # Classes Inválidas: 'dados' não é um vetor/lista de 5 inteiros, ou contém valores fora do intervalo [1, 6].
    # Classes Válidas: 'dados' é um vetor/lista de 5 inteiros. Casos de teste: { [2, 3, 4, 5, 6], [1, 3, 2, 5, 4] (Sequência, deve ser True), [1, 2, 3, 4, 6] (Não é sequência, deve ser False) }
    def checkSeqMaior(self, dados):
        v = sorted(dados)
        return (
            v[0] + 1 == v[1]
            and v[1] + 1 == v[2]
            and v[2] + 1 == v[3]
            and v[3] + 1 == v[4]
        )

    # Método: checkQuadra
    # Descrição: Verifica se uma combinação de 5 dados contém uma "Quadra" (4 dados com o mesmo valor).
    # Domínio de entrada: Um vetor/lista 'dados' contendo 5 valores inteiros de dados.
    # Domínio de saída: Um valor booleano (True se houver Quadra, False caso contrário).
    # Classes Inválidas: 'dados' não é um vetor/lista de 5 inteiros, ou contém valores fora do intervalo [1, 6].
    # Classes Válidas: 'dados' é um vetor/lista de 5 inteiros. Casos de teste: { [3, 3, 3, 3, 1], [6, 4, 4, 4, 4], [2, 2, 2, 1, 1] (Não é Quadra, deve ser False) }
    def checkQuadra(slf, dados):
        v = sorted(dados)
        return (v[0] == v[1] and v[1] == v[2] and v[2] == v[3]) or (
            v[1] == v[2] and v[2] == v[3] and v[3] == v[4]
        )

    # Método: checkQuina
    # Descrição: Verifica se uma combinação de 5 dados constitui uma "Quina" (5 dados com o mesmo valor).
    # Domínio de entrada: Um vetor/lista 'v' contendo 5 valores inteiros de dados.
    # Domínio de saída: Um valor booleano (True se for Quina, False caso contrário).
    # Classes Inválidas: 'v' não é um vetor/lista de 5 inteiros, ou contém valores fora do intervalo [1, 6].
    # Classes Válidas: 'v' é um vetor/lista de 5 inteiros. Casos de teste: { [5, 5, 5, 5, 5], [1, 1, 1, 1, 1], [1, 2, 3, 4, 5] (Não é Quina, deve ser False) }
    def checkQuina(self, v):
        return v[0] == v[1] and v[1] == v[2] and v[2] == v[3] and v[3] == v[4]
