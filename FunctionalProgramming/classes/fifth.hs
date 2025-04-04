-- porque haskell é bonito

main = do
    putStrLn "HW"
    putStrLn $ show $ 6 +-+=@ 8 -- 52
    putStrLn $ show $ f1234 6 8 -- 52
    putStrLn $ show $ (+-+=@) 6 8 -- 52
    putStrLn $ show $ 6 `f1234` 8 -- 52
    putStrLn $ show $ x `mod` 5 -- 2
    putStrLn $ show $ g 9 -- 14
    putStrLn $ show $ f 9 -- 14
    putStrLn $ show $ g' 9 -- 14
    putStrLn $ show $ f' 9 -- 14
    putStrLn $ show $ h' 9 -- 14
    putStrLn $ show $ mapa dobro lista -- [2,4,6,8,10]
    putStrLn $ show $ mapa (+5) lista -- [6,7,8,9,10]
    putStrLn $ show $ mapa (+1) $ mapa dobro lista -- [3,5,7,9,11]
    putStrLn $ show $ filtra (>3) lista -- [4,5]
    putStrLn $ show $ soma lista -- 15
    putStrLn $ show $ pega 3 lista -- [1,2,3]
    putStrLn $ show lista
    putStrLn $ show $ soma $ mapa (^2) $ filtra (<4) lista -- 14
    putStrLn $ show $ soma $ mapa (2^) $ pega 3 lista -- 14 ; 2^1 + 2^2 + 2^3
    putStrLn $ show $ 2 `pertence` lista -- True
    putStrLn $ show $ 6 `pertence` lista -- False
    putStrLn $ show $ "j" `pertence` ["j", "o", "s", "e"] -- True
    putStrLn $ show $ lista2
    putStrLn $ show $ qs lista2 -- [6,7,8,10,13,15]
    putStrLn $ show $ qs "jose" -- "ejos"

-- dado um vetor imprima para mim a soma do quadrado de todos os numeros menores que 4 (linha 21)
-- a soma das potencias de 2 dos 3 primeiros elementos do vetor (linha 22)

lista = [1,2,3,4,5]
lista2 = [7, 6, 10, 15, 8, 13]
lista3 = [20, 35, 11, 12]

x = 42
y = x + 5

f1234 a b = a ^ 2 + b * 2 -- identificador

a +-+=@ b = a ^ 2 + b * 2-- identificador

add :: Integer -> Integer -> Integer
add a b = a + b
f a = add 5 a
g = add 5

add' a b = (+) a b
f' a = (+) 5 a
g' = (+) 5
h' = (+5)

-- f', g' e h' são funções identicas 

mapa :: (a -> b) -> [a] -> [b]
mapa _ [] = []
mapa f (x:xs) = f x : mapa f xs

dobro x = 2 * x

filtra :: (a -> Bool) -> [a] -> [a]
filtra _ [] = []
filtra teste (x:xs)
    | teste x = x : filtra teste xs
    | otherwise = filtra teste xs

soma :: (Num a) => [a] -> a
soma [] = 0
soma (x:xs) = x + soma xs

pega :: (Eq t, Num t) => t -> [a] -> [a]
pega _ [] = []
pega 0 _ = []
pega n (x:xs) = x : pega (n-1) xs


-- type class
-- ela define a capacidade que o tipo tem, esse tipo tem capacidade de fazer isso ou aquilo 
-- a classe de tipos é uma abstração, ela não é um tipo, ela é uma abstração
pertence :: Eq a => a -> [a] -> Bool
_ `pertence` [] = False
e `pertence` (x:xs)
    | e == x = True -- se pertence a cabeça é true
    | otherwise = e `pertence` xs -- se não, pertence a cauda


-- implementação do quicksort

qs :: Ord a => [a] -> [a]
qs [] = []
qs (pivo:xs) = menores ++ iguais ++ maiores
    where
        menores = qs $ filtra (< pivo) xs
        iguais = pivo : filtra (== pivo) xs
        maiores = qs $ filtra (> pivo) xs