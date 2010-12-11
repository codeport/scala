def queens(n: Int): List[List[Int]] = { 
  //FIXME:
  def isSafe(col: Int, queens: List[Int], delta: Int): Boolean = false
  def placeQueens(k: Int): List[List[Int]] =
     if (k == 0) List(List()) 
     else for { 
        queens <- placeQueens(k - 1)
        column <- List.range(1, n + 1)
        if isSafe(column, queens, 1) } yield column :: queens 
  placeQueens(n)
}

// vim: set ts=4 sw=4 et:
