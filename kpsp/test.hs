import Data.Char 

factorial n = product [1 .. n]

average ns = sum ns `div` length ns

average2 ns = div (sum ns) (length ns)

n = a `div` length xs
  where
    a = 10
    xs = [1,2,3,4,5]
	

pattern :: Eq a => [a] -> Int	
pattern [] = 0
pattern [_] = 1 -- also possible: pattern (_:[]) = 1
pattern (x:y:_) = if (x == y) then 2 else 3 
-- also possible
-- pattern (x:y:_) | x==y = 2
--				   | otherwise = 3

pattern2 :: [(Char,Char)] -> Int
pattern2 [_,_,_] = 3
pattern2 [('a','b')] = 1
pattern2 (_:_:_:_:_) = 4
pattern2 (_) = 0


f1 :: Eq a => [[a]] -> (a,a)
f1 [_:[a], [b]] = (a,b)

f2 :: Eq a => [[a]] -> (a,a)
f2 [a:[_],[b]] = (a,b)

f3 :: Eq a => [[a]] -> (a,a)
f3 [a:[], [b]] = (a,b)


-- [] :: [a]
-- f4 :: [[b]] -> String
f4 [[], [[]]] = "ok"
f4 _ = "ko"

f5 :: [[[b]]] -> [b]
f5 [_,a : [[]]] = a

-- Faktoren berechnen
factors :: Int -> [Int]
factors n = 
  [x | x <- [1..n], n `mod` x == 0]
  
-- alle Faktoren ausser der Zahl selbst
factors2 :: Int -> [Int]
factors2 n = 
  [x | x <- [1..n-1], n `mod` x == 0]
  
-- Primzahl
prime :: Int -> Bool
prime n = factors n == [1,n]

-- Primzahlen berechnen
primes :: Int -> [Int]
primes n = [x | x <- [2..n], prime x]

-- Vorkommnisse von Elementen
positions x xs =
  [i | (x', i) <- zip xs [0..], x' == x]
  
--pyths :: Int -> [(Int,Int,Int)]
pyths n = 
  [(x,y,z) | x <- [1..n],y <- [1..n],z <- [1..n], x^2+y^2 == z^2]

-- Perfects
perfects :: Int -> [Int]
perfects x = 
  [i | i <- [1..x], i == (sum (factors2 i))]
  

-- Skalarprodukt
scalar xs ys =
  sum [ x*y | (x,y) <- zip xs ys]
  
-- gibt jedes zweite Element der Liste zurück
eachSecond (x:_:xs) = x : eachSecond xs
-- eachSecond [x] = [x]
-- eachSecond [] = []
-- >
eachSecond xs = xs

-- Duplikate entfernen
remAdjDups (x:y:xs) 
  | x == y = remAdjDups (y:xs)
  | otherwise = x : remAdjDups (y:xs)
remAdjDups xs = xs

-- Duplikate entfernen mit as-Patterns
remAdjDups2 (x:ys@(y:_))
  | x == y = remAdjDups2 ys
  | otherwise = x : remAdjDups2 ys
remAdjDups2 xs = xs

-- Duplikate gruppieren
group (x:xs) = reverse (h xs [x][])
  where
    h (x:xs) accu1@(y:_) accu2
	  | x == y = h xs (x:accu1) accu2
	  | otherwise = h xs [x] (accu1:accu2)
    h [] accu1 accu2 = accu1 : accu2
group [] = []

-- 
foldr2 :: ( a -> b -> b ) -> b -> [a] -> b
foldr2 f v [] = v
foldr2 f v (x:xs) = x `f` (foldr f v xs)

revR [x] = foldr (\x accu -> accu++[x])

revApp :: [a] -> [a] -> [a]
revApp rs xs = foldl (flip(:)) xs rs
-- revApp = flip (foldl (flip(:)))

isPrime 1 = False
isPrime 2 = True
isPrime x = isEven x
    where
        isEven z | filter even [z] == [] = length [y | y <- 2:(filter (not . even) [2.. (round (sqrt (fromInteger x)))]), mod x y == 0] == 0
                 | otherwise = False

andR :: [Bool] -> Bool
andR = foldr (&&) True
-- = andR xs = foldr (&&) True

--euclid :: Integer -> Integer -> (Integer,(Integer,Integer))
euclid a b = (gcd,(x,y))
    where
      (gcd,(x,y)) = (b,(0,1))
	  
-- Testframework
insertTests = 
	[((99,[1,2,3],0), [99,1,2,3]), ((99,[1,2,3],1), [1,99,2,3]),((99,[1,2,3],2), [1,2,99,3]),((99,[1,2,3],3),[1,2,3,99])]

insert :: a -> [a] -> Int -> [a]
insert x ys n = (take n ys) ++ [x] ++ (drop n ys)

--insertT x ys n = insert x ys n
--insertT = uncurry3 insert

--sut :: a -> b

--tests :: [(a,b)]

eq :: b -> b -> Bool
eq x y = andR x y

uncurry :: (a -> b -> c)-> ((a,b) -> c)
uncurry f (x,y) = f x y

uncurry3 :: (a->b->c->d)->((a,b,c)->d)
uncurry3 f (x,y,z) = f x y z

test :: (b->b->Bool)->f->(f->(a->b))->[(a,b)]->Bool
test eq sut uncurry tests = and [eq (uncurry sut x)y | (x,y) <- tests]
--

test02 :: IO (String, String)
test02 =
  do putStrLn "Your First Name:"
     firstName <- getLine
     putStrLn "Your Family Name:"
     familyName <- getLine
     return (firstName,familyName)
	 
test03 :: IO ()
test03 = 
  do (firstName,familyName) <- test02
     let upperFirstName = map toUpper firstName
         upperFamilyName = map toUpper familyName
     putStrLn ("Hi " ++ upperFirstName ++ " " ++ upperFamilyName)

test04 :: IO ()
test04 = 
  do fileName <- getLine
     stringFileContents <- readFile (fileName ++ ".inp")
     let xys = read stringFileContents :: [(Int, Int)]
     let result = [x+y | (x,y) <- xys]
     writeFile (fileName ++ ".outp") (show result)
	 
test05 :: IO [String]
test05 =
  do s <- getLine
     if s == "." then
	   return []
	 else
	   do ss <- test05
	      return (s : ss)
		  
stringToBin 