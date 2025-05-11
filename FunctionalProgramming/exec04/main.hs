main = do
    -- putStrLn "Digite o numero de pinos derrubados por jogada separados por espaço:"
    match <- getLine
    let numbers = map read (words match) :: [Int]
    let frames = makeLists numbers
    let scores = sumLists frames
    let totalScore = foldl (+) 0 scores
    -- putStrLn $ "Frames: " ++ show frames
    -- putStrLn $ "Formatado: " ++ formatGame frames ++ " | " ++ show totalScore
    -- putStrLn $ "Somas parciais: " ++ show scores
    putStrLn $ formatGame frames ++ " | " ++ show totalScore


{-
1. Receive all the plays separated by a space
2. Convert it to a list
3. In a list with K elements, get the nth element that is contained in K and
make a pair with the n + 1 element in the list ->
-> if the nth element is the number ten it will make pair with the number 0, representing a strike, 
-> if its the last frame, just add the remaining plays to the last frame
4. For each frame, check if it is a strike or spare, and calculate the score including the bonus from the next plays.
5. Sum the list of frame scores (including bonuses) to get the final match score.
6. Process the list of plays into frames (including handling of strikes and the 10th frame) ->
-> then compute the score for each frame using strike/spare rules
-> then format each frame for display, and finally show the formatted frames and total score.
-}

type Frame = [Int]

-- Get a list of plays and then tranforms it to a list of frames
-- Make pairs with the numbers, but for the last frame handle more than two plays
makeLists :: Frame -> [Frame]
-- Start in the frame 0
makeLists = makeLists' 0
  where
    makeLists' :: Int -> Frame -> [Frame]
    makeLists' _ [] = []
    makeLists' n [x]
      -- Only one play left and its not the last frame yet
      -- Handle malformed matches
      | n < 9    = [[x, 0]]
      | otherwise = [[x]]
    makeLists' n (10:xs)
      -- Strike and its not the last frame yet, make a pair with 0
      | n < 9    = [10, 0] : makeLists' (n + 1) xs
      | otherwise = appendToLast 10 (makeLists' n xs)
    makeLists' n (x:y:xs)
      -- If its not the last frame, make a pair with the two plays
      | n < 9     = [x, y] : makeLists' (n + 1) xs
      -- If its the last frame, add the last plays in the final frame
      | otherwise = appendToLast x (appendToLast y (makeLists' n xs))

    appendToLast :: Int -> [Frame] -> [Frame]
    -- If the list is empty, creates a new frame with the passed value
    -- prevents init from throwing an exception
    appendToLast val []     = [[val]]
    -- Init -> get all the elements, but not the last (like a pop())
    appendToLast val frames = init frames ++ [[val] ++ last frames]


isStrike :: Frame -> Bool
isStrike [10, _] = True
isStrike _ = False

isSpare :: Frame -> Bool
isSpare (x:y:_) = x /= 10 && x + y == 10
isSpare _ = False

sumLists :: [Frame] -> [Int]
sumLists [] = []
-- If there is only one frame left, sum all the values in it
sumLists [f] = [sum f]
sumLists (f:fs)
  | isStrike f = (sum f + bonusStrike fs) : sumLists fs
  | isSpare f  = (sum f + bonusSpare fs)  : sumLists fs
  | otherwise  = sum f : sumLists fs
  where
    bonusStrike :: [Frame] -> Int
    bonusStrike [] = 0
    -- If the next element has two or more plays
    bonusStrike ((x:y:_):rest)
      | x == 10 = 10 + if null rest then y else getTopList (getTopFrame rest)
      | otherwise = x + y
    -- If the frame has only one play
    bonusStrike ([x]:rest)
      | x == 10 = 10 + if null rest then 0 else getTopList (getTopFrame rest)
      | otherwise = x
    bonusStrike _ = 0
    
    bonusSpare :: [Frame] -> Int
    bonusSpare [] = 0
    -- Return the first value of the next frame
    bonusSpare ((x:_):_) = x
    bonusSpare _ = 0

-- Format each frame to the specified output
formatFrame :: Frame -> String
formatFrame frame
  | isStrike frame = "X _"
  | isSpare frame =
    let x:y:rest = frame
        xStr = formatValue x
    in xStr ++ " /" ++ concatMap ((" " ++) . formatValue) rest
    -- If there are more than 2 plays, its the final frame plus the extra plays
  | length frame > 2 = formatFinalFrame frame
  -- If there is no strike or spare, just add spaces
  | otherwise = unwords (map formatValue frame)

{-
  Format specifically the final frame
  In my implementation, I get the input, then transform it to a list of number
  And then I make pair with it, but in the final frame I make triplets (the final play + 2 extra plays)
-}
formatFinalFrame :: Frame -> String
formatFinalFrame [x,y,z]
  -- Handle if there is an strike in the final play, and then a spare in the extra plays
  | x == 10 && y + z == 10 = "X " ++ (if y == 10 then "X" else show y) ++ " /"
  -- Handle if there is a strike in the final play, and then a strike in the extra play
  | x == 10 && y == 10    = "X X " ++ formatValue z
  -- Handle if there is a strike in the final play, and then two extra plays that do not equal 10
  | x == 10                = "X " ++ show y ++ " " ++ show z
  -- Handle if there is a spare between the final play and the first extra play
  | x + y == 10            = show x ++ " / " ++ formatValue z
  -- Fallback case, if all the elements are 10, only print X X X
  | otherwise              = unwords (map formatValue [x,y,z])
formatFinalFrame frame = unwords (map formatValue frame)

-- If the element is the number 10, add a "X" char, if no, show the number
formatValue :: Int -> String
formatValue 10 = "X"
formatValue n  = show n

-- For each list, convert it to a String and then add a '|' character after 
formatGame :: [Frame] -> String
formatGame frames = concat $ zipWith format frames [1..]
  where
    format f i
      | i == length frames = formatFrame f
      | otherwise = formatFrame f ++ " | "

-- These two functions are substitutes for the head function
-- Head throws an exception if the list is empty

-- Returns the first element in a list
-- If the list is empty, return 0
getTopList :: Num a => [a] -> a
getTopList [] = 0
getTopList [x] = x
getTopList (x:_) = x

-- Returns the first sub-list in a frame's list
-- If the first sublist is empty, return an empty list
getTopFrame :: [[a]] -> [a]
getTopFrame [] = []
getTopFrame (x:_) = x