(ns compojure-tutorial.pages.test-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]))

(defn test-page
  []
  (templ (page-wrapper [:div
                        {:class "text-green-700 hover:text-blue-600 md:hover:text-orange-500 p-10"}
                        "HII"])))