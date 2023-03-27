package com.lizw.designpattern.chain_of_responsibility.example3

class Child02Handler3(next: Handler3? = null) : Handler3(next) {
    override fun handleBySelf(request: Request): Boolean {
        if (request.target == "02")
            return true
        return false
    }
    
    override fun handle(request: Request) {
        println("child02 handled")
    }
}