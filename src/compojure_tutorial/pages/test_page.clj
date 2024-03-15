(ns compojure-tutorial.pages.test-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.components.button :refer [button]]))

(defn test-page
  []
  (templ (page-wrapper [:div
                        (button {:color "red"
                                 :class "w-96 h-96 rounded-xl text-green-500"}
                                "HI THERE")])))