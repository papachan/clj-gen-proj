# Contributing

Bug reports, fixes and documentation improvements are welcome. Please open an [issue](https://github.com/username/your-library/issues) first for anything bigger than a small fix.

## Development

You need a JDK (17+) and the [Clojure CLI](https://clojure.org/guides/install_clojure).

```sh
clojure -M:dev:test:runner                 # run the tests (Kaocha)
clojure -M:clj-kondo --lint src test       # lint
clojure -M:format-check                    # check formatting (format-fix to fix)
```

## Pull requests

* Use a feature branch and write clear commit messages.
* Add tests, and make sure the tests, linter and format check pass.
* Add an entry to the [CHANGELOG](CHANGELOG.md).
* By contributing, you agree that your work is licensed under the project [LICENSE](LICENSE).