var totalDeposit = 0;
var Account = function(deposit){
    totalDeposit = deposit;
}
Account.prototype.getAccount = function(){
    return totalDeposit;
}
Account.prototype.withDraw = function(money){
    this.money = money;
    totalDeposit -= money;
}
Account.prototype.AtmRemainingCash = function() {
    return this.money;
}
module.exports = Account;