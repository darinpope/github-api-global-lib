#!/usr/bin/groovy

def call(name) {
    // you can call any valid step functions from your code, just like you can from Pipeline scripts
    sh "echo Hello world, ${name}"
}
