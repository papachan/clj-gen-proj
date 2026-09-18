(ns com.example.views
  (:require
   [clojure.string :as str]
   [re-frame.core :as re-frame]
   [com.example.events :as events]
   [com.example.myapp :as webapp]
   [com.example.subs :as subs]))


(defn- nav-link
  [{:keys [label route active?]}]
  [:li
   [:a
    {:href      "#"
     :on-click  #(re-frame/dispatch [::events/push-state {:route route}])
     :className (str (if active? "text-blue-600" "underline")
                     " hover:text-gray-300 text-3xl font-bold")}
    label]])

(defn navigation
  [page-selected]
  (let [auth @(re-frame/subscribe [::subs/auth])]
    [:header
     [:nav
      {:className "bg-gray-200 p-6"}
      [:div
       {:className "container mx-auto flex items-center justify-between gap-6"}
       (when auth
         [:ul
          {:className "flex gap-8"}
          [nav-link {:label   "home"
                     :route   ::webapp/home
                     :active? (= "home" page-selected)}]
          [nav-link {:label   "subpage 1"
                     :route   ::webapp/subpage
                     :active? (str/starts-with? page-selected "subpage")}]])
       (if auth
         [:div
          {:className "ml-auto flex items-center gap-4"}
          ;; after a page refresh only the cookie survives, so the username
          ;; may be unknown -- show it only when we have it
          (when-let [username (:username auth)]
            [:span
             {:className "text-gray-700"}
             "Signed in as "
             [:span {:className "font-semibold"} username]])
          [:button
           {:type      "button"
            :on-click  #(re-frame/dispatch [::events/logout])
            :className "rounded-md bg-gray-700 px-4 py-2 text-sm font-semibold text-white hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"}
           "Log out"]]
         [:button
          {:type      "button"
           :on-click  #(re-frame/dispatch [::events/push-state {:route ::webapp/login}])
           :className "ml-auto rounded-md bg-blue-600 px-4 py-2 text-sm font-semibold text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"}
          "Log in"])]]]))

(defn container
  [name]
  [:main
   {:className "container mx-auto mt-10"}
   [:div
    {:className "flex justify-center items-center h-screen"}
    [:div
     {:className "bg-white p-10 rounded-lg shadow-lg"}
     [:h1
      {:className "text-2xl font-bold mb-4"}
      name]
     [:p
      {:className "text-gray-700"}
      "Lorem ipsum"]]]])

(defn- text-field
  [{:keys [id label type value placeholder auto-complete disabled?]}]
  [:div
   [:label
    {:htmlFor   id
     :className "block text-sm font-medium text-gray-700"}
    label]
   [:input
    {:id           id
     :name         id
     :type         type
     :value        value
     :placeholder  placeholder
     :autoComplete auto-complete
     :disabled     disabled?
     :on-change    #(re-frame/dispatch-sync
                     [::events/set-login-field (keyword id) (.. % -target -value)])
     :className    (str "mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 "
                        "text-gray-900 placeholder-gray-400 shadow-sm "
                        "focus:border-blue-500 focus:outline-none focus:ring-1 focus:ring-blue-500 "
                        "disabled:cursor-not-allowed disabled:bg-gray-100")}]])

(defn login-form
  []
  (let [{:keys [username password]} @(re-frame/subscribe [::subs/login-form])
        error       @(re-frame/subscribe [::subs/login-error])
        submitting? @(re-frame/subscribe [::subs/login-submitting?])]
    [:form
     {:className "space-y-5"
      :on-submit (fn [e]
                   (.preventDefault e)
                   (re-frame/dispatch [::events/login-submit]))}
     (when error
       [:div
        {:role      "alert"
         :className "rounded-md border border-red-300 bg-red-50 px-4 py-3 text-sm text-red-700"}
        error])
     [text-field {:id            "username"
                  :label         "Username"
                  :type          "text"
                  :value         username
                  :placeholder   "your username"
                  :auto-complete "username"
                  :disabled?     submitting?}]
     [text-field {:id            "password"
                  :label         "Password"
                  :type          "password"
                  :value         password
                  :placeholder   "••••••••"
                  :auto-complete "current-password"
                  :disabled?     submitting?}]
     [:button
      {:type      "submit"
       :disabled  submitting?
       :className (str "flex w-full justify-center rounded-md bg-blue-600 px-4 py-2 "
                       "text-sm font-semibold text-white shadow-sm hover:bg-blue-700 "
                       "focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 "
                       "disabled:cursor-not-allowed disabled:bg-blue-300")}
      (if submitting? "Signing in..." "Sign in")]]))

(defn login
  []
  [:div
   {:className "min-h-screen bg-gray-100"}
   [navigation "login"]
   [:main
    {:className "container mx-auto px-4 py-16"}
    [:div
     {:className "mx-auto w-full max-w-md rounded-lg bg-white p-8 shadow-lg"}
     [:h1
      {:className "text-2xl font-bold text-gray-900"}
      "Sign in"]
     [:p
      {:className "mt-1 mb-6 text-sm text-gray-500"}
      "Use your account to continue."]
     [login-form]]]])

(defn home
  []
  [:div
   {:className "bg-gray-100"}
   (navigation "home")
   (container "home")])

(defn subpage
  []
  [:div
   {:className "bg-gray-100"}
   (navigation "subpage 1")
   (container "subpage 1")])
