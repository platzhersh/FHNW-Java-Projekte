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

toTuple :: (a -> a -> a) -> (a,a) -> a
toTuple f = \(x,y) -> f x y