(ns compojure-tutorial.pages.home
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.db.repos :refer [get-all-repos]]
            [compojure-tutorial.templates.repo-list-item :refer [repo-list-item]]))

(defn home
  []
  (let [projects (get-all-repos)]
    (templ
     (page-wrapper [:div
                    {:class "p-6"}
                    [:ul
                     (for [proj projects]
                       (repo-list-item proj))]]))))
