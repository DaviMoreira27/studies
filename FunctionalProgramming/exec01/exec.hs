main :: IO ()
main = do
    firstV <- getLine
    secondV <- getLine
    thirdV <- getLine
    let a = read firstV
    let b = read secondV
    let c = read thirdV
    -- putStrLn ("First triangule side " ++ firstV ++ "\n" ++ "Second triangule side " ++ secondV ++ "\n" ++ "Third triangule side " ++ thirdV ++ "\n")
    -- print (isATriangule a b c)
    putStrLn (mainFunction a b c)

mainFunction :: (Ord a, Show a, Floating a) => a -> a -> a -> String
mainFunction valueA valueB valueC 
    | isATriangule valueA valueB valueC = show $ getAreaByHeronFormula valueA valueB valueC
    | otherwise = "-"

isATriangule :: (Ord a, Num a) => a -> a -> a -> Bool
isATriangule a b c =
    let minimumV = getTwoMinimumValues a b c
        in sum minimumV >= maximum [a, b, c]    

getPerimeter :: Num a => a -> a -> a -> a
getPerimeter a b c = a + b + c

getSemiPerimeter :: Fractional a => a -> a
getSemiPerimeter p = p / 2

getAreaByHeronFormula :: Floating a => a -> a -> a -> a
getAreaByHeronFormula sideA sideB sideC = 
    let p = getSemiPerimeter $ getPerimeter sideA sideB sideC
        in sqrt (p * (p - sideA) * (p - sideB) * (p - sideC))

getTwoMinimumValues :: (Ord a) => a -> a -> a -> [a]
getTwoMinimumValues a b c = filter (/= maximum [a, b, c]) [a, b, c]