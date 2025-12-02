import unittest

from placar import Placar


class TestPlacarMcdc(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    def test_add_mcdc_posicao_maior_que_limite(self):
        with self.assertRaises(IndexError):
            self.placar.add(11, [1, 2, 3, 4, 5])

    def test_full_mcdc(self):
        self.assertTrue(self.placar.checkFull([1, 1, 1, 1, 1]))

    def test_seq_mcdc_1(self):
        self.assertFalse(self.placar.checkSeqMaior([1, 2, 3, 5, 6]))

    def test_seq_mcdc_2(self):
        self.assertFalse(self.placar.checkSeqMaior([1, 2, 4, 5, 6]))

    def test_seq_mcdc_3(self):
        self.assertFalse(self.placar.checkSeqMaior([1, 3, 4, 5, 6]))

    def test_quadra_mcdc_1(self):
        self.assertFalse(self.placar.checkQuadra([2, 2, 2, 3, 4]))

    def test_quadra_mcdc_2(self):
        self.assertTrue(self.placar.checkQuadra([1, 1, 1, 1, 2]))

    def test_quina_mcdc_1(self):
        self.assertFalse(self.placar.checkQuina([3, 3, 3, 3, 4]))

    def test_quina_mcdc_2(self):
        self.assertFalse(self.placar.checkQuina([4, 4, 5, 4, 4]))

    def test_quina_mcdc_3(self):
        self.assertFalse(self.placar.checkQuina([6, 5, 6, 6, 6]))

    def test_full_mcdc_1(self):
        self.assertFalse(self.placar.checkFull([2, 2, 2, 3, 2]))

    def test_full_mcdc_2(self):
        self.assertFalse(self.placar.checkFull([3, 3, 3, 4, 5]))
