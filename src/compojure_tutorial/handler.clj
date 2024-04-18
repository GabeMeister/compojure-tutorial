(ns compojure-tutorial.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults]]
            [clojure.pprint :refer [pprint]]

            [compojure-tutorial.utils.templates :refer [templ]]

            [compojure-tutorial.pages.home-page :refer [home-page]]
            [compojure-tutorial.pages.presentation-page :refer [presentation-page]]
            [compojure-tutorial.pages.test-page :refer [test-page test-page-2]]

            [compojure-tutorial.partials.repo-id-edit :refer [repo-id-edit]]
            [compojure-tutorial.partials.repo-id :refer [get-repo-id put-repo-id]]))

(defroutes app-routes
  ;; 
  ;; PAGES
  ;;

  (GET "/" [] (home-page))
  (GET "/test-page" [] (test-page))
  (GET "/test-page-2" [] (test-page-2))
  (GET "/presentation/:id"
    [id slide part]
    (presentation-page id slide part))

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
