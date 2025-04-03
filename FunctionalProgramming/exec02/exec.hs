main :: IO ()
main = do
    firstV <- getLine
    secondV <- getLine
    let a = read firstV
    let b = read secondV
    putStrLn $ show $ createList a b

isPositive :: (Integral a) => a -> a -> Bool
isPositive x y = x > 0 && y > 0

createList :: (Integral a) => a -> a -> [a]
createList a b
    | isPositive a b == True = [a.. b]
    | otherwise = []
    