package com.hao.server;
public class Main {

    public static void main(String[] args) {

        // launch the server
        new TelnetServer(args.length == 0 ? null : args[0]).run();
    }
}
