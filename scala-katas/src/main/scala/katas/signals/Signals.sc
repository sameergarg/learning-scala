import katas.signals.{BankAccount, ConsolidatedAccounts}

object signals{


private val johnAccount = new BankAccount
private val harryAccount = new BankAccount
private val sallyAccount = new BankAccount
private val accounts = List(johnAccount, harryAccount, sallyAccount)

val consolidatedAccounts = new ConsolidatedAccounts(accounts)
  consolidatedAccounts.accountsTotal

  johnAccount.deposit(20)
  harryAccount.deposit(30)
  sallyAccount.deposit(40)
  consolidatedAccounts.accountsTotal
}