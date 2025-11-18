import unittest

from placar import Placar


class TestStr(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_placar_vazio_todas_posicoes_livres(self):
        resultado = str(self.placar)
        self.assertIsInstance(resultado, str)
        self.assertIn("(1)", resultado)
        self.assertIn("(10)", resultado)

    def test_placar_com_posicoes_ocupadas(self):
        self.placar.add(1, [1, 1, 1, 1, 1])
        self.placar.add(7, [2, 2, 3, 3, 3])
        resultado = str(self.placar)
        self.assertIsInstance(resultado, str)


class TestUmaLinha(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_posicao_0_vazia(self):
        resultado = self.placar.uma_linha(0)
        self.assertEqual(resultado, "(1) ")

    def test_posicao_1_vazia(self):
        resultado = self.placar.uma_linha(1)
        self.assertEqual(resultado, "(2) ")

    def test_posicao_2_vazia(self):
        resultado = self.placar.uma_linha(2)
        self.assertEqual(resultado, "(3) ")

    def test_posicao_3_vazia(self):
        resultado = self.placar.uma_linha(3)
        self.assertEqual(resultado, "(4) ")

    def test_posicao_4_vazia(self):
        resultado = self.placar.uma_linha(4)
        self.assertEqual(resultado, "(5) ")

    def test_posicao_5_vazia(self):
        resultado = self.placar.uma_linha(5)
        self.assertEqual(resultado, "(6) ")

    def test_posicao_6_vazia(self):
        resultado = self.placar.uma_linha(6)
        self.assertEqual(resultado, "(7) ")

    def test_posicao_7_vazia(self):
        resultado = self.placar.uma_linha(7)
        self.assertEqual(resultado, "(8) ")

    def test_posicao_8_vazia(self):
        resultado = self.placar.uma_linha(8)
        self.assertEqual(resultado, "(9) ")

    def test_posicao_9_vazia(self):
        resultado = self.placar.uma_linha(9)
        self.assertEqual(resultado, "(10)")

    def test_posicao_0_ocupada(self):
        self.placar.placar[0] = 5
        self.placar.taken[0] = True
        resultado = self.placar.uma_linha(0)
        self.assertEqual(resultado, " 5  ")

    def test_posicao_1_ocupada(self):
        self.placar.placar[1] = 8
        self.placar.taken[1] = True
        resultado = self.placar.uma_linha(1)
        self.assertEqual(resultado, " 8  ")

    def test_posicao_2_ocupada(self):
        self.placar.placar[2] = 12
        self.placar.taken[2] = True
        resultado = self.placar.uma_linha(2)
        self.assertEqual(resultado, " 12 ")

    def test_posicao_3_ocupada(self):
        self.placar.placar[3] = 16
        self.placar.taken[3] = True
        resultado = self.placar.uma_linha(3)
        self.assertEqual(resultado, " 16 ")

    def test_posicao_4_ocupada(self):
        self.placar.placar[4] = 20
        self.placar.taken[4] = True
        resultado = self.placar.uma_linha(4)
        self.assertEqual(resultado, " 20 ")

    def test_posicao_5_ocupada(self):
        self.placar.placar[5] = 24
        self.placar.taken[5] = True
        resultado = self.placar.uma_linha(5)
        self.assertEqual(resultado, " 24 ")

    def test_posicao_6_ocupada(self):
        self.placar.placar[6] = 15
        self.placar.taken[6] = True
        resultado = self.placar.uma_linha(6)
        self.assertEqual(resultado, " 15 ")

    def test_posicao_7_ocupada(self):
        self.placar.placar[7] = 20
        self.placar.taken[7] = True
        resultado = self.placar.uma_linha(7)
        self.assertEqual(resultado, " 20 ")

    def test_posicao_8_ocupada(self):
        self.placar.placar[8] = 30
        self.placar.taken[8] = True
        resultado = self.placar.uma_linha(8)
        self.assertEqual(resultado, " 30 ")

    def test_posicao_9_ocupada(self):
        self.placar.placar[9] = 40
        self.placar.taken[9] = True
        resultado = self.placar.uma_linha(9)
        self.assertEqual(resultado, " 40 ")


class TestAdd(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_posicao_0_invalida(self):
        with self.assertRaises(IndexError):
            self.placar.add(0, [1, 2, 3, 4, 5])

    def test_posicao_11_invalida(self):
        with self.assertRaises(IndexError):
            self.placar.add(11, [1, 2, 3, 4, 5])

    def test_posicao_ja_ocupada(self):
        self.placar.add(5, [5, 5, 5, 5, 5])
        with self.assertRaises(ValueError):
            self.placar.add(5, [1, 2, 3, 4, 5])

    def test_posicao_1_com_valores_validos(self):
        self.placar.add(1, [1, 1, 2, 3, 4])
        self.assertEqual(self.placar.placar[0], 2)
        self.assertTrue(self.placar.taken[0])

    def test_posicao_2_com_valores_validos(self):
        self.placar.add(2, [2, 2, 2, 3, 4])
        self.assertEqual(self.placar.placar[1], 6)
        self.assertTrue(self.placar.taken[1])

    def test_posicao_3_com_valores_validos(self):
        self.placar.add(3, [3, 3, 1, 2, 5])
        self.assertEqual(self.placar.placar[2], 6)
        self.assertTrue(self.placar.taken[2])

    def test_posicao_4_com_valores_validos(self):
        self.placar.add(4, [4, 4, 4, 1, 2])
        self.assertEqual(self.placar.placar[3], 12)
        self.assertTrue(self.placar.taken[3])

    def test_posicao_5_com_valores_validos(self):
        self.placar.add(5, [5, 5, 1, 2, 3])
        self.assertEqual(self.placar.placar[4], 10)
        self.assertTrue(self.placar.taken[4])

    def test_posicao_6_com_valores_validos(self):
        self.placar.add(6, [6, 6, 6, 1, 2])
        self.assertEqual(self.placar.placar[5], 18)
        self.assertTrue(self.placar.taken[5])

    def test_posicao_7_full_satisfeita(self):
        self.placar.add(7, [2, 2, 3, 3, 3])
        self.assertEqual(self.placar.placar[6], 15)
        self.assertTrue(self.placar.taken[6])

    def test_posicao_7_full_nao_satisfeita(self):
        self.placar.add(7, [1, 2, 3, 4, 5])
        self.assertEqual(self.placar.placar[6], 0)
        self.assertTrue(self.placar.taken[6])

    def test_posicao_8_sequencia_satisfeita(self):
        self.placar.add(8, [2, 3, 4, 5, 6])
        self.assertEqual(self.placar.placar[7], 20)
        self.assertTrue(self.placar.taken[7])

    def test_posicao_8_sequencia_nao_satisfeita(self):
        self.placar.add(8, [1, 2, 2, 3, 4])
        self.assertEqual(self.placar.placar[7], 0)
        self.assertTrue(self.placar.taken[7])

    def test_posicao_9_quadra_satisfeita(self):
        self.placar.add(9, [4, 4, 4, 4, 2])
        self.assertEqual(self.placar.placar[8], 30)
        self.assertTrue(self.placar.taken[8])

    def test_posicao_9_quadra_nao_satisfeita(self):
        self.placar.add(9, [3, 3, 3, 2, 2])
        self.assertEqual(self.placar.placar[8], 0)
        self.assertTrue(self.placar.taken[8])

    def test_posicao_10_quina_satisfeita(self):
        self.placar.add(10, [5, 5, 5, 5, 5])
        self.assertEqual(self.placar.placar[9], 40)
        self.assertTrue(self.placar.taken[9])

    def test_posicao_10_quina_nao_satisfeita(self):
        self.placar.add(10, [5, 5, 5, 5, 4])
        self.assertEqual(self.placar.placar[9], 0)
        self.assertTrue(self.placar.taken[9])


class TestGetScore(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_k_none_sem_parametro_todas_ocupadas(self):
        self.placar.placar = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
        self.placar.taken = [True] * 10
        self.assertEqual(self.placar.getScore(), 550)

    def test_k_none_sem_parametro_algumas_ocupadas(self):
        self.placar.placar = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
        self.placar.taken = [
            True,
            False,
            True,
            False,
            True,
            False,
            True,
            False,
            True,
            False,
        ]
        self.assertEqual(self.placar.getScore(), 250)

    def test_k_none_sem_parametro_nenhuma_ocupada(self):
        self.placar.placar = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
        self.placar.taken = [False] * 10
        self.assertEqual(self.placar.getScore(), 0)

    def test_k_valido_posicao_0(self):
        self.placar.placar[0] = 5
        self.assertEqual(self.placar.getScore(0), 5)

    def test_k_valido_posicao_5(self):
        self.placar.placar[5] = 25
        self.assertEqual(self.placar.getScore(5), 25)

    def test_k_valido_posicao_9(self):
        self.placar.placar[9] = 40
        self.assertEqual(self.placar.getScore(9), 40)


class TestGetTaken(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_posicao_ocupada_retorna_true(self):
        self.placar.taken[0] = True
        self.assertTrue(self.placar.getTaken(0))

    def test_posicao_livre_retorna_false(self):
        self.placar.taken[9] = False
        self.assertFalse(self.placar.getTaken(9))


class TestGetName(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_k_0_retorna_ones(self):
        self.assertEqual(self.placar.getName(0), "Ones")

    def test_k_1_retorna_twos(self):
        self.assertEqual(self.placar.getName(1), "Twos")

    def test_k_2_retorna_threes(self):
        self.assertEqual(self.placar.getName(2), "Threes")

    def test_k_3_retorna_fours(self):
        self.assertEqual(self.placar.getName(3), "Fours")

    def test_k_4_retorna_fives(self):
        self.assertEqual(self.placar.getName(4), "Fives")

    def test_k_5_retorna_sixes(self):
        self.assertEqual(self.placar.getName(5), "Sixes")

    def test_k_6_retorna_full(self):
        self.assertEqual(self.placar.getName(6), "Full")

    def test_k_7_retorna_sequence(self):
        self.assertEqual(self.placar.getName(7), "Sequence")

    def test_k_8_retorna_four_of_a_kind(self):
        self.assertEqual(self.placar.getName(8), "Four of a kind")

    def test_k_9_retorna_general(self):
        self.assertEqual(self.placar.getName(9), "General")


class TestConta(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_n_1_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(1, [2, 3, 4, 5, 6]), 0)

    def test_n_2_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(2, [1, 3, 4, 5, 6]), 0)

    def test_n_3_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(3, [1, 2, 4, 5, 6]), 0)

    def test_n_4_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(4, [1, 2, 3, 5, 6]), 0)

    def test_n_5_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(5, [1, 2, 3, 4, 6]), 0)

    def test_n_6_com_0_ocorrencias(self):
        self.assertEqual(self.placar.conta(6, [1, 2, 3, 4, 5]), 0)

    def test_n_1_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(1, [1, 1, 1, 1, 1]), 5)

    def test_n_2_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(2, [2, 2, 2, 2, 2]), 5)

    def test_n_3_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(3, [3, 3, 3, 3, 3]), 5)

    def test_n_4_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(4, [4, 4, 4, 4, 4]), 5)

    def test_n_5_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(5, [5, 5, 5, 5, 5]), 5)

    def test_n_6_com_5_ocorrencias(self):
        self.assertEqual(self.placar.conta(6, [6, 6, 6, 6, 6]), 5)


class TestCheckFull(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_full_true_2_5_2_5_5(self):
        self.assertTrue(self.placar.checkFull([2, 5, 2, 5, 5]))

    def test_full_true_2_2_2_2_2(self):
        self.assertTrue(self.placar.checkFull([2, 2, 2, 2, 2]))

    def test_full_false_2_2_2_2_5(self):
        self.assertFalse(self.placar.checkFull([2, 2, 2, 2, 5]))

    def test_full_false_1_2_3_4_5(self):
        self.assertFalse(self.placar.checkFull([1, 2, 3, 4, 5]))


class TestCheckSeqMaior(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_seq_true_1_2_3_4_5(self):
        self.assertTrue(self.placar.checkSeqMaior([1, 2, 3, 4, 5]))

    def test_seq_true_2_6_4_5_3(self):
        self.assertTrue(self.placar.checkSeqMaior([2, 6, 4, 5, 3]))

    def test_seq_false_1_2_2_4_5(self):
        self.assertFalse(self.placar.checkSeqMaior([1, 2, 2, 4, 5]))


class TestCheckQuadra(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_quadra_true_6_6_6_6_1(self):
        self.assertTrue(self.placar.checkQuadra([6, 6, 6, 6, 1]))

    def test_quadra_true_1_1_1_1_1(self):
        self.assertTrue(self.placar.checkQuadra([1, 1, 1, 1, 1]))

    def test_quadra_false_3_3_3_2_2(self):
        self.assertFalse(self.placar.checkQuadra([3, 3, 3, 2, 2]))


class TestCheckQuina(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()

    def test_quina_true_4_4_4_4_4(self):
        self.assertTrue(self.placar.checkQuina([4, 4, 4, 4, 4]))

    def test_quina_false_4_4_4_4_2(self):
        self.assertFalse(self.placar.checkQuina([4, 4, 4, 4, 2]))
