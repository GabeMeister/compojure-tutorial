(ns compojure-tutorial.pages.home
  (:require [clojure.pprint :refer [pprint]]
            [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-all-repos]]))


(defn home
  []
  (let [projects (get-all-repos)]
    (templ
     [:html
      [:head
       [:script {:src "https://unpkg.com/htmx.org@1.9.10/dist/htmx.min.js"}]]
      [:body
       [:div
        [:ul
         (for [proj projects]
           [:div
            {:hx-target "this" :hx-swap "outerHTML"}
            [:span
             {:hx-get (str "/repo/" (:id proj) "/edit")}
             "Edit "]
            [:a
             {:href
              (str "https://yearendrecap.com/presentation/"
                   (:id proj))}
             (:name proj)]])]]]])))
