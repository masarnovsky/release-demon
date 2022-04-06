package by.masarnovsky.releasedemon.backend.utils

fun <T> Set<T>.getRandomNValues(count: Int): Set<T> {
  val newSet = mutableSetOf<T>()
  if (count <= 0) return newSet
  if (count >= this.size) {
    newSet.addAll(this)
    return newSet
  }

  while (newSet.size != count) {
    newSet.add(this.random())
  }

  return newSet
}
