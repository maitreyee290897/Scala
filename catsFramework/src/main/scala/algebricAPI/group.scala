trait Group[A] {
  def empty: A
  def combine(x: A, y: A): A
  def inverse(a: A): A
}