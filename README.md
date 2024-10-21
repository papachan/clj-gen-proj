### clj-gen-proj

This babashka script parse any template from template directory, after cloning this repo you will be able to use four differents templates: Basic, Backend, Basic-clojurescript-app and Fullstack.

- Starting by a basic project structure choose a `Basic` project
- Basic backend project ( api with swagger ) choose `Backend` project.
- A complete fullstack project ( clojure files and clojurescript files at the same level ) choose `Fullstack`.
- A dummy clojurescript app choose `basic-clojurescript-app`.
- A dummy project using integrant choose `integrant-app`.
- A dummy clojurescript project with flowstorm debugger `basic-shadow-flowstorm-app`.

You can just run the script by using:

    bb create-project.clj fullstack some.namespace

If you want to list all the availables templates, you can create a tiny script:

```
#!/usr/bin/env bb
(ns main
  (:require
   [clojure.string :as str]
   [babashka.fs :as fs]))

(->> (fs/file "template")
     (fs/list-dir)
     (mapv #(-> (str %)
                (str/split #"/")
                second))
     (run! println))
```
