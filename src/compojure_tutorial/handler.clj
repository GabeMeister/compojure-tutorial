(ns compojure-tutorial.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults]]
            [compojure-tutorial.pages.home :refer [home]]
            [compojure-tutorial.partials.repo-id-edit :refer [repo-id-edit]]
            [compojure-tutorial.partials.repo-id :refer [get-repo-id put-repo-id]]))

(defroutes app-routes
  (GET "/" [] (home))
  (GET "/repo/:id" [id] (get-repo-id id))
  (PUT "/repo/:id" req (put-repo-id req))
  (GET "/repo/:id/edit" [id] (repo-id-edit id))
  (route/resources "/styles.css")
  (route/not-found "Not Found"))

(defn get-site-defaults
  []
  (assoc-in site-defaults [:security :anti-forgery] false))

(def app
  (wrap-defaults app-routes (get-site-defaults)))
