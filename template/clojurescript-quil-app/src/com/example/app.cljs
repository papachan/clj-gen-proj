(ns com.example.app
  (:require
   [quil.core :as q :include-macros true]
   [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb)
  {:color  0
   :angle  0
   :paused false})

(defn update-state [state]
  (if (:paused state)
    state
    (assoc state
           :color (mod (+ (:color state) 0.35) 255)
           :angle (+ (:angle state) 0.05))))

(defn mouse-pressed [state {:keys [x y]}]
  (if (and (<= 0 x (q/width))
           (<= 0 y (q/height)))
    (update state :paused not)
    state))

(defn draw-state [state]
  (q/background 240)
  (q/fill (:color state) 255 255)
  (let [angle (:angle state)
        x     (* 150 (q/cos angle))
        y     (* 150 (q/sin angle))]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (q/ellipse x y 100 100))))

(q/defsketch sketch-app
  :host "app"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :mouse-pressed mouse-pressed
  :middleware [m/fun-mode])

(defn render []
  ;; render is called on page load and after shadow-cljs hot-reloads code.
  ;; This function is called explicitly by init and implicitly by re-render.
  (.log js/console "render!"))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (.log js/console "stop"))

(defn ^:export re-render []
  ;; after shadow-cljs hot-reloads code.
  ;; This function is called implicitly by its annotation.
  (.log js/console "reload")
  (render))

(defn ^:export init []
  (.log js/console "start")
  (render))
