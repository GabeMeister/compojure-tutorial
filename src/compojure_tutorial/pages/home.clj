(ns compojure-tutorial.pages.home
  (:require [clojure.pprint :refer [pprint]]
            [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.components.link :refer [link]]
            [compojure-tutorial.db.repos :refer [get-all-repos]]
            [compojure-tutorial.templates.repo-list-item :refer [repo-list-item]]))

(defn home
  []
  (let [projects (get-all-repos)]
    (templ
     [:html
      [:head
       [:script {:src "https://unpkg.com/htmx.org@1.9.10/dist/htmx.min.js"}]
       [:link {:rel "icon"
               :type "image/x-icon"
               :href "/img/favicon.ico"}]
       [:link {:rel "stylesheet"
               :href "/css/styles.css"}]
       [:title "Compojure Tutorial"]]
      [:body
       {:class "p-6"}
       [:div
        [:ul
         (for [proj projects]
           (repo-list-item proj))]]]])))
