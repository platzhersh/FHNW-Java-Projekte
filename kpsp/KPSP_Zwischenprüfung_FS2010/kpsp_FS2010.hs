{-
	Aufgabe 2.2

	Betrachten Sie eine Funktion front, die ein Element und eine Liste von Listen von Elementen als Eingabe nimmt
	und eine Liste von Listen von Elementen als Ausgabe liefert. Die Listen der Ausgabeliste werden erzeugt,
	indem an jede Liste der Eingabeliste das gegebene Element vorne angefügt wird.
	
	Beispiel:
	front 17 [[1,2,3],[4,5]] = [[17,1,2,3],[17,4,5]]


	e) Schreiben Sie die Funktion front
-}

front :: a -> [[a]] -> [[a]]
-- front x ys = [ x:(ys !! z) | z <- [0..(length ys)-1]]
front x ys = [ x:xs | xs <- ys]

{-
	Aufgabe 2.3

	Betrachten Sie eine Funktion comb, die eine ganze Zahl n >= 0 (Typ Int) als Eingabe nimmt und eine Liste von Listen 
	von Wahrheitswerten als Ausgabe liefert. Die Ausgabeliste enthält 2^n Listen der Länge n. Durch diese Listen werden 
	alle möglichen Kombinationen von Wahrheitswerten dargestellt, und zwar in der üblichen systematischen Reihenfolge.

	c) Schreiben Sie die Funktion comb. Benutzen Sie dazu die Funktion front als auch die Listenfunktion ++ (append)
		aus dem Standard Prelude. Hinweis: Überlegen Sie, wie Sie beispielsweise comb 4 aus comb 3 bestimmen können.
-}

comb :: Int -> [[Bool]]
comb 0 = [[]]
comb x = (front True (comb (x-1))) ++ (front False (comb (x-1)))

{-
	Aufgabe 2.4

	Betrachten Sie eine Funktion all, die eine Liste von Wahrheitswerten als Eingabe nimmt und einen Warheitswert als 
	Ausgabe liefert. Dieser Wahrheitswert ist true, falls alle Warheitswerte in der Liste true sind; sonst ist er false.
	
	c) Schreiben Sie die Funktion all. Verwenden Sie dazu den Operator &&.
	
-}

all2 :: [Bool] -> Bool
all2 [] = True
all2 (x:xs) = x && (all2 xs)


{-
	Aufgabe 2.5

	Betrachten Sie eine Funktion check, die einen booleschen Ausdruck vom Typ BExpr als Eingabe nimmt und einen Wahrheitswert
	als Ausgabe liefert. Der gelieferte Wahrheitswert ist true, falls der boolesche Ausdruck eine Tautologie ist ( das heisst,
	für alle möglichen Kombinationen von Wahrheitswerten den Wert true liefert); sonst ist der Wahrheitswert false.
	
	c) Schreiben Sie die Funktion check. Machen Sie dazu Gebrauch von vorher definierten Funktionen.
	
-}

bexpr1 :: (Int, [Bool]-> Bool)
bexpr1 = (2, \[x,y] -> not (x && y) == not x || not y)
bexpr2 :: (Int, [Bool]-> Bool)
bexpr2 = (2, \[x,y] -> False)
bexpr3 :: (Int, [Bool]-> Bool)
bexpr3 = (3, \[x,y,z] -> z)


type BExpr = (Int, [Bool]-> Bool)

check :: (Int, [Bool]-> Bool) -> Bool
check (x,xs) = all2 [ xs z | z <- comb x ]

{-
	Aufgabe 3

	Die Komposition o von Funktion ist assoziativ: (f o g) o h = f o (go h); die Klammern können also weggelassen werden.
	Schreiben Sie eine Funktion comp in Haskell, die eine Liste von Funktionen fi (1 <= i <= n, n >= 0) als Eingabe nimmt,
	und die Komposition dieser Funktionen als Ausgabe liefert:
	
	comp[ f1, f2,...,fn] = f1 o f2 o ... o fn
	
-}

comp = foldr (.) id

{-
	Aufgabe 4 

	Bäume lassen sich beispielsweise durch den folgenden Datentyp darstellen
	
	data Tree a = Node a [Tree a]
	
	1. Betrachten Sie eine Funktion numNodes, die einen Baum als Eingabe nimmt, und die Anzahl seiner Knoten als Ausgabe liefert.
	Beispielsweise ist numNodes tree1 gleich 5.
-}

data Tree a = Node a [Tree a]
type Node = Int 

tree1 = Node 3 [Node 4 [Node 3 [], Node 2 []], Node 5 []]

numNodes :: Tree a -> Int 
numNodes (Node _ ts) = 1 + sum [numNodes t | t <- ts]

{-
	2. Betrachten Sie eine Funktion sumTree, die einen Baum als Eingabe nimmt, und die Summe der Werte seiner Knoten als Ausgabe liefert.
	Beispielsweise ist sumTree tree1 gleich 17.
-}

sumTree :: Num a => Tree a -> a
sumTree (Node x ts) = x + sum [ sumTree z | z <- ts ]