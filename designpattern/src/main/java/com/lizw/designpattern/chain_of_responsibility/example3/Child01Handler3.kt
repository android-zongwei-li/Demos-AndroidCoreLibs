package com.lizw.designpattern.chain_of_responsibility.example3

class Child01Handler3(next: Handler3? = null) : Handler3(next) {
    override fun handleBySelf(request: Request): Boolean {
        if (request.target == "01")
            return true
        return false
    }
    
    override fun handle(request: Request) {
        println("child01 handled")
    }
}