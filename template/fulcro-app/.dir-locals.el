;;; Directory Local Variables            -*- no-byte-compile: t -*-
;;; For more information see (info "(emacs) Directory Variables")

((nil . ((cider-clojure-cli-aliases . ":frontend")
         (cider-default-cljs-repl . custom)
         (cider-redirect-server-output-to-repl . t)
         (cider-preferred-build-tool . clojure-cli)
         (eval . (progn
                   (make-variable-buffer-local 'cider-jack-in-nrepl-middlewares)
                   (add-to-list 'cider-jack-in-nrepl-middlewares
                                "cider.nrepl/cider-middleware")
                   (add-to-list 'cider-jack-in-nrepl-middlewares
                                "shadow.cljs.devtools.server.nrepl/middleware"))))
