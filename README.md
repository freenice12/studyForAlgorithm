# Study For Algorithm!
Algorithm!

## Day 1
* Find shortest route from start point to door point
* Jerry: always moves 1 point from current Location (ex: E, W, S, N)
* Tom: makes Jerry detour by setting obstacles on the map

## Day 2
* Find longest word
* ex) str1: dkahffk123dkahffkdy, str2: dkahffk12dkahffk
* result) dkahffk12

## Day 3
* Find triangles in the picture
![Picture](https://scontent.cdninstagram.com/hphotos-xpa1/t51.2885-15/e15/11142340_793016000747130_1938939836_n.jpg)

## Day 4
* Matched Square
* Find number what is half of Total Square Number
* ex)
* 1 1 3
* 1 1 2
* 2 3 2
* Total Square Number : 16, half = 8
* We can choose Square : from (1,1) to (3, 3) ==> 1 + 2 + 3 + 2 = 8

## Day 5
* Ordering finded triangles

## Day 6
* 40 * 40 board, 40 numbers should fill when create board
* all numbers range 1 to 9
* fill number from 1 to 9 (40 * 40 - 40) ==> 1580 squares.
* each square has diff number
* ex )
* 1 2
* 3 4 ==> 6 (2-1 = 1, 3-1 = 2, 4-2 = 2, 4-3 = 1)
* find min diff number by filling numbers on board



# exDay
* Fibonacci Sequence
* [IOError](https://code.google.com/codejam/contest/9214486/dashboard)
* Find repeating decimal (ex: 1/3 => 3, 1/7 => 142857, ...)

## Picture's title and sort problem
* Given string like:
  * rawTitle.extension, location, time
  
* It should be rename the title like below:
  * from  
: abc.jpg, A, 2013-09-04  
: see.jpg, A, 2012-09-05
  * to  
: A1.jpg, A, 2012-09-05  
: A2.jpg, A, 2013-09-04  

* When the picture which same location is over 10  
* Then title must be prefix 0 like '01' ~ '09' and 10 and so on.  

* The result shoud be:
> A1.jpg  
> A2.jpg

## Counting steps 1 & 2
* Given String like:
  * 011100
  * It means number '28'
  
* Rule like below:
  * even should be half
  * odd should be minus 1
  * until be zero. 
  
* ex) "0101"
  * means 5
  * 5 -> 4 (step 1 (-1))
  * 4 -> 2 (step 2 (/2))
  * 2 -> 1 (step 3 (/2))
  * 1 -> 0 (step 4 (-1))
  
* result: 4

> counting steps 2 should be shown file comment
