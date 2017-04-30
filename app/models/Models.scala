package models

//TODO add keynotes fields and use date instead of string
case class Conference
(
  name: String,
  email: String,
  description: String,
  from: String,
  to: String,
  place: String,
  id: Option[Int] = None
)