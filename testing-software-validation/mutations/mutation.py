import unittest

from placar import Placar


class TestPlacarMutation(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    def test_str_formato_colunas(self):
            p = Placar()
            resultado = str(p)

            esperado_prefixo = "(1)   |   (7)   |  (4)"

            primeira_linha = resultado.split("\n")[0]
            self.assertTrue(
                primeira_linha.startswith(esperado_prefixo),
                f"Linha obtida:\n{primeira_linha}"
            )
