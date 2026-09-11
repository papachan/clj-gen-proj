;;; Directory Local Variables            -*- no-byte-compile: t -*-
;;; For more information see (info "(emacs) Directory Variables")

((clojure-mode . ((cider-clojure-cli-aliases . ":web")
                  (cider-preferred-build-tool . clojure-cli)
                  (cider-custom-cljs-repl-init-form     . "(user/cljs-repl)")
                  (cider-default-cljs-repl              . custom)
                  (cider-redirect-server-output-to-repl . t)
                  (cider-repl-display-help-banner       . nil)
                  (clojure-toplevel-inside-comment-form . t))))
