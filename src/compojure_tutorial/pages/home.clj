(ns compojure-tutorial.pages.home
  (:require [hiccup2.core :as h]
            [clojure.pprint :refer [pprint]]
            [compojure-tutorial.db.repos :refer [get-all-repos]]))

(defn templ
  [content]
  (str (h/html content)))

(defn home
  []
  (let [projects (get-all-repos)]
    (templ
     [:div
      [:ul
       (for [proj projects]
         [:div
          [:a
           {:href
            (str "https://yearendrecap.com/presentation/"
                 (:id proj))}
           (:name proj)]])]])))
