
case class Email(from: String, to: String, subject: String, body: String)

type EmailFilter = Email => Boolean

val sentByOneOf: String => EmailFilter = sender => email => email.from == sender

val minimumSize: Int => EmailFilter = size => email => email.body.length() > size

val notSentByAnyOf: Set[String] => EmailFilter

val every: (EmailFilter*) => Boolean = emailFilters => emailFilters.map(filter => filter)
