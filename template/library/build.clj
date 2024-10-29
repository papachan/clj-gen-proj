(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]
            [clojure.string :as str]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'org.clojars.your-username/your-library)
(def lib-name 'your-library)
(def version (->> "VERSION.txt" slurp str/trim))
(def main 'com.example.main)
(def class-dir "target/classes")

(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean "Clean the target classes folder" [opts]
  (b/delete {:path "target"}))

(defn test "Run all the tests." [opts]
  (let [basis    (b/create-basis {:aliases [:test]})
        cmds     (b/java-command
                  {:basis     @basis
                   :main      'clojure.main
                   :main-args ["-m" "cognitect.test-runner"]})
        {:keys [exit]} (b/process cmds)]
    (when-not (zero? exit) (throw (ex-info "Tests failed" {}))))
  opts)

(defn sync-pom [opts]
  (b/delete {:path "pom.xml"})
  (b/copy-file {:src    (str class-dir
                             "/META-INF/maven/org.clojars.your-username/your-library/pom.xml")
                :target "pom.xml"})
  opts)

(defn- jar-opts [opts]
  (let [github-url "https://github.com/your-username/your-library"
        scm-url    "git@github.com:your-username/your-library.git"]
    (assoc opts
           :class-dir class-dir
           :lib       lib
           :version   version
           :basis     @basis
           :src-dirs  ["src"]
           :scm       {:url                 github-url
                       :connection          (str "scm:git:" github-url ".git")
                       :developerConnection (str "scm:git:ssh:" scm-url)
                       :tag                 (str "v" version)}
           :pom-data  [[:licenses
                        [:license
                         [:name "Eclipse Public License 1.0"]
                         [:url  "https://www.eclipse.org/org/documents/epl-v10.html"]]]])))

(defn jar "Run a jar for deploy" [opts]
  (clean nil)
  (b/write-pom (jar-opts {}))
  (b/copy-dir {:src-dirs   ["resources" "src"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file  (format "target/%s.jar" lib-name)})
  (sync-pom nil)
  opts)

(defn deploy "Deploy the JAR to Clojars." [opts]
  (let [{:keys [jar-file] :as opts} (jar-opts opts)]
    (dd/deploy {:installer :remote :artifact (b/resolve-path jar-file)
                :pom-file (b/pom-path (select-keys opts [:lib :class-dir]))}))
  opts)