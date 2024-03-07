(ns compojure-tutorial.partials.repo-id-edit
  (:require [compojure-tutorial.utils.templates :refer [templ]]
            [compojure-tutorial.db.repos :refer [get-repo-by-id]]
            [compojure-tutorial.components.loading-spinner :refer [loading-spinner]]))

(defn repo-id-edit
  [id]
  (let [repo-id (Integer/parseInt id)
        repo (get-repo-by-id repo-id)
        cancel-path (str "/repo/" repo-id)
        put-path (str "/repo/" id)
        name (:repos/name repo)
        loading-id (str "edit-repo-loading-" repo-id)]
    (templ
     [:form
      {:hx-put put-path
       :hx-indicator (str "#" loading-id)
       :hx-target "this"
       :hx-swap "outerHTML"}
      [:div
       [:label "Repo Name"]
       [:br]
       [:input {:type "text"
                :name "repoName"
                :value name}]]
      [:div
       [:button "Save"]
       [:button {:hx-get cancel-path :hx-indicator (str "#" loading-id)} "Cancel"]]
      [:div
       (loading-spinner {:id loading-id})]])))