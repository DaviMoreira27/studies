from . import Dados
from . import RolaDados
from . import Placar

NRODADAS = 10

seed = int(input("Digite a semente (zero para aleatório): "))

rd = RolaDados(5, seed)
pl = Placar()
print(pl)
for rodada in range(NRODADAS):
    print("****** Rodada " + str(rodada + 1))
    print("Pressione ENTER para lançar os dados")
    input()
    # primeira tentativa
    rd.rolar()
    print("1          2          3          4          5")
    print(rd)
    # segunda tentativa
    print("Digite os números dos dados que quiser TROCAR. Separados por espaços.")
    muda = input()
    rd.rolar(muda)
    print("1          2          3          4          5")
    print(rd)
    # terceira tentativa
    print("Digite os números dos dados que quiser TROCAR. Separados por espaços.")
    muda = input()
    values = rd.rolar(muda)
    print("1          2          3          4          5")
    print(rd)
    print("\n\n")
    print(pl)
    pos = 0
    while pos <= 0:
        try:
            pos = int(input("Escolha a posição que quer ocupar com essa jogada ===> "))
            if pos > NRODADAS or pos <= 0:
                pos = 0
            pl.add(pos, values)
        except (IndexError, ValueError):
            pos = 0
        if pos == 0:
            print("Valor inválido. Posição ocupada ou inexistente.")

    print("\n\n")
    print(pl)

print("***********************************")
print("***")
print("*** Seu escore final foi: " + str(pl.getScore()))
print("***")
print("***********************************")
