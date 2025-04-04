main = do
    putStrLn $ show $ somaPos lista -- 29
    putStrLn $ show $ bhaskara 1 2 1 -- [-1]
    putStrLn $ show $ bhaskara 1 2 3 -- []
    putStrLn $ show $ bhaskara 1 0 (-4) -- [2.0, -2.0]

-- guarda
sinal(x)
    | x > 0 = 1 -- testa o primeiro guarda e retorna 1
    | x == 0 = 0 -- testa o segundo guarda e retorna 0
    | otherwise = -1 -- testa o terceiro guarda e retorna -1
-- caso eu colocasse o otherwise no primeiro guarda ele não testaria os outros

lista = [1, 2, -3, 4, -5, 6, 7, -8, 9, -10]


-- soma de posições em uma lista
somaPos [] = 0 -- quando a lista estiver vazia retorna 0
somaPos (x:xs) -- pega o primeiro elemento e o resto da lista
    | x > 0 = x + somaPos xs -- se a cabeça for positiva, soma com o resto da lista
    | otherwise = somaPos xs -- se não for positiva, só soma o resto da lista

-- bhaskara
bhaskara a b c
    | delta < 0 = []
    | delta == 0 = [x]
    | otherwise = [x', x'']
    where
        delta = b^2 - 4*a*c
        x = -b / (2*a)
        x' = (-b + sqrt delta) / ( 2 * a )
        x'' = (-b - sqrt delta) / ( 2 * a )