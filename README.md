### clj-gen-proj

This babashka script parse any template from template directory, after cloning this repo you will be able to use four differents templates: Basic, Backend, Basic-clojurescript-app and Fullstack.

- Starting by a basic project structure choose a `Basic` project
- Basic backend project ( api with swagger ) choose `Backend` project.
- A complete fullstack project ( clojure files and clojurescript files at the same level ) choose `Fullstack`.
- A dummy clojurescript app choose `basic-clojurescript-app`.
- A dummy project using integrant choose `integrant-app`.
- A dummy clojurescript project with flowstorm debugger `basic-shadow-flowstorm-app`.
- A dummy library project, choose `library`.

You can just run the script by using:

    bb create-project.clj fullstack some.namespace

List all the available templates by running this command:

    bb run list:templates
