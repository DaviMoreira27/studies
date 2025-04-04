main :: IO ()
main = do
    firstV <- getLine
    secondV <- getLine
    let a = read firstV
    let b = read secondV
    let (perfect1, abundant1, deficient1) = classifyRange a b
    putStrLn $ show $ length deficient1
    putStrLn $ show $ length perfect1
    putStrLn $ show $ length abundant1

isPositive :: (Integral a) => a -> a -> Bool
isPositive x y = x > 0 && y > 0

createList :: (Integral a) => a -> a -> [a]
createList a b
    | isPositive a b == True = [a.. b]
    | otherwise = []

-- For each item in this list I must get all their dividers. Then I sum all the dividers
-- And compare to see if its smaller, bigger or equal than the number
    
getDividers :: Int -> [Int]
getDividers n = filter (\x -> n `mod` x == 0) [1..(n-1)]

isPerfect :: Int -> Bool
isPerfect n = sum (getDividers n) == n

isAbundant :: Int -> Bool
isAbundant n = sum (getDividers n) > n

isDeficient :: Int -> Bool
isDeficient n = sum (getDividers n) < n

classifyRange :: Int -> Int -> ([Int], [Int], [Int])
classifyRange start end = 
    let numbers = createList start end
        perfect = [n | n <- numbers, isPerfect n]
        abundant = [n | n <- numbers, isAbundant n]
        deficient = [n | n <- numbers, isDeficient n]
    in (perfect, abundant, deficient)
