-- Monad
main = do
    putStrLn  "Hello world!!!"
    putStrLn (show y) -- 8
    putStrLn (show p) -- "8"
    --putStrLn (show w) -- loop
    putStrLn (show (f True)) -- 5
    putStrLn (show (f False)) -- 42
    putStrLn (show (f (y > 10))) -- 42
    putStrLn $ show $ f $ y > 10 -- 42
    putStrLn $ show $ g 10 -- 11
    putStrLn $ show $ fat 5
    putStrLn $ show o3
    putStrLn $ show o5
    putStrLn $ show $ r o3


x = y + 1
y = 8
p = show y
w = w + 1

f True = 5
f False = 42

g h = h + 1 

fat 0 = 1
fat n = n * fat (n - 1)

o1 = []
o2 = [5]
o3 = [True, False, y > 10, fat 5 < 100] -- True, False, False, False
-- o4 = [4, False, "oi"]
o5 = 7:6:o2 -- [7, 6, 5]


r [] = 0
r (x:xs) = 1 + r xs