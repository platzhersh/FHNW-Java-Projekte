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
	
	Minimum und Maximum von möglicherweise leeren Listen liessen sich leicht definieren, wenn wir die uns zur Verfügung stehenden ganzen Zahlen Int ergänzen würden durch die zwei weiteren "Werte" -Inf und +Inf. In Haskell können wir das leicht durch folgenden neuen Typ IntIf realisieren:

-}

data IntInf
  = MinusInf
  | Normal Int
  | PlusInf
  
instance Show IntInf where
  show (Normal x) = show x
  show MinusInf = "- Infinity"
  show PlusInf = "+ Infinity"
  
{- 
	Dann könnten wir die Ordnungsrelation als Funktion less zwischen zwei Werten vom Typ IntIf definieren durch folgende Tabelle:
	
	less	-Inf	m: Int	+Inf
	--------------------------------
	-Inf	false	true	true
	n: Int	false	n < m	true
	+Inf	false	false	false
-}

less :: IntInf -> IntInf -> Bool
less MinusInf MinusInf = False
less MinusInf _ = True

less (Normal x) MinusInf = False
less (Normal x) (Normal y) = x < y
less (Normal x) PlusInf = True

less PlusInf _ = False

{-
	Schliesslich können wir eine Funktion minListInf definieren, die das kleinste Element einer Liste von Werten vom Typ IntInf bestimmt - selbst wenn die Liste leer ist.
	
	e) Schreiben Sie die Funktion minListInf. Hinweis: mit Vorteil schreiben Sie dazu zuerst eine Funktion minInf, die den kleineren von zwei Werten vom Typ IntInf bestimmt.
-}

minInf :: IntInf -> IntInf -> IntInf
minInf x y
  | less x y = x
  | otherwise = y

minListInf :: [IntInf] -> IntInf
minListInf [] = PlusInf
minListInf (x:xs) = minInf x (minListInf xs)
  
minListInf2 :: [IntInf] -> IntInf
minListInf2 [] = PlusInf
minListInf2 (x:xs) 
  | less x y = x
  | otherwise = y
  where y = minListInf2 xs
  
{-
	Aufgabe 3
	
	Betrachten Sie eine Funktion delDups, die eine Liste von Elementen als Eingabe nimmt und eine Liste von Elementen als Ausgabe liefert. Die Ausgabeliste enthält dieselben Elemente wie die Eingabeliste, jedoch jedes Element höchstens einmal. Beispiel:
	
	delDups [3,5,3,4,7,4,4,3,6] = [3,5,4,7,6]

-}

delDups :: Eq a => [a] -> [a]
delDups [] = []
delDups (x:xs) = [x] ++ delDups (filter (\y -> y/=x) xs)

{-
	Aufgabe 4.1
	
	Boolesche Ausdrücke sind entweder:
	- eine boolesche Konstante (true, T oder false, F), oder
	- die Negation (not) eines booleschen Ausdrucks, oder
	- die Konjunktion (and) von zwei booleschen Ausdrücken, oder
	- die Disjunktion (or) von zwei booleschen Ausdrücken.
	
	Beispiel für einen booleschen Ausdruck:
	
	(T and F) or not F
	
	a) Schreiben Sie einen neuen Datentyp BExpr zur Darstellung boolescher Ausdrücke
	b) Stellen Sie unseren Beispielausdruck mit diesem Datentyp dar.
-}

-- a)

data BExpr = Konstante Bool
  | Negation BExpr
  | Konjunktion BExpr BExpr
  | Disjunktion BExpr BExpr
  
instance Show BExpr where
  show (Konstante b) = show b
  show (Negation b) = "(not " ++ show b ++ ")"
  show (Konjunktion a b) = "(" ++ show a ++ " and " ++ show b ++ ")"
  show (Disjunktion a b) = "(" ++ show a ++ " or " ++ show b ++ ")"

-- b)
example = Disjunktion (Konjunktion (Konstante True) (Konstante False)) (Negation (Konstante False))

{-
	Aufgabe 4.2
	
	Betrachten Sie eine Funktion eval, die einen booleschen Ausdruck nach den bekannten Regeln für not, and und or auswertet. Die Auswertung unseres Beispielausdrucks liefert den Wert true.

-}

eval :: BExpr -> Bool
eval (Konstante b) = b
eval (Konjunktion a b) = (eval a) && (eval b)
eval (Disjunktion a b) = (eval a) || (eval b)
eval (Negation b) = not (eval b)

{-
	Aufgabe 4.3 
	
	Zwei boolesche Ausdrücke A und B lassen sich auch durch eine Implikation (implies, ->) verknüpfen. Unser Datetyp hat zwar keinen Konstruktor für die Implifikation, aber die Implikation lässt sich gemäss folgendem Zusammenhang leicht über die Negation und Disjunktion darstellen:
	
	A -> B = ¬ A or B
	
	Betrachten Sie eine Funktion makeImpl, die zwei boolesche Ausdrücke A und B als Eingabe nimmt und einen booleschen Ausdruck a -> B als Ausgabe liefert, entsprechend dem eben genannten Zusammenhang.

-}

makeImpl :: BExpr -> BExpr -> BExpr
makeImpl a b = Disjunktion (Negation a) b