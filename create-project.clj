(ns something
  (:require
   [clojure.string :as str]
   [babashka.fs :as fs]
   [clojure.java.shell :as shell]
   [clojure.java.io :as io]))


(defn ns->path [s]
  (-> s
      (str/replace "-" "_")
      (str/replace "." "/")))

(defn die [& message]
  (binding [*out* *err*]
    (apply println message)
    (System/exit 1)))

(defn -main [& [project-ns project-name]]
  (when (or (nil? project-ns)
            (nil? project-name))
    (die "Add an argument after calling" (-> *file*
                                             (str/split #"\\")
                                             last)))
  (let [dir (io/file project-name)]
    (io/make-parents (io/file dir "src"))
    (fs/create-dirs (io/file dir (str "src/" (ns->path project-ns))))
    (doseq [src (->> (io/file "template")
                     (file-seq)
                     (filter #(.isFile %)))
            :let [relative (-> (.getPath src)
                               (str/replace #"\\" "/")
                               (str/replace-first #".*?template/" "")
                               (str/replace "com/example" (ns->path project-ns)))
                  dest (io/file dir relative)]]
      (spit dest
            (-> src
                slurp
                (str/replace "com.example" project-ns))))))

(apply -main *command-line-args*)
