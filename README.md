# compojure-tutorial

FIXME

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Deploying

    lein ring uberjar
    scp ./target/compojure-tutorial-0.1.0-SNAPSHOT-standalone.jar root@<ip_addr>:/root
    scp ./env.json root@<ip_addr>:/root
    ssh root@<ip_addr>
    ./jdk-17.0.10/bin/java -jar compojure-tutorial-0.1.0-SNAPSHOT-standalone.jar

    *Browse to http://<ip_addr>:3000

## License

Copyright © 2024 FIXME
