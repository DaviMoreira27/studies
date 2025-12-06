import unittest

from placar import Placar


class TestPlacarMutation(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    def test_placar_inicia_com_zero(self):
        esperado = [0] * 10
        self.assertEqual(self.placar.placar, esperado)

    def test_taken_inicia_com_false(self):
        esperado = [False] * 10
        self.assertEqual(self.placar.taken, esperado)

    def test_nomes_tamanho(self):
        esperado = 10
        self.assertEqual(len(self.placar.nomes), esperado)

    def test_nomes_conteudo_e_ordem(self):
            p = Placar()
            esperado = [
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

            self.assertEqual(p.nomes, esperado)

    def test_str_formato_colunas(self):
            p = Placar()
            resultado = str(p)

            esperado_prefixo = "(1)   |   (7)   |  (4)"

            primeira_linha = resultado.split("\n")[0]
            self.assertTrue(
                primeira_linha.startswith(esperado_prefixo),
                f"Linha obtida:\n{primeira_linha}"
            )
