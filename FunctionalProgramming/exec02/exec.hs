main :: IO ()
main = do
    firstV <- getLine
    secondV <- getLine
    let a = read firstV
    let b = read secondV
    putStrLn $ show $ createList a b

perfectNumber = 0
abudantNumber = 0
defectNumber = 0

isPositive :: (Integral a) => a -> a -> Bool
isPositive x y = x > 0 && y > 0

createList :: (Integral a) => a -> a -> [a]
createList a b
    | isPositive a b == True = [a.. b]
    | otherwise = []

-- For each item in this list I must get all their dividers. Then I sum all the dividers
-- And compare to see if its smaller, bigger or equal than the number
    
getDividers :: Int -> [Int]
getDividers n = filter (\x -> n `mod` x == 0) [1..n]

getResult :: [Int] -> ()
getResult [] = 0
getResult (x:xs)
    | sum (getDividers x) + getResult xs == x = perfectNumber +=
    | sum (getDividers x) + getResult xs > x = abudantNumber +=
    | sum (getDividers x) + getResult xs < x = defectNumber +=

