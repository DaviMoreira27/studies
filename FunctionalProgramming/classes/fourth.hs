main = do
    putStrLn $ show $ somaPos lista -- 34
    putStrLn $ show $ somaNeg lista -- -21
    putStrLn $ show $ somaPar lista -- 6
    putStrLn $ show $ somaSe "Pos" lista -- 34
    putStrLn $ show $ somaSe "Neg" lista -- -21
    putStrLn $ show $ somaSe "Par" lista -- 6
    putStrLn $ show $ somaTeste ehPar lista -- 6
    putStrLn $ show $ operaTeste ehPar (+) 0 lista -- 6
    putStrLn $ show $ operaTeste ehImpar (*) 1 lista -- 945
    putStrLn $ show $ operaTeste ehPositivo (+) 0 lista -- 34

lista = [1,-2,3,-4,5,-6,7,8,-9,10]

ehPositivo x = x > 0
ehNegativo x = x < 0
ehPar x = mod x 2 == 0
ehImpar x = mod x 2 == 1

somaPos [] = 0
somaPos (x:xs)
    | x > 0 = x + somaPos xs
    | otherwise = somaPos xs

somaNeg [] = 0
somaNeg (x:xs)
    | x < 0 = x + somaNeg xs
    | otherwise = somaNeg xs

somaPar [] = 0
somaPar (x:xs)
    | mod x 2 == 0 = x + somaPar xs
    | otherwise = somaPar xs

somaSe cond [] = 0
somaSe cond (x:xs)
    | cond == "Pos" && x > 0 = x + somaSe cond xs
    | cond == "Neg" && x < 0 = x + somaSe cond xs
    | cond == "Par" && mod x 2 == 0 = x + somaSe cond xs
    | otherwise = somaSe cond xs

somaTeste teste [] = 0
somaTeste teste (x:xs)
    | teste x = x + somaTeste teste xs
    | otherwise = somaTeste teste xs

operaTeste teste op neutro [] = neutro
operaTeste teste op neutro (x:xs)
    | teste x = op x (operaTeste teste op neutro xs)
    | otherwise = operaTeste teste op neutro xs

-- type 
-- type class

k :: Integer
k = 5

m :: Int
m = 5

-- qual a diferença dos dois?
-- integer: inteiro matematico, não existe limite para inteiro
-- int: tipo de dado que tem um limite superior, existe um maior int 