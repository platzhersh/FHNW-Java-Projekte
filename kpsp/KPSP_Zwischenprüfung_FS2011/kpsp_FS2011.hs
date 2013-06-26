{-
	Aufgabe 2.1

	Schreiben Sie eine Funktion f1 :: [Int] -> Int unter Verwendung von Pattern Matching mit folgenden Eigenschaften:
	
	a) die leere Liste wird auf 0 abgebildet;
	b) eine Liste mit genau einem Element wird auf 1 abgebildet;
	c) eine Liste mit genau zwei Elementen, von denen das zweite eine 0 ist, wird auf 2 abgebildet;
	d) eine Liste mit mindestens 3 Elementen, von denen das erste und das dritte gleich sind, wird auf 3 abgebildet;
	e) alle übrigen Listen werden auf 4 abgebildet
	
-}

f1 :: [Int] -> Int
f1 [] = 0
f1 [_] = 1
f1 [_,0] = 2
f1 (x:_:y:xs)
  | x == y = 3
  | otherwise = 4
f1 (_:_) = 4

{-
	Aufgabe 2.2

	Eine quadratische n x n Matrix ganzer Zahlen werde durch eine Liste von n Listen, jede mit der Länge n, von Int dargestellt (n >= 0). Schreiben Sie eine Funktion f2 :: [[Int]] -> Int unter Verwendung von Pattern Matching, die eine derartig dargestellte Matrix auf die Summe ihrer Diagonalelemente abbildet, für n = 0,1,2. Für alle anderen Werte von n ist der Funktionswert undefiniert.
-}

-- simpel
f2 :: [[Int]] -> Int
f2 [] = 0
f2 [[x]] = x
f2 [[x,_],[_,y]] = x+y
f2 [[x,_,_],[_,y,_],[_,_,z]] = x+y+z

-- extreme
f2e :: [[Int]] -> Int
f2e [] = 0
f2e [xs] = last xs
f2e (x:xs) = ( x !! ((length x) - (length xs)-1) ) + f2e xs

