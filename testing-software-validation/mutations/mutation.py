import unittest

from placar import Placar


class TestPlacarMutation(unittest.TestCase):
    def setUp(self):
        self.placar = Placar()
        self.placar.__init__()

    pass
