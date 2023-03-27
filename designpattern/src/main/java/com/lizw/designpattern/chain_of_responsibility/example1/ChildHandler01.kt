package com.lizw.designpattern.chain_of_responsibility.example1

class ChildHandler01 : Handler() {
    override fun handleRequest(request: String) {
        if ("ChildHandler01" == request) {
            println("do with ChildHandler01")
        } else {
            if (next == null) {
                println("ChildHandler01 no next")
            } else {
                next?.handleRequest(request)
            }
        }
    }
}