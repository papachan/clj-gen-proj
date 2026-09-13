### clojure project generator

This babashka script parse any template from template directory, after cloning this repo you will be able to use four differents templates: Basic, Backend, Basic-clojurescript-app and Fullstack.

| Template | Description |
|---|---|
| basic | Bare deps.edn + one app.clj, :run-m/:run-x aliases |
| backend | Ring/Jetty + reitit + mount + aero |
| integrant-app | Same shape but Integrant + reitit |
| fullstack | Backend (reitit+malli+hiccup+swagger-ui) + re-frame frontend + Tailwind |
| basic-clojurescript-app | shadow-cljs + reagent |
| basic-shadow-flowstorm-app | reagent + FlowStorm-instrumented cljs compiler, re-frame-10x |
| shadow-uix-app | shadow-cljs + UIx (React hooks) + Tailwind |
| fulcro-app | Fulcro 3 client + fulcro-inspect |
| clojurescript-quil-app | shadow-cljs + Quil sketch |
| library | tools.build + deps-deploy + codox + test-runner |
| clojurescript-quil-app | shadow-cljs with quil sketch |

You can just run the script by using:

    bb create-project.clj fullstack some.namespace

List all the available templates by running this command:

    bb run list:templates
