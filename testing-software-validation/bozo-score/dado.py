from random import Random


class Dado(object):
    s010 = "|  *  |\n"
    s100 = "|*    |\n"
    s001 = "|    *|\n"
    s000 = "|     |\n"
    s101 = "|*   *|\n"
    s111 = "|* * *|\n"

    def __init__(self, lado=6, seed=0):
        self.rd = Random()
        if seed != 0:
            self.rd.seed(seed)
        self.lados = lado
        self.atual = 0
        self.rolar()

    def __str__(self):
        if self.lados != 6:
            return None
        s = "+-----+\n"
        k = self.getLado()
        if k == 1:
            s += self.s000 + self.s010 + self.s000
        elif k == 2:
            s += self.s100 + self.s000 + self.s001
        elif k == 3:
            s += self.s100 + self.s010 + self.s001
        elif k == 4:
            s += self.s101 + self.s000 + self.s101
        elif k == 5:
            s += self.s101 + self.s010 + self.s101
        else:
            s += self.s111 + self.s000 + self.s111

        s += "+-----+\n"
        return s

    def rolar(self):
        self.atual = self.rd.randint(1, self.lados)
        return self.atual

    def getLado(self):
        return self.atual


def main():
    d = Dado(6)
    for k in range(100):
        d.rolar()
        print("{})\n{}".format(k + 1, d))


if __name__ == "__main__":
    main()
