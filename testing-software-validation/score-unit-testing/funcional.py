import unittest

from placar import Placar


class TestPlacar(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    def test_uma_linha_valido(self):
        entrada = 5
        saida = self.placar.uma_linha(entrada)
        self.assertIsInstance(saida, str)

    def test_add_invalido_dados(self):
        posicao = 5
        dados = {1, 2, 5, 6, 7}
        with self.assertRaises(AssertionError):
            self.placar.add(posicao, dados)

    def test_add_invalido_posicao(self):
        posicao = 21
        dados = {1, 2, 5, 6, 2}
        with self.assertRaises(AssertionError):
            self.placar.add(posicao, dados)

    def test_get_score_total(self):
        saida = self.placar.getScore()
        self.assertEqual(saida, 0)

    def test_add_valido_quina(self):
        self.placar.add(10, [5, 5, 5, 5, 5])
        self.assertEqual(self.placar.placar[9], 40)

    def test_add_repetido(self):
        self.placar.add(2, [2, 2, 2, 3, 4])
        with self.assertRaises(ValueError):
            self.placar.add(2, [2, 2, 2, 3, 4])

    def test_add_valido_full(self):
        self.placar.add(7, [2, 2, 3, 3, 3])
        self.assertEqual(self.placar.placar[6], 15)

    def test_add_valido_seq_maior(self):
        self.placar.add(8, [2, 3, 4, 5, 6])
        self.assertEqual(self.placar.placar[7], 20)

    def test_add_valido_quadra(self):
        self.placar.add(9, [4, 4, 4, 4, 2])
        self.assertEqual(self.placar.placar[8], 30)

    def test_get_score_posicao(self):
        self.placar.add(1, [1, 1, 2, 3, 1])
        self.assertEqual(self.placar.getScore(0), 3)

    def test_get_score_invalido(self):
        with self.assertRaises(AssertionError):
            _ = self.placar.getScore(15)

    def test_get_taken_verdadeiro(self):
        self.placar.add(3, [3, 3, 2, 3, 4])
        self.assertTrue(self.placar.getTaken(2))

    def test_get_taken_falso(self):
        self.assertFalse(self.placar.getTaken(5))

    def test_get_name_valido(self):
        self.assertEqual(self.placar.getName(1), "Twos")

    def test_get_name_invalido(self):
        with self.assertRaises(AssertionError):
            _ = self.placar.getName(11)

    def test_conta_valido(self):
        with self.assertRaises(AssertionError):
            self.placar.conta(2, [1, 2, 2, 3])

    def test_check_full_true(self):
        self.assertTrue(self.placar.checkFull([3, 3, 3, 2, 2]))

    def test_check_full_false(self):
        self.assertFalse(self.placar.checkFull([1, 2, 3, 4, 5]))

    def test_check_seq_maior_true(self):
        self.assertTrue(self.placar.checkSeqMaior([2, 3, 4, 5, 6]))

    def test_check_seq_maior_false(self):
        self.assertFalse(self.placar.checkSeqMaior([1, 2, 3, 4, 6]))

    def test_check_quadra_true(self):
        self.assertTrue(self.placar.checkQuadra([4, 4, 4, 4, 2]))

    def test_check_quadra_false(self):
        self.assertFalse(self.placar.checkQuadra([2, 2, 2, 1, 1]))

    def test_check_quina_true(self):
        self.assertTrue(self.placar.checkQuina([6, 6, 6, 6, 6]))

    def test_check_quina_false(self):
        self.assertFalse(self.placar.checkQuina([6, 6, 6, 5, 6]))
