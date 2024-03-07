(ns compojure-tutorial.templates.repo-list-item
  (:require [compojure-tutorial.components.link :refer [link]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]))

(defn repo-list-item
  [repo]
  (let [id (:repos/id repo)
        edit-link (str "/repo/" id "/edit")
        loader-id (str "edit-spinner-" id)
        external-link (str "https://yearendrecap.com/presentation/"
                           (:repos/id repo))
        repo-name (:repos/name repo)]
    [:div
     {:hx-target "this" :hx-swap "outerHTML"}
     [:h1.text-2xl.flex.items-center.gabe-heading
      [:span.mr-2 repo-name]
      (loading-spinner {:id loader-id})
      [:span.text-lg.ml-2
       (link {:hx-get edit-link :hx-indicator (str "#" loader-id)} "Edit")]
      [:span.text-lg.ml-2
       (link
        {:href external-link
         :target "_blank"}
        "Go ↗")]]
     [:style "
              .gabe-heading {
                color: red;
              }
              "]]))