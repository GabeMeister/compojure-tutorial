(ns compojure-tutorial.pages.presentation-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.db.repos :refer [get-repo-data get-repo-data-part]]
            [clojure.data.json :as json]))

(defn presentation-page
  [id slide part]
  (let [repo-data (get-repo-data-part id part)]
    (templ
     (page-wrapper [:div
                    {:class "p-6"}
                    [:pre id]
                    [:pre slide]
                    [:pre part]
                    [:pre (json/write-str repo-data :indent 2)]]))))
