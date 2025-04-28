main :: IO ()
main = do
    firstV <- getLine
    secondV <- getLine
    let x = read firstV
    let y = read secondV
    putStrLn $ show $ safeMaximum . consecutiveDifferences $ filter (<x) (filter (<=y) primes)


-- FILTER
pegaEqnt :: (a -> Bool) -> [a] -> [a]
pegaEqnt _ [] = []
pegaEqnt t (x:xs)
    | t x = x:pegaEqnt t xs
    | otherwise = []

-- FILTER
ignoraEqnt :: (a -> Bool) -> [a] -> [a]
ignoraEqnt _ [] = []
ignoraEqnt t (x:xs)
    | t x = ignoraEqnt t xs
    | otherwise = x:xs

-- This method is just an encapsulation for easier use
notMultiple x = \y -> y `mod` x /= 0

-- Crivo de Eratóstenes  
-- Get the first prime number (2)
-- TODO: #1 Study this more
primes = crivo [2..]
    where
        crivo (x:xs) = x:(crivo $ filter (notMultiple x) xs)


-- In a list with N elements, if all the elements being prime numbers
-- Its necessary to tranverse the list checking how much (n + 1) - n is
-- The result can be stored in a list, and then return the max number

safeMaximum :: [Int] -> Int
safeMaximum [] = 0
safeMaximum xs = maximum xs


-- Drop -> removes the first element of the list
-- ZipWith -> Combines two list using an operation, basically the zipa com
consecutiveDifferences :: [Int] -> [Int]
consecutiveDifferences xs = zipWith (-) (drop 1 xs) xs


-- zipaCom ::(a -> b -> c) -> [a] -> [b] -> [c]
-- zipaCom _ [] _ = []
-- zipaCom _ _ [] = []
-- zipaCom op (x:xs) (y:ys) = (op x y):zipaCom op xs ys