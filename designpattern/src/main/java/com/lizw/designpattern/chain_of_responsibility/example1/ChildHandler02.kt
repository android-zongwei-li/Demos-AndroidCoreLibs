package com.lizw.designpattern.chain_of_responsibility.example1

class ChildHandler02 : Handler() {
    override fun handleRequest(request: String) {
        if ("ChildHandler02" == request) {
            println("do with ChildHandler02")
        } else {
            if (next == null) {
                println("ChildHandler02 no next")
            } else {
                next?.handleRequest(request)
            }
        }
    }
}