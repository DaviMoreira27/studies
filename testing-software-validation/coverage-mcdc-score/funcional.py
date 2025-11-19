import unittest

from placar import Placar

class TestPlacar(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    def test_str_format(self):
        texto = str(self.placar)

        self.assertIsInstance(texto, str)

        self.assertEqual(texto.count("-------|----------|-------"), 3)
        self.assertIn("       +----------+", texto)

        for pos in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]:
            self.assertIn(self.placar.uma_linha(pos), texto)

    def test_uma_linha_valido_2(self):
        entrada = 9
        saida = self.placar.uma_linha(entrada)
        self.assertIsInstance(saida, str)

    def test_add_valido_quina(self):
        self.placar.add(10, [5, 5, 5, 5, 5])
        self.assertEqual(self.placar.placar[9], 40)

    def test_add_repetido(self):
        self.placar.add(1, [1, 2, 3, 4, 5])
        with self.assertRaises(ValueError):
            self.placar.add(1, [1, 1, 1, 1, 1])

    def test_add_raises_indexerror_when_posicao_less_than_1(self):
        with self.assertRaises(IndexError):
            self.placar.add(0, [1, 2, 3, 4, 5])

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

    def test_get_score_taken(self):
        self.placar.add(1, [1, 1, 2, 3, 1])
        self.assertEqual(self.placar.getScore(None), 3)

    def test_get_taken_verdadeiro(self):
        self.placar.add(3, [3, 3, 2, 3, 4])
        self.assertTrue(self.placar.getTaken(2))

    def test_get_taken_falso(self):
        self.assertFalse(self.placar.getTaken(5))

    def test_get_name_valido(self):
        self.assertEqual(self.placar.getName(1), "Twos")

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

    def test_conta_zero_ocorrencias(self):
        resultado = self.placar.conta(3, [1, 2, 4, 5, 6])
        self.assertEqual(resultado, 0)

    def test_conta_todas_ocorrencias(self):
        resultado = self.placar.conta(2, [2, 2, 2, 2, 2])
        self.assertEqual(resultado, 5)

    def test_conta_ocorrencias_intermediarias(self):
        resultado = self.placar.conta(4, [4, 1, 4, 2, 5])
        self.assertEqual(resultado, 2)

    def test_add_mcdc_posicao_maior_que_limite(self):
        with self.assertRaises(IndexError):
            self.placar.add(11, [1,2,3,4,5])

    def test_full_mcdc(self):
        self.assertTrue(self.placar.checkFull([1,1,1,1,1]))

    def test_seq_mcdc_1(self):
        self.assertFalse(self.placar.checkSeqMaior([1,2,3,5,6]))

    def test_seq_mcdc_2(self):
        self.assertFalse(self.placar.checkSeqMaior([1,2,4,5,6]))

    def test_seq_mcdc_3(self):
        self.assertFalse(self.placar.checkSeqMaior([1,3,4,5,6]))

    def test_quadra_mcdc_1(self):
        self.assertFalse(self.placar.checkQuadra([2,2,2,3,4]))

    def test_quadra_mcdc_2(self):
        self.assertTrue(self.placar.checkQuadra([1,1,1,1,2]))

    def test_quina_mcdc_1(self):
        self.assertFalse(self.placar.checkQuina([3,3,3,3,4]))

    def test_quina_mcdc_2(self):
        self.assertFalse(self.placar.checkQuina([4,4,5,4,4]))

    def test_quina_mcdc_3(self):
        self.assertFalse(self.placar.checkQuina([6,5,6,6,6]))

    def test_full_mcdc_1(self):
        self.assertFalse(self.placar.checkFull([2,2,2,3,2]))

    def test_full_mcdc_2(self):
        self.assertFalse(self.placar.checkFull([3,3,3,4,5]))
