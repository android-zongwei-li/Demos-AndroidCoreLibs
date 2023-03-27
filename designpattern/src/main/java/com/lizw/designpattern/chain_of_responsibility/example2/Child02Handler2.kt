package com.lizw.designpattern.chain_of_responsibility.example2

class Child02Handler2(next: Handler2? = null) : Handler2(next) {
    
    override fun handleBySelf(request: Request): Boolean {
        if (request.target == "child02")
            return true
        return false
    }
    
    override fun handle(request: Request) {
        println("do with Child02")
    }
}