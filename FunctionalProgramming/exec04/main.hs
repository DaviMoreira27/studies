main = do
    -- putStrLn "Digite o numero de pinos derrubados por jogada separados por espaço:"
    entrada <- getLine
    let numeros = map read (words entrada) :: [Int]
    let frames = makeLists numeros
    let scores = sumLists frames
    let totalScore = foldl (+) 0 scores
    -- putStrLn $ "Frames: " ++ show frames
    -- putStrLn $ "Formatado: " ++ formatGame frames ++ " | " ++ show totalScore
    -- putStrLn $ "Somas parciais: " ++ show scores
    putStrLn $ formatGame frames ++ " | " ++ show totalScore


{-
1. Receive all the plays separated by a space - Ok
2. Convert it to a list - Ok
3. In a list with K elements, get the nth element that is contained in K and
make a pair with the n + 1 element in the list, if the nth element is the number ten
it will make pair with the number 0, representing a strike - Ok
4. For each tuple checks if it makes a spare or strike, if so add a plus one on the points multiplier variable
5. Use reduce to sum the total points of the match, then add 10 * the points multipler variable
6. Show the result (dont know how yet)
-}

type Frame = [Int]

makeLists :: Frame -> [Frame]
makeLists = makeLists' 0
  where
    makeLists' :: Int -> Frame -> [Frame]
    makeLists' _ [] = []
    makeLists' n [x]
      | n < 9    = [[x, 0]]
      | otherwise = [[x]]
    makeLists' n (10:xs)
      | n < 9    = [10, 0] : makeLists' (n + 1) xs
      | otherwise = appendToLast 10 (makeLists' n xs)
    makeLists' n (x:y:xs)
      | n < 9     = [x, y] : makeLists' (n + 1) xs
      | otherwise = appendToLast x (appendToLast y (makeLists' n xs))

    appendToLast :: Int -> [Frame] -> [Frame]
    appendToLast val []     = [[val]]
    appendToLast val frames = init frames ++ [[val] ++ last frames]


isStrike :: Frame -> Bool
isStrike [10, _] = True
isStrike _ = False

isSpare :: Frame -> Bool
isSpare (x:y:_) = x /= 10 && x + y == 10
isSpare _ = False

sumLists :: [Frame] -> [Int]
sumLists [] = []
sumLists [f] = [sum f]
sumLists (f:fs)
  | isStrike f = sum f + bonusStrike fs : sumLists fs
  | isSpare f = sum f + bonusSpare fs : sumLists fs
  | otherwise = sum f : sumLists fs
  where
    bonusStrike (a:_) = sum (take 2 a)
    bonusStrike _ = 0

    bonusSpare ((x:_):_) = x
    bonusSpare _ = 0

formatFrame :: Frame -> String
formatFrame frame
  | isStrike frame = "X _"
  | isSpare frame =
      let x:y:rest = frame
      in show x ++ " /" ++ concatMap ((" " ++) . show) rest
  | length frame > 2 = unwords (map show frame)
  | otherwise = unwords (map show frame)

formatGame :: [Frame] -> String
formatGame frames = concat $ zipWith format frames [1..]
  where
    format f i
      | i == length frames = formatFrame f
      | otherwise = formatFrame f ++ " | "