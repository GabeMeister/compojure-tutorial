(ns compojure-tutorial.pages.presentation-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.db.repos :refer [get-repo-data]]
            [clojure.data.json :as json]))

(defn presentation-page
  [id]
  (let [repo-data (get-repo-data id)]
    (templ
     (page-wrapper [:div
                    {:class "p-6"}
                    [:pre (json/write-str repo-data :indent 2)]]))))
