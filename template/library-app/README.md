# Project Library

[![Clojars Project](https://img.shields.io/clojars/v/your-namespace/library-name.svg)](https://clojars.org/your-namespace/library-name)

## Documentation

#### This Library for what?

You can find the documentation here: [API](https://github.com/papachan/montalivet/blob/main/API.md).

Run the project's tests:

    $ clojure -T:build test

Generate a new Jar for clojars.

    $ clojure -T:build jar

Deploy the artefact to clojars -- needs `CLOJARS_USERNAME` and `CLOJARS_PASSWORD` environment variables:

    $ clojure -T:build deploy


## Manual installation

1. Check out the source code: [https://github.com/username/library-name](https://github.com/username/library-name)
2. Install it:

    $ clojure -T:build install


# Deploy notes for clojars

1. Update the version from the build.clj file and generate a new pom.xml - git commit it.
2. Generate a new jar clojure -X:deploy
3. CLOJARS_USERNAME='username' CLOJARS_PASSWORD='deploy_token' clojure -X:deploy

See [CONTRIBUTING.md](CONTRIBUTING.md) and the [CHANGELOG](CHANGELOG.md).

## License

Copyright &copy; 2026 Your-Username.

Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE).
