#!/usr/bin/env bb
(ns create-project
  (:require
   [clojure.string :as str]
   [babashka.fs :as fs]
   [clojure.java.shell :as shell]
   [clojure.java.io :as io]))


(def templates
  (->> (fs/file "template")
       (fs/list-dir)
       (map #(-> (str %)
                 (str/split #"/|\\")
                 second))
       (into #{})))

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
    (die "Please use a valid template, here are the valid templates:" (str/join " or " (map #(str "`" % "`") templates))))
  (let [project-name "generated"
        template-dir (str "template/" (str/lower-case template-name) "/")
        dir          (io/file project-name)]
    (io/make-parents (io/file dir "src"))
    (if (contains? #{"fullstack"} template-name)
      (doseq [path ["src/backend/" "src/frontend/"]]
        (fs/create-dirs (io/file dir (str path (ns->path project-ns)))))
      (fs/create-dirs (io/file dir (str "src/" (ns->path project-ns)))))
    (doseq [src (->> (io/file template-dir)
                     (file-seq)
                     (filter #(.isFile %)))
            :let [relative (-> (.getPath src)
                               (str/replace #"\\" "/")
                               (str/replace-first (re-pattern (str ".*?" template-dir)) "")
                               (str/replace "com/example" (ns->path project-ns)))
                  dest (io/file dir relative)
                  path (.getPath (.getParentFile dest))]]
      (fs/create-dirs path)
      (spit dest
            (-> src
                slurp
                (str/replace "com.example" project-ns))))))

(apply -main *command-line-args*)
