main :: IO ()
main = do
    putStrLn "Teste"
    putStrLn $ show $ soma $ ignoraEqnt (<100) $ pegaEqnt (<200) $ primos
    putStrLn $ show $ zipa [100.. 150] [200.. 250]
    putStrLn $ show $ pega 10 $ zipa [1..] primos
    putStrLn $ show $ primeiro $ head $ ignoraEqnt ((<1000).segundo) $ zipa [1..] primos
    putStrLn $ show $ zipaCom (<) [2.. 10] [12.. 20]
    putStrLn $ show $ zipaCom (mod) [2.. 10] [12.. 20]
    putStrLn $ show $ pega 10 $ zipaCom (+) uns primos

uns = 1:uns

primos = crivo [2..]
    where
        -- crivo (x:xs) = x:(crivo $ filtra (naoEhMultiplo x) xs)
        -- crivo (x:xs) = x:(crivo $ filtra (\y -> `mod` x /= 0) x xs)
        -- Dot operator: https://stackoverflow.com/questions/631284/dot-operator-in-haskell-need-more-explanation
        -- (.) :: (a -> b) -> (c -> a) -> (c -> b)
        crivo (x:xs) = x:(crivo $ filtra ( (/=0).(`mod` x) ) xs)

naoEhMultiplo :: Integer -> Integer -> Bool
naoEhMultiplo x y = y `mod` x /= 0

naoEhMultiplo' x = \y -> y `mod` x /= 0

filtra :: (a -> Bool) -> [a] -> [a]
filtra _ [] = []
filtra t (x:xs)
    | t x = x:filtra t xs
    | otherwise = filtra t xs

pega :: Integer -> [a] -> [a]
pega _ [] = []
pega 0 _ = []
pega n (x:xs) = x:pega (n - 1) xs


-- Pega Enquanto
pegaEqnt :: (a -> Bool) -> [a] -> [a]
pegaEqnt _ [] = []
pegaEqnt t (x:xs)
    | t x = x:pegaEqnt t xs
    | otherwise = []

ignoraEqnt :: (a -> Bool) -> [a] -> [a]
ignoraEqnt _ [] = []
ignoraEqnt t (x:xs)
    | t x = ignoraEqnt t xs
    | otherwise = x:xs

soma :: (Num a) => [a] -> a
soma [] = 0
soma (x:xs) = x + soma xs

zipa :: [a] -> [b] -> [(a,b)]
zipa [] _ = []
zipa _ [] = []
zipa (x:xs) (y:ys) = (x,y):zipa xs ys

primeiro :: (a,b) -> a
primeiro (x,_) = x

segundo :: (a,b) -> b
segundo (_,x) = x

zipaCom ::(a -> b -> c) -> [a] -> [b] -> [c]
zipaCom _ [] _ = []
zipaCom _ _ [] = []
zipaCom op (x:xs) (y:ys) = (op x y):zipaCom op xs ys
