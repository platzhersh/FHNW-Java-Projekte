{-
	Aufgabe 1.1

	Betrachten Sie eine Funktion minTup, die ein Paar von Werten als Eingabe nimmt und den kleineren der beiden Werte als Ausgabe liefert.

	b) Schreiben Sie die Funktion minTup
-}

minTup :: Ord a => (a,a) -> a
minTup (x,y) 
  | x < y = x
  | otherwise = y
  
{-
	Aufgabe 1.2

	Betrachten Sie eine curried Funktion minCur, die zwei Werte als Eingabe nimmt und den kleineren der beiden Werte als Ausgabe liefert.

	b) Schreiben Sie die Funktion minCur
-}

minCur :: Ord a => a -> a -> a
minCur x y
  | x < y = x
  | otherwise = y
  
minCur2 x y = curry minTup x y

{-
	Aufgabe 1.3

	Betrachten Sie eine Funktion höherer Ordnung toCurry, die eine Funktion als Eingabe nimmt und eine curried Funktion als Ausgabe liefert. Die Ausgabe-Funktion hat dabei exakt die gleiche Funktionalität wie die Eingabe-Funktion; nur nimmt die Eingabe-Funktion ein Paar von Werten als Eingabe, während die Ausgabe-Funktion zwei 'getrennte' Werte als Eingabe als Eingabe nimmt. Die Funktion toCurry macht also aus einer Funktion auf einem Paar eine gleichartige curried Funktion auf zwei Werten. Beispielsweise liesse sich minCur aus minTup definieren durch:
	
	minCur = toCurry minTup

-}

--toCurry ::
toCurry f = curry f 

toCurry2 :: ((a,a) -> a) -> a -> a -> a
toCurry2 f = \x y -> f (x,y)

{-
	Aufgabe 1.4

	Betrachten Sie nun die genau umgekehrte Funktion höherer Ordnung toTuple. Mit dieser Funktion liesse sich beispielsweise minTup aus minCur definieren durch:
	
	minTup = toTuple minCur

-}

toTuple f = uncurry f

toTuple2 :: (a -> a -> a) -> (a,a) -> a
toTuple2 f = \(x,y) -> f x y

{-
	Aufgabe 1.5

	Betrachten Sie zwei Funktionen minList1 und minList2. Beide haben den gleichen Typ und die gleiche Funktionalität, unterscheiden sich aber in ihrer Implementierung. Die Funktionen nehmen eine nicht-leere Liste von Werten als Eingabe, und liefern den kleinsten dieser Werte als Ausgabe.
	
	Idee der Implementierung von minList1: Das Minimum einer Liste mit mindestens einem Element ist gleich dem Minimum aus dem ersten Element der Liste und dem Minimum der restlichen Elmente der Liste.
	
	Idee der Implementierung von minList2: Das Minimum einer Liste mit mindestens zwei Elementen ist gleich dem Minimum der um das grössere der beiden ersten Elemente der Liste verkürzten Liste.

-}

minList1 :: Ord a => [a] -> a
minList1 [x] = x
minList1 (x:xs)
  | x > head xs = minList1 xs
  | otherwise = minList1 (x:(tail xs))

  
minList12 :: Ord a => [a] -> a
minList12 [x] = x
minList12 (x:xs) = minCur x (minList12 xs)
  
minList2 ::Ord a => [a] -> a
minList2 [x] = x
minList2 (x:y:xs)
  | x > y = minList2 (y:xs)
  | otherwise = minList2 (x:xs)
  
minList22 ::Ord a => [a] -> a
minList22 [x] = x
minList22 (x:y:xs) = minList22 ((minCur x y):xs)

{-
	Aufgabe 1.6

	Besonders elegant wäre es natürlich, das kleinste Element einer Liste durch Anwendung unserer bekannten Funktionen foldl oder foldr zu bestimmen. Leider geht dies nicht unmittelbar, da die fold-Funktionen in der leeren Liste verankert sind, unsere Listen aber mindestens ein Element haben. Dieses Problem lässt sich aber leicht durch eine Funktion foldleft1 lösen, die wir wie folgt definieren

-}

foldleft1 :: (a -> a -> a) -> [a] -> a
foldleft1 f (x : xs) = foldl f x xs

minList3 xs = foldleft1 minCur xs

{-
	Aufgabe 1.7

	

-}