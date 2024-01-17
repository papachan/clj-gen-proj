#!/usr/bin/env bb
(ns create-project
  (:require
   [clojure.string :as str]
   [babashka.fs :as fs]
   [clojure.java.shell :as shell]
   [clojure.java.io :as io]))

(def templates #{"basic" "backend" "fullstack"})

(defn ns->path [s]
  (-> s
      (str/replace "-" "_")
      (str/replace "." "/")))

(defn die [& message]
  (binding [*out* *err*]
    (apply println message)
    (System/exit 1)))

(defn -main [& [template-name project-ns]]
  (when (or (nil? template-name)
            (nil? project-ns))
    (die "Use two arguments after calling" (-> *file*
                                               (str/split #"\\")
                                               last)
         "- eg: bb create-project.clj [template-type] [your-namespace]"))
  (when (not (contains? templates (str/lower-case template-name)))
    (die "Please use a valid template, here are the valid templates: " (apply str (interpose " or " (map #(str "`" % "`") templates)))))
  (let [project-name "generated"
        template-dir (str "template/" (str/lower-case template-name) "/")
        dir          (io/file project-name)]
    (io/make-parents (io/file dir "src"))
    (fs/create-dirs (io/file dir (str "src/" (ns->path project-ns))))
    (doseq [src (->> (io/file template-dir)
                     (file-seq)
                     (filter #(.isFile %)))
            :let [relative (-> (.getPath src)
                               (str/replace #"\\" "/")
                               (str/replace-first (re-pattern (str ".*?" template-dir)) "")
                               (str/replace "com/example" (ns->path project-ns)))
                  dest (io/file dir relative)]]
      (when (str/includes? (str dest) "env/dev")
        (fs/create-dirs (io/file dir "env/dev")))
      (spit dest
            (-> src
                slurp
                (str/replace "com.example" project-ns))))))

(apply -main *command-line-args*)
