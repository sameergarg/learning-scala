
val fruits = List("apple", "banana", "orange")

fruits.foreach(println)

//for comprehension
for{
  fruit<-fruits
} yield fruit.toUpperCase
//translated
fruits.map(_.toUpperCase)

//with guard
for{
  fruit<-fruits
  if(fruit.startsWith("a"))
} yield fruit.toUpperCase
//translated
fruits.withFilter(_.startsWith("a")).map(_.toUpperCase)

//with counter
for{
  (fruit,index)<-fruits.zipWithIndex
  if(index==1)
} yield fruit

//nested for - generate matrix
for{
  numbers<- 1 to 3
  characters<- 'a' to 'c'
} yield (numbers, characters)
//translated
(1 to 3).flatMap(number=>('a' to 'c').map(character=>(number,character)))

//nested for with guard - generate matrix
val nestedForWithGuard = for{
  number<- 1 to 3
  character<- 'a' to 'c'
  if(number!=1)
} yield (number, character)

val nestedUsingFlatMap = (1 to 3).withFilter(_!=1).flatMap(number => ('a' to 'c').map(character=> (number, character)))

nestedForWithGuard == nestedUsingFlatMap







