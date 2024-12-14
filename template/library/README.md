# Project Library


[![Clojars Project](https://img.shields.io/clojars/v/your-namespace/library-name.svg)](https://clojars.org/your-namespace/library-name)


#### This Library for what?

Build a deployable jar of this library:

    $ clojure -T:build jar

Install it locally:

    $ clojure -X:install

Run the project's tests:

    $ clojure -T:build test

Generate a new Jar for clojars.

    $ clojure -T:build jar

Deploy the artefact to clojars.

    $ clojure -T:build deploy


Deploy it to Clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment variables:

    $ clojure -X:deploy

# Deploy notes for clojars

1. Update the version of the maven package in pom.xml - and git commit it.
2. Build the jar via `make`
3. CLOJARS_USERNAME='' CLOJARS_PASSWORD='deploy_token' clojure -A:deploy

## License

Copyright &copy; 2024 Your-Username.

Licensed under the Eclipse Public License [EPL 1.0](LICENSE).
