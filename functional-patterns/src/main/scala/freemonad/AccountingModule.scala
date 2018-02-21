package freemonad

import cats.free.Free
import cats.{Id, ~>}
import freemonad.AccountingModule.Algebra.{Delete, Query, Store}

import scala.collection.mutable

object AccountingModule extends App {

  case class AccountNumber(value: String) extends AnyVal

  case class Balance(value: BigDecimal) extends AnyVal

  case class Account(accountNumber: AccountNumber, balance: Balance)

  //algebra
  sealed trait AccountRepoA[M]

  object Algebra {

    case class Store(account: Account) extends AccountRepoA[Account]

    case class Query(accountNumber: AccountNumber) extends AccountRepoA[Option[Account]]

    case class Delete(accountNumber: AccountNumber) extends AccountRepoA[Unit]

  }

  // Create a type based on Free[_]
  type AccountRepo[A] = Free[AccountRepoA, A]

  import cats.free.Free.liftF

  // Create smart constructors using liftF
  object DSL {
    def store(account: Account): AccountRepo[Account] = liftF[AccountRepoA, Account](Store(account))

    def query(accountNumber: AccountNumber): AccountRepo[Option[Account]] = liftF[AccountRepoA, Option[Account]](Query(accountNumber))

    def delete(accountNumber: AccountNumber): AccountRepo[Unit] = liftF[AccountRepoA, Unit](Delete(accountNumber))
  }

  //program
  object Program {

    import DSL._

    def checkBalanceProgram: Free[AccountRepoA, Balance] = for {
      newAccount <- store(Account(AccountNumber("1222"), Balance(100)))
      storedAccount <- query(newAccount.accountNumber)
      balance = storedAccount.get.balance
      _ <- delete(newAccount.accountNumber)
    } yield balance
  }

  //compiler
  object Interpreter {

    val bank = mutable.Map.empty[AccountNumber, Account]

    def idInterpreter: AccountRepoA ~> Id = new (AccountRepoA ~> Id) {
      override def apply[A](fa: AccountRepoA[A]): Id[A] = fa match {
        case Store(account) =>
          bank.put(account.accountNumber, account)
          account
        case Query(accountNumber) => bank.get(accountNumber)
        case Delete(accountNumber) =>
          bank.remove(accountNumber)
          ()
      }
    }

    type Error[A] = Either[String, A]
    def eitherInterpreter: AccountRepoA ~> Error = new (AccountRepoA ~> Error) {
      override def apply[A](fa: AccountRepoA[A]): Error[A] = fa match {
        case Store(account) => bank.put(account.accountNumber, account)
          Right(account)
        case Query(accountNumber) =>
          val acct: Option[Account] = bank.get(accountNumber)
          Either.cond(acct.isDefined, acct, "No such account")
        case Delete(accountNumber) =>  bank.remove(accountNumber)
          Right(())
      }
    }


  }

  //execute
  val result = Program.checkBalanceProgram.foldMap(Interpreter.idInterpreter)
  DSL.store(Account(AccountNumber("1"), Balance(100)))

  println(result.value)

}
