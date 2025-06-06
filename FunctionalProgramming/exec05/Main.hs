import System.IO
import Data.Maybe (fromMaybe)
import Text.Read (readMaybe)
import Control.Monad ((>=>))
import Data.List (sortBy)
import Data.Ord (comparing)
import Data.List (sort)

{-
    CSV:
        0. Country, 
        1. Confirmed, 
        2. Deaths, 
        3. Recovery, 
        4. Active.
    INPUT:
        0. Confirmed, 
        1. Deaths, 
        2. Recovery, 
        3. Active.

-}

main :: IO ()
main = do
  content <- readFile "dados.csv"
  input <- getLine
  let numbers = map read (words input) :: [Int]
      n1 = fromMaybe 0 (safeIndex 0 numbers)
      n2 = fromMaybe 1 (safeIndex 1 numbers)
      n3 = fromMaybe 2 (safeIndex 2 numbers)
      n4 = fromMaybe 3 (safeIndex 3 numbers)

      linesList = lines content
      csvMatrix = map splitByComma linesList

      filteredConfirmedRows = filterConfirmed 1 n1 csvMatrix
      -- The first n2 rows with the biggets active number
      topActive = topRowsByIndex 4 n2 csvMatrix
      bottomConfirmed = bottomRowsByIndex 1 n3 topActive
      topConfirmed = topRowsByIndex 1 n4 csvMatrix

  -- Sum Active columns where confirmed is bigger than N1
  print $ sumColumn 4 filteredConfirmedRows

  -- In the n2 countries with the biggest Active values, take the n3 countries with
  -- lowest Confirmed values and show the sum of the column Deaths
  print $ sumColumn 2 bottomConfirmed

  -- Print the n4 countries with the biggest Confirmed value in alphabetical order
  printSortedCountries topConfirmed



filterConfirmed :: Int -> Int -> [[String]] -> [[String]]
filterConfirmed index limit = filter (\row -> case safeIndex index row >>= readMaybe of
                                                  Just n  -> n > limit
                                                  Nothing -> False)

sumColumn :: Int -> [[String]] -> Int
sumColumn index = sum . map (fromMaybe 0 . (safeIndex index >=> readMaybe))

topRowsByIndex :: Int -> Int -> [[String]] -> [[String]]
topRowsByIndex index count =
  take count
  . sortBy (flip $ comparing (fromMaybe 0 . (safeIndex index >=> readMaybe)))

bottomRowsByIndex :: Int -> Int -> [[String]] -> [[String]]
bottomRowsByIndex index count =
  take count
  . sortBy (comparing (fromMaybe 0 . (safeIndex index >=> readMaybe)))

printSortedCountries :: [[String]] -> IO ()
printSortedCountries rows =
  mapM_ putStrLn $ sort $ map (fromMaybe "" . safeIndex 0) rows

safeIndex :: Int -> [a] -> Maybe a
safeIndex i xs
  | i < length xs = Just (xs !! i)
  | otherwise     = Nothing

splitByComma :: String -> [String]
splitByComma = splitBy ','

splitBy :: Char -> String -> [String]
splitBy _ [] = [""]
splitBy delim (c:cs)
  | c == delim = "":rest
  | otherwise  = (c : fromMaybe "" (safeHead rest)) : drop 1 rest
  where
    rest = splitBy delim cs

safeHead :: [a] -> Maybe a
safeHead (x:_) = Just x
safeHead []    = Nothing

safeLast :: [a] -> Maybe a
safeLast [] = Nothing
safeLast xs = Just (last xs)