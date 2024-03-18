(ns compojure-tutorial.pages.test-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.components.button :refer [button]]))

(defn test-page
  []
  (templ (page-wrapper [:div
                        (button {:class "w-96"}
                                "HI THERE")])))
