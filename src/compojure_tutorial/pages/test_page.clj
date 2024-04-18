(ns compojure-tutorial.pages.test-page
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.templates.page-wrapper :refer [page-wrapper]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]
            [compojure-tutorial.components.button :refer [button]]))

(defn test-page
  []
  (templ (page-wrapper [:div
                        [:a {:hx-get "/test-page-2"
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "#loading-indicator"
                             :class "cursor-pointer"}
                         "Next"]
                        [:div {:class "h-6 w-6"}
                         (loading-spinner {:id "loading-indicator"
                                           :class "htmx-indicator w-6 h-6"})]
                        [:div "Content on page 1"]])))

(defn test-page-2
  []
  (templ (page-wrapper [:div
                        [:a {:hx-get "/test-page"
                             :hx-target "body"
                             :hx-swap "outerHTML"
                             :hx-push-url "true"
                             :hx-indicator "#loading-indicator"
                             :class "cursor-pointer"}
                         "Back"]
                        [:div {:class "h-6 w-6"}
                         (loading-spinner {:id "loading-indicator"
                                           :class "htmx-indicator w-6 h-6"})]
                        [:div "Content on page 2"]])))
