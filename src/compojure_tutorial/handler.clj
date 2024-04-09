(ns compojure-tutorial.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults]]

            [compojure-tutorial.pages.home-page :refer [home-page]]
            [compojure-tutorial.pages.presentation-page :refer [presentation-page]]
            [compojure-tutorial.pages.test-page :refer [test-page]]

            [compojure-tutorial.partials.repo-id-edit :refer [repo-id-edit]]
            [compojure-tutorial.partials.repo-id :refer [get-repo-id put-repo-id]]))

(defroutes app-routes
  ;; 
  ;; PAGES
  ;;

  (GET "/" [] (home-page))
  (GET "/test-page" [] (test-page))
  (GET "/presentation/:id" [id] (presentation-page id))

  ;; 
  ;; PARTIALS
  ;;

  (GET "/repo/:id" [id] (get-repo-id id))
  (PUT "/repo/:id" req (put-repo-id req))
  (GET "/repo/:id/edit" [id] (repo-id-edit id))

  ;; 
  ;; RESOURCES
  ;;

  (route/resources "/styles.css")
  (route/not-found "Not Found"))

(defn get-site-defaults
  []
  (assoc-in site-defaults [:security :anti-forgery] false))

(def app
  (wrap-defaults app-routes (get-site-defaults)))
