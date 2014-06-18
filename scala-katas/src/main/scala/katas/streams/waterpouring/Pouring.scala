package katas.streams.waterpouring

/**
 * Given	two	water	jugs,	J1	and	J2,	with
capaciLes	C1	and	C2	and	iniLal	amounts	W1
and	W2,	ﬁnd	acLons	to	end	up	with	W1’	and
W2’	in	the	jugs
•  Example		problem:
– We	have	a	5	gallon	and	a	2	gallon	jug
– IniLally	both	are	full
– We	want	to	end	up	with	exactly	one	gallon
in	J2	and	don’t	care	how	much	is	in	J1
 */
/**
 *
 * @param capacity max amount of water a glass can hold. Index represent the glass. Vector(2,5) of size two represent two glasses
 *                 of capacity 2 and 5 litres
 */
class Pouring(val capacity: Vector[Int]) {

  // int value will represent the amount of water in a glass
  type Glass = Int

  // vector index will represent glass and the value the amount of water in litres
  type State = Vector[Glass]

  val initialState = capacity map (x => 0)

  //number of glasses
  val glasses = 0 to capacity.length

  trait Action {
    def change(fromState: State) :State
  }
  case class Empty(glass: Glass) extends Action {
    override def change(fromState: State): State = fromState updated(glass, 0)
  }
  case class Fill(glass: Glass) extends Action {
    override def change(fromState: State): State = fromState updated(glass, capacity(glass))
  }

  case class Pour(from: Glass, to: Glass) extends Action {
    override def change(currentState: State): State = {
      val remainingCapacityToGlass = capacity(to) - currentState(to)
      val pourableAmount = currentState(from) min remainingCapacityToGlass
      currentState updated(from, currentState(from)-pourableAmount) updated(to, currentState(to)+pourableAmount)
    }
  }

  val moves =
    (for(glass<-glasses) yield Empty(glass)) ++
    (for(glass <- glasses) yield Fill(glass)) ++
    (for (from<-glasses; to <- glasses; if(from != to))yield Pour(from, to))

  //Paths
  class Path(history:List[Action]) {

    def endState = (history foldRight initialState) ((action, state) => action change state)

    def add(action:Action) = new Path(action::history)

    override def toString: String = history.reverse mkString(",") + "--> end state:"+endState
  }

  val initialPath = new Path(Nil)

  def from(paths: Set[Path]): Stream[Set[Path]] =
    if(paths.isEmpty) Stream.empty
    else {
      val nextPaths = for{
        path <- paths
        next <- moves map path.add
      } yield next
      paths #:: from(nextPaths)
    }

  val pathSets = from(Set(initialPath))

}
