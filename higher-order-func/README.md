## On higher-order functions
  
  A higher-order function, as opposed to a first-order function, can have one of three forms:
  
  One or more of its parameters is a function, and it returns some value.
  It returns a function, but none of its parameters is a function.
  Both of the above: One or more of its parameters is a function, and it returns a function.

We called methods like map, filter, or flatMap and passed a function to it that was used to 
transform or filter a collection in some way. Very often, the functions 
we passed to these methods were anonymous functions, sometimes involving a little bit of duplication.

## Freemail service
Users should be able to configure when an email is supposed to be blocked.
We are representing emails as instances of a simple case class:
```
case class Email(
  subject: String,
  text: String,
  sender: String,
  recipient: String)
```

We want to be able to filter new emails by the criteria specified by the user, 
so we have a filtering function that makes use of a predicate, a function of 
type Email => Boolean to determine whether the email is to be blocked.
If the predicate is true, the email is accepted, otherwise it will be blocked

```
type EmailFilter = Email => Boolean
def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)
```

Following filters needs to be supported:

* sentByOneOf: String => EmailFilter
* notSentByAnyOf: Set[String] => EmailFilter
* minimumSize: Int => EmailFilter
* maximumSize: Int => EmailFilter
