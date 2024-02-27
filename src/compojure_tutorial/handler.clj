(ns compojure-tutorial.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure-tutorial.pages.home :refer [home]]))

(defroutes app-routes
  (GET "/" [] (home))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
